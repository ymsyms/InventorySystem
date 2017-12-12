package sg.edu.iss.inventory.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.UserService;
import sg.edu.iss.inventory.validator.LoginValidator;

@RequestMapping(value="user")
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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String logic(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model,HttpSession session) {
		session.invalidate();
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView authenticate(@ModelAttribute @Valid User user,BindingResult result,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors())
			return  new ModelAndView("login");
		UserSession us = new UserSession();
		if (user.getUserId() != null && user.getPassword() != null) {
			User u = userService.authenticate(user.getUserId(), user.getPassword());
			if(u != null)
			{
				us.setUser(u);
				// PUT CODE FOR SETTING SESSION ID
				us.setSessionId(session.getId());
				mav = new ModelAndView("redirect:/product/list");
			}
			else
			{		
				return mav;
			}
			
		}
		else
		{		
			return mav;
		}
		session.setAttribute("USERSESSION", us);
		return mav;
	}
}
