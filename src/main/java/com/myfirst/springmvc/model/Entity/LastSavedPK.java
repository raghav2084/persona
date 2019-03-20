package com.myfirst.springmvc.model.Entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="LastSavedPKTable")
public class LastSavedPK {
	
	private String lastSavedPKName;
	private String lastSavedPKValue;
	
	@DynamoDBHashKey(attributeName = "lastSavedPKName")
	public String getLastSavedPKName() {
		return lastSavedPKName;
	}
	public void setLastSavedPKName(String lastSavedPKName) {
		this.lastSavedPKName = lastSavedPKName;
	}
	@DynamoDBAttribute(attributeName = "lastSavedPKValue")
	public String getLastSavedPKValue() {
		return lastSavedPKValue;
	}
	public void setLastSavedPKValue(String lastSavedPKValue) {
		this.lastSavedPKValue = lastSavedPKValue;
	}
	@Override
	public String toString() {
		return "LastSavedPK [lastSavedPKName=" + lastSavedPKName + ", lastSavedPKValue=" + lastSavedPKValue + "]";
	}
	
	

}
