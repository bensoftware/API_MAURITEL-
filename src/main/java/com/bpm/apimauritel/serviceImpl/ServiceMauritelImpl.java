package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.ServiceMauritel;
import com.bpm.apimauritel.repositories.ServiceMauritelRepository;
import com.bpm.apimauritel.services.ServiceMauritelService;

@Service
public class ServiceMauritelImpl implements ServiceMauritelService {

	final Logger logger = LoggerFactory.getLogger(ServiceMauritelImpl.class);
	
	
	@Autowired
	ServiceMauritelRepository serviceMauritelRepository;
	
	@Override
	public ServiceMauritel save(ServiceMauritel serviceMauritel) throws Exception {
		// TODO Auto-generated method stub
		ServiceMauritel serviceMauritelOut=null;
		try {
			serviceMauritelOut=serviceMauritelRepository.save(serviceMauritel);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("EXCEPTION SAVE METHOD " + e.getMessage());
		}
		return serviceMauritelOut;
	}

	@Override
	public void update(ServiceMauritel serviceMauritel) throws Exception {
		// TODO Auto-generated method stub
		try {
			serviceMauritel=serviceMauritelRepository.save(serviceMauritel);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("EXCEPTION UPDATE METHOD " + e.getMessage());
		}
	}

	@Override
	public List<ServiceMauritel> findAllServices() throws Exception {
		// TODO Auto-generated method stub
		List<ServiceMauritel> listOfServieMaurtels=new ArrayList<>();
		try {
		      listOfServieMaurtels=	serviceMauritelRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("EXCEPTION FINDALL() METHOD " + e.getMessage());
		}
		return listOfServieMaurtels;
	}

}
