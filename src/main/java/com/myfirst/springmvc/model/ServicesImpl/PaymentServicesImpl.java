package com.myfirst.springmvc.model.ServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.Payment;

@Repository
public class PaymentServicesImpl {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServicesImpl.class);
	static AmazonDynamoDBClient client = System.getenv("ENV").equals("PROD") ?
										new AmazonDynamoDBClient(new BasicAWSCredentials(
										System.getenv("DB_KEY"),
										System.getenv("DB_SECRET")))
										:
										new AmazonDynamoDBClient();
	static DynamoDBMapper mapper = new DynamoDBMapper(client);
	static DynamoDB dynamoDB = new DynamoDB(client);
	static String ENDPOINT 		= 		System.getenv("ENV").equals("PROD") ?
										System.getenv("AWS_ENDPOINT")
										:
										"http://localhost:8000";

	// Save payment object to DB
	public void savePayment(Payment pay) {
		client.setEndpoint(ENDPOINT);

		try {
			DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
			// Construct a map of expected current values for the conditional
			// write
			Map<String, ExpectedAttributeValue> expected = new HashMap<String, ExpectedAttributeValue>();
			// for paymentID Not equal to 0 i.e save fails if Payment ID ==0
			expected.put("paymentID", new ExpectedAttributeValue().withComparisonOperator("NE")
					.withValue(new AttributeValue().withS("0")));
			saveExpression.setExpected(expected);

			mapper.save(pay, saveExpression);
			logger.info("Saved payments: " + pay);

		} catch (ConditionalCheckFailedException e) {
			logger.error("Payment not saved : " + pay);
		}

	}

	public Payment getPayment(String paymentID) {
		Payment p = new Payment();
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		p = mapper.load(Payment.class, paymentID, config);
		if (p != null) {

		} else {
			throw new DynamoDBMappingException("User does not exist in DB");
		}

		return p;
	}

	public List<Payment> getAllPayments() {
		client.setEndpoint(ENDPOINT);
		List<Payment> p = new ArrayList<Payment>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
		scanFilter.put("paymentID", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		p = mapper.scan(Payment.class, scanExpression);
		
		return p;
	}
	
	public List<Payment> getAllPaymentsOfToday(String today) {
		client.setEndpoint(ENDPOINT);
		List<Payment> p = new ArrayList<Payment>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
		scanFilter.put("paymentID", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		scanExpression.addFilterCondition("paymentDate", 
                new Condition()
                   .withComparisonOperator(ComparisonOperator.EQ)
                   .withAttributeValueList(new AttributeValue().withS(today)));
		p = mapper.scan(Payment.class, scanExpression);
		return p;
	}
	//overloaded using today and tomorrow call
	public List<Payment> getAllPaymentsOfToday(String today, String tomorrow) {
		client.setEndpoint(ENDPOINT);
		List<Payment> p = new ArrayList<Payment>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
		scanFilter.put("paymentID", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		scanExpression.addFilterCondition("paymentDate", 
				 new Condition()
                 .withComparisonOperator(ComparisonOperator.BETWEEN.toString())
                 .withAttributeValueList(new AttributeValue().withS(today), new AttributeValue().withS(tomorrow)));
			
		p = mapper.scan(Payment.class, scanExpression);
		return p;
	}

	
	public void deletePayment(String payID) throws AmazonClientException {
		Payment pay = getPayment(payID);
		try {
			mapper.delete(pay);
		} catch (AmazonClientException e) {
			logger.error("Error occured in deletePay(String payID) for " + pay);
			throw new AmazonClientException("Error occured in deletePay(String payID)");
		}
	}

	public List<Payment> findPaymentsByEmployee(String empName) {
		client.setEndpoint(ENDPOINT);
		logger.info("findPaymentsByEmployee :" + empName);
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		scanExpression.addFilterCondition("serviceProviderName",
				new Condition().withComparisonOperator(ComparisonOperator.EQ)
						.withAttributeValueList(new AttributeValue().withS(empName)));
		/*
		 * scanExpression.addFilterCondition("Price", new Condition()
		 * .withComparisonOperator(ComparisonOperator.LT)
		 * .withAttributeValueList(new AttributeValue().withN(value)));
		 */
		List<Payment> scanResult = mapper.scan(Payment.class, scanExpression);

		for (Payment pay : scanResult) {
			logger.info("findPaymentsByEmployee " + pay);
		}
		return scanResult;
	}

	// Update Payement using Payment object
	public void updatePayment(Payment pay) throws AmazonClientException {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER);
		try {
			mapper.save(pay, config);
			//logger.info("Payment updated: " + pay);
		} catch (AmazonClientException e) {
			logger.error("Error occured in updatePayment for " + pay);
			throw new AmazonClientException("Error occured in updatePayment");
		}
	}

	public List<Payment> getAllPaymentsOfBetween(String earlierDate, String laterDate) {
		client.setEndpoint(ENDPOINT);
		logger.info("laterDate " + laterDate);
		logger.info("earlierDate " + earlierDate);
		String isoEarlierdate = ConvertToISOJODA(earlierDate);
		String isoLaterDate = ConvertToNextDateISOJODA(laterDate);
		List<Payment> p = new ArrayList<Payment>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
	Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
		scanFilter.put("paymentID", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		scanExpression.addFilterCondition("paymentDate", 
                new Condition()
                   .withComparisonOperator(ComparisonOperator.BETWEEN.toString())
                   .withAttributeValueList(new AttributeValue().withS(isoEarlierdate), new AttributeValue().withS(isoLaterDate)));
				
		
		p = mapper.scan(Payment.class, scanExpression);
		return p;
	}
	
	public List<Payment> getAllPaymentsOfEmployeeByDateRanges (Employees emp, String toDate, String fromDate){
		client.setEndpoint(ENDPOINT);
		logger.info("getAllPaymentsOfEmployeeByDateRanges Emp: "+ emp.getEmpPhoneNum() +" " + fromDate + " toDate: "+toDate);
		List<Payment> p = new ArrayList<Payment>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
	Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ).withAttributeValueList(new AttributeValue().withS(emp.getEmpName()));
		scanFilter.put("serviceProviderName", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		scanExpression.addFilterCondition("paymentDate", 
                new Condition()
                   .withComparisonOperator(ComparisonOperator.BETWEEN.toString())
                   .withAttributeValueList(new AttributeValue().withS(toDate), new AttributeValue().withS(fromDate)));
				
		
		p = mapper.scan(Payment.class, scanExpression);
	
		
		return p;
	}
	
	/***********************Helper methods for JODA TIME***************************/
	
	//used to converting "05/05/2013" to Joda ISO
	String ConvertToISOJODA (String s){
		logger.info("Inside ConvertToISOJODA: "+ s);
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "MMM d, yyyy hh:mm a" );
		DateTime dateTime1 = formatter.parseDateTime( s );
		String iso8601String = dateTime1.toString();
		return iso8601String;
	}
	
	//used to get next date from input
	String ConvertToNextDateISOJODA (String s){
		logger.info("Inside ConvertToNextDateISOJODA: "+ s);
		DateTimeFormatter formatter = DateTimeFormat.forPattern( "MMM d, yyyy hh:mm a" );
		DateTime dateTime1 = formatter.parseDateTime( s );
		dateTime1 =dateTime1.plusDays(1);
		String iso8601String = dateTime1.toString();
		logger.info("Done ConvertToNextDateISOJODA: "+ iso8601String);
		return iso8601String;
	}

}	

// NOTES:

/*
 * 
 * 
 * 
 * Condition rangeKeyCondition = new Condition()
 * .withComparisonOperator(ComparisonOperator.BETWEEN.toString())
 * .withAttributeValueList(new AttributeValue().withS(startDate), new
 * AttributeValue().withS(endDate));
 * 
 */
