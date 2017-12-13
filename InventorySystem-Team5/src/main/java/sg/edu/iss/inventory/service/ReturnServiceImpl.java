package sg.edu.iss.inventory.service;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.Return;
import sg.edu.iss.inventory.repository.ProductRepository;
import sg.edu.iss.inventory.repository.ReturnRepository;

@Service
public class ReturnServiceImpl implements ReturnService {


	@Resource
	private ProductRepository prodRepo;
	@Resource
	private ReturnRepository returnRepo;
	
	
	@Override
	@Transactional
	public ArrayList<Product> findProduct()
	{
		return prodRepo.findActiveProduct();
	}
	@Override
	@Transactional
	public ArrayList<Product> findDealer() {
		
		return prodRepo.findcarDealer();
	}
	@Override
	@Transactional
	public Product findProduct(String id)
	{
		return prodRepo.findProductByPartNo(id);
	}
	
	@Override
	@Transactional
	public boolean createReturn(HttpServletRequest req,Return returns,String partNo) {
	
		Return rtn = returns;
		List<Product>prod= prodRepo.findAll();
		
		Product prd =null;
		
		for(Product p :prod)
		{
			
			if(p.getPartNo().equals(partNo))
			{
				
				prd = p;
			}
		}
		int retrnQty =rtn.getReturnQty();		
		int AvailQty=prd.getAvailableQty();
		if((AvailQty-retrnQty)<0)
		{
			req.setAttribute("returnQtyError", "Return Quantity cannot be less than your stock");
			req.setAttribute("return", rtn);
			return false;
			
		}
		else
		{
			prd.setAvailableQty(AvailQty-retrnQty);				
			rtn.setProduct(prd);
			prodRepo.saveAndFlush(prd);			
			returnRepo.saveAndFlush(rtn);
			return true;
			
		}
		
		
	}
}
