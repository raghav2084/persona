package com.myfirst.springmvc.model.ServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.myfirst.springmvc.model.Entity.Admin;
import com.myfirst.springmvc.model.Entity.AuthenticationResponse;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.utils.AWSProperties;

@Repository
public class AdminServicesImpl {

	private static final Logger logger = LoggerFactory.getLogger(AdminServicesImpl.class);
	static AmazonDynamoDBClient client = System.getenv("ENV").equals("PROD") ?
										new AmazonDynamoDBClient(new BasicAWSCredentials(
										System.getenv("DB_KEY"),
										System.getenv("DB_SECRET")))
										:
										new AmazonDynamoDBClient();
	static String ENDPOINT 		= 		System.getenv("ENV").equals("PROD") ?
										System.getenv("AWS_ENDPOINT")
										:
										"http://localhost:8000";
	static DynamoDBMapper mapper = new DynamoDBMapper(client);
	static DynamoDB dynamoDB = new DynamoDB(client);
	//static Admin a = null;
	static AuthenticationResponse authenticationResponse ;
	static AWSProperties prop = new AWSProperties();
	
	
	
	
	static AuthenticationResponse findByUserName(String uname) throws DynamoDBMappingException {
		client.setEndpoint(ENDPOINT);
		System.out.println("ENDPOINT: " + ENDPOINT);
		/*
		System.out.println("READING SYS ENV - ENV "+System.getenv("ENV"));
		System.out.println("READING SYS ENV - DB_KEY "+System.getenv("DB_KEY"));
		System.out.println("READING SYS ENV - DB_SECRET "+System.getenv("DB_SECRET"));
		System.out.println("READING SYS ENV - AWS_ENDPOINT "+System.getenv("AWS_ENDPOINT"));
		*/
		
		Admin a = mapper.load(Admin.class, uname);
		 AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		if(a != null){
			logger.info("returned admin from DB");
			//System.out.println("returned admin from DB");
			authenticationResponse.setUserAuthResponse(true);
			authenticationResponse.setAdmin(a);
			return authenticationResponse;
		}
		else 
		{
			authenticationResponse.setUserAuthResponse(false);
			logger.info("findByUserName else block");
			throw new DynamoDBMappingException("User not found");
			
		}	
		
	}
	/*
	//boolean version
	public static boolean isAdminAuthenticated(String uname, String pass) throws DynamoDBMappingException{

		boolean result = false;
		AuthenticationResponse authResp = findByUserName(uname);
		if (authResp.isUserAuthResponse()==true) {
			logger.info("Inside isAdminAuthenticated service Imp ");
			if (authResp.getAdmin().getIsOwner().equalsIgnoreCase("Y") && pass.equals(authResp.getAdmin().getUserPassword()))
			{
				logger.info("Owner Authenticated! User: "+uname+ " Pass: " +pass);
				result = true;
			}	
			else
		{
			logger.info("Owner NOT Authenticated! User: "+uname+ " Pass: " +pass);
			result = false;
		}
		
	}
		return result;	
	}// end of isAdminAuthenticated
	
*/
	
	
	
