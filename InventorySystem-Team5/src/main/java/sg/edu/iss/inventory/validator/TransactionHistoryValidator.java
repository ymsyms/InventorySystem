package sg.edu.iss.inventory.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.inventory.model.Product;

@Component
public class TransactionHistoryValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Product.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Product product = (Product) arg0;	
		
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "partNo", "partNo", "Part No. is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "fromDate", "fromDate", "From Date is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "toDate", "toDate", "To Date is required.");
		System.out.println(product.toString());
	}}
