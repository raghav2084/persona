package com.myfirst.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.myfirst.springmvc.model.Entity.Admin;
import com.myfirst.springmvc.model.Services.AdminServices;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	AdminServices adminservice;
	
	
	//testing to see how many times this is executed
		@PostConstruct
		public void init(){
		//logger.info("HomeController Init Method");	
		
		}
	
	
	//Entry point of the application.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		return "home";
	}
	
	//logout employee and redirect to login page
	@RequestMapping(value = {"emp/logout","own/logout"}, method = RequestMethod.GET)
	public String logoutUser(HttpSession session) {
		logger.info("Logging out user: " + session.getAttribute("loggedInUser"));
		session.removeAttribute("loggedInUser");
		return "redirect:/";
	}
	


	/**
	 * Does authentication of the users (both Admin & Emp)
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String UserLogin(@RequestParam String lg_username, @RequestParam String lg_password,
			@RequestParam String usertype, Model model, HttpSession session) {
		logger.info("Inside Authentication Call of the HomeController.");
		String returnPage="home";
		
		try{
					
					
					if(usertype.equals("iamemp") && adminservice.isEmpAuthenticated(lg_username, lg_password).isUserAuthResponse()==true)
					{
						logger.info("Employee Authentication Succeed for uname "+lg_username+ " pass: "+lg_password);
								//updating lastLogingDateTime
								Admin a = adminservice.isEmpAuthenticated(lg_username, lg_password).getAdmin();
								// for Fri 16 Oct, 2015
								Date d = new Date();
								String strDateFormat = "MMM d, yyyy hh:mm a";
								SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
								String today = sdf.format(d);
								a.setLastLoginDateTime(today);
								adminservice.updateAdmin(a);
								session.setAttribute("loggedInUser", a);
								
						returnPage="redirect:/emp/home";
						
					}
					else if (usertype.equals("iamadmin") && adminservice.isAdminAuthenticated(lg_username, lg_password).isUserAuthResponse()==true){
						logger.info("Owner Authentication Succeed for uname "+lg_username+ " pass: "+lg_password);
								//updating lastLogingDateTime
								Admin a = adminservice.isAdminAuthenticated(lg_username, lg_password).getAdmin();
								// for Fri 16 Oct, 2015
								Date d = new Date();
								String strDateFormat = "MMM d, yyyy hh:mm a";
								SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
								String today = sdf.format(d);
								a.setLastLoginDateTime(today);
								adminservice.updateAdmin(a);
						session.setAttribute("loggedInUser", a);
						returnPage="redirect:/own/home";						
					}
					else {
						logger.info("Authentication Failed for uname "+lg_username+ " pass: "+lg_password);
						model.addAttribute("loginError", "Incorrect Username or Password. Please try again");
						returnPage="home";
						
					}
		}catch(DynamoDBMappingException e)
					{
						logger.error("Exception occurred Authentication Failed for uname "+lg_username+ " pass: "+lg_password);
						logger.error(e.getMessage());
						model.addAttribute("loginError", "Incorrect Username or Password. Please try again");
					}
		
		return returnPage;
	}
	
	
	
	
}
