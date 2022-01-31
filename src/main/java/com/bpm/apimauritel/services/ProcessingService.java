package com.bpm.apimauritel.services;

import java.util.List;
import java.util.Set;

import com.bpm.apimauritel.entities.ServiceT;

public interface  ProcessingService {

	
	List<ServiceT> getServicesByAmount(String amount) throws Exception;
	
	Set<Double> listAmount()  throws Exception;
}
