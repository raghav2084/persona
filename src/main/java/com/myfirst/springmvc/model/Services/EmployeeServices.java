package com.myfirst.springmvc.model.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.ServicesImpl.EmployeeServicesImpl;

@Service
public class EmployeeServices {
	
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServices.class);
	@Autowired
	private EmployeeServicesImpl employeeServicesImpl;

	// retuns a list of String (Names) of all employees
	public List<String> getAllEmpolyeesNames() {
		return employeeServicesImpl.getAllEmployeesNames();
	}

	// retuns a list of String (Names) of all active employees
	public List<String> getAllActiveEmpolyeesNames() {
		return employeeServicesImpl.getAllActiveEmployeesNames();
	}

	// retuns a list of all employees
	public List<Employees> getAllEmpolyees() {
		List<Employees> a = employeeServicesImpl.getAllEmployees();
		//Collections.sort(a);
		 return a;
	}

	public void SaveEmployee(Employees emp) {
		employeeServicesImpl.saveEmployee(emp);
	}

	public void DeleteEmployee(String empPhoneNum) {
		employeeServicesImpl.deleteEmployee(empPhoneNum);
	}

	// Delete Employee using employee object
	public void DeleteEmployee(Employees emp) {
		employeeServicesImpl.deleteEmployee(emp);
	}

	public Employees getEmployee(String empNum) throws DynamoDBMappingException {
		Employees emp = new Employees();
		emp = employeeServicesImpl.getEmployee(empNum);
		return emp;
	}

	public void saveEmployee(String empName, String empNum, String empSex, String empStatus) {
		employeeServicesImpl.saveEmployee(empName, empNum, empSex, empStatus);
	}

	public void UpdateEmployee(Employees emp) {
		employeeServicesImpl.updateEmployee(emp);
	}

	// returns list of all ACTIVE employees
	public List<Employees> getAllActiveEmployees() {
		List<Employees> empList = new ArrayList<Employees>();
		empList = employeeServicesImpl.getAllActiveEmployees();
		logger.info("getAllActiveEmployees size: "+empList.size());
		return empList;
	}
	
	
}
