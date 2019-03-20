package com.myfirst.springmvc.model.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Employees")
public class Employees implements Comparable<Employees>{
	
	private String empName;
	private String empSex;
	private String empPhoneNum;
	private String empStatus;
	
	@DynamoDBAttribute(attributeName = "empStatus")
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	@DynamoDBAttribute(attributeName = "empName")
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@DynamoDBAttribute(attributeName = "empSex")
	public String getEmpSex() {
		return empSex;
	}
	public void setEmpSex(String empSex) {
		this.empSex = empSex;
	}
	@DynamoDBHashKey(attributeName = "empPhoneNum")
	public String getEmpPhoneNum() {
		return empPhoneNum;
	}
	public void setEmpPhoneNum(String empPhoneNum) {
		this.empPhoneNum = empPhoneNum;
	}
	@Override
	public String toString() {
		return "Employee [empName=" + empName + ", empSex=" + empSex + ", empPhoneNum=" + empPhoneNum + ", empStatus="
				+ empStatus + "]";
	}
	@Override
	public int compareTo(Employees o) {
		int comp = empName.compareTo(o.empName);
		return comp;
	}

	
}
