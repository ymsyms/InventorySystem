package sg.edu.iss.inventory.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import sg.edu.iss.inventory.model.Return;;
@Component
public class ReturnValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return Return.class.isAssignableFrom(arg0);
	}
	@Override
	public void validate(Object target, Errors errors) {
		Return returns = (Return) target;
		//ValidationUtils.rejectIfEmpty(errors, "partNo", "error.return.partNo.empty");
		ValidationUtils.rejectIfEmpty(errors, "returnQty", "error.return.returnQty.empty");
		//ValidationUtils.rejectIfEmpty(errors, "returnDate", "error.return.returnDate.empty");
	    System.out.println(returns.toString());
	}
}