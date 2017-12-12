package sg.edu.iss.inventory.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.model.Order;
import sg.edu.iss.inventory.model.OrderDetail;
import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.ProductSupplier;
import sg.edu.iss.inventory.model.Supplier;
import sg.edu.iss.inventory.repository.OrderDetailRepository;
import sg.edu.iss.inventory.repository.OrderRepository;
import sg.edu.iss.inventory.repository.ProductRepository;
import sg.edu.iss.inventory.repository.ProductSupplierRepository;
import sg.edu.iss.inventory.repository.SupplierRepository;

@Service
public class ReportServiceImpl implements ReportService {

	@Resource
	SupplierRepository SupplierRepository;
	@Resource
	OrderRepository OrderRepository;
	@Resource
	OrderDetailRepository OrderDetailRepository;
	@Resource
	ProductSupplierRepository productSupplierRepository;
	@Resource 
	ProductRepository ProductRepository;

	// to check supplier
	@Transactional
	public Supplier findSupplierbySupplierId(int supplierId) {
		return SupplierRepository.findSupplierbySupplierId(supplierId);
	}

	// to retrieve order
	@Transactional
	public ArrayList<Order> findOrderBySupplierId(int supplierId) {
		return OrderRepository.findOrderBySupplierId(supplierId);
	}

	//Ord.Qty
	// to retrieve order detail
	@Transactional
	public ArrayList<OrderDetail> findOrderDetailByOrderId(int orderId) {
		return OrderDetailRepository.findOrderDetailByOrderId(orderId);
	}

	//PartNo,UnitPrice,Min.Ord.Qty
	// to retrieve product price by supplier ID
	@Transactional
	public ArrayList<ProductSupplier> findProductSupplierByProductId(int supplierId) {
		return productSupplierRepository.findProductSupplierByProductId(supplierId);
	}
	
	//availQty,ReorderLvl
	// to retrieve product price by supplier ID
		@Transactional
		public Product findProductByPartNo(String partNo) {
			return ProductRepository.findProductByPartNo(partNo);
		}
}
