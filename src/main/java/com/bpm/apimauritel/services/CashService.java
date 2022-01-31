package com.bpm.apimauritel.services;

import com.bpm.apimauritel.entities.ServiceT;

public interface CashService {
	
	public void saveService() throws Exception ;
	
	public void saveDetailService(ServiceT serviceT) throws Exception ;
	
	public void Upadate() throws Exception ;
	
	//public void SaveData() throws Exception ;
}
