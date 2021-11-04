package com.bpm.apimauritel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.securities.JWT;
import com.bpm.apimauritel.services.RechargeService;

import antlr.Token;

@SpringBootApplication
public class ApiMauritelApplication implements ApplicationRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiMauritelApplication.class, args);
	}

	@Autowired
	RechargeService rechargeService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
	   //	UserDto user=new UserDto();
		
		//System.err.println("Utilisateur DTO : "+rechargeService.checkStatus());
		
		UserDto userDto =new UserDto();
		userDto.setUsername("admin");
		userDto.setPassword("Admin1234");
		
		TokenDto token=rechargeService.authentication(userDto);
		//System.err.println("Utilisateur DTO : "+rechargeService.authentication(userDto));
		
		 
	   //--test rechargeParServiceMarketing
//		RechargeMarketingDto rechargeMarketingDto=new RechargeMarketingDto();
//		rechargeMarketingDto.setAmount("5000");
//		rechargeMarketingDto.setReceiver("37818077");
//		rechargeMarketingDto.setSender("37804578");
//		rechargeMarketingDto.setService("1");
//		 
//		
//		ResponseRechargeDto responseRechargeDto= rechargeService.rechargeParServiceMarketing(rechargeMarketingDto,token );
//		System.err.println("Response : "+responseRechargeDto);
//		
//		
           //--test getMarketingServices
		       List<ServiceDto> serviceDtoList=new  ArrayList<>();
		 // serviceDtoList=rechargeService.getMarketingServices(rechargeService.authentication(userDto));
        //		 for (ServiceDto serviceDto : serviceDtoList) {
       //			System.err.println(""+serviceDto);
      //		}
	
		//System.err.println("List of  : "+rechargeService.getMarketingServiceArray(rechargeService.authentication(userDto)));
		
//		       RechargeClassiqueDto rechargeClassiqueDto =new RechargeClassiqueDto();
//		       
//		       rechargeClassiqueDto.setAmount("300");
//		       rechargeClassiqueDto.setReceiver("37818077");
//		       rechargeClassiqueDto.setSender("37818077");
//		       
//		       ResponseRechargeDto responseRechargeDto2= rechargeService.rechargeClassique(rechargeClassiqueDto,token);
//		   	System.err.println("Response : "+responseRechargeDto2);
		       
		       JWT jwt=new JWT();
		       jwt.testDecodeJWT(token);
	}
	
}
