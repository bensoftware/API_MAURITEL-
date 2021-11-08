package com.bpm.apimauritel;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.securities.JWT;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;

@SpringBootApplication
public class ApiMauritelApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(ApiMauritelApplication.class, args);
	}

	@Autowired
	RechargeService rechargeService;

	@Autowired
	ServiceService serviceService;

	@Autowired
	DetailServiceService detailServiceService;

	@Autowired
	CashService cashService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// UserDto user=new UserDto();

		// System.err.println("Utilisateur DTO : "+rechargeService.checkStatus());

		TokenDto token = rechargeService.authentication();
		// System.err.println("Utilisateur DTO :
		// "+rechargeService.authentication(userDto));

		// --test rechargeParServiceMarketing
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
		// --test getMarketingServices
		List<ServiceDto> serviceDtoList = new ArrayList<>();
		// serviceDtoList=rechargeService.getMarketingServices(rechargeService.authentication(userDto));
		// for (ServiceDto serviceDto : serviceDtoList) {
		// System.err.println(""+serviceDto);
		// }

		// System.err.println("List of :
		// "+rechargeService.getMarketingServiceArray(rechargeService.authentication(userDto)));

//		       RechargeClassiqueDto rechargeClassiqueDto =new RechargeClassiqueDto();
//		       
//		       rechargeClassiqueDto.setAmount("300");
//		       rechargeClassiqueDto.setReceiver("37818077");
//		       rechargeClassiqueDto.setSender("37818077");
//		       
//		       ResponseRechargeDto responseRechargeDto2= rechargeService.rechargeClassique(rechargeClassiqueDto,token);
//		   	System.err.println("Response : "+responseRechargeDto2);

		JWT jwt = new JWT();
		jwt.testDecodeJWT(token);

		int G = JWT.getExpirationTime(token);
		 System.err.println("Response : " + serviceService.getAllServices());

		List<DetailService> listDetailService = new ArrayList<>();

		DetailService detailService = new DetailService();
		detailService.setAmount("300");
		detailService.setDescription("----");
		// detailService.setService(serviceT);

		listDetailService.add(detailService);

		ServiceT s = new ServiceT();
		s.setCodeOperation("1");
		s.setCodeService("9");
		s.setDetailServices(listDetailService);

		// ServiceT serviceT= serviceService.save(s);

		// System.err.println("Service "+serviceT);

		// detailServiceService.save(detailService);

		// cashService.saveService();

	}

}
