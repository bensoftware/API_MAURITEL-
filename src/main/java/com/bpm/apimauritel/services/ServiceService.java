package com.bpm.apimauritel.services;

import java.util.List;

import com.bpm.apimauritel.entities.ServiceT;

public interface ServiceService {

	public ServiceT save(ServiceT service) throws Exception;
	
	public void update(ServiceT service) throws Exception;

	public void getServiceServiceByCodeOperation(ServiceT service) throws Exception;

	public List<ServiceT> getAllServices() throws Exception;
	
	public ServiceT findServiceByCodeService(String codeOperation) throws Exception;

}
