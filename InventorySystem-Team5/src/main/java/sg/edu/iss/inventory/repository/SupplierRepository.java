package sg.edu.iss.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

	@Query("SELECT s FROM Supplier s WHERE s.supplierId =:supplierId ")
	Supplier findSupplierbySupplierId(@Param("supplierId") int supplierId);
}
