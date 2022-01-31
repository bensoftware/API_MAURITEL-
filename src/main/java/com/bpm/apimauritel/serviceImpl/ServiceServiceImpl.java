package com.bpm.apimauritel.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.repositories.ServiceTRepository;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	ServiceTRepository serviceTRepository;

	@Override
	public ServiceT save(ServiceT service) throws Exception {
		ServiceT serviceT = null;
		try {
			serviceT = serviceTRepository.saveAndFlush(service);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return serviceT;
	}

	@Override
	public void update(ServiceT service) throws Exception {
		try {
			serviceTRepository.save(service);
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
		List<ServiceT> listServiceT = null;
		try {
			listServiceT = serviceTRepository.findAll();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return listServiceT;
	}

	
	@Override
	public ServiceT findServiceByCodeService(String codeOperation) throws Exception {
		ServiceT serviceT = null;
		try {
			serviceT = serviceTRepository.findServiceByCodeService(codeOperation);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return serviceT;
	}

	
	@Override
	public ServiceT findServiceById(Long idService) throws Exception {
		// TODO Auto-generated method stub
		ServiceT serviceT = null;

		try {
			serviceT = serviceTRepository.findById(idService).get();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return serviceT;
	}

}
