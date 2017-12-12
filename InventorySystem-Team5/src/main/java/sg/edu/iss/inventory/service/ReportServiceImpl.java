package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.model.Product;

@Service
public class ReportServiceImpl implements ReportService {

@Resource ReportService reportServiceImpl;
	
	@Transactional
	public ArrayList<Product> findAllProduct()
	{
//		ArrayList<Product> userList = (ArrayList<Product>)reportServiceImpl.findActiveProduct();
//		return userList;
		
		return null;
	}
		
	
}
