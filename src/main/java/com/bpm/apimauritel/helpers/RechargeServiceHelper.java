package com.bpm.apimauritel.helpers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.entities.TransactionPayement;

public class RechargeServiceHelper {
	
	
	public static HttpHeaders getHeaders(TokenDto token) {
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization","Bearer " + token.getToken());
		return headers;
	}
	
	public static  Map<String, String> getParametters(RechargeMarketingDto rechargeMarketingDto) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap();
		params.put("sender", rechargeMarketingDto.getSender());
		params.put("receiver",rechargeMarketingDto.getReceiver());
		params.put("amount", rechargeMarketingDto.getAmount());
		params.put("service", rechargeMarketingDto.getCodeService());
		return params;
	}
	
	public static  Map<String, String> getParamettersRechargeClassique(RechargeClassiqueDto rechargeClassiqueDto) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap();
		params.put("sender", rechargeClassiqueDto.getSender());
		params.put("receiver",rechargeClassiqueDto.getReceiver());
		params.put("amount", rechargeClassiqueDto.getAmount());
		return params;
	}
	
	
	public static  TransactionPayement bindTransactionPayement(RechargeMarketingDto rechargeMarketingDto) {
		
		TransactionPayement transactionPayement=new TransactionPayement();
		
		transactionPayement.setAmountPay(Double.parseDouble(rechargeMarketingDto.getAmount()));
		
		transactionPayement.setTransactionId(rechargeMarketingDto.getIdTransction());
		
		transactionPayement.setSender(rechargeMarketingDto.getSender());
		
		transactionPayement.setReceiver(rechargeMarketingDto.getReceiver());
		
		return transactionPayement;
	}
	

}
