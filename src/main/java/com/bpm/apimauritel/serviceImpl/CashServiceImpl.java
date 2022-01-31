package com.bpm.apimauritel.serviceImpl;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.helpers.CashHelper;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceServiceT;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class CashServiceImpl implements CashService {

	final Logger logger = LoggerFactory.getLogger(CashServiceImpl.class);

	@Autowired
	ServiceService serviceService;

	@Autowired
	RechargeService rechargeService;

	@Autowired
	DetailServiceServiceT detailServiceServiceT;
	
	List<ServiceDto> listServiceFromMauritel;

	@Override
	public void saveService() throws Exception {

		ServiceT serviceT = null;
		
		listServiceFromMauritel = rechargeService.getMarketingServices();

		Hashtable<String, String> serviceSaved = new Hashtable<>();

		for (ServiceDto serviceDto : listServiceFromMauritel) {
							// CodeService == au code unique qui identifie un service venant de l'API MAURITEL
			if (!serviceSaved.contains(serviceDto.getService())){
				serviceT = new ServiceT();
				serviceT.setCodeOperation(serviceDto.getCodeOperation());
				serviceT.setCodeService(serviceDto.getService());
				ServiceT service=serviceService.findServiceByCodeService(serviceDto.getService());
				if(service==null) {
					
					 //Lorsqu'on ne le trouve pas........................: Comment bloquer    ?
					
				   //  Si ce service n'existe plus dans Mauritel service : Comment le savoir  ?
					
				 //    Comparaison entre ce qui est dans la base de donnée et ce qui vient de Mauritel Service.
				
			   //      Est ce que ce qui est dans la base de  donnée correspond à ce qui viens de Mauritel.
					
					serviceT = serviceService.save(serviceT);
					saveDetailService(serviceT);
				}else{
					service.setCodeOperation(serviceDto.getCodeOperation());
					service = serviceService.save(service);
					this.saveDetailService(service);
				}
			}
			serviceSaved.put(serviceDto.getCodeOperation(),serviceDto.getService());
		}
	}


	@Override
	public void saveDetailService(ServiceT serviceT) throws Exception {
		DetailService detailService = null;
		for (ServiceDto serviceDto : listServiceFromMauritel) {
								//System.err.println("");
			 if(serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
				detailService = new DetailService();
				detailService.setAmount(serviceDto.getAmount());
				detailService.setDescription(serviceDto.getDescription());
				detailService.setService(serviceT);
				try {
				DetailService detailServiceTest = detailServiceServiceT.findDetailServiceByDescription(serviceDto.getDescription());
					if(detailServiceTest == null){
						 //On insert le DetailService pour ce service,serviceT.	
						detailServiceServiceT.save(detailService);
					}else{
					  //On insert le DetailService pour ce service.
					 //Mise à jour des informations modifiées. 
						detailService.setAmount(serviceDto.getAmount());
						detailService.setDescription(serviceDto.getDescription());
						detailService.setService(serviceT);
						detailServiceServiceT.save(detailService);
					}
				}catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		}
	}

	

	@Override
	public void Upadate() throws Exception {
		           
	    List<ServiceT>	listServicesFromDatabase=serviceService.getAllServices();

	                   //Verifier si un Service dans la base de donnée existe chez MAURITEL.

	    Hashtable<String, ServiceT> serviceTempo = new Hashtable<>();
	    Hashtable<String, ServiceT> serviceLoop = CashHelper.listToHashTableServiceT(listServicesFromDatabase);
	    		// getting keySet() into Set
        Set<String> setOfServiceCodeService = serviceLoop.keySet();
        
	    listServiceFromMauritel =rechargeService.getMarketingServices();
	
	    ServiceT serviceTForUpdate =null;
	    
	    //Mise à Jour
	    if(listServicesFromDatabase.size()==listServiceFromMauritel.size() || listServiceFromMauritel.size()>listServicesFromDatabase.size()){
	  //Save And Update
	    }else if(listServicesFromDatabase.size()>listServiceFromMauritel.size()){
    //Blocage du service au niveau de la base de donnée Local si le service n'existe plus chez MAURITEL
	    		this.deActivateService();
		}
	}
	
	private void deActivateService() throws Exception {
		 	List<ServiceT>	listServicesFromDatabase=serviceService.getAllServices();
       // Verifier si un Service dans la base de donnée existe chez MAURITEL.
		    listServiceFromMauritel =rechargeService.getMarketingServices();
		    Hashtable<String, ServiceDto> HtableFromMauritel = CashHelper.listToHashTableServiceDto(listServiceFromMauritel);
		    Hashtable<String, ServiceT>   serviceLoop = CashHelper.listToHashTableServiceT(listServicesFromDatabase);
	        for(ServiceT serviceT : listServicesFromDatabase){
	        		if(!HtableFromMauritel.contains(serviceT.getCodeService())){
	        		    ServiceT serviceUPDATE=serviceLoop.get(serviceT.getCodeService());
	        		    serviceUPDATE.setNotActivated(false);
	        		    serviceService.save(serviceUPDATE);
	        		}
			} 
	}


	
	
	
	
	
	
	
}
