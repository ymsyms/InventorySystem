package sg.edu.iss.inventory.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.iss.inventory.service.TransactionService;
import sg.edu.iss.inventory.validator.TransactionValidator;

@RequestMapping(value = "/transaction")
@Controller
public class TransactionController {

	@Autowired
	private TransactionService tService;
	@Autowired
	private TransactionValidator tValidator;

	// =========Testing block 1a start==========================
	// @Resource
	// ProductRepository pRepo;
	// HashMap<Product, Integer> usageSummary = new HashMap<Product, Integer>();
	// =========Testing block 1a end============================

	@InitBinder("transaction")
	private void initTransactionBinder(WebDataBinder binder) {
		binder.addValidators(tValidator);
	}

	@RequestMapping(value = "/usageSummary")
	public ModelAndView openUsageSummary(HttpSession session) {
		// ==============Testing block 1b start====================================
		//
		// session.setAttribute("firstLoad", "yes");
		// String loadStatus = (String) session.getAttribute("firstLoad");
		// if(usageSummary.size() == 0 && loadStatus.equals("yes") ) {
		// session.setAttribute("usageSummary", usageSummary);
		// usageSummary.put(pRepo.findProductByPartNo("2345HB-SIL"), 0);
		// usageSummary.put(pRepo.findProductByPartNo("01234-03"), 0);
		// usageSummary.put(pRepo.findProductByPartNo("02367VW-B03"), 0);
		// loadStatus = "no";
		// }
		// ==============Testing block 1b end=====================================

		ModelAndView mav = new ModelAndView("usage-summary");
		return mav;
	}

	@RequestMapping(value = "/submitUsage", method = RequestMethod.POST)
	public ModelAndView submitUsage(HttpServletRequest request, HttpSession session) {
		boolean recordingResult = tService.addTransaction(request, session);
		ModelAndView mav = new ModelAndView("usage-summary");
		if (recordingResult) {
			mav = new ModelAndView("success-confirmation");
			return mav;
		} else {
			return mav;
		}
	}

	@RequestMapping(value = "/cartRemove", method = RequestMethod.POST)
	public ModelAndView removeItem(HttpServletRequest request, HttpSession session) {
		tService.removeItem(request, session);
		ModelAndView mav = new ModelAndView("usage-summary");
		return mav;
	}

	@RequestMapping(value = "/cartUpdate", method = RequestMethod.POST)
	public ModelAndView updateItem(HttpServletRequest request, HttpSession session) {
		tService.updateItem(request, session);
		ModelAndView mav = new ModelAndView("usage-summary");
		return mav;

	}

	@RequestMapping(value = "/cartClear", method = RequestMethod.POST)
	public ModelAndView clearCart(HttpSession session) {
		tService.clearCart(session);
		ModelAndView mav = new ModelAndView("usage-summary");
		return mav;
	}

}
