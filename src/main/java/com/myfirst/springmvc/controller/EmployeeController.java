package com.myfirst.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.Payment;
import com.myfirst.springmvc.model.Services.EmployeeServices;
import com.myfirst.springmvc.model.Services.PaymentServices;

/**
 * Handles requests for the application home page.
 */
@Controller
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@Autowired
	EmployeeServices empServices ;
	@Autowired
	PaymentServices payServices;
	
	
	
	//to initialize the controller with getting empnames only once
	@PostConstruct
	public void init(){
	logger.info("EmployeeController Init Method");	
	//allEmpNames = empServices.getAllEmpolyeesNames();
	}
	
	
	
	
	
	@RequestMapping(value = {"/emp/home", "/home"}, method = RequestMethod.GET)
	public String rediretToEmployeePageAfterSucessfullEmpLogin(Model model) {
		logger.info("rediretToEmployeePageAfterSucessfullEmpLogin");
		List<Employees> allActiveEmp = new ArrayList<Employees>();
		List<Payment> allpayments = new ArrayList<Payment>();
		allActiveEmp= empServices.getAllActiveEmployees();
		logger.info("rediretToEmployeePageAfterSucessfullEmpLogin size: "+allActiveEmp.size());
		allpayments = payServices.getAllPayments();
		model.addAttribute("AllEmpNames", allActiveEmp);
		model.addAttribute("paymentsList",allpayments);
		
		
		return "employee/employeeLoginView";
	}
	
	
	@RequestMapping(value = "emp/contact", method = RequestMethod.GET)
	public String EmployeeContactPage( HttpServletRequest request) {
		logger.info("EmployeeContactPage");
		logger.info("request.getRequestURI(): "+request.getRequestURI());
		logger.info("request.getContextPath(): "+request.getContextPath());
		logger.info("request.getQueryString(): "+request.getServletPath());
		return "employee/empContactPage";
	}
	

}
