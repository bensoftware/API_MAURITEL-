package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bpm.apimauritel.entities.ServiceT;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceT,Long>{	
	
	public ServiceT findServiceByCodeService(String codeOperation) ;
	

}
