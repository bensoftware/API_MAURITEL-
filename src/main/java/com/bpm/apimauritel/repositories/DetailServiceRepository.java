package com.bpm.apimauritel.repositories;

import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.DetailService;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface  DetailServiceRepository extends JpaRepository<DetailService,Long>{

	public DetailService findDetailServiceByDescription(String description) ;
}
