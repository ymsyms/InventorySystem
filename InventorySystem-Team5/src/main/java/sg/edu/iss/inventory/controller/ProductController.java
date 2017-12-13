package sg.edu.iss.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.service.ProductService;
import sg.edu.iss.inventory.service.UserLoginService;
import sg.edu.iss.inventory.validator.ProductValidator;

@RequestMapping(value="product")
@Controller
public class ProductController {

	@Autowired
	ProductService productService;	
	
	@Autowired
	UserLoginService userLoginService;	
	
	@Autowired
	private ProductValidator pValidator;

	@InitBinder("product")
	private void initEmployeeBinder(WebDataBinder binder) {
		binder.addValidators(pValidator);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView productListPage(Model model, @RequestParam(required = false) Integer page,@ModelAttribute("product") Product product,HttpSession session) {
		model.addAttribute("product",new Product());
		
		User user = userLoginService.getUserDetails();
		UserSession userSession = new UserSession();
		userSession.setUser(user);
		session.setAttribute("USERSESSION", userSession);
			
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
	

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView searchProduct(@ModelAttribute Product product, HttpSession session, HttpServletRequest request,
			BindingResult result, @RequestParam(required = false) Integer page) {
		ModelAndView mav = new ModelAndView("product-list");
		ArrayList<Product> productList = new ArrayList<Product>();
		String option = (String) request.getParameter("selectoption");
		String key = (String) request.getParameter("searchVar");
		String partDescriptions = "partDescription";
		String partNos = "partNo";
		if (option.equals(partNos)) {
			productList = productService.searchProductByPartNo(key);
		} else if (option.equals(partDescriptions)) {

			productList = productService.findByPartDescription(key);
		} else {
			productList = productService.findByCarDealer(key);
		}

		if (!productList.isEmpty()) {
			PagedListHolder<Product> pagedListHolder = new PagedListHolder<>(productList);
			pagedListHolder.setPageSize(7);
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
		} else {
			// to add error message
			mav = new ModelAndView("redirect:/product/list");
		}
		return mav;
	}
	
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editProductPage(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("product-edit");
		Product product = productService.findProducts(id);
		mav.addObject("product", product);
		mav.addObject("productlist", productService.findAllProduct());
		return mav;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public ModelAndView editProduct(@ModelAttribute @Valid Product product, BindingResult result,
			@PathVariable String id, final RedirectAttributes redirectAttributes) /* throws ProductNotFound */ {

		if (result.hasErrors())
			return new ModelAndView("product-edit");

		ModelAndView mav = new ModelAndView("redirect:/product/list");

		productService.changeProduct(product);		

		String message = "Product was successfully updated.";
		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@PathVariable String id, final RedirectAttributes redirectAttributes)
	/* throws ProductNotFound */ {

		ModelAndView mav = new ModelAndView("redirect:/product/list");
		Product product = productService.findProducts(id);

		productService.removeProduct(product);		
		
		String message = "The product " + product.getPartNo() + " was successfully deleted.";

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView newProductPage() {
		ModelAndView mav = new ModelAndView("product-new", "product", new Product());
		//mav.addObject("productlist", productService.findAllProduct());
		return mav;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createNewProduct(@ModelAttribute @Valid Product product, BindingResult result,
			final RedirectAttributes redirectAttributes) {

		if (result.hasErrors()){
			return new ModelAndView("product-new");
			}

		ModelAndView mav = new ModelAndView();
		String message = "New product " + product.getPartNo() + " was successfully created.";

		productService.createProduct(product);
		mav.setViewName("redirect:/product/list");

		redirectAttributes.addFlashAttribute("message", message);
		return mav;
	}		
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public ModelAndView productDetailPage(@PathVariable String id) {
		ModelAndView mav = new ModelAndView("product-detail");
		Product product = productService.findProducts(id);
		mav.addObject("product", product);
		mav.addObject("productlist", productService.findAllProduct());
		return mav;
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.POST)
	public ModelAndView productDetail(@ModelAttribute @Valid Product product, BindingResult result,
			@PathVariable String id, final RedirectAttributes redirectAttributes) /* throws ProductNotFound */ {

		if (result.hasErrors())
			return new ModelAndView("product-detail");

		ModelAndView mav = new ModelAndView("redirect:/product/list");

		return mav;
	}
	
	@RequestMapping(value = "/use", method = RequestMethod.POST)
	public ModelAndView useProduct(HttpServletRequest request, HttpSession session) {
		ModelAndView mav = new ModelAndView("product-list");
		productService.useProduct(request, session);
		
		mav = new ModelAndView("redirect:/product/list");
		return mav;
	}	
}
