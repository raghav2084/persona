package com.myfirst.springmvc.model.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfirst.springmvc.model.Entity.LastSavedPK;
import com.myfirst.springmvc.model.ServicesImpl.LastSavedPKTableServiceImpl;

@Service
public class LastSavedPKTableService {
	
	private static final Logger logger = LoggerFactory.getLogger(LastSavedPKTableService.class);
	
	@Autowired
	LastSavedPKTableServiceImpl impl;
	
	
	public void saveAndUpdateLastSavedPKCount (LastSavedPK LastSavedPK){
		impl.saveAndUpdateLastSavedPKCount(LastSavedPK);
	}
	
	public LastSavedPK getLastSavedPKCount (String tablePKName){
		LastSavedPK result = new LastSavedPK();
		result = impl.getLastSavedPKCount(tablePKName);
		return result;
	}


}
