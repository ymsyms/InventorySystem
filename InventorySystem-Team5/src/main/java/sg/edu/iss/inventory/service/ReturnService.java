package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.model.Return;
import sg.edu.iss.inventory.model.Product;

public interface ReturnService {
	
	
	ArrayList<Product> findProduct();
	
	ArrayList<Product> findDealer();

	Product findProduct(String id);
	
	boolean createReturn(HttpServletRequest req,Return returns,String id);
		
}
