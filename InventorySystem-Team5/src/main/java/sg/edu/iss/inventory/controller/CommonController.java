package sg.edu.iss.inventory.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.service.UserService;
import sg.edu.iss.inventory.validator.LoginValidator;

@Controller
public class CommonController {

	@Autowired
	UserService userService;
	@Autowired
	private LoginValidator lValidator;

	@InitBinder("user")
	private void initUserBinder(WebDataBinder binder) {
		binder.addValidators(lValidator);
	}

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String logic(Model model) {
//		model.addAttribute("user", new User());
//		return "login";
//	}

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
//
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public ModelAndView authenticate(@ModelAttribute @Valid User user, BindingResult result, HttpSession session) {
//		ModelAndView mav = new ModelAndView();
//
//		if (result.hasErrors())
//			return new ModelAndView("login");
//		UserSession us = new UserSession();
//		if (user.getUserId() != null && user.getPassword() != null) {
//			User u = userService.authenticate(user.getUserId(), user.getPassword());
//			if (u != null) {
//				us.setUser(u);
//				// PUT CODE FOR SETTING SESSION ID
//				us.setSessionId(session.getId());
//				HashMap<Product, Integer> usageSummary = new HashMap<Product, Integer>();
//				session.setAttribute("usageSummary", usageSummary);
//				mav = new ModelAndView("redirect:/product/list");
//			} else {
//				return mav;
//			}
//
//		} else {
//			return mav;
//		}
//		session.setAttribute("USERSESSION", us);
//		return mav;
//	}
}
