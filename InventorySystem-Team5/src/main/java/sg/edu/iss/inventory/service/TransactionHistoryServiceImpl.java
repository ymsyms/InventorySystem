package sg.edu.iss.inventory.service;
import java.util.*;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.inventory.model.TransactionDetail;
import sg.edu.iss.inventory.repository.TransactionDetailRepository;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService{

	@Resource
	TransactionDetailRepository transRepo;
	
	@Override
	@Transactional
	public ArrayList<TransactionDetail> findTransactionDetails(String partNo,Date  startDate,Date endDate)
	{
		return transRepo.findTransactionsByPartNo(partNo,startDate,endDate);
	}
}
