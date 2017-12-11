package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {

	@Query("SELECT p FROM Product p WHERE p.partNo=:partNo and p.productStatus='Valid'")
	Product findProductByPartNo(@Param("partNo") String partNo);
	
	@Query("SELECT p FROM Product p WHERE p.productStatus='Valid'")
	ArrayList<Product> findActiveProduct();

}
