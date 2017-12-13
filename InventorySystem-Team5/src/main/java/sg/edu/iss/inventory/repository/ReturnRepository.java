package sg.edu.iss.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.Return;

public interface ReturnRepository extends JpaRepository<Return,Integer>{

	@Query("SELECT max(r.returnId) from Return r")
	Integer getMaxReturnId();
	

}
