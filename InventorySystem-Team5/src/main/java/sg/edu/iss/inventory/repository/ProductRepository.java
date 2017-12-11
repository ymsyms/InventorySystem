package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
	@Query("SELECT p FROM Product p WHERE p.productStatus='Valid'")
	ArrayList<Product> findActiveProduct();
	
	@Query("SELECT distinct p.carDealer FROM Product p WHERE p.productStatus='Valid'")
	ArrayList<Product> findcarDealer();	
	
	@Query("SELECT distinct p.partDescription FROM Product p WHERE p.productStatus='Valid'")
	ArrayList<Product> findPartDescription();	
	
	@Query("SELECT distinct p.color FROM Product p WHERE p.productStatus='Valid'")
	ArrayList<Product> findColor();		
	
	@Query("SELECT p FROM Product p WHERE p.partNo =:partNo and p.productStatus='Valid'")
	Product findProductByPartNo(@Param("partNo") String partNo);
	
	@Query("SELECT p FROM Product p WHERE p.partNo LIKE %:partNo% and p.productStatus='Valid'")
	ArrayList<Product> serachProductByPartNo(@Param("partNo") String partNo);
	
	@Query("SELECT p FROM Product p WHERE p.carDealer=:carDealer and p.productStatus='Valid'")
	Product findProductByCarDealer(@Param("carDealer") String carDealer);
	
	@Query("SELECT p FROM Product p WHERE p.color=:color and p.productStatus='Valid'")
	Product findProductByColor(@Param("color") String color);

	@Query("SELECT p FROM Product p WHERE p.shelfLocation LIKE %:shelfLocation% and p.productStatus='Valid'")
	Product findProductByShelfLocation(@Param("shelfLocation") String shelfLocation);
	
	@Query("SELECT p FROM Product p WHERE p.carDealer=:carDealer and p.color=:color and p.productStatus='Valid'")
	Product findProductByDealerNColor(@Param("carDealer") String carDealer, @Param("color") String color);

}
