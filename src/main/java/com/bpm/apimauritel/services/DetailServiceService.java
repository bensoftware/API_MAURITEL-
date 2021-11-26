package com.bpm.apimauritel.services;

import java.util.List;

import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;

public interface DetailServiceService {	
	
	public DetailService save(DetailService detailService) throws Exception;
	
	public void update(DetailService detailService) throws Exception;
	
	public DetailService findDetailServiceByDescription(String description) throws Exception;
	
	public  List<DetailService> findByService(ServiceT serviceT) throws Exception;
	
    public  List<DetailService>  findDetailServiceByAmount(String amount) throws Exception;
	
	
	public  List<DetailService>  getDetailServiceByAmountAndIdService(String amount,ServiceT serviceT) throws Exception;
	
	
	public  List<DetailService>  getDetailServiceByAmount(String amount) throws Exception;
	
	
	public  List<DetailService>  findAllDetailService() throws Exception;
	
	
	
}
