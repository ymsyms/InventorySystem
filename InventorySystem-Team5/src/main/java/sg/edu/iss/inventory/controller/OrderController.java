package sg.edu.iss.inventory.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.inventory.exception.MismatchPartNumException;
import sg.edu.iss.inventory.model.OrderCartItem;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.OrderService;
import sg.edu.iss.inventory.service.ProductService;
import sg.edu.iss.inventory.service.UserLoginService;
import sg.edu.iss.inventory.service.UtilitiesService;

@RequestMapping(value = "/admin/order")
@Controller
public class OrderController {

	@Autowired
	UserLoginService userLoginService;
	@Autowired
	private OrderService oService;
	@Autowired
	private ProductService pService;

	//
	//
	//
	//
	// list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session, HttpServletRequest request) {

		// setOrderList
		ArrayList<OrderCartItem> oList = (ArrayList<OrderCartItem>) session.getAttribute("OLIST");
		if (oList == null) {
			try {
				oList = oService.createToOrderList();
				session.setAttribute("OLIST", oList);
			} catch (MismatchPartNumException e) {
				request.setAttribute("errorMessage", e.getMessage());
			}
		}

		// setProductList
		ArrayList<Product> pList = (ArrayList<Product>) session.getAttribute("PLIST");
		if (pList == null) {
			pList = pService.findAllProduct();
			session.setAttribute("PLIST", pList);
		}

		return new ModelAndView("order-list");
	}

	//
	//
	//
	//
	// add
	@RequestMapping(value = "/add")
	public ModelAndView add(HttpSession session, HttpServletRequest request) {

		// updateOrderList
		ArrayList<OrderCartItem> oList = (ArrayList<OrderCartItem>) session.getAttribute("OLIST");

		for (int i = 0; i < oList.size(); i++) {
			// orderQty
			if (UtilitiesService.isInt(request.getParameter("orderQty" + i))) {
				int oQty = Integer.parseInt(request.getParameter("orderQty" + i));
				oList.get(i).setQuantity(oQty);
			}
			// selectedPSId
			int sPSId = Integer.parseInt(request.getParameter("productSupplier" + i));
			oList.get(i).setSelectedSupplierId(sPSId);
		}

		// add part to OrderList
		try {
			String addPartNo = (String) request.getParameter("addPartNo");
			oService.addListItem(addPartNo, oList);
		} catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return new ModelAndView("order-list");
	}

	//
	//
	//
	//
	// reset
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView reset(HttpSession session, HttpServletRequest request) {

		// setOrderList
		ArrayList<OrderCartItem> oList = (ArrayList<OrderCartItem>) session.getAttribute("OLIST");
		try {
			oList = oService.createToOrderList();
			session.setAttribute("OLIST", oList);
		} catch (MismatchPartNumException e) {
			request.setAttribute("errorMessage", e.getMessage());
		}

		return new ModelAndView("order-list");
	}

	//
	//
	//
	//
	// submit
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ModelAndView submitOrder(HttpSession session, HttpServletRequest request) {
		boolean proceed = true;
		// updateOrderList
		ArrayList<OrderCartItem> oList = (ArrayList<OrderCartItem>) session.getAttribute("OLIST");
		for (int i = 0; i < oList.size(); i++) {
			// orderQty
			if (!UtilitiesService.isInt(request.getParameter("orderQty" + i))) {
				proceed = false;
			} else {
				int oQty = Integer.parseInt(request.getParameter("orderQty" + i));
				oList.get(i).setQuantity(oQty);
				// selectedPSId
				int sPSId = Integer.parseInt(request.getParameter("productSupplier" + i));
				oList.get(i).setSelectedSupplierId(sPSId);
			}
		}
		if (proceed) {
			// createOrder
			User u = userLoginService.getUserDetails();
			oService.logOrders(u, oList);

			// resetOrder
			session.setAttribute("OLIST", null);
			return new ModelAndView("order-summary");
		} else {
			request.setAttribute("errorMessage", "Order Quantity Invalid");
			return new ModelAndView("order-list");
		}
	}

	//
	//
	//
	//
	// delete
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@PathVariable String id, final RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {

		// updateOrderList
		ArrayList<OrderCartItem> oList = (ArrayList<OrderCartItem>) session.getAttribute("OLIST");
		oService.removeListItem(id, oList);
		return new ModelAndView("redirect:/admin/order/list");
	}

}
