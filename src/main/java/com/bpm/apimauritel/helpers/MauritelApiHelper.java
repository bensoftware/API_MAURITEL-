package com.bpm.apimauritel.helpers;

import java.util.ArrayList;
import java.util.List;

import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;


public class MauritelApiHelper {

	public static void checkRechargeClassique(RechargeClassiqueDto rechargeClassiqueDto) {
		List<String> listValidatorErrors = new ArrayList<>();

		// Amount
		if (rechargeClassiqueDto.getAmount().isEmpty() || rechargeClassiqueDto.getAmount() == null) {
             
		}
		
		//
		if (rechargeClassiqueDto.getReceiver().isEmpty() || rechargeClassiqueDto.getReceiver() == null) {
            
		}
		
        if (rechargeClassiqueDto.getSender().isEmpty() || rechargeClassiqueDto.getSender()== null) {
            
		}
	}
	
	public static void check(RechargeMarketingDto rechargeMarketingDto) {
		List<String> listValidatorErrors = new ArrayList<>();

		 // Amount
		if(rechargeMarketingDto.getAmount().isEmpty() || rechargeMarketingDto.getAmount() == null) {
             
		}
		
		//
		if(rechargeMarketingDto.getReceiver().isEmpty() || rechargeMarketingDto.getReceiver() == null) {
            
		}
		
		//
        if(rechargeMarketingDto.getSender().isEmpty() || rechargeMarketingDto.getSender()== null) {
            
		}
        
        //
        
		
	}
}
