package sg.edu.iss.inventory.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.exception.MismatchSupplierIdException;
import sg.edu.iss.inventory.model.Order;
import sg.edu.iss.inventory.model.OrderDetail;
import sg.edu.iss.inventory.model.OrderDetailId;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.ProductSupplier;
import sg.edu.iss.inventory.model.Supplier;
import sg.edu.iss.inventory.repository.SupplierRepository;
import sg.edu.iss.inventory.service.ReportService;

@RequestMapping(value = "report")
@Controller
public class ReportController {

	@Autowired
	ReportService reportService;
	@Resource
	SupplierRepository sRepo;

	@RequestMapping(value = "/generate", method = RequestMethod.GET)

	public ModelAndView reorderReportPageDisplay(Model model, HttpSession session) {

		ArrayList<Supplier> sList = (ArrayList<Supplier>) sRepo.findAll();
		session.setAttribute("SLIST", sList);

		model.addAttribute("supplier", new Supplier());
		ModelAndView mav = new ModelAndView("report");
		return mav;
	}

	@RequestMapping(value = "/supplier", method = RequestMethod.POST)
	public ModelAndView reorderReportPage(@ModelAttribute Supplier supplierR, HttpSession session,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("report");

		int supplierId = supplierR.getSupplierId();

		// generate supplier ID/Name for Report
		Supplier supplier = reportService.findSupplierbySupplierId(supplierId);
		if (supplier != null) 
		{
			//supplier ID
			mav.addObject("supplierId", supplierId);

			ArrayList<Double> unitPrice = new ArrayList<Double>();
			ArrayList<Integer> ordQty = new ArrayList<Integer>();
			ArrayList<Double> price = new ArrayList<Double>();
			double total = 0;

			//orderID
			ArrayList<Order> orderList = (ArrayList<Order>) reportService.findOrderBySupplierId(supplier);
			
			if (!orderList.isEmpty()) {
				
				//orderDetail
				ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
				for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
					Order order = (Order) iterator.next();
					orderDetailList = reportService.findOrderDetailByOrderId(order.getOrderId());
				}
				
				mav.addObject("orderDetailList", orderDetailList);

				//orderDetailId
				ArrayList<OrderDetailId> orderDetailIdList = new ArrayList<OrderDetailId>();

				for (Iterator<OrderDetail> iterator = orderDetailList.iterator(); iterator.hasNext();) {
					OrderDetail orderDetail = (OrderDetail) iterator.next();
					ordQty.add(orderDetail.getOrderQty());
					orderDetailIdList.add(orderDetail.getId());
				}
				mav.addObject("orderDetailIdList", orderDetailIdList);

				//partNo
				ArrayList<String> partNoList = new ArrayList<String>();
				for (Iterator<OrderDetailId> iterator = orderDetailIdList.iterator(); iterator.hasNext();) {
					OrderDetailId orderDetailId = (OrderDetailId) iterator.next();	
					partNoList.add(orderDetailId.getPartNo());
				}
				
				// Product (Reorder Point, AvailQty)
				ArrayList<Product> productList = new ArrayList<Product>();
				for (Iterator<String> iterator = partNoList.iterator(); iterator.hasNext();) {
					String partNo = (String) iterator.next();	
					productList.add(reportService.findProductByPartNo(partNo));
				}
				mav.addObject("productList", productList);
				
				// ProductSupplier(UnitPrice)
				ArrayList<ProductSupplier> pSupplierList = new ArrayList<ProductSupplier>();
				for (Iterator<String> iterator = partNoList.iterator(); iterator.hasNext();) {
					String partNo = (String) iterator.next();	
					pSupplierList.add(reportService.findProductSupplierByPNoSId(partNo, supplierId));
				}
				mav.addObject("pSupplierList", pSupplierList);
				
				for (Iterator<ProductSupplier> iterator = pSupplierList.iterator(); iterator.hasNext();) {
					ProductSupplier productSupplier = (ProductSupplier) iterator.next();
					unitPrice.add(productSupplier.getUnitPrice());
				}

				for (int g = 0; g < unitPrice.size(); g++) {
					price.add(unitPrice.get(g) * ordQty.get(g));
				}
				mav.addObject("price", price);

				for (int h = 0; h < price.size(); h++) {
					total += price.get(h);
				}

				mav.addObject("total", total);
			}
			else {

				try {
					throw new MismatchSupplierIdException("There is no report available for this supplier ID!");
				}
				catch (MismatchSupplierIdException e) {
					request.setAttribute("errorMessage", e.getMessage());
				}
			}

		}
		return mav;
	}
}
