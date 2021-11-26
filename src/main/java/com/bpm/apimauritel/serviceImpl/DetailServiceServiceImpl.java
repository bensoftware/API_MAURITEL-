package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.repositories.DetailServiceRepository;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class DetailServiceServiceImpl implements DetailServiceService {

	public final Logger logger = LoggerFactory.getLogger(DetailServiceServiceImpl.class);
	
	@Autowired
	DetailServiceRepository detailServiceRepository;

	@Autowired
	ServiceService serviceService;

	@Override
	public DetailService save(DetailService detailService) throws Exception {
		// TODO Auto-generated method stub
		DetailService detService=null;
		try {
			detService=detailServiceRepository.saveAndFlush(detailService);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return detService;
	}

	@Override
	public void update(DetailService detailService) throws Exception {
		try {
			detailServiceRepository.save(detailService);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public DetailService findDetailServiceByDescription(String description) throws Exception {
		// TODO Auto-generated method stub
		DetailService detailService=null;
		try {
			detailService=	detailServiceRepository.findDetailServiceByDescription(description);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return detailService;
	}


	@Override
	public List<DetailService> findByService(ServiceT serviceT) throws Exception {
		// TODO Auto-generated method stub
		List<DetailService> listDeatDetailServices=new ArrayList<>();
		try {
			listDeatDetailServices=detailServiceRepository.findByService(serviceT);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			throw new Exception("EXCEPTION : " + e.getMessage());
		}
		return listDeatDetailServices;
	}

	@Override
	public List<DetailService> findDetailServiceByAmount(String amount) throws Exception {
		// TODO Auto-generated method stub
	      try {
	    		return detailServiceRepository.findDetailServiceByAmount(amount);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	
	@Override
	public List<DetailService> getDetailServiceByAmountAndIdService(String amount,ServiceT serviceT) throws Exception {
		// TODO Auto-generated method stub
		try {
			return detailServiceRepository.getDetailServiceByAmountAndIdService(amount, serviceT);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<DetailService> findAllDetailService() throws Exception {
		// TODO Auto-generated method stub
		try {
			return detailServiceRepository.findAll();
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}
	
	
	@Override
	public List<DetailService> getDetailServiceByAmount(String amount) throws Exception {
		// TODO Auto-generated method stub
        List<ServiceT> listServiceT = serviceService.getAllServices();
		List<DetailService> filteredServicesT = listServiceT
				.stream()
				.flatMap(serviceT -> serviceT.getDetailServices()
				.stream())
				.filter(detailService -> detailService.getAmount().equalsIgnoreCase(amount))
				.collect(Collectors.toList());
		return filteredServicesT;
	}

}
