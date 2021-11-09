package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.DetailService;

@Repository
public interface DetailServiceRepository extends JpaRepository<DetailService, Long> {

	public DetailService findDetailServiceByDescription(String description);

	public DetailService findDetailServiceByService(String IdService);

	@Query("select u from DetailService u where u.service=?1 ")
	public DetailService getDetailServiceByIdService(Long IdService);
}
