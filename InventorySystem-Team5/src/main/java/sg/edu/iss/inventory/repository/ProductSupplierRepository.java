package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.ProductSupplier;

public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Integer> {

	@Query("SELECT p FROM ProductSupplier p where p.id.supplierId = :supplierId")
	ArrayList<ProductSupplier> findProductSupplierBySupplierId(@Param("supplierId") int supplierId);
	
	@Query("SELECT p FROM ProductSupplier p where p.id.partNo = :partNo")
	ArrayList<ProductSupplier> findProductSupplierByProductId(@Param("partNo") String partNo);
	
	@Query("SELECT p FROM ProductSupplier p where p.id.partNo = :partNo and p.id.supplierId = :supplierId")
	ProductSupplier findProductSupplierByPNoSId(@Param("partNo") String partNo,@Param("supplierId") int supplierId);
}
