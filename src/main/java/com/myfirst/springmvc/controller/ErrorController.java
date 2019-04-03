package com.myfirst.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.myfirst.springmvc.model.Entity.Admin;

@Controller
public class ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest, HttpSession session) {
    	Admin sessionUser = (Admin) session.getAttribute("loggedInUser");
    	ModelAndView errorPage = new ModelAndView("errors/errorPage");
        String RedirectMsg = "";
        String ErrorType ="";
        String Header = "";
        String RedirectURL = "";
        int httpErrorCode = getErrorCode(httpRequest);
        logger.info("Inside ErrorController Error Code " +httpErrorCode);
        logger.info("Inside getRequestURL " +httpRequest.getRequestURL());
        logger.info("Inside getRequestURI " +httpRequest.getRequestURI());
        //logger.info("logged in user: " + sessionUser);	
    	if(sessionUser != null && sessionUser.getIsOwner().equalsIgnoreCase("y")){
    		//logger.info("Error by auth user - OWNER: " + sessionUser);	
    		Header = "../include/OwnHeader.jsp";
    		RedirectMsg = "Back to Home";
    		RedirectURL="home";
    	}
    	else if(sessionUser != null && sessionUser.getIsOwner().equalsIgnoreCase("n")){
    		//logger.info("Error by auth user - EMP: " + sessionUser);
    		Header = "../include/empHeader-without-jum.jsp";
    		RedirectMsg = "Back to Home";
    		RedirectURL="home";
    	}else{
    		logger.info("Error by un-auth user" + sessionUser);
    		RedirectMsg = "Back to Login";
    		RedirectURL="/springmvc/";
    		
    	}
 
        switch (httpErrorCode) {
            case 400: {
            	
            	ErrorType = "400. Sorry, the page you tried cannot be found";
                break;
            }
            case 401: {
            	ErrorType = "401. Sorry, the page you tried cannot be found";
                break;
            }
            case 404: {
            	ErrorType = "404. Sorry, the page you tried cannot be found";
            	
                break;
            }
            case 500: {
            	ErrorType = "500. Sorry, the page you tried cannot be found";
                break;
            }
        }
        errorPage.addObject("RedirectMsg", RedirectMsg);
        errorPage.addObject("ErrorType", ErrorType);
        errorPage.addObject("Header", Header);
        errorPage.addObject("RedirectURL", RedirectURL);
        return errorPage;
    }
     
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
          .getAttribute("javax.servlet.error.status_code");
    }
}