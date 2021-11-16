package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class RechargeMarketingDto {
	
	private String sender;
	private String receiver;
	private String amount;
	private String codeService;
	private String service;
	
	private long idService;
	private String idTransction;
	private String operateur ;
	 
}
