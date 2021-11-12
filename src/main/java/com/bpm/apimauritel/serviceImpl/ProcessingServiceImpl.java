package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.ProcessingService;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class ProcessingServiceImpl implements ProcessingService {

	@Autowired
	ServiceService serviceService;
	
	@Autowired
	DetailServiceService detailServiceService;
	
	@Override
	public List<ServiceT> getServicesByAmount(String amount) throws Exception {
		// TODO Auto-generated method stub
		  List<ServiceT>  listServiceT=new ArrayList<>();
		try {
			  listServiceT=serviceService.getAllServices();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Exception : " + e.getMessage());
		}
      
		List<ServiceT>  newListService=new ArrayList<>();
		
		for (ServiceT serviceT : listServiceT) {
			List<DetailService> listDetailServices=new ArrayList<>();
			listDetailServices=serviceT.getDetailServices();
				for (DetailService detailServiceT : listDetailServices) {
					if(detailServiceT.getAmount().equals(amount)){
						//
					try {
						serviceT.setDetailServices(detailServiceService.getDetailServiceByAmountAndIdService(amount,serviceT));
						newListService.add(serviceT);
					}catch (Exception e) {
						// TODO: handle exception
						throw new Exception(e.getMessage());
					}
				  }
				}
				listDetailServices.clear();
		}
		return newListService;
	}

}
