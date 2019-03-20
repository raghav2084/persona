package com.myfirst.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myfirst.springmvc.model.Entity.Admin;
import com.myfirst.springmvc.model.Entity.Employees;
import com.myfirst.springmvc.model.Entity.Payment;
import com.myfirst.springmvc.model.Services.AdminServices;
import com.myfirst.springmvc.model.Services.EmployeeServices;
import com.myfirst.springmvc.model.Services.PaymentServices;

@Controller
public class OwnerController {

	private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);

	@Autowired
	EmployeeServices empServices;
	@Autowired
	PaymentServices payServices;
	@Autowired
	AdminServices AdmServices;

	@RequestMapping(value = { "/own/home", "/home" }, method = RequestMethod.GET)
	public String rediretToOwnerPageAfterSucessfullEmpLogin(Model model, HttpSession session) {
		logger.info("rediretToOwnerPageAfterSucessfullEmpLogin");
		List<Employees> allEmpNames = new ArrayList<Employees>();
		allEmpNames = empServices.getAllActiveEmployees();
		logger.info("rediretToOwnerPageAfterSucessfullEmpLogin Active Emp Size: " + allEmpNames.size());
		session.setAttribute("AllEmpNames", allEmpNames);

		List<Payment> allpayments = new ArrayList<Payment>();
		allpayments = payServices.getAllPayments();
		model.addAttribute("paymentsList", allpayments);
		return "owner/ownerLoginView";
	}

	// Directs to Add/remove user
	@RequestMapping(value = { "/own/manageemp" }, method = RequestMethod.GET)
	public String addRemoveEmployees(Model model, HttpSession session) {
		logger.info("addRemoveEmployees");
		List<Employees> allEmp = new ArrayList<Employees>();
		allEmp = empServices.getAllEmpolyees();
		logger.info("addRemoveEmployees size: " + allEmp.size());
		model.addAttribute("AllEmp", allEmp);

		// USed to update the Nav bar dropdown with all recent changes
		List<Employees> allEmpNames = new ArrayList<Employees>();
		allEmpNames = empServices.getAllActiveEmployees();
		session.setAttribute("AllEmpNames", allEmpNames);
		return "owner/addRemoveEmployees";
	}

	// Add new employee POST method
	@RequestMapping(value = { "/own/addNewEmp" }, method = RequestMethod.POST)
	public String addNewEmployee(Employees emp, Model model) {
		logger.info("addNewEmployee");
		logger.info("addNewEmployee Added:" + emp);
		empServices.SaveEmployee(emp);
		return "redirect:/own/manageemp";
	}

	// Editing employee POST method
	@RequestMapping(value = { "/own/editEmp" }, method = RequestMethod.POST)
	public String EditEmployee(@RequestParam String editEmpModalHiddenEmpNum, Employees emp,
			RedirectAttributes redirectAttrs) {
		logger.info("EditingEmployee :" + emp);
		logger.info("EditingEmployee empNumber hidden: " + editEmpModalHiddenEmpNum);
		// Phone Number (PK) has NOT changed
		if (editEmpModalHiddenEmpNum.equals(emp.getEmpPhoneNum())) {
			logger.info("EditingEmployee Phone number  NOT changed:");
			empServices.UpdateEmployee(emp);
		}
		// Phone Number (PK) has changed
		else {
			logger.info("EditingEmployee Phone number changed:");
			Employees itemRetrieved = empServices.getEmployee(editEmpModalHiddenEmpNum);
			logger.info("itemRetrieved :" + itemRetrieved);
			empServices.DeleteEmployee(itemRetrieved);
			empServices.SaveEmployee(emp);
		}

		return "redirect:/own/manageemp";
	}

	// Deleting employee POST method
	@RequestMapping(value = { "/own/deleteEmp" }, method = RequestMethod.POST)
	public String DeleteEmployee(@RequestParam String deleteEmpModalHiddenEmpNum, String uname,
			RedirectAttributes redirectAttrs) {
		logger.info("DeletingEmployee :" + deleteEmpModalHiddenEmpNum);
		empServices.DeleteEmployee(deleteEmpModalHiddenEmpNum);
		redirectAttrs.addFlashAttribute("DELETED", "DELETED");
		redirectAttrs.addFlashAttribute("Uname", uname);
		logger.info("DeletingEmployee Completed for user:" + deleteEmpModalHiddenEmpNum);
		return "redirect:/own/manageemp";
	}

	@RequestMapping(value = { "/own/empreport" }, method = RequestMethod.GET)
	public String empReport(@RequestParam String num, Model model) {
		logger.info("empReport :" + num);
		Date d = new Date();
		// for Oct 16, 2015 used for printing only
		String strDateFormat1 = "MMM d, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		String today = sdf.format(d);
		

		DateTime todayInISO = new DateTime(d);
		todayInISO  = todayInISO.withTime(0, 0, 0, 0);
		//String todayInISOString = todayInISO.toString();
		
		DateTime nextDayinISO = todayInISO.plusDays(1);
		//String nextDayinISOString = nextDayinISO.toString();
		
		Employees emp = new Employees();
		emp = empServices.getEmployee(num);
		logger.info("empReport emp found: " + emp);
		List<Payment> p = new ArrayList<Payment>();
		p = payServices.getAllPaymentsOfEmployeeByDate(emp, todayInISO, nextDayinISO);
		if (p.size() != 0) {
			// set the message to show current date
			model.addAttribute("msg", "Today : " + today);
			model.addAttribute("paymentsList", p);
		} else {
			model.addAttribute("msg", "Today : " + today);
			model.addAttribute("NoResultsFound", "No payments found for " + today);

		}
		model.addAttribute("Emp", emp);
		//model.addAttribute("paymentsList", p);
		// session.setAttribute("AllEmpNames", allEmpNames);
		return "owner/employeeReport";
	}
	
	
	

	// to run employee report of the day
	@RequestMapping(value = { "/own/empreporttoday" })
	public String empReportToday(@RequestParam String num, Model model) {
		logger.info("empReportToday :" + num);
		Employees emp = new Employees();
		emp = empServices.getEmployee(num);
		logger.info("empReport emp found: " + emp);
		List<Payment> p = new ArrayList<Payment>();
		p = payServices.findPaymentsByEmployee(emp.getEmpName());
		model.addAttribute("Emp", emp);
		model.addAttribute("paymentsList", p);

		Date d = new Date();
		// for Fri, 16 Oct, 2015
		String strDateFormat1 = "EEE d MMM, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		String today = sdf.format(d);
		// set the message to show current date
		model.addAttribute("msg", "Today : " + today);
		// session.setAttribute("AllEmpNames", allEmpNames);
		return "owner/employeeReport";
	}

	// to run employee report for past week
	@RequestMapping(value = { "/own/empreportweek" })
	public String empReportWeek(@RequestParam String num, Model model) {
		logger.info("empReportWeek :" + num);
		Employees emp = new Employees();
		emp = empServices.getEmployee(num);
		logger.info("empReport emp found: " + emp);
		List<Payment> p = new ArrayList<Payment>();
		p = payServices.findPaymentsByEmployee(emp.getEmpName());
		model.addAttribute("Emp", emp);
		model.addAttribute("paymentsList", p);

		Date d = new Date();
		long oneWeekAgo = (d.getTime() - (7L * 24L * 60L * 60L * 1000L)); // One
																			// week
																			// ago.
		// for Fri 16 Oct, 2015
		String strDateFormat1 = "d MMM, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		String today = sdf.format(d);
		String oneWEEKAGO = sdf.format(oneWeekAgo);
		// set the message to show current date
		model.addAttribute("msg", "Past week : " + today + " - " + oneWEEKAGO);
		// session.setAttribute("AllEmpNames", allEmpNames);
		return "owner/employeeReport";
	}

	// to run employee report for past two week
	@RequestMapping(value = { "/own/empreporttwweek" })
	public String empReportTwoWeek(@RequestParam String num, Model model) {
		logger.info("empReportWeek :" + num);
		Employees emp = new Employees();
		emp = empServices.getEmployee(num);
		logger.info("empReport emp found: " + emp);
		List<Payment> p = new ArrayList<Payment>();
		p = payServices.findPaymentsByEmployee(emp.getEmpName());
		model.addAttribute("Emp", emp);
		model.addAttribute("paymentsList", p);

		Date d = new Date();
		long oneWeekAgo = (d.getTime() - (14L * 24L * 60L * 60L * 1000L)); // two
																			// week
																			// ago.
		// for 16 Oct, 2015
		String strDateFormat1 = "d MMM, yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat1);
		String today = sdf.format(d);
		String twoWEEKAGO = sdf.format(oneWeekAgo);
		// set the message to show current date
		model.addAttribute("msg", "Past two weeks : " + today + " - " + twoWEEKAGO);
		// session.setAttribute("AllEmpNames", allEmpNames);
		return "owner/employeeReport";
	}
	
	
	// Directs to Add/remove user
		@RequestMapping(value = { "/own/manageadmins" }, method = RequestMethod.GET)
		public String addRemoveAdmins(Model model, HttpSession session) {
			logger.info("addRemoveAdmins");
			 
			if(session.getAttribute("loggedInUser") != null){
				Admin a = (Admin) session.getAttribute("loggedInUser") ;
				if(a.getIsOwner().equalsIgnoreCase("y") && a.getUserName().equalsIgnoreCase("raghav2084"))
				{
					logger.info("addRemoveAdmins logged in Admin is raghav2084");
					List<Admin> allAdm = new ArrayList<Admin>();
					allAdm = AdmServices.getAllAdmin();
					logger.info("addRemoveAdmins size: " + allAdm.size());
					model.addAttribute("AllAdm", allAdm);
					
					return "owner/addRemoveAdmins";
				}else{
					logger.info("addRemoveAdmins logged in Admin is NOT raghav2084");
					model.addAttribute("ErrorType", "Not Authorized");
					model.addAttribute("ErrorMsg", "You are not authorized to access this page");
					return "errors/addRemoveAdminsError";
				}
			}
			else{
				logger.info("addRemoveAdmins NO Logged in ADMIN");
				model.addAttribute("ErrorType", "Not Authorized");
				model.addAttribute("ErrorMsg", "You are not authorized to access this page");
				return "errors/addRemoveAdminsError";
			}
			
		}
		
		
		// Editing Admin POST method
		@RequestMapping(value = { "/own/editAdmin" }, method = RequestMethod.POST)
		public String EditAdmin(@RequestParam String editAdmModalHiddenAdmUname, Admin adm,
				RedirectAttributes redirectAttrs) {
			logger.info("EditAdmin :" + adm);
			logger.info("EditAdmin Admin UserName hidden: " + editAdmModalHiddenAdmUname);
			AdmServices.updateAdmin(adm);

			return "redirect:/own/manageadmins";
		}

		
		// Deleting Admin POST method
		@RequestMapping(value = { "/own/deleteAdmin" }, method = RequestMethod.POST)
		public String DeleteAdmin(@RequestParam String deleteAdmModalHiddenAdmName, RedirectAttributes redirectAttrs) {
			logger.info("DeleteAdmin :" + deleteAdmModalHiddenAdmName);
			AdmServices.deleteAdmin(deleteAdmModalHiddenAdmName);
			logger.info("DeletingEmployee Completed for user:" + deleteAdmModalHiddenAdmName);
			return "redirect:/own/manageadmins";
		}
}
