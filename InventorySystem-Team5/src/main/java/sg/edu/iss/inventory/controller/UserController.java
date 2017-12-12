package sg.edu.iss.inventory.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.UserService;

@RequestMapping(value="admin/user")
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String newUserPage(Model model) {
		User user = new User();
		model.addAttribute(user);
		return "user-new";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView userListPage() {
		ModelAndView mav = new ModelAndView("user-list");
		ArrayList<User> userList = (ArrayList<User>)userService.findAllUser();
		mav.addObject("userList", userList);
		return mav;
	}
	
	
//	@RequestMapping(value="/create",method= RequestMethod.POST)
//	public ModelAndView createNewUser(@ModelAttribute UserController user,BiBindingResult result,final RedirectAttributes redirectAttributes)
//	{
////		if(result.hasError())
////		{
////			return new ModelAndView("user-new");
////		}
//		
//		ModelAndView modelAndView = new ModelAndView();
//		
//		return modelAndView;
//	}

}
