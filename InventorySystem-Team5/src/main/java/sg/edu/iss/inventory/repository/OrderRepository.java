package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Order;
import sg.edu.iss.inventory.model.Supplier;

public interface OrderRepository extends JpaRepository<Order,Integer>
{
	@Query("SELECT o FROM Order o where o.orderId = :orderId")
	Order findOrderByOrderId(@Param("orderId") int orderId);
	
	@Query("SELECT o FROM Order o where o.supplier = :supplierId")
	ArrayList<Order> findOrderBySupplierId(@Param("supplierId") Supplier supplierId);
}
