package com.myfirst.springmvc.model.Entity;

public class ServiceType {

	
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public String toString() {
		return "ServiceType [serviceName=" + serviceName + "]";
	}
}
