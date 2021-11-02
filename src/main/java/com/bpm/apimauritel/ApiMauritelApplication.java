package com.bpm.apimauritel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.services.RechargeService;

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
		
		
		//System.err.println("Utilisateur DTO : "+rechargeService.authentication(userDto));
		

		System.err.println("List of  : "+rechargeService.getMarketingService(rechargeService.authentication(userDto)));
	}
	
}
