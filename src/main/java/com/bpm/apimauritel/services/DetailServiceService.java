package com.bpm.apimauritel.services;

import com.bpm.apimauritel.entities.DetailService;

public interface DetailServiceService {	
	public void save(DetailService detailService) throws Exception;
	public void update(DetailService detailService) throws Exception;
	public DetailService findDetailServiceByDescription(String description) ;
}
