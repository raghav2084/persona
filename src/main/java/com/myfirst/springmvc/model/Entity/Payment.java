package com.myfirst.springmvc.model.Entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Payments")
public class Payment {

	private String paymentID;
	private String customerName;
	private String customerPhoneNumber;
	private String paymentDate;
	//private Date paymentDate;
	private int initialAmount;
	private int finalAmount;
	private int discountAmount;
	//private List<String> serviceList;
	private String serviceList;
	private String serviceProviderName;  //emp name
	
	@DynamoDBAttribute(attributeName = "serviceProviderName")
	public String getServiceProviderName() {
		return serviceProviderName;
	}
	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	@DynamoDBHashKey(attributeName = "paymentID")
	public String getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	@DynamoDBAttribute(attributeName = "customerName")
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	@DynamoDBAttribute(attributeName = "customerPhoneNumber")
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	
	@DynamoDBAttribute(attributeName = "paymentDate")
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	@DynamoDBAttribute(attributeName = "initialAmount")
	public int getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(int initialAmount) {
		this.initialAmount = initialAmount;
	}
	@DynamoDBAttribute(attributeName = "finalAmount")
	public int getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(int finalAmount) {
		this.finalAmount = finalAmount;
	}
	@DynamoDBAttribute(attributeName = "discountAmount")
	public int getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}
	//@DynamoDBAttribute(attributeName = "serviceList")
	
	/*public List<String> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}*/
	@Override
	public String toString() {
		return "Payment [paymentID=" + paymentID + ", customerName=" + customerName + ", customerPhoneNumber="
				+ customerPhoneNumber + ", paymentDate=" + paymentDate + ", initialAmount=" + initialAmount
				+ ", finalAmount=" + finalAmount + ", discountAmount=" + discountAmount + ", serviceList=" + serviceList
				+ ", serviceProviderName=" + serviceProviderName + "]";
	}
	@DynamoDBAttribute(attributeName = "serviceList")	
	public String getServiceList() {
		return serviceList;
	}
	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}
	
	/*@DynamoDBAttribute(attributeName = "paymentDate")
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}*/
	
	
	
	
}
