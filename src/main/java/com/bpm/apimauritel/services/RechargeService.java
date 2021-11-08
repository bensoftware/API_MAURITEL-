package com.bpm.apimauritel.services;

import java.util.List;
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;

public interface RechargeService {

	public String checkStatus() throws Exception;

	public TokenDto authentication() throws Exception;

	public ResponseRechargeDto rechargeClassique(RechargeClassiqueDto rechargeClassiqueDto ) throws Exception;
	
	public ResponseRechargeDto rechargeParServiceMarketing(RechargeMarketingDto rechargeMarketingDto) throws Exception;
	
	public List<ServiceDto> getMarketingServices() throws Exception;
	
}
