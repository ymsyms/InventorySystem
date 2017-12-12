package sg.edu.iss.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.inventory.model.Supplier;;

public interface ReportRepository extends JpaRepository<Supplier,Integer>
{
	
//	@Query("SELECT s FROM Supplier s WHERE s.supplierId =:supplierId")
//	ArrayList<Supplier> findSupplierId(@Param("supplierId") String supplierId);

}
