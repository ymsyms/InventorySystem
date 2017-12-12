package sg.edu.iss.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.service.ProductService;
import sg.edu.iss.inventory.service.UserService;
import sg.edu.iss.inventory.validator.LoginValidator;

@Controller
public class CommonController {

	@Autowired
	UserService userService;
	@Autowired
	ProductService productService;	
	@Autowired
	private LoginValidator lValidator;
	
	@RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
	public ModelAndView productListPage(Model model, @RequestParam(required = false) Integer page,@ModelAttribute("product") Product product) {
		model.addAttribute("product",new Product());
		ModelAndView mav = new ModelAndView("product-list");
		List<Product> productList = (List<Product>) productService.findAllProduct();
		PagedListHolder<Product> pagedListHolder = new PagedListHolder<>(productList);
		pagedListHolder.setPageSize(5);
		mav.addObject("maxPages", pagedListHolder.getPageCount());
		if (page == null || page < 1 || page > pagedListHolder.getPageCount())
			page = 1;

		mav.addObject("page", page);
		if (page == null || page < 1 || page > pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(0);
			mav.addObject("productList", pagedListHolder.getPageList());
		} else if (page <= pagedListHolder.getPageCount()) {
			pagedListHolder.setPage(page - 1);
			mav.addObject("productList", pagedListHolder.getPageList());
		}
		
		ArrayList<Product> carDealerList = (ArrayList<Product>)productService.searchAllCarDealer();
		mav.addObject("carDealerList", carDealerList);
		
		ArrayList<Product> partDescriptionList = (ArrayList<Product>)productService.searchAllPartDescription();
		mav.addObject("partDescriptionList", partDescriptionList);
		
		ArrayList<Product> colorList = (ArrayList<Product>)productService.searchAllColor();
		mav.addObject("colorList", colorList);
		
		return mav;
	}	
	

	@InitBinder("user")
	private void initUserBinder(WebDataBinder binder) {
		binder.addValidators(lValidator);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpSession session) {
		session.invalidate();
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;

	}
	
	// customize the error message
		private String getErrorMessage(HttpServletRequest request, String key) {

			Exception exception = (Exception) request.getSession().getAttribute(key);

			String error = "";
			if (exception instanceof BadCredentialsException) {
				error = "Invalid username and password!";
			} else if (exception instanceof LockedException) {
				error = exception.getMessage();
			} else {
				error = "Invalid username and password!";
			}

			return error;
		}

		// for 403 access denied page
		@RequestMapping(value = "/403", method = RequestMethod.GET)
		public ModelAndView accesssDenied() {

			ModelAndView model = new ModelAndView();

			// check if user is login
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				UserDetails userDetail = (UserDetails) auth.getPrincipal();
				System.out.println(userDetail);

				model.addObject("username", userDetail.getUsername());

			}

			model.setViewName("403");
			return model;

		}
}