	public static AuthenticationResponse isAdminAuthenticated(String uname, String pass) throws DynamoDBMappingException{

		AuthenticationResponse ReturnAuthResp = new AuthenticationResponse();
		AuthenticationResponse authResp = findByUserName(uname);
		if (authResp.isUserAuthResponse()==true) {
			logger.info("Inside isAdminAuthenticated service Imp ");
			if (authResp.getAdmin().getIsOwner().equalsIgnoreCase("Y") && pass.equals(authResp.getAdmin().getUserPassword()))
			{
				logger.info("Owner Authenticated! User: "+uname+ " Pass: " +pass);
				ReturnAuthResp.setUserAuthResponse(true);
				ReturnAuthResp.setAdmin(authResp.getAdmin());
			}	
			else
		{
			logger.info("Owner NOT Authenticated! User: "+uname+ " Pass: " +pass);
			ReturnAuthResp.setUserAuthResponse(false);
			ReturnAuthResp.setAdmin(null);
		}
		
	}
		return ReturnAuthResp;
	}// end of isAdminAuthenticated
	
	
	
	
	
	
	/*
	//boolean version
	public static boolean isEmpAuthenticated(String uname, String pass) throws DynamoDBMappingException{
		boolean result = false;
		AuthenticationResponse authResp = findByUserName(uname);
		if (authResp.isUserAuthResponse()==true) {
			logger.info("Inside isEmpAuthenticated service Imp ");
			if (authResp.getAdmin().getIsOwner().equalsIgnoreCase("N") && pass.equals(authResp.getAdmin().getUserPassword()))
			{
				logger.info("Emp Authenticated! User: "+uname+ " Pass: " +pass);
				result = true;
			}	
			else
		{
			logger.info("Emp NOT Authenticated! User: "+uname+ " Pass: " +pass);
			result = false;
		}
		
	}
		return result;	
	}//end of isEmpAuthenticated
	*/

	public static AuthenticationResponse isEmpAuthenticated(String uname, String pass) throws DynamoDBMappingException{
		AuthenticationResponse ReturnAuthResp = new AuthenticationResponse();
		AuthenticationResponse authResp = findByUserName(uname);
		if (authResp.isUserAuthResponse()==true) {
			logger.info("Inside isEmpAuthenticated service Imp ");
			if (authResp.getAdmin().getIsOwner().equalsIgnoreCase("N") && pass.equals(authResp.getAdmin().getUserPassword()))
			{
				logger.info("Emp Authenticated! User: "+uname+ " Pass: " +pass);
				ReturnAuthResp.setUserAuthResponse(true);
				ReturnAuthResp.setAdmin(authResp.getAdmin());
				
			}	
			else
		{
			logger.info("Emp NOT Authenticated! User: "+uname+ " Pass: " +pass);
			ReturnAuthResp.setUserAuthResponse(false);
			ReturnAuthResp.setAdmin(null);
		}
		
	}
		return ReturnAuthResp;	
	}//end of isEmpAuthenticated
	
	// returns list of all employees (Actice and Inactive)
		public List<Admin> getAllAdmin()  throws AmazonClientException{
			client.setEndpoint(ENDPOINT);
			List<Admin> allAdminList = new ArrayList<Admin>();
			DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
			Map<String, Condition> scanFilter = new HashMap<String, Condition>();
			Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
			scanFilter.put("username", scanCondition);
			scanExpression.setScanFilter(scanFilter);
			allAdminList = mapper.scan(Admin.class, scanExpression);
			return allAdminList;
		}
		// Update Admin using object
		public void updateAdmin(Admin admin) throws AmazonClientException  {
			DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES);
			try{
			mapper.save(admin, config);
			} catch (AmazonClientException e)
			{
				logger.error("Error occured in updateAdmin for "+admin);
				throw new AmazonClientException("Error occured in updateAdmin");
			}
		}
		
		// Delete Admin using primary key
		public void deleteAdmin(String AdmUserName) throws AmazonClientException{
			Admin emp = getAdmin(AdmUserName);
			try{
			mapper.delete(emp);
			}catch (AmazonClientException e)
			{
				logger.error("Error occured in deleteAdmin(String AdmUserName) for "+emp);
				throw new AmazonClientException("Error occured in deleteAdmin(String AdmUserName) ");
			}
		}
		
		// get Admin object using AdmUserName (Primay key)
		public Admin getAdmin(String AdmUserName) throws AmazonClientException {
			client.setEndpoint(ENDPOINT);
			DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
			Admin emp = mapper.load(Admin.class, AdmUserName, config);
			if (emp != null) {

			} else {
				throw new AmazonClientException("User does not exist in DB");
			}

			return emp;
		}
}
