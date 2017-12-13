package sg.edu.iss.inventory.service;

import java.util.ArrayList;
import java.util.List;

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
	@Override
	@Transactional
	public Supplier findSupplierbySupplierId(int supplierId) {
		
		return SupplierRepository.findSupplierbySupplierId(supplierId);
	}

	// to retrieve order
	@Override
	@Transactional
	public List<Order> findOrderBySupplierId(Supplier supplierId) {
		
		return OrderRepository.findOrderBySupplierId(supplierId);
	}

	//Ord.Qty
	// to retrieve order detail
	@Override
	@Transactional
	public ArrayList<OrderDetail> findOrderDetailByOrderId(int orderId) {
		return OrderDetailRepository.findOrderDetailByOrderId(orderId);
	}

	//PartNo,UnitPrice,Min.Ord.Qty
	// to retrieve product price by supplier ID
	@Override
	@Transactional
	public ArrayList<ProductSupplier> findProductSupplierByProductId(int supplierId) {
		return productSupplierRepository.findProductSupplierBySupplierId(supplierId);
	}
	
	//availQty,ReorderLvl
	// to retrieve product price by supplier ID
		@Override
		@Transactional
		public Product findProductByPartNo(String partNo) {
			return ProductRepository.findProductByPartNo(partNo);
		}
		
		
		
		
		@Override
		@Transactional
		public ProductSupplier findProductSupplierByPNoSId(String partNo, int supplierId) {
			return productSupplierRepository.findProductSupplierByPNoSId(partNo,supplierId);
		}
}
