package sg.edu.iss.inventory.controller;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.Return;
import sg.edu.iss.inventory.repository.ProductRepository;
import sg.edu.iss.inventory.repository.ProductSupplierRepository;
import sg.edu.iss.inventory.repository.ReturnRepository;
import sg.edu.iss.inventory.service.ReturnService;
import sg.edu.iss.inventory.validator.ReturnValidator;


@RequestMapping(value="/return")
@Controller
public class ReturnController {

	@Resource
	ProductRepository prodRepo;
	@Resource
	ReturnRepository returnRepo;
	@Resource
	ProductSupplierRepository prodSupplierRepo;	
	@Autowired
	private ReturnService rService;
	@Autowired
	private ReturnValidator rValidator;

	@InitBinder("Return")
	private void initEmployeeBinder(WebDataBinder binder) {
		binder.addValidators(rValidator);
	}
	@RequestMapping(value="/load",method=RequestMethod.GET)
	public ModelAndView loadReturnPage() {
		
		ModelAndView mav = new ModelAndView("return-new");
		ArrayList<Product> productList = rService.findProduct();	
		mav.addObject("productList", productList);		
		return mav;
	}
	
	@RequestMapping(value="/getProdDetails",method=RequestMethod.GET)
	@ResponseBody
	public String[] getProdDetails(@RequestParam("prodId") String prodId) {
		
		Product prd = rService.findProduct(prodId);
		String[] str = new String[2];
		str[0]=prd.getPartDescription();
		str[1]=prd.getCarDealer();				
		return str;
	}
	
		
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createReturn(@ModelAttribute @Valid Return returnItem, BindingResult result,
			final RedirectAttributes redirectAttributes,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String partNo=(String)request.getParameter("hdnpartNo");
		boolean isSuccess=rService.createReturn(request,returnItem,partNo);	
		if(isSuccess)
		{
			mav = new ModelAndView("return-success");
			return mav;
		}
		else				
		{				
			mav = new ModelAndView("return-new");														
			ArrayList<Product> productList = rService.findProduct();	
			mav.addObject("productList", productList);	
			mav.addObject("return", (Return)request.getAttribute("return"));
			return mav;						
		}
		/*if (result.hasErrors())
			mav = new ModelAndView("return-new");
		else
		{
			rService.createReturn(request,returnItem,partNo);	
			mav = new ModelAndView("return-success");
		}
			return mav;*/
		
		
	}
	
	
}
