package com.bpm.apimauritel.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import com.bpm.apimauritel.dtos.Recharge;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.entities.TransactionPayement;

public class RechargeServiceHelper {

	public static HttpHeaders getHeaders(TokenDto token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", "Bearer " + token.getToken());
		return headers;
	}

	public static Map<String, String> getParametters(RechargeMarketingDto rechargeMarketingDto) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap();
		params.put("sender", rechargeMarketingDto.getSender());
		params.put("receiver", rechargeMarketingDto.getReceiver());
		params.put("amount", rechargeMarketingDto.getAmount());
		params.put("service", rechargeMarketingDto.getCodeService());
		return params;
	}

	public static Map<String, String> getParamettersRechargeClassique(Recharge recharge) {
		// TODO Auto-generated method stub
		Map<String, String> params = new HashMap();
		params.put("sender", recharge.getSender());
		params.put("receiver", recharge.getReceiver());
		params.put("amount", recharge.getAmount());
		return params;
	}

	public static TransactionPayement bindTransactionPayement(Recharge recharge,
			String typeRecharge) {

		TransactionPayement transactionPayement = new TransactionPayement();
		// transactionPayement.setAmountPay(Double.parseDouble(rechargeMarketingDto.getAmount()));
		transactionPayement.setTypeRecharge(typeRecharge);
		transactionPayement.setAmountPay(Double.parseDouble(recharge.getAmount()));
		transactionPayement.setDateRequest(new Date());
		transactionPayement.setTransactionId(recharge.getIdTransction());
		transactionPayement.setSender(recharge.getSender());
		transactionPayement.setReceiver(recharge.getReceiver());
		return transactionPayement;
	}

	public static TransactionPayement bindClassicTransactionPayement(Recharge recharge,
			String typeRecharge) {
		TransactionPayement transactionPayement = new TransactionPayement();
		// transactionPayement.setAmountPay(Double.parseDouble(rechargeMarketingDto.getAmount()));
		transactionPayement.setTypeRecharge(typeRecharge);
		transactionPayement.setAmountPay(Double.parseDouble(recharge.getAmount()));
		transactionPayement.setDateRequest(new Date());
		transactionPayement.setTransactionId(recharge.getIdTransction());
		transactionPayement.setSender(recharge.getSender());
		transactionPayement.setReceiver(recharge.getReceiver());
		return transactionPayement;
	}
	
	
	public static RechargeMarketingDto BindRequestMarketingDto (Recharge recharge) {

		RechargeMarketingDto rechargeMarketingDto=new RechargeMarketingDto();
		
		rechargeMarketingDto.setAmount(recharge.getAmount());
		rechargeMarketingDto.setCodeService(recharge.getCodeService());
		rechargeMarketingDto.setIdTransction(recharge.getIdTransction());
		rechargeMarketingDto.setReceiver(recharge.getReceiver());
		rechargeMarketingDto.setSender(recharge.getSender());
		rechargeMarketingDto.setService(recharge.getBundleId());
		return rechargeMarketingDto;
	}
	

}
