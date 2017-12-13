package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import sg.edu.iss.inventory.model.Product;

public interface ProductService {

	ArrayList<Product> findAllProduct();

	Product findProducts(String productNo);

	Product createProduct(Product product);

	Product changeProduct(Product product);

	void removeProduct(Product product);

	ArrayList<Product> searchAllCarDealer();

	ArrayList<Product> searchAllPartDescription();

	ArrayList<Product> searchAllColor();

	ArrayList<Product> findByPartDescription(String partDescription);

	ArrayList<Product> findByCarDealer(String carDealer);

	ArrayList<Product> searchProductByPartNo(String partNo);

	boolean useProduct(HttpServletRequest request, HttpSession session);

}