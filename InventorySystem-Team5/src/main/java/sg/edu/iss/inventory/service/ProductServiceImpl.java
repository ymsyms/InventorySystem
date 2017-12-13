package sg.edu.iss.inventory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource ProductRepository productRepository;
	
	@Override
	@Transactional
	public ArrayList<Product> findAllProduct()
	{
		ArrayList<Product> userList = (ArrayList<Product>)productRepository.findActiveProduct();
		return userList;
	}
		
	@Override
	@Transactional
	public Product findProducts(String productNo) {
		Product l = productRepository.findProductByPartNo(productNo);
		return l;
	}
	
	@Override
	@Transactional
	public Product createProduct(Product product) {
		product.setProductStatus("Valid");
		return productRepository.saveAndFlush(product);
	}

	@Override
	@Transactional
	public Product changeProduct(Product product) {
		product.setProductStatus("Valid");
		return productRepository.saveAndFlush(product);
	}

	@Override
	@Transactional
	public void removeProduct(Product product) {
		product.setProductStatus("Invalid");
		productRepository.saveAndFlush(product);
	}
	
	@Override
	public boolean useProduct(HttpServletRequest request, HttpSession session) {
		
		int usageQuantity = 0;
		int availableQuantity = 0;
		String strUsageQuantity = request.getParameter("newQuantity");
		String strAvailableQuantity = request.getParameter("availableQuantity");
		
		//Checking whether user left the quantity field empty when submitting
		if(strUsageQuantity != null && strAvailableQuantity != null) {
			if(UtilitiesService.isInt(strUsageQuantity) && UtilitiesService.isInt(strAvailableQuantity)) {
				usageQuantity = Integer.parseInt(request.getParameter("newQuantity"));
				availableQuantity = Integer.parseInt(request.getParameter("availableQuantity"));
			}
			else {
				request.setAttribute("qtyErrorMessage", "Only integers allowed in quantity field");
				return false;
			}
		}
		else {
			request.setAttribute("qtyErrorMessage", "Quantity cannot be left empty");
			return false;
		}
		
		if (usageQuantity <= availableQuantity) {
			HashMap<Product, Integer> usageSummary = (HashMap) session.getAttribute("usageSummary");
			String partNo = request.getParameter("partNo");
			Product toBeAdded = productRepository.findProductByPartNo(partNo);
			int newQuantity = 0;

			boolean containsProduct = false; // Checks if the product is already in the usage summary hashmap
			for (Map.Entry<Product, Integer> pair : usageSummary.entrySet()) {
				if (toBeAdded.equals(pair.getKey())) { // Execute the inner block after finding a matching Product
					containsProduct = true;

					newQuantity = pair.getValue() + usageQuantity; // Allows adding onto an existing usage
																		// quantity

					if (newQuantity <= availableQuantity) { // If final usage quantity is still within available quantity, the addition will be allowed
						usageSummary.put(pair.getKey(), pair.getValue() + usageQuantity);
						request.setAttribute("addingStatusMessage", "Usage of " + partNo + " has been added " + "(" + usageQuantity + " added. Total: " + newQuantity + ")");
						return true;
					}
					else {
						request.setAttribute("qtyErrorMessage", "You cannot add this quantity as it will exceed the available quantity. (Current usage quantity: " + pair.getValue() + ")");
						return false;
					}
				}
			}

			if (containsProduct == false) { // Adding the product if the hashmap doesn't contain it yet
				usageSummary.put(toBeAdded, usageQuantity);
				request.setAttribute("addingStatusMessage", "Usage of " + partNo + " has been added " + "(" + usageQuantity + " added. Total: " + usageQuantity + ")");
				return true;
			}
		} else {
			request.setAttribute("qtyErrorMessage", "You cannot set a usage quantity higher than the available quantity");
			return false;
		}
		return false;
	}

	@Override
	@Transactional
	public ArrayList<Product> findByPartDescription(String partDescription){
		return productRepository.findProductByPartDescription(partDescription);
	}
	@Override
	@Transactional
	public ArrayList<Product> findByCarDealer(String carDealer){
		return productRepository.findProductByCarDealer(carDealer);
	}
	@Override
	@Transactional
	public ArrayList<Product> searchProductByPartNo(String partNo){
		return productRepository.serachProductByPartNo(partNo);
	}
	@Override
	@Transactional
	public ArrayList<Product> searchAllCarDealer()
	{
		return productRepository.findcarDealer();
	}
	
	@Override
	@Transactional
	public ArrayList<Product> searchAllPartDescription()
	{
		return productRepository.findPartDescription();
	}

	@Override
	@Transactional
	public ArrayList<Product> searchAllColor() {
		return productRepository.findColor();
	}
}
