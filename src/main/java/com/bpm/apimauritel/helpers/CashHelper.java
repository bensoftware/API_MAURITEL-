package com.bpm.apimauritel.helpers;

import java.util.List;

import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.DetailServiceService;

public class CashHelper {

	
	public static void  SaveDetailService(ServiceT serviceT,List<ServiceDto> listService,DetailServiceService detailServiceService) throws Exception {
		
		 DetailService detailService=null;
			for(ServiceDto serviceDto : listService) {
				System.err.println("");
				if(serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
					 detailService =new DetailService();
					 detailService.setAmount(serviceDto.getAmount());
					 detailService.setDescription(serviceDto.getDescription()); 
					 detailService.setService(serviceT);
					 try {
						 if(detailServiceService.findDetailServiceByDescription(serviceDto.getDescription())==null) {
							 detailServiceService.save(detailService);
						 }else{
							 // logger.info('');
						 }
					} catch (Exception e) {
						throw new Exception(e.getMessage());
					}
				}
				
				// listDetailService.add(detailService);
			}
			
	}
}
