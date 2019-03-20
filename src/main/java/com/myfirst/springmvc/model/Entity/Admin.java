package com.myfirst.springmvc.model.Entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Admin")
public class Admin {
	
	private String userName;
	private String userPassword;
	private String lastLoginDateTime;
	private String passwordModifiedDateTime;
	private String email;
	private String isOwner;
	
	@DynamoDBAttribute(attributeName="isowner")
	public String getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(String isOwner) {
		this.isOwner = isOwner;
	}
	@DynamoDBHashKey(attributeName="username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@DynamoDBAttribute(attributeName="userPassword")
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@DynamoDBAttribute(attributeName="lastLoginDateTime")
	public String getLastLoginDateTime() {
		return lastLoginDateTime;
	}
	public void setLastLoginDateTime(String lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}
	
	@DynamoDBAttribute(attributeName="passwordModifiedDateTime")
	public String getPasswordModifiedDateTime() {
		return passwordModifiedDateTime;
	}
	public void setPasswordModifiedDateTime(String passwordModifiedDateTime) {
		this.passwordModifiedDateTime = passwordModifiedDateTime;
	}
	
	@DynamoDBAttribute(attributeName="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Admin [userName=" + userName + ", userPassword=" + userPassword + ", lastLoginDateTime="
				+ lastLoginDateTime + ", passwordModifiedDateTime=" + passwordModifiedDateTime + ", email=" + email
				+ ", isOwner=" + isOwner + "]";
	}

}
