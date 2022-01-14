package com.bpm.apimauritel.serviceImpl;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.services.GlobalService;

@Service
public class GlobaleServiceImpl  implements GlobalService{

	@Value("${delai.exception}")
    private	int delai; // minute
	
	@Value("${delai.retry.exception}")
	private	int delaiRetry; // minute
	
	private	Date dateException=null;
	
	private	Date dateNotification=null;

	@Override
	public int getDelaiException() {
		// TODO Auto-generated method stub
		return delai;
	}

	@Override
	public Date getDateException() {
		// TODO Auto-generated method stub
		return dateException;
	}

	@Override
	public void setDateException(Date date) {
		// TODO Auto-generated method stub
		this.dateException=date;
	}

	@Override
	public int getDelaiRetryException() {
		// TODO Auto-generated method stub
		return delaiRetry;
	}

	
	@Override
	public void setNotification(Date notification) {
		this.dateNotification=notification;
	}

	@Override
	public Date getNotification() {
		// TODO Auto-generated method stub
		return dateNotification;
	}
	
		
}
