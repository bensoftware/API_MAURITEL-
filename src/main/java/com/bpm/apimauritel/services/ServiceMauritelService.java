package com.bpm.apimauritel.services;

import java.util.List;
import com.bpm.apimauritel.entities.ServiceMauritel;

public interface ServiceMauritelService {

	public ServiceMauritel save(ServiceMauritel serviceMauritel) throws Exception;

	public void update(ServiceMauritel serviceMauritel) throws Exception;

	public List<ServiceMauritel> findAllServices() throws Exception;
	
	public ServiceMauritel findById(long id) throws Exception;
	
	public ServiceMauritel findByCodeService(String codeService) throws Exception;

}
