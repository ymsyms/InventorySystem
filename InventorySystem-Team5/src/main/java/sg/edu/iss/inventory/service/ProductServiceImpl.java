package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.annotation.Resource;

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
