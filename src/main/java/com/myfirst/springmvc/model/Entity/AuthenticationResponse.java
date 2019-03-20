package com.myfirst.springmvc.model.Entity;

import com.myfirst.springmvc.model.Entity.Admin;
public class AuthenticationResponse {
	
	private boolean UserAuthResponse;
	private Admin admin;
	public boolean isUserAuthResponse() {
		return UserAuthResponse;
	}
	public void setUserAuthResponse(boolean userAuthResponse) {
		UserAuthResponse = userAuthResponse;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	@Override
	public String toString() {
		return "AuthenticationResponse [UserAuthResponse=" + UserAuthResponse + ", admin=" + admin + "]";
	}
	
	

}
