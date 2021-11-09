package com.bpm.apimauritel.serviceImpl;

import java.util.Hashtable;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceService;
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
	DetailServiceService detailServiceService;

	List<ServiceDto> listService;

	@Override
	public void saveService() throws Exception {

		ServiceT serviceT = null;
		listService = rechargeService.getMarketingServices();

		Hashtable<String, String> serviceSaved = new Hashtable<>();

		for (ServiceDto serviceDto : listService) {
			// Also check in the Database if it exists
			if (!serviceSaved.contains(serviceDto.getService())) {
				serviceT = new ServiceT();
				serviceT.setCodeOperation(serviceDto.getCodeOperation());
				serviceT.setCodeService(serviceDto.getService());
				// serviceT.setDetailServices(listDetailService);
				if (serviceService.findServiceByCodeService(serviceDto.getService()) == null) {
					serviceT = serviceService.save(serviceT);
				}
				saveDetailService(serviceT);
			}
			serviceSaved.put(serviceDto.getCodeOperation(), serviceDto.getService());
		}
	}

	@Override
	public void saveDetailService(ServiceT serviceT) throws Exception {
		DetailService detailService = null;
		for (ServiceDto serviceDto : listService) {
			System.err.println("");
			if (serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
				detailService = new DetailService();
				detailService.setAmount(serviceDto.getAmount());
				detailService.setDescription(serviceDto.getDescription());
				detailService.setService(serviceT);
				try {
					detailService = detailServiceService.findDetailServiceByDescription(serviceDto.getDescription());
					if (detailService == null) {
						detailServiceService.save(detailService);
					} else {
						// logger.info('');
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		}
	}
	

}
