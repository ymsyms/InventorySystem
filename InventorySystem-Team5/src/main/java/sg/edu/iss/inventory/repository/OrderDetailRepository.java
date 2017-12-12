package sg.edu.iss.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.inventory.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer>
{

}
