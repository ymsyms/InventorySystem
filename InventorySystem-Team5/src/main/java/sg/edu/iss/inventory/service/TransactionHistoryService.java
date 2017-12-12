package sg.edu.iss.inventory.service;

import java.util.ArrayList;
import java.util.Date;
import sg.edu.iss.inventory.model.TransactionDetail;

public interface TransactionHistoryService {
	
	ArrayList<TransactionDetail> findTransactionDetails(String partNo,Date startDate,Date endDate);

	//Transaction createTransaction(Transaction trsrtn);
}
