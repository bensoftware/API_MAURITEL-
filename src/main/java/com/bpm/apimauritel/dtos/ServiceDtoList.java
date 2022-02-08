package com.bpm.apimauritel.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ServiceDtoList implements Serializable {


	private static final long serialVersionUID = 1L;
	List<ServiceDto> listServiceDto;

	public ServiceDtoList() {
		listServiceDto=new ArrayList<>();
	}
	
	
}
