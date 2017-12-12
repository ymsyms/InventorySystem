package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import sg.edu.iss.inventory.exception.DuplicatePartNumException;
import sg.edu.iss.inventory.exception.MismatchPartNumException;
import sg.edu.iss.inventory.model.OrderCartItem;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.ProductSupplier;
import sg.edu.iss.inventory.model.User;

public interface OrderService {

	public ArrayList<OrderCartItem> createToOrderList() throws MismatchPartNumException;

	public void addListItem(String partNum, ArrayList<OrderCartItem> orderList)
			throws DuplicatePartNumException, MismatchPartNumException;

	public void removeListItem(String partNum, ArrayList<OrderCartItem> orderList);

	public int computeQty(Product product, ProductSupplier ps) throws MismatchPartNumException;

	public OrderCartItem getLowPriceItem(Product product, ArrayList<ProductSupplier> prodSupList)
			throws MismatchPartNumException;
	
	public void logOrders(User user, ArrayList<OrderCartItem> orderList);
}
