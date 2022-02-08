package com.bpm.apimauritel.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ListServices {
	
	private List<ResponseService> services;

	public ListServices(List<ResponseService> services) {
		super();
		this.services = services;
	}

}
