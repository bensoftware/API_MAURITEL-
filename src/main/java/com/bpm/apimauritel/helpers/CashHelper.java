package com.bpm.apimauritel.helpers;

import java.util.Hashtable;
import java.util.List;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.ServiceService;

public class CashHelper {

	public static void  SaveDetailService(ServiceT serviceT,List<ServiceDto> listService,DetailServiceService detailServiceService) throws Exception {
		
		 DetailService detailService=null;
			for(ServiceDto serviceDto : listService) {
				//System.err.println("");
				if(serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
					 detailService =new DetailService();
					 detailService.setAmount(serviceDto.getAmount());
					 detailService.setDescription(serviceDto.getDescription()); 
					 detailService.setService(serviceT);
					 try {
						 detailService=detailServiceService.findDetailServiceByDescription(serviceDto.getDescription());
						 if(detailService==null){
							 detailServiceService.save(detailService);
						 }else{
							 // logger.info('');
						 }
					}catch (Exception e) {
						throw new Exception(e.getMessage());
					}
				}
			}
	}
	
	
	public static  Hashtable<String,ServiceT> listToHashTableServiceT(List<ServiceT> listServiceT){
	    Hashtable<String, ServiceT> serviceTempo = new Hashtable<>();
	    for(ServiceT serviceT : listServiceT){
	    	serviceTempo.put(serviceT.getCodeService(), serviceT);
		}
		return serviceTempo;
	}
	
	
	public static  Hashtable<String,ServiceDto> listToHashTableServiceDto(List<ServiceDto> listServiceDto){
	    Hashtable<String, ServiceDto> serviceTempo = new Hashtable<>();
	    for(ServiceDto serviceDto : listServiceDto){
	    	serviceTempo.put(serviceDto.getService(),serviceDto);
		}
		return serviceTempo;
	}
	
	
	
}
