package com.bpm.apimauritel.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;

@RestController
@CrossOrigin("*")
public class MauritelApi {

	@Autowired
	ServiceService serviceService;
	
	
	@Autowired
	RechargeService rechargeService;
	
	@RequestMapping(value="/", produces ={"application/json"},method=RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP";	
	}
	
	
	@RequestMapping(value="/getAllMargetingService", produces ={"application/json"},method=RequestMethod.GET)
	public @ResponseBody ResponseDto getAllMargetingService() throws Exception {
		return new ResponseDto(serviceService.getAllServices());
	}
	
	
	
	@RequestMapping(value="/rechargeClassique", produces ={"application/json"},method=RequestMethod.POST)
	public @ResponseBody ResponseDto rechargeClassique(RechargeClassiqueDto rechargeClassiqueDto ) throws Exception{
		ResponseRechargeDto responseRechargeDto=new ResponseRechargeDto();
	
		if(rechargeClassiqueDto==null) {
			throw new Exception("Les informations sur le formulaire sont null");
		}
	    	//Test all attributes 
		
		  //Test key
		
		 //Test IP
		
		responseRechargeDto=rechargeService.rechargeClassique(rechargeClassiqueDto);
		
		System.err.println("Response in the Controller : " +responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	
	@GetMapping
	public void RechargeMargetingService() {
		
	}

}
