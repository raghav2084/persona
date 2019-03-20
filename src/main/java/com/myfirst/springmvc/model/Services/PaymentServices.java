package com.myfirst.springmvc.model.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.Payment;
import com.myfirst.springmvc.model.ServicesImpl.PaymentServicesImpl;

@Service
public class PaymentServices {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServices.class);
	@Autowired
	PaymentServicesImpl impl;

	// Save payment object to DB
	public void savePayment(Payment pay) throws AmazonClientException {
		// logger.info("pay :"+pay);
		impl.savePayment(pay);

	}

	public Payment getPayment(String paymentID) throws AmazonClientException {
		Payment p = new Payment();
		p = impl.getPayment(paymentID);
		return p;
	}

	public List<Payment> getAllPayments() throws AmazonClientException {
		List<Payment> p = new ArrayList<Payment>();
		List<Payment> ret = new ArrayList<Payment>();
		p = impl.getAllPayments();
		ret = ListOfFormattedDatePayments(p);
		return ret;
	}

	public void deletePayment(String pay) throws AmazonClientException {
		impl.deletePayment(pay);
	}

	// find payments by employee
	public List<Payment> findPaymentsByEmployee(String empName) throws AmazonClientException {
		logger.info("Employee Name" + empName);
		List<Payment> p = new ArrayList<Payment>();
		p = impl.findPaymentsByEmployee(empName);
		return p;
	}

	// Update Payment and get updated payment
	public Payment updatePaymentAndReturnUpdatedPayment(Payment pay) throws AmazonClientException {
		Payment payment = new Payment();
		impl.updatePayment(pay);
		payment = impl.getPayment(pay.getPaymentID());
		return payment;
	}

	// Update Payment
	public void updatePayment(Payment pay) throws AmazonClientException {
		impl.updatePayment(pay);
	}

	public List<Payment> getAllPaymentsOfToday(String today) {
		logger.info("getAllPaymentsOfToday: " + today);
		List<Payment> p = new ArrayList<Payment>();
		p = impl.getAllPaymentsOfToday(today);
		for (int i = 0; i < p.size(); i++) {
			logger.info("getAllPaymentsOfToday RETURNED" + p.get(i));
		}
		return p;
	}

	// used to get payments of a given date - using BETWEEN function at the back
	// end
	public List<Payment> getAllPaymentsOfToday(String today, String tomorrow) {
		logger.info("getAllPaymentsOfToday today: " + today);
		logger.info("getAllPaymentsOfToday tomorrow " + tomorrow);
		List<Payment> p = new ArrayList<Payment>();
		List<Payment> ret = new ArrayList<Payment>();
		p = impl.getAllPaymentsOfToday(today, tomorrow);
		ret = ListOfFormattedDatePayments(p);
		return ret;
	}

	public List<Payment> getAllPaymentsOfRangeDates(String fromDate, String toDate) {
		logger.info("getAllPaymentsOfRangeDates: " + fromDate + " toDate: " + toDate);
		Date fromDateDate = ConvertStringToDate(fromDate);
		Date toDateDate = ConvertStringToDate(toDate);
		List<Payment> p = new ArrayList<Payment>();
		List<Payment> ret = new ArrayList<Payment>();
		if (fromDateDate.compareTo(toDateDate) > 0) {
			logger.info("fromDate is after toDate");
			String fromdate = fromDate + " 12:00 PM";
			String todate = toDate + " 12:00 AM";
			String todateToSend = ConvertDateFormatStringToString2(todate);
			String fromdateToSend = ConvertDateFormatStringToString2(fromdate);
			p = impl.getAllPaymentsOfBetween(todateToSend, fromdateToSend);
			ret = ListOfFormattedDatePayments(p);
		} else if (fromDateDate.compareTo(toDateDate) < 0) {
			logger.info("fromDate is before toDate");
			String fromdate = fromDate + " 12:00 AM";
			String todate = toDate + " 12:00 PM";
			String todateToSend = ConvertDateFormatStringToString2(todate);
			String fromdateToSend = ConvertDateFormatStringToString2(fromdate);
			p = impl.getAllPaymentsOfBetween(fromdateToSend, todateToSend);
			ret = ListOfFormattedDatePayments(p);
		}
		return ret;
	}

	public List<Payment> getAllPaymentsOfEmployeeByDateRanges(Employees emp, DateTime toDate, DateTime fromDate) {
		logger.info("getAllPaymentsOfEmployeeByDateRanges Emp: " + emp.getEmpPhoneNum() + " " + fromDate + " toDate: "
				+ toDate);
		// Date fromDateDate =ConvertStringToDate(fromDate);
		// Date toDateDate =ConvertStringToDate(toDate);
		List<Payment> p = new ArrayList<Payment>();
		List<Payment> ret = new ArrayList<Payment>();

		if (fromDate.isAfter(toDate.getMillis())) {
			logger.info("fromDate is after toDate");
			fromDate = fromDate.plusDays(1);
			p = impl.getAllPaymentsOfEmployeeByDateRanges(emp, toDate.toString(), fromDate.toString());
			ret = ListOfFormattedDatePayments(p);
		} else if (fromDate.isBefore(toDate.getMillis())) {
			logger.info("fromDate is before toDate");
			toDate = toDate.plusDays(1);
			p = impl.getAllPaymentsOfEmployeeByDateRanges(emp, fromDate.toString(), toDate.toString());
			ret = ListOfFormattedDatePayments(p);
		}

		return ret;
	}

	public List<Payment> getAllPaymentsOfEmployeeByDate(Employees emp, DateTime today, DateTime tomorrow) {
		logger.info("getAllPaymentsOfEmployeeByDate Emp: " + emp.getEmpName() + " fromDate" + today + " toDate: "
				+ tomorrow);
		List<Payment> p = new ArrayList<Payment>();
		List<Payment> ret = new ArrayList<Payment>();
		p = impl.getAllPaymentsOfEmployeeByDateRanges(emp, today.toString(), tomorrow.toString());
		ret = ListOfFormattedDatePayments(p);
		return ret;
	}

	/***************** HELPER METHOD *****************/



	//// used for converting from Oct 18, 2015 (string) to Oct 18, 2015 (Date)
	Date ConvertStringToDate(String stringDate) {
		Date dateToReturn = null;
		String strDateFormat1 = "MMM d, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		try {
			dateToReturn = sdf.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateToReturn;
	}

	// used for converting from Oct 18, 2015 12:00 PM (string) to Oct 18, 2015
	// 12:00 PM ( strings in milliseconds)
	String ConvertDateFormatStringToString2(String oldfomratdate) {
		logger.info("Inside ConvertDateFormatStringToString2");
		String returnString = null;
		String strDateFormat1 = "MMM d, yyyy hh:mm a";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		// sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
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

	//converting isoString to user friendly time format.
	String isoParse(String s){
		// Parse string into a DateTime. Passing to constructor conveniently uses the built-in ISO 8601 parser built into DateTime class.
		DateTime dateTime2 = new DateTime( s );		
		DateTimeFormatter formatter1 = DateTimeFormat.forPattern( "MMM d, yyyy hh:mm a" );
		String output = formatter1.print( dateTime2 );
		return output;
	}
	
	List<Payment> ListOfFormattedDatePayments (List<Payment> p)
	{
		List<Payment> ret = new ArrayList<Payment>();
		for (int i = 0; i < p.size(); i++) {
			logger.info("getAllPaymentsOfToday RETURNED" + p.get(i));
			Payment payment = new Payment();
			payment.setCustomerName(p.get(i).getCustomerName());
			payment.setCustomerPhoneNumber(p.get(i).getCustomerPhoneNumber());
			payment.setDiscountAmount(p.get(i).getDiscountAmount());
			payment.setFinalAmount(p.get(i).getFinalAmount());
			payment.setInitialAmount(p.get(i).getInitialAmount());
			payment.setPaymentID(p.get(i).getPaymentID());
			payment.setServiceList(p.get(i).getServiceList());
			payment.setServiceProviderName(p.get(i).getServiceProviderName());
			String paymentDate = isoParse(p.get(i).getPaymentDate());
			payment.setPaymentDate(paymentDate);
		
		ret.add(i, payment);
		}
		return ret;	
	}
}
