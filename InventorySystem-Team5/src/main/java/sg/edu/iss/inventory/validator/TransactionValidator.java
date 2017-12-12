package sg.edu.iss.inventory.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import sg.edu.iss.inventory.model.Transaction;


@Component
public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return Transaction.class.isAssignableFrom(arg0);

	}

	@Override
	public void validate(Object arg0, Errors arg1) {
		Transaction transaction = (Transaction) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "partNo", "custName", "Customer name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(arg1, "quantity", "error.quantity", "Quantity is required.");
		System.out.println(transaction.toString());
	}
}
