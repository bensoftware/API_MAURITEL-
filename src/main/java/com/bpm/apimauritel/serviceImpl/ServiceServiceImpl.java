package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.repositories.ServiceRepository;
import com.bpm.apimauritel.services.ServiceService;
import com.bpm.apimauritel.services.TransactionPayementService;

@Service
public class ServiceServiceImpl implements ServiceService{
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Override
	public ServiceT save(ServiceT service) throws Exception {
		ServiceT serviceT=null;
		try {
			serviceT=serviceRepository.saveAndFlush(service);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return serviceT;
	}

	@Override
	public void update(ServiceT service) throws Exception {
		try {
			serviceRepository.save(service);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void getServiceServiceByCodeOperation(ServiceT service) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ServiceT> getAllServices() throws Exception {
		List<ServiceT> listServiceT=null;
		try {
			listServiceT=serviceRepository.findAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return listServiceT;
	}

	@Override
	public ServiceT findServiceByCodeService(String codeOperation) throws Exception {
		ServiceT serviceT= null;
		try {
			serviceT=serviceRepository.findServiceByCodeService(codeOperation);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return serviceT;
	}

	@Override
	public ServiceT findServiceById(Long idService) throws Exception {
		// TODO Auto-generated method stub
		ServiceT serviceT= null;
		
		try {
			serviceT=serviceRepository.findById(idService).get();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	

}
