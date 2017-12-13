package sg.edu.iss.inventory.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.User;

public interface UserRepository extends JpaRepository<User, String>{

	@Query("SELECT u FROM User u WHERE u.userId=:userId AND u.password=:password")
	User findUserByIdPwd(@Param("userId") String userId, @Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.userId=:userId")
	User findByUserId(@Param("userId") String userId);
	
	@Query("SELECT u FROM User u WHERE u.userStatus='Valid'")
	ArrayList<User> findAllValidUser();

}