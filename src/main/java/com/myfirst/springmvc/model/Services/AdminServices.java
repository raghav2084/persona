package com.myfirst.springmvc.model.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.myfirst.springmvc.model.Entity.Admin;
import com.myfirst.springmvc.model.Entity.AuthenticationResponse;
import com.myfirst.springmvc.model.ServicesImpl.AdminServicesImpl;

@Service
public class AdminServices {

	@Autowired
	private AdminServicesImpl adminServicesImpl;

	public List<Admin> getAllAdmins() {
		return new ArrayList<Admin>();
	}

	public Admin findByUserName(String uname) {

		return new Admin();
	}

	public void addAdmin(Admin admin) {

	}

	/*
	 * public boolean isAdminAuthenticated(String uname, String pass) throws
	 * DynamoDBMappingException{ return
	 * AdminServicesImpl.isAdminAuthenticated(uname, pass); }
	 */

	public AuthenticationResponse isAdminAuthenticated(String uname, String pass) throws DynamoDBMappingException {
		return AdminServicesImpl.isAdminAuthenticated(uname, pass);
	}

	/*
	 * public boolean isEmpAuthenticated(String uname, String pass) throws
	 * DynamoDBMappingException{ return
	 * AdminServicesImpl.isEmpAuthenticated(uname, pass); }
	 */
	public AuthenticationResponse isEmpAuthenticated(String uname, String pass) throws DynamoDBMappingException {
		return AdminServicesImpl.isEmpAuthenticated(uname, pass);
	}

	// returns list of all employees (Actice and Inactive)
	public List<Admin> getAllAdmin() throws AmazonClientException {
		List<Admin> allAdminList = adminServicesImpl.getAllAdmin();
		return allAdminList;
	}

	public void updateAdmin(Admin admin) throws AmazonClientException {
		adminServicesImpl.updateAdmin(admin);
	}

	// Delete Admin using primary key
	public void deleteAdmin(String AdmUserName) throws AmazonClientException {
		adminServicesImpl.deleteAdmin(AdmUserName);
	}
}
