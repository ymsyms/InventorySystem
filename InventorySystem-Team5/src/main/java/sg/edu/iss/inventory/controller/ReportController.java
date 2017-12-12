package sg.edu.iss.inventory.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.model.Order;
import sg.edu.iss.inventory.model.OrderDetail;
import sg.edu.iss.inventory.model.OrderDetailId;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.ProductSupplier;
import sg.edu.iss.inventory.model.ProductSupplierId;
import sg.edu.iss.inventory.model.Supplier;
import sg.edu.iss.inventory.service.ReportService;

@RequestMapping(value = "report")
@Controller
public class ReportController {

	@Autowired
	ReportService reportService;

	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView reorderReportPageDisplay(Model model) {
		model.addAttribute("supplier", new Supplier());
		ModelAndView mav = new ModelAndView("report");
		return mav;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/supplier", method = RequestMethod.POST)
	public ModelAndView reorderReportPage(@ModelAttribute Supplier supplierR) {

		int supplierId = supplierR.getSupplierId();
		ModelAndView mav = new ModelAndView("report");

		// generate supplier ID/Name for Report
		Supplier supplier = reportService.findSupplierbySupplierId(supplierId);
		if (supplier != null) {
			ArrayList<Double> unitPrice = new ArrayList<Double>();
			ArrayList<Integer> ordQty = new ArrayList<Integer>();
			ArrayList<Double> price= new ArrayList<Double>();
			double total = 0;

			// Generate orderID to get OrderDetail
			ArrayList<Order> orderList = (ArrayList<Order>) reportService.findOrderBySupplierId(supplier);

			// Get Order Detail List
			ArrayList<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
			for (Iterator<Order> iterator = orderList.iterator(); iterator.hasNext();) {
				Order order = (Order) iterator.next();
				orderDetailList = reportService.findOrderDetailByOrderId(order.getOrderId());
			}
			mav.addObject("orderDetailList", orderDetailList);

			ArrayList<OrderDetailId> orderDetailIdList = new ArrayList<OrderDetailId>();

			for (Iterator<OrderDetail> iterator = orderDetailList.iterator(); iterator.hasNext();) {
				OrderDetail orderDetail = (OrderDetail) iterator.next();
				ordQty.add(orderDetail.getOrderQty());
				orderDetailIdList.add(orderDetail.getId());
			}

			mav.addObject("orderDetailIdList", orderDetailIdList);

			// Get Product Supplier List
			ArrayList<ProductSupplier> pSupplierList = reportService.findProductSupplierByProductId(supplierId);
			mav.addObject("pSupplierList", pSupplierList);

			// Get Product List
			ArrayList<Product> productList = new ArrayList<Product>();

			for (Iterator<ProductSupplier> iterator = pSupplierList.iterator(); iterator.hasNext();) {
				ProductSupplier productSupplier = (ProductSupplier) iterator.next();
				ProductSupplierId productSupplierId = productSupplier.getId();
				unitPrice.add(productSupplier.getUnitPrice());
				String partNo = productSupplierId.getPartNo();
				productList.add(reportService.findProductByPartNo(partNo));
			}
			mav.addObject("productList", productList);

			for (int g = 0; g < unitPrice.size(); g++) {
				price.add(unitPrice.get(g) * ordQty.get(g));				
			}
			mav.addObject("price", price);
			
			for (int h = 0; h < price.size(); h++) {
				total += price.get(h);
			}
			
			mav.addObject("total", total);
		}
		return mav;
	}

}
