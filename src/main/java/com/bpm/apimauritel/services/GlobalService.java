package com.bpm.apimauritel.services;

import java.util.Date;

public interface GlobalService {

	public int getDelaiException();
	public int getDelaiRetryException();
	public Date getDateException();
	public void setDateException(Date date);
	public void setNotification(Date notification);
	public Date getNotification();
}
