package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer>
{
	@Query("SELECT o FROM OrderDetail o where o.id.orderId = :orderId")
	ArrayList<OrderDetail> findOrderDetailByOrderId(@Param("orderId") int orderId);
}
