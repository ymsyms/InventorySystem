package sg.edu.iss.inventory.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.service.ProductService;

@RequestMapping(value="product")
@Controller
public class ProductController {

	@Autowired
	ProductService productService;	
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView productListPage(Model model) {
		model.addAttribute("product",new Product());
		ModelAndView mav = new ModelAndView("product-list");
		ArrayList<Product> productList = (ArrayList<Product>)productService.findAllProduct();
		mav.addObject("productList", productList);
		return mav;
	}	
	
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchProduct(@ModelAttribute Product product, HttpSession session, BindingResult result) {
		ModelAndView mav = new ModelAndView("product-list");
		
		if (product.getPartNo() != null) {
			ArrayList<Product> productList = new ArrayList<Product>();
			Product p = productService.findProducts(product.getPartNo());
			if(p != null)
			{
				productList.add(p);
				mav.addObject("productList", productList);
			}
			else
			{
				//to add error message
				mav = new ModelAndView("redirect:/product/list");
			}
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
		mav.addObject("productlist", productService.findAllProduct());
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
}
