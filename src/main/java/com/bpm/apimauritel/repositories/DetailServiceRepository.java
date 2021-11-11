package com.bpm.apimauritel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;

@Repository
public interface DetailServiceRepository extends JpaRepository<DetailService, Long> {

	public DetailService findDetailServiceByDescription(String description);

	public DetailService findDetailServiceByService(String IdService);

	@Query("select u from DetailService u where u.service=?1 ")
	public  List<DetailService> getDetailServiceByIdService(Long IdService);
	
	
	@Query("select u from DetailService u where   u.amount=?1 and u.service=?2")
	public  List<DetailService>  getDetailServiceByAmountAndIdService(String amount,ServiceT serviceT) throws Exception;
	
	
	public  List<DetailService> findDetailServiceByAmount(String amount);
	
	public  List<DetailService> findByService(ServiceT serviceT);
	
	
}
