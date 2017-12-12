package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import sg.edu.iss.inventory.model.Order;
import sg.edu.iss.inventory.model.OrderDetail;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.ProductSupplier;
import sg.edu.iss.inventory.model.Supplier;

public interface ReportService {

	// to check supplier
	Supplier findSupplierbySupplierId(int supplierId);

	// to retrieve order
	ArrayList<Order> findOrderBySupplierId(Supplier supplierId);

	//Ord.Qty
	// to retrieve order detail
	OrderDetail findOrderDetailByOrderId(int orderId);

	//PartNo,UnitPrice,Min.Ord.Qty
	// to retrieve product price by supplier ID
	ArrayList<ProductSupplier> findProductSupplierByProductId(int supplierId);

	//availQty,ReorderLvl
	// to retrieve product price by supplier ID
	Product findProductByPartNo(String partNo);
}