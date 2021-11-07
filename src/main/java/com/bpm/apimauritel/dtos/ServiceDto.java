package com.bpm.apimauritel.dtos;

import java.io.Serializable;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ServiceDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String Service;
	private String CodeOperation;
	private String Description;
	private String Amount;
	
	public ServiceDto(String service, String codeOperation, String description, String amount) {
		super();
		Service = service;
		CodeOperation = codeOperation;
		Description = description;
		Amount = amount;
	}
	public ServiceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
