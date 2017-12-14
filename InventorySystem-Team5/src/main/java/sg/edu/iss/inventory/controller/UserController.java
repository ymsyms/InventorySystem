package sg.edu.iss.inventory.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.UserLoginService;
import sg.edu.iss.inventory.service.UserService;

@RequestMapping(value="/admin/user")
@Controller
public class UserController {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserLoginService userLoginService;	
	
    @Autowired
    private PasswordEncoder encoder;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView userListPage(Model model, @ModelAttribute("user") User user) {
		model.addAttribute("user",new User());
		ModelAndView mav = new ModelAndView("user-list");
		ArrayList<User> userList = (ArrayList<User>)userService.findAllUser();
		mav.addObject("userList", userList);
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("user-edit");
		User user = userService.findUser(id);
		mav.addObject("user", user);
		mav.addObject("userList", userService.findAllUser());
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editUser(@ModelAttribute @Valid User user, BindingResult result,
			@PathVariable String id, final RedirectAttributes redirectAttributes) /* throws ProductNotFound */ {

		if (result.hasErrors())
			return new ModelAndView("user-edit");

		ModelAndView mav = new ModelAndView("redirect:/admin/user/list");

		userService.changeUser(user);		

		String message = "User was successfully updated.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable String id, final RedirectAttributes redirectAttributes)
	/* throws ProductNotFound */ {

		ModelAndView mav = new ModelAndView("redirect:/admin/user/list");
		User user = userService.findUser(id);

		userService.removeUser(user);		
		
		String message = "The user " + user.getUserId() + " was successfully deleted.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	

	
	
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newUserPage() {
		ModelAndView mav = new ModelAndView("user-new", "user", new User());
		//mav.addObject("usertlist", productService.findAllProduct());
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewUser(@ModelAttribute @Valid User user, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()){
			return new ModelAndView("product-new");
			}

		ModelAndView mav = new ModelAndView();
		String message = "New User " + user.getUserId() + " was successfully created.";
		user.setPassword(encoder.encode(user.getPassword()));
		userService.createUser(user);
		mav.setViewName("redirect:/admin/user/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}		
	

}