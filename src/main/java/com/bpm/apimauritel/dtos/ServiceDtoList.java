package com.bpm.apimauritel.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ServiceDtoList {

	List<ServiceDto> listServiceDto;

	public ServiceDtoList() {
		listServiceDto=new ArrayList<>();
	}
	
	
}
