package sg.edu.iss.inventory.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface TransactionService {

	boolean addTransaction(HttpServletRequest request, HttpSession session);
	void removeItem(HttpServletRequest request, HttpSession session);
	void updateItem(HttpServletRequest request, HttpSession session);
	void clearCart(HttpSession session);
}
