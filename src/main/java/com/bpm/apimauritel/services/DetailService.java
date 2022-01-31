package com.bpm.apimauritel.services;

import java.util.List;

import com.bpm.apimauritel.entities.Detail;

public interface DetailService {

	public void save(Detail detail) throws Exception;
	
	public void update(Detail detail) throws Exception;
	
	public List<Detail> findAllDetailServices() throws Exception;
}
