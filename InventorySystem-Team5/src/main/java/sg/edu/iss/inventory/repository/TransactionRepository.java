package sg.edu.iss.inventory.repository;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

	@Query("SELECT t FROM Transaction t WHERE t.transactionDate=:transDate AND t.customerName=:custName")
	ArrayList<Transaction> findTransactionByDateAndCustomer(@Param("transDate") Date transDate, @Param("custName") String custName);
	
	@Query("SELECT t FROM Transaction t WHERE t.transactionId=:tId")
	Transaction findTransactionByTransactionId(@Param("tId") int transId);
}
