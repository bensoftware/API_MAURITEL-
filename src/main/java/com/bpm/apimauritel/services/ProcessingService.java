package com.bpm.apimauritel.services;

import java.util.List;

import com.bpm.apimauritel.entities.ServiceT;

public interface  ProcessingService {

	
	List<ServiceT> getServicesByAmount(String amount) throws Exception;
}
