package sg.edu.iss.inventory.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.model.Product;
import sg.edu.iss.inventory.model.TransactionDetail;
import sg.edu.iss.inventory.repository.TransactionDetailRepository;
import sg.edu.iss.inventory.repository.TransactionRepository;
import sg.edu.iss.inventory.service.ProductService;
import sg.edu.iss.inventory.service.TransactionHistoryService;
import sg.edu.iss.inventory.validator.TransactionHistoryValidator;

@RequestMapping(value = "/transaction")
@Controller
public class TransactionHistoryController {

	@Autowired
	private TransactionHistoryService thService;
	@Autowired
	private ProductService pService;
	@Resource
	TransactionDetailRepository transdetailrepo;
	@Resource
	TransactionRepository transrepo;
	@Autowired
	private TransactionHistoryValidator thValidator;

	@InitBinder("transactionHistory")
	private void initTransactionBinder(WebDataBinder binder) {
		binder.addValidators(thValidator);
	}

	@RequestMapping(value = "/viewTranHistory/{partNo}", method = RequestMethod.GET)
	public ModelAndView openTranHistory(@PathVariable String partNo) {
		Product product = pService.findProducts(partNo);
		ModelAndView mav = new ModelAndView("transaction-history");
		mav.addObject("product", product);
		return mav;
	}

	@RequestMapping(value = "/searchTranHistory/{partNo}", method = RequestMethod.POST)
	public ModelAndView searchTranHistory(HttpServletRequest request, @PathVariable String partNo,
			@RequestParam(value = "sdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date sDate,
			@RequestParam(value = "edate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eDate) throws ParseException {
		ModelAndView mav = new ModelAndView("transaction-history");
		Product product = pService.findProducts(partNo);

		if (sDate == null || eDate == null) {
			request.setAttribute("dateErrorMessage", "Date fields cannot be empty");
		}
		else {
			String dt1 = "";
			String dt2 = "";
			if (eDate.before(sDate)) {
				Date holder = sDate;

				sDate = eDate;
				eDate = holder;

				dt1 = request.getParameter("edate");
				dt2 = request.getParameter("sdate");
			} else {
				dt1 = request.getParameter("sdate");
				dt2 = request.getParameter("edate");
			}

			ArrayList<TransactionDetail> trnsDetails = thService.findTransactionDetails(partNo, sDate, eDate);
			ArrayList<Date> trnsDates = new ArrayList<Date>();
			if(trnsDetails.size() > 0) {
				request.setAttribute("statusMessage", "Transaction History between " + dt1 + " and " + dt2);
			}
			else {
				request.setAttribute("statusMessage", "No matching results found");
			}
			

			for (TransactionDetail td : trnsDetails) {
				Date tdDate = transrepo.findTransactionByTransactionId(td.getId().getTransactionId())
						.getTransactionDate();
				trnsDates.add(tdDate);
			}

			
			mav.addObject("transHistory", trnsDetails);
			mav.addObject("trnsDates", trnsDates);
			// String dt1 = request.getParameter("sdate");
			// request.setAttribute("sdate", dt1);
			// String dt2 = request.getParameter("edate");
			// request.setAttribute("edate", dt2);
		}
		mav.addObject("product", product);
		return mav;
	}
}
