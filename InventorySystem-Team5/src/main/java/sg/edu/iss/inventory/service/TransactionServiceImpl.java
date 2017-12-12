package sg.edu.iss.inventory.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.controller.UserSession;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.Transaction;
import sg.edu.iss.inventory.model.TransactionDetail;
import sg.edu.iss.inventory.model.TransactionDetailId;
import sg.edu.iss.inventory.model.User;
import sg.edu.iss.inventory.repository.ProductRepository;
import sg.edu.iss.inventory.repository.TransactionDetailRepository;
import sg.edu.iss.inventory.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	 @Resource
	 TransactionRepository transRepository;
	 @Resource
	 TransactionDetailRepository transDetailRepository;
	@Resource
	ProductRepository productRepository;
	@Autowired
	UserLoginService userLoginService;	

	@Override
	@Transactional
	public boolean addTransaction(HttpServletRequest request, HttpSession session) {
		Calendar cal = Calendar.getInstance();
		Date transactionDate = cal.getTime();
		String customerName = request.getParameter("custName");
		
		//Actual use block start ==========================
		
		User user = userLoginService.getUserDetails();
		Transaction newTrans = new Transaction(transactionDate, user, customerName);
		//Actual use block end ============================
		
		HashMap<Product, Integer> usageSummary = (HashMap) session.getAttribute("usageSummary");
		boolean validTrans = false;			//To check against cases where all usage quantities are 0
		for (Map.Entry<Product, Integer> pair : usageSummary.entrySet()) {
			if(pair.getValue() > 0) {
				validTrans = true;
				break;
			}
		}
		
		if(customerName == null || customerName == "" || validTrans == false) {
			if(validTrans == false) {
				request.setAttribute("qtyErrorMessage", "Usage quantities cannot all be 0.");
			}
			if(customerName == null || customerName == "") {
				request.setAttribute("nameErrorMessage", "Customer name is required");
			}
			return false;
		}
		else {
			try {
				transRepository.saveAndFlush(newTrans);
				
				ArrayList<Transaction> userTrans = transRepository.findTransactionByDateAndCustomer(transactionDate, customerName);
				
				for (Map.Entry<Product, Integer> pair : usageSummary.entrySet()) {
					int usageQty = pair.getValue();
					if(usageQty > 0) {
						String partNo = pair.getKey().getPartNo();
						TransactionDetailId transIdSet = new TransactionDetailId(userTrans.get(userTrans.size()-1).getTransactionId(), partNo);
						TransactionDetail newTransDetail = new TransactionDetail(transIdSet, usageQty);
						transDetailRepository.saveAndFlush(newTransDetail);
		
						int currentStock = pair.getKey().getAvailableQty();
						int newQuantity =  currentStock - usageQty;
						Product toBeUpdated = productRepository.findProductByPartNo(partNo);
						toBeUpdated.setAvailableQty(newQuantity);
						productRepository.saveAndFlush(toBeUpdated);  
					}             //If all database updates can be completed, user will be redirected
				}				  //to a page indicating the submission was successful
				session.setAttribute("usageSummary", new HashMap<Product, Integer>());      //Reinstantiate usage summary hashmap to flush old data
				return true;
			}
			finally {
			}
		}
	}
	
	@Override
	public void removeItem(HttpServletRequest request, HttpSession session) {
		HashMap<Product, Integer> usageSummary = (HashMap) session.getAttribute("usageSummary");
		int size = usageSummary.size();

		for (int i = 0; i < size; i++) { // Iterate through every row of the usage summary table
			String chkCheck = "chk" + i; // Get the id of the checkbox in a particular row of the usage summary table
			String chkStatus = request.getParameter(chkCheck);
			if (chkStatus != null) {
				if (chkStatus.equals("on")) { // If a checkbox is checked, that row will be deleted
					String txtId = "partNo" + i;

					// Checks the current row in the table in usage summary against every pair in
					// the usageSummary hashmap to find the entry to be removed
					for (Map.Entry<Product, Integer> pair : usageSummary.entrySet()) {

						// Check for the product key in the hashmap with a part number matching
						// the part number in the table row
						if (pair.getKey().getPartNo().equals(request.getParameter(txtId))) {
							usageSummary.remove(pair.getKey());
							break; // Break to avoid ConcurrentModificationException. No issues since only 
								   // 1 key will be removed in the whole loop
						}
					}
				}
			}
		}
	}

	@Override
	public void updateItem(HttpServletRequest request, HttpSession session) {
		HashMap<Product, Integer> usageSummary = (HashMap) session.getAttribute("usageSummary");
		int size = usageSummary.size();

		for (int i = 0; i < size; i++) {      // Iterate through every row of the usage summary table
			String txtId = "partNo" + i;
			String qtyId = "qty" + i;
			
			if(isInt(request.getParameter(qtyId))) {
				// Loop through the usage summary hashmap to find and update every pair
				for (Map.Entry<Product, Integer> pair : usageSummary.entrySet()) {
					String partNo = request.getParameter(txtId);
					if (partNo != null) {

						// Check for the product key in the hashmap with a part number matching
						// the part number in the table row
						if (pair.getKey().getPartNo().equals(partNo)) {
							Product fromDb = productRepository.findProductByPartNo(partNo);
							if (fromDb != null) {
								int availableStock = fromDb.getAvailableQty();
								Integer requestedValue = Integer.parseInt(request.getParameter(qtyId));  //The intended end value of the quantity update
								if (requestedValue <= availableStock) {
									Integer newQty = requestedValue;
									usageSummary.put(pair.getKey(), newQty);
									break;  // Break to avoid ConcurrentModificationException. No issues since only 1
											// quantity will be updated in the whole loop
								}
								else {
									request.setAttribute("qtyErrorMessage", "Usage quantity cannot be higher than available quantity");
								}
							}
						}
					}
				}
			}
			else {
				request.setAttribute("qtyErrorMessage", "Quantity must be an integer");
			}
		}
	}
	
	public void clearCart(HttpSession session) {
//		HashMap<Product, Integer> usageSummary = (HashMap) session.getAttribute("usageSummary");
//		usageSummary = new HashMap<Product, Integer>();
		session.setAttribute("usageSummary", new HashMap<Product, Integer>());
	}
	
	private boolean isInt(String forParsing) {
		try {
			int result = Integer.parseInt(forParsing);
			return true;
		}
		catch (Exception e){
			return false;
		}
	}
}
