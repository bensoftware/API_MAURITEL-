package com.bpm.apimauritel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.CashDataService;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceServiceT;
import com.bpm.apimauritel.services.ProcessingService;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceMauritelService;
import com.bpm.apimauritel.services.ServiceService;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bpm.apimauritel"})
public class ApiMauritelApplication implements ApplicationRunner {

	@Autowired
	RechargeService rechargeService;

	@Autowired
	ServiceService serviceService;

	@Autowired
	DetailServiceServiceT detailServiceServiceT;

	@Autowired
	CashService cashService;

	@Autowired
	CashDataService cashDataService;
	
	@Autowired
	ProcessingService processingService;
	
	
	@Autowired
	ServiceMauritelService serviceServiceMauritelService;
	
	@Autowired
	AmountService amountService;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiMauritelApplication.class, args);
	}

	
	@Override
	public void run(ApplicationArguments args) throws Exception {

		
		 // --test rechargeParServiceMarketing
//		RechargeMarketingDto rechargeMarketingDto=new RechargeMarketingDto();
//		rechargeMarketingDto.setAmount("5000");
//		rechargeMarketingDto.setReceiver("37818077");
//		rechargeMarketingDto.setSender("37804578");
//		rechargeMarketingDto.setCodeService("1");
//		 
//		System.err.println("TIME " +new Date().getTime() / 1000);
//	//	ResponseRechargeDto responseRechargeDto= rechargeService.rechargeParServiceMarketing(rechargeMarketingDto);
//   //System.err.println("Response : "+responseRechargeDto);	
//  //		
// //		
//		// --test getMarketingServices
//		List<ServiceDto> serviceDtoList = new ArrayList<>();
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

//		JWT jwt = new JWT();
//		jwt.testDecodeJWT(token);
//		int G = JWT.getExpirationTime(token);
//		

//		List<DetailService> listDetailService = new ArrayList<>();
//
//		DetailService detailService = new DetailService();
//		detailService.setAmount("300");
//		detailService.setDescription("----");
//	    detailService.setService(serviceT);
//
//		listDetailService.add(detailService);
//
//		ServiceT s = new ServiceT();
//		s.setId(40L);
		// s.setCodeOperation("1");
		// s.setCodeService("9");
//		  s.setDetailServices(listDetailService);

		// System.err.println("DetailService : "+detailServiceService.findByService(s));

		// ServiceT serviceT= serviceService.save(s);

		// System.err.println("Service "+serviceT);

		// detailServiceService.save(detailService);

		
		// cashService.saveService();

//        List<ServiceT> listServiceT = serviceService.getAllServices();
//		
//		String amount = "200";
//
//		List<DetailService> filteredServicesT = listServiceT
//				.stream()
//				.flatMap(serviceT -> serviceT.getDetailServices()
//				.stream())
//				.filter(detailService -> detailService.getAmount().equalsIgnoreCase("200"))
//				.collect(Collectors.toList());
//
//	       //	filteredServicesT.forEach(detailService->detailService.getService());
//		  //	System.err.println("Liste of services Amount  : " + rechargeService.getMontants(detailServiceService.findAllDetailService()));
//		System.err.println("Liste Filtered" +processingService.getServicesByAmount("500"));
//		// +processingService.getServicesByAmount("500"));
		
		//cashDataService
		//cashService.saveService();
		//cashDataService.savePureData();
	}

}
