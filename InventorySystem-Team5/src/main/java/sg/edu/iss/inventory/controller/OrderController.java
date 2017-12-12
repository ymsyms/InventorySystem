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
import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.OrderService;
import sg.edu.iss.inventory.service.UserLoginService;

@RequestMapping(value = "/admin/order")
@Controller
public class OrderController {

	@Autowired
	private OrderService oService;
	@Autowired
	UserLoginService userLoginService;
	
	// list
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(HttpSession session) {
		retrieveOrderList(session);
		return new ModelAndView("order-list");
	}

	// add
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addOrderItem(HttpSession session, HttpServletRequest request) {

		ArrayList<OrderCartItem> orderList = retrieveOrderList(session);
		putNewDataToOrderList(request, orderList);
		try {
			String partNo = (String) request.getParameter("partNo");
			oService.addListItem(partNo, orderList);
		}
		catch (Exception e) {
			request.setAttribute("errorMessage", e.getMessage());
		}
		return new ModelAndView("order-list");
	}

	// reset
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView reset(HttpSession session, HttpServletRequest request) {
		session.setAttribute("ORDERLIST", null);
		retrieveOrderList(session);
		return new ModelAndView("order-list");
	}

	//
	// submit
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public ModelAndView submitOrder(HttpSession session, HttpServletRequest request) {
		ArrayList<OrderCartItem> orderList = retrieveOrderList(session);
		putNewDataToOrderList(request, orderList);

		User u = userLoginService.getUserDetails();

		
		oService.logOrders(u, orderList);

		orderList = null;
		retrieveOrderList(session);
		return new ModelAndView("order-summary");
	}

	//
	//
	//
	//
	// delete
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@PathVariable String id, final RedirectAttributes redirectAttributes,
			HttpSession session, HttpServletRequest request) {
		ArrayList<OrderCartItem> orderList = retrieveOrderList(session);
		oService.removeListItem(id, orderList);
		return new ModelAndView("redirect:/admin/order/list");
	}

	//
	//
	//
	//
	// retrieveOrderList
	private ArrayList<OrderCartItem> retrieveOrderList(HttpSession session) {
		ArrayList<OrderCartItem> orderList = (ArrayList<OrderCartItem>) session.getAttribute("ORDERLIST");
		if (orderList == null) {
			try {
				orderList = oService.createToOrderList();
			}
			catch (MismatchPartNumException e) {
				e.printStackTrace();
			}
			session.setAttribute("ORDERLIST", orderList);
		}
		return orderList;
	}

	//
	//
	//
	//
	// putNewDataToOrderList
	private void putNewDataToOrderList(HttpServletRequest request, ArrayList<OrderCartItem> orderList) {
		for (int i = 0; i < orderList.size(); i++) {
			int orderQty = Integer.parseInt(request.getParameter("orderQty" + i));
			orderList.get(i).setQuantity(orderQty);
			
			int selectedSupplierId = Integer.parseInt(request.getParameter("productSupplier" + i));
			orderList.get(i).setSelectedSupplierId(selectedSupplierId);
		}
	}

}
