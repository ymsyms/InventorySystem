package sg.edu.iss.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.service.ReportService;

@RequestMapping(value="report")
@Controller
public class ReportController {
	
	@Autowired
	ReportService reportService;		
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView productListPage() {
		return null;
	}	
}
