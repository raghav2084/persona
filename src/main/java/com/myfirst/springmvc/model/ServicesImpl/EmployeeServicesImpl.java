package com.myfirst.springmvc.model.ServicesImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.myfirst.springmvc.model.Entity.Employees;

@Repository
public class EmployeeServicesImpl {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServicesImpl.class);
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

	// returns list of names(string) of all employees
	public List<String> getAllEmployeesNames() {
		List<Employees> empList = getAllEmployees();
		List<String> allEmpList = new ArrayList<String>();
		for (int i = 0; i < empList.size(); i++) {
			allEmpList.add(empList.get(i).getEmpName());
		}
		return allEmpList;
	}

	// returns list of all employees (Actice and Inactive)
	public List<Employees> getAllEmployees()  throws AmazonClientException{
		
		client.setEndpoint(ENDPOINT);
		List<Employees> allEmpList = new ArrayList<Employees>();
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> scanFilter = new HashMap<String, Condition>();
		Condition scanCondition = new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL);
		scanFilter.put("empPhoneNum", scanCondition);
		scanExpression.setScanFilter(scanFilter);
		allEmpList = mapper.scan(Employees.class, scanExpression);
		return allEmpList;
	}

	// returns list of all ACTIVE employees
	public List<Employees> getAllActiveEmployees()  throws AmazonClientException{
		client.setEndpoint(ENDPOINT);
		String tableName = "Employees";
		List<Employees> allEmpList = new ArrayList<Employees>();
		Table table = dynamoDB.getTable(tableName);

		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":pr", "Active");

		ItemCollection<ScanOutcome> items = table.scan("empStatus = :pr", // FilterExpression
				"empName, empSex, empPhoneNum, empStatus", //ProjectionExpression
				null, // ExpressionAttributeNames - not used in this example
				expressionAttributeValues);

		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Employees e = new Employees();
			//logger.info(iterator.next());
			Item item = iterator.next();
			
			e.setEmpName(item.getString("empName"));
			e.setEmpPhoneNum(item.getString("empPhoneNum"));
			e.setEmpSex(item.getString("empSex"));
			e.setEmpStatus(item.getString("empStatus"));
			allEmpList.add(e);			
		}
		
		//return null;
		return allEmpList;
	}

	// Add new Employee
	public void saveEmployee(Employees emp) throws AmazonClientException {
		emp.setEmpStatus("Active");
		mapper.save(emp);
	}

	// Add new Employee using String att
	public void saveEmployee(String empName, String empNum, String empSex, String empStatus) throws AmazonClientException{
		Employees emp = new Employees();
		emp.setEmpName(empName);
		emp.setEmpPhoneNum(empNum);
		emp.setEmpSex(empSex);
		emp.setEmpStatus(empStatus);
		mapper.save(emp);
	}

	// Delete Employee using primary key
	public void deleteEmployee(String empPhoneNum) throws AmazonClientException{
		Employees emp = getEmployee(empPhoneNum);
		try{
		mapper.delete(emp);
		}catch (AmazonClientException e)
		{
			logger.error("Error occured in deleteEmployee(String empPhoneNum) for "+emp);
			throw new AmazonClientException("Error occured in (String empPhoneNum)");
		}
	}

	// Delete Employee using employee object
	public void deleteEmployee(Employees emp) throws AmazonClientException{
		try{
			mapper.delete(emp);
			}catch (AmazonClientException e)
			{
				logger.error("Error occured in deleteEmployee(Employees emp) for "+emp);
				throw new AmazonClientException("Error occured in deleteEmployee(Employees emp)");
			}
	}

	// get employee object using PhoneNum (Primay key)
	public Employees getEmployee(String EmpPhone) throws AmazonClientException {
		client.setEndpoint(ENDPOINT);
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
		Employees emp = mapper.load(Employees.class, EmpPhone, config);
		if (emp != null) {

		} else {
			throw new AmazonClientException("User does not exist in DB");
		}

		return emp;
	}

	public boolean doesEmployeeExist(String empPhNum) {
		boolean result = false;

		return result;

	}

	// Update Employee using object
	public void updateEmployee(Employees emp) throws AmazonClientException  {
		DynamoDBMapperConfig config = new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.CLOBBER);
		try{
		mapper.save(emp, config);
		} catch (AmazonClientException e)
		{
			logger.error("Error occured in updateEmployee for "+emp);
			throw new AmazonClientException("Error occured in updateEmployee");
		}
	}
	
	
	//List of names (String) of all Active Employee
	public List<String> getAllActiveEmployeesNames() throws AmazonClientException{
	List<Employees> empList = getAllActiveEmployees();
		List<String> allEmpList = new ArrayList<String>();
		for (int i = 0; i < empList.size(); i++) {
			allEmpList.add(empList.get(i).getEmpName());
		}
		return allEmpList;		
		
		//return null;
		
		
	}

}
