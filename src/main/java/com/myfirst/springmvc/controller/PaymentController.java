package com.myfirst.springmvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.LastSavedPK;
import com.myfirst.springmvc.model.Entity.Payment;
import com.myfirst.springmvc.model.Services.EmployeeServices;
import com.myfirst.springmvc.model.Services.LastSavedPKTableService;
import com.myfirst.springmvc.model.Services.PaymentServices;

@Controller
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	List<Payment> paymentToReturn = new ArrayList<Payment>();

	@Autowired
	PaymentServices paymentservices;
	@Autowired
	LastSavedPKTableService lastsaved;
	@Autowired
	EmployeeServices empServices;

	// All Payments GET method
	@RequestMapping(value = { "/own/allpayments" }, method = RequestMethod.GET)
	public String allPayments(Model model) {
		logger.info("allPayments");
		Date d = new Date();
		// for Oct 16, 2015 used for printing only
		String strDateFormat1 = "MMM d, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		String today = sdf.format(d);

		
		DateTime todayInISO = new DateTime(d);
		todayInISO  = todayInISO.withTime(0, 0, 0, 0);
		String todayInISOString = todayInISO.toString();
		
		DateTime nextDayinISO = todayInISO.plusDays(1);
		String nextDayinISOString = nextDayinISO.toString();
		
		List<Payment> allpayments = new ArrayList<Payment>();
		// allpayments = paymentservices.getAllPayments();
		//allpayments = paymentservices.getAllPaymentsOfToday(today);
		allpayments = paymentservices.getAllPaymentsOfToday(todayInISOString,nextDayinISOString);

		if (allpayments.size() != 0) {
			// set the message to show current date
			model.addAttribute("msg", "Today : " + today);
			model.addAttribute("paymentsList", allpayments);
		} else {
			model.addAttribute("msg", "Today : " + today);
			model.addAttribute("NoResultsFound", "No payments found for " + today);

		}
		return "owner/allPayments";
	}

	// All Payments POST method for Range Dates
	@RequestMapping(value = { "/own/allpayments" }, method = RequestMethod.POST)
	public String rangePayments(String fromDate, String toDate, Model model) {
		logger.info("rangePayments");
		logger.info("fromDate: " + fromDate);
		logger.info("toDate: " + toDate);
		List<Payment> allpayments = new ArrayList<Payment>();
		// if both dates are same
		if (fromDate.equalsIgnoreCase(toDate)) {
			logger.info("rangePayments from and to dates are same");
			String formattedDate = ConvertDateFormatStringToString(toDate);
			String todayInJODAISO = convertToISOJODAString(toDate);
			String nextDateInJODAISO = convertToNextDateISOJODAString(toDate);
			//allpayments = paymentservices.getAllPaymentsOfToday(formattedDate);
			allpayments = paymentservices.getAllPaymentsOfToday(todayInJODAISO, nextDateInJODAISO);
			if (allpayments.size() != 0) {
				// set the message to show current date
				model.addAttribute("msg", "Date : " + formattedDate);
				model.addAttribute("paymentsList", allpayments);
			} else {
				model.addAttribute("msg", "Date : " + formattedDate);
				model.addAttribute("NoResultsFound", "No payments found for " + formattedDate);

			}

		} // end of both same dates if
			// if dates are range
		else {
			String formattedFromDate = ConvertDateFormatStringToString(fromDate);
			String formattedToDate = ConvertDateFormatStringToString(toDate);
			allpayments = paymentservices.getAllPaymentsOfRangeDates(formattedFromDate, formattedToDate);
			if (allpayments.size() != 0) {
				// set the message to show current date
				model.addAttribute("msg", "From : " + formattedFromDate + "\t To: " + formattedToDate);
				model.addAttribute("paymentsList", allpayments);
			} else {
				model.addAttribute("msg", "From : " + formattedFromDate + "\t To: " + formattedToDate);
				model.addAttribute("NoResultsFound",
						"No payments found between " + formattedFromDate + " and " + formattedToDate);

			}
		}
		return "owner/allPayments";
	}
	
	//To get payments of an emp based on range dates
			@RequestMapping(value = { "/own/empreport" }, method = RequestMethod.POST)
			public String empReportSearchDate( String EmpPhoneNumHidden, String fromDate, String toDate, Model model) {
				logger.info("empReport POST:" + EmpPhoneNumHidden);
				logger.info("empReport fromDate:" + fromDate);
				logger.info("empReport toDate:" + toDate);
				Employees emp = new Employees();
				emp = empServices.getEmployee(EmpPhoneNumHidden);
				logger.info("empReport emp found: " + emp);
				List<Payment> p = new ArrayList<Payment>();
				
				if (fromDate.equalsIgnoreCase(toDate)){
					logger.info("rangePayments from and to dates are same");
					String formattedDate = ConvertDateFormatStringToString(toDate);
					DateTime todayInJODAISODateTime = convertToISOJODADateTime(toDate);
					DateTime nextDateInJODAISODateTime = convertToNextDateISOJODADateTime(toDate);
					p = paymentservices.getAllPaymentsOfEmployeeByDate(emp,todayInJODAISODateTime, nextDateInJODAISODateTime);
					if (p.size() != 0) {
						// set the message to show current date
						model.addAttribute("msg", "Date : " + formattedDate);
						model.addAttribute("paymentsList", p);
					} else {
						model.addAttribute("msg", "Date : " + formattedDate);
						model.addAttribute("NoResultsFound", "No payments found for " + formattedDate);

					}
					
				}else{
					String formattedFromDate = ConvertDateFormatStringToString(fromDate);
					String formattedToDate = ConvertDateFormatStringToString(toDate);
					DateTime toDateJODAISODateTime = convertToISOJODADateTime(toDate);
					DateTime fromDateInJODAISODateTime = convertToISOJODADateTime(fromDate);
					p = paymentservices.getAllPaymentsOfEmployeeByDateRanges(emp,toDateJODAISODateTime, fromDateInJODAISODateTime);
					if (p.size() != 0) {
						// set the message to show current date
						model.addAttribute("msg", "From : " + formattedFromDate + "\t To: " + formattedToDate);
						model.addAttribute("paymentsList", p);
					} else {
						model.addAttribute("msg", "From : " + formattedFromDate + "\t To: " + formattedToDate);
						model.addAttribute("NoResultsFound",
								"No payments found between " + formattedFromDate + " and " + formattedToDate);

					}
				}
				
				//p = payServices.getAllPaymentsOfEmployeeByDateRanges(emp, toDate, fromDate);
				model.addAttribute("Emp", emp);
				model.addAttribute("paymentsList", p);
				// session.setAttribute("AllEmpNames", allEmpNames);
				return "owner/employeeReport";
			}
			
			
			
			
	// AJAX Delete Payments using PaymentID POST method
	@RequestMapping(value = { "/own/deletePay" }, method = RequestMethod.POST)
	public @ResponseBody Payment deletePayments(
			@RequestParam("deletePayModalHiddenPayName") String deletePayModalHiddenPayName,
			HttpServletResponse response) {
		logger.info("AJAX deleting payment ID : " + deletePayModalHiddenPayName);
		Payment pay = new Payment();
		Payment payAboutToDelete = paymentservices.getPayment(deletePayModalHiddenPayName);
		logger.info("AJAX payAboutToDelete : " + payAboutToDelete);
		paymentservices.deletePayment(deletePayModalHiddenPayName);
		// logger.info("Payment deleted payment ID : " +
		// deletePayModalHiddenPayName);
		// return "redirect:/own/allpayments";
		// return "hello";

		pay = payAboutToDelete;

		return pay;
	}

	// AJAX Edit Payments Post method
	@RequestMapping(value = { "/own/editPay" }, method = RequestMethod.POST)
	public @ResponseBody Payment editPay(Payment pay, Model model) {
		// logger.info("AJAX editPay " + pay);
		logger.info("AJAX editPay ");
		Payment oldPayment = paymentservices.getPayment(pay.getPaymentID());
		logger.info("AJAX editPay oldPayment " + oldPayment);
		String s = ConvertStringToISO(pay.getPaymentDate());
		pay.setPaymentDate(s);
		paymentservices.updatePayment(pay);
		Payment newPayment = paymentservices.getPayment(pay.getPaymentID());
		logger.info("AJAX editPay newPayment " + newPayment);
		return newPayment;
	}

	// testing to see how many times this is executed
	@PostConstruct
	public void init() {
		logger.info("PaymentController Init Method");

	}

	/*
	 * // Delete Payments using PaymentID POST method
	 * 
	 * @RequestMapping(value = { "/own/deletePay" }, method =
	 * RequestMethod.POST) public String deletePayments(@RequestParam String
	 * deletePayModalHiddenPayName) { logger.info("deleting payment ID : " +
	 * deletePayModalHiddenPayName); //List<Payment> allpayments = new
	 * ArrayList<Payment>();
	 * paymentservices.deletePayment(deletePayModalHiddenPayName); logger.info(
	 * "Payment deleted payment ID : " + deletePayModalHiddenPayName); return
	 * "redirect:/own/allpayments"; }
	 */

	// Edit Payments Post method
	/*
	 * @RequestMapping(value = { "/own/editPay" }, method = RequestMethod.POST)
	 * public String editPay(Payment pay, Model model) { logger.info("editPay "
	 * + pay);
	 * 
	 * List<Payment> allpayments = new ArrayList<Payment>(); allpayments =
	 * paymentservices.getAllPayments(); model.addAttribute("paymentsList",
	 * allpayments);
	 * 
	 * return "owner/allPayments"; }
	 */

	// payment request coming from employee page
	@RequestMapping(value = "emp/savepayment", method = RequestMethod.POST)
	public String SavePaymentFromEmp(Payment payment, Model model, RedirectAttributes redirectAttrs) {
		logger.info("Inside PaymentContoller SavePaymentFromEmp Call.");
		logger.info("" + payment);

		String s = ConvertStringToISO(payment.getPaymentDate());
		payment.setPaymentDate(s);
		
		LastSavedPK newPayID = new LastSavedPK();

		LastSavedPK lastSavedPK = lastsaved.getLastSavedPKCount("Payments#paymentID");
		logger.info("lastSavedPK Payments#paymentID: " + lastSavedPK.getLastSavedPKValue());

		// incrementing the pamentID by one
		int i = Integer.parseInt(lastSavedPK.getLastSavedPKValue());
		int j = i + 1;
		String newID = Integer.toString(j);

		// formatting the date
		/*String unformattedDate = payment.getPaymentDate();
		String formattedDate = ConvertDateFormatStringToStringWithTime(unformattedDate);
		payment.setPaymentDate(formattedDate);*/

		payment.setPaymentID(newID);
		paymentservices.savePayment(payment);

		newPayID.setLastSavedPKName("Payments#paymentID");
		newPayID.setLastSavedPKValue(newID);
		logger.info("New Payments#paymentID: " + newPayID);

		lastsaved.saveAndUpdateLastSavedPKCount(newPayID);

		return "redirect:/emp/home";

	}

	// payment request coming from owner page
	@RequestMapping(value = "own/savepayment", method = RequestMethod.POST)
	public String SavePaymentFromOwner(Payment payment, Model model, RedirectAttributes redirectAttrs) {
		logger.info("Inside PaymentContoller SavePaymentFromOwner Call.");

		logger.info("" + payment);
		
		//String s = ConvertDateFormatStringToString2(payment.getPaymentDate());
		String s = ConvertStringToISO(payment.getPaymentDate());
		payment.setPaymentDate(s);

		LastSavedPK newPayID = new LastSavedPK();

		LastSavedPK lastSavedPK = lastsaved.getLastSavedPKCount("Payments#paymentID");
		logger.info("lastSavedPK Payments#paymentID: " + lastSavedPK.getLastSavedPKValue());

		// incrementing the pamentID by one
		int i = Integer.parseInt(lastSavedPK.getLastSavedPKValue());
		int j = i + 1;
		String newID = Integer.toString(j);

		payment.setPaymentID(newID);
		paymentservices.savePayment(payment);

		newPayID.setLastSavedPKName("Payments#paymentID");
		newPayID.setLastSavedPKValue(newID);
		logger.info("New Payments#paymentID: " + newPayID);

		lastsaved.saveAndUpdateLastSavedPKCount(newPayID);

		String returnPage = "redirect:/own/home";
		return returnPage;

	}
	
	
	
	
	/***************************HELPER METHODS*******************/

	// used for converting from 10/18/2015 to Oct 18, 2015 (both strings)
	String ConvertDateFormatStringToString(String oldfomratdate) {
		String returnString = null;
		String strDateFormat1 = "MM/dd/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		Date result;

		try {
			result = sdf.parse(oldfomratdate);
			String strDateFormat2 = "MMM d, yyyy";
			SimpleDateFormat df = new SimpleDateFormat(strDateFormat2);
			// System.out.println("asdasd : " + df.format(result));
			returnString = df.format(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return returnString;
	}

	//used to converting "05/05/2013" to Joda ISO
	String convertToISOJODAString (String s){
		logger.info("Inside convertToISOJODAString: "+ s);
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "MM/dd/yyyy" );
		DateTime dateTime1 = formatter.parseDateTime( s );
		String iso8601String = dateTime1.toString();
		return iso8601String;
	}
	
	//used to converting "05/05/2013" to Joda ISO
	DateTime convertToISOJODADateTime (String s){
			logger.info("Inside convertToISOJODADateTime: "+ s);
			DateTimeFormatter formatter = DateTimeFormat.forPattern( "MM/dd/yyyy" );
			DateTime dateTime1 = formatter.parseDateTime( s );
			return dateTime1;
		}
	
	//used to get next date from input
		String convertToNextDateISOJODAString (String s){
			logger.info("Inside convertToNextDateISOJODAString: "+ s);
			DateTimeFormatter formatter = DateTimeFormat.forPattern( "MM/dd/yyyy" );
			DateTime dateTime1 = formatter.parseDateTime( s );
			dateTime1 =dateTime1.plusDays(1);
			String iso8601String = dateTime1.toString();
			logger.info("Done convertToNextDateISOJODAString: "+ iso8601String);
			return iso8601String;
		}
	
		//used to get next date from input
		DateTime convertToNextDateISOJODADateTime (String s){
					logger.info("Inside convertToNextDateISOJODADateTime: "+ s);
					DateTimeFormatter formatter = DateTimeFormat.forPattern( "MM/dd/yyyy" );
					DateTime dateTime1 = formatter.parseDateTime( s );
					dateTime1 =dateTime1.plusDays(1);
					logger.info("Done convertToNextDateISOJODADateTime: "+ dateTime1.toString());
					return dateTime1;
				}
			
	
	// used for converting from Oct 18, 2015 12:00 PM(both strings)
	String ConvertDateFormatStringToStringWithTime(String oldfomratdate) {
		String returnString = null;
		String strDateFormat1 = "MM/dd/yyyy hh:mm a";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		returnString = sdf.format(oldfomratdate);
		return returnString;
	}
	
	// used for converting from Oct 18, 2015 12:00 PM (string) to Oct 18, 2015 12:00 PM ( strings in milliseconds)
		String ConvertDateFormatStringToString2(String oldfomratdate) {
			logger.info("Inside ConvertDateFormatStringToString2");
			String returnString = null;
			String strDateFormat1 = "MMM d, yyyy hh:mm a";
			SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
			//sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date result;

			try {
				result = sdf.parse(oldfomratdate);
				SimpleDateFormat df = new SimpleDateFormat(strDateFormat1);
				returnString = df.format(result.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return returnString;
		}
		
	//converting incoming date to ISO date format using Joda Time
	//MMM d, yyyy hh:mm a to ISO 
		String ConvertStringToISO (String s){
			logger.info("Inside ConvertStringToISO");
			DateTimeFormatter formatter1 = DateTimeFormat.forPattern( "MMM d, yyyy hh:mm a" );
			DateTime dateTime1 = formatter1.parseDateTime( s );
			String iso8601String = dateTime1.toString();
			return iso8601String;
		}

}/// end of controller class
