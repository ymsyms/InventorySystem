package sg.edu.iss.inventory.repository;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.inventory.model.TransactionDetail;
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,Integer>{
	
	/*@Query("SELECT td FROM TransactionDetail td,Transaction t where td.transactionId = :t.transactionId AND t.transactionDate:sdate AND td.partNo:partNo")
	ArrayList<TransactionDetail> findTransactionsByPartNo(@Param("partNo") String id,@Param("transactionDate") Date sdate);*/
	
//	@Query("SELECT td FROM TransactionDetail td inner join Transaction t on td.id.transactionId=t.transactionId AND td.id.partNo=:partNo AND t.transactionDate between :sDate AND :eDate")
//	ArrayList<TransactionDetail> findTransactionsByPartNo(@Param("partNo") String partNo,@Param("sDate")  Date sDate,@Param("eDate")  Date eDate);
	
	@Query("SELECT td FROM TransactionDetail td inner join Transaction t on td.id.transactionId=t.transactionId AND td.id.partNo=:partNo AND t.transactionDate >= :sDate AND t.transactionDate<=:eDate")
	ArrayList<TransactionDetail> findTransactionsByPartNo(@Param("partNo") String partNo,@Param("sDate")  Date sDate,@Param("eDate") Date eDate);

}