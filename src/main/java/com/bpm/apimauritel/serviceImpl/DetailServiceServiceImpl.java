package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.repositories.DetailServiceRepository;
import com.bpm.apimauritel.services.DetailServiceService;

@Service
public class DetailServiceServiceImpl implements DetailServiceService {

	public final Logger logger = LoggerFactory.getLogger(DetailServiceServiceImpl.class);
	
	@Autowired
	DetailServiceRepository detailServiceRepository;


	@Override
	public void save(DetailService detailService) throws Exception {
		// TODO Auto-generated method stub
		try {
			detailServiceRepository.save(detailService);
		} catch (Exception e) {
			// TODO: handle exception
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
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
		return detailServiceRepository.findDetailServiceByAmount(amount);
	}

	
	@Override
	public List<DetailService> getDetailServiceByAmountAndIdService(String amount,ServiceT serviceT) throws Exception {
		// TODO Auto-generated method stub
		return detailServiceRepository.getDetailServiceByAmountAndIdService(amount, serviceT);
	}

}
