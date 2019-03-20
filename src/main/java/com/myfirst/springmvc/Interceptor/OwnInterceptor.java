package com.myfirst.springmvc.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.myfirst.springmvc.model.Entity.Admin;

public class OwnInterceptor implements HandlerInterceptor {

	 private static final Logger logger = LoggerFactory.getLogger(OwnInterceptor.class);
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//logger.info("In afterCompletion "+arg0.getRequestURL().toString());
		//logger.info("In afterCompletion "+arg0.getContextPath().toString());

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		//logger.info("In postHandle "+arg0.getRequestURL().toString());
		//logger.info("In postHandle "+arg0.getContextPath().toString());

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		//logger.info("In preHandle "+arg0.getRequestURL().toString());
		
		String login = arg0.getContextPath() +"/";
		logger.info("OwnInterceptor In preHandle "+arg0.getContextPath().toString());
		//arg0.getSession().getAttribute("loggedInUser");
		if(arg0.getSession().getAttribute("loggedInUser")==null ){			
			arg1.sendRedirect(login);
			return false;
		} else 
		{
			Admin a = (Admin) arg0.getSession().getAttribute("loggedInUser"); 
			if (a.getIsOwner().equalsIgnoreCase("n")){
				arg1.sendRedirect(login);
				return false;				
			}
		}	
		return true;
	}

}
