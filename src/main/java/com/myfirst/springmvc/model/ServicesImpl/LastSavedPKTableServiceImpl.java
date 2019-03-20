package com.myfirst.springmvc.model.ServicesImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.LastSavedPK;

@Repository
public class LastSavedPKTableServiceImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(LastSavedPKTableServiceImpl.class);
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
	
	
	
	public void saveAndUpdateLastSavedPKCount (LastSavedPK LastSavedPK){
		client.setEndpoint(ENDPOINT);
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER);
		mapper.save(LastSavedPK, config);
		
	}

	
	public LastSavedPK getLastSavedPKCount (String tablePKName){
		client.setEndpoint(ENDPOINT);
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		LastSavedPK result = mapper.load(LastSavedPK.class, tablePKName, config);
		return result;
		
	}


}
