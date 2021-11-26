package com.bpm.apimauritel.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.entities.TransactionPayement;
import com.bpm.apimauritel.helpers.RechargeServiceHelper;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;
import com.bpm.apimauritel.services.TransactionPayementService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
public class MauritelApi {

	private final Logger logger = LoggerFactory.getLogger(MauritelApi.class);

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	ServiceService serviceService;

	@Autowired
	RechargeService rechargeService;

	@Autowired
	DetailServiceService detailServiceService;

	@Autowired
	TransactionPayementService transactionPayementService;

	@RequestMapping(value = "/", produces = {"application/json"}, method = RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP";
	}

	@Operation(summary = "BPM ***Get all services ")
	@RequestMapping(value = "/getAllMargetingService", produces = {"application/json"}, method = RequestMethod.GET)
	public @ResponseBody ResponseDto getAllMargetingService() throws Exception {
		List<ServiceT> listServiceT = new ArrayList<>();
		try {
			listServiceT = serviceService.getAllServices();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		return new ResponseDto(listServiceT);
	}

	

	@Operation(summary = "BPM *** Get Details Service By using the service ID")
	@RequestMapping(value = "/getDetailServicesByIdService/", produces = {"application/json"}, method = RequestMethod.GET)
	public ResponseDto getDetailServicesByIdService(@RequestParam(name = "idService", required = true) Long idService)
			throws Exception {

		logger.info("GET DETAIL SERVICE IN : " + idService.longValue());

		ServiceT serviceT = new ServiceT();
		serviceT.setId(idService);

		List<DetailService> listDetailServices = new ArrayList<>();
		try {
			listDetailServices = detailServiceService.findByService(serviceT);
			logger.info("LISTE DES DETAILS SERVICE OU TYPE DE SERVICE " + listDetailServices);
		}catch (Exception e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}

		logger.info("GET DETAIL SERVICE  [OUT]  : " + listDetailServices);

		return new ResponseDto(listDetailServices);
	}

	@Operation(summary = "BPM ** List of all amount ")
	@RequestMapping(value = "/getAmounts", produces = {"application/json"}, method = RequestMethod.GET)
	public ResponseDto getAmounts() throws Exception {

		ResponseDto responseDto =new ResponseDto();
		logger.info("GET DETAIL SERVICE  AMOUNT [IN]    : ");
		try {
			List<DetailService> listDetailServices=detailServiceService.findAllDetailService();
			responseDto.setResponse(listDetailServices);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	
		///responseDto.setResponse(listDetailServices);
		logger.info("GET DETAIL SERVICE AMOUNT  [OUT]   : ");
		 return responseDto;
	}

	
	@RequestMapping(value = "/getDetailServicesByAmount/", produces = {"application/json" }, method = RequestMethod.GET)
	public ResponseDto getDetailServicesByAmount(@RequestParam(name = "amount", required = true) Long amount)
			throws Exception {

		logger.info("GET DETAIL SERVICE BY AMOUT [IN]   : " + amount.longValue());

		ResponseDto responseDto =new ResponseDto();
	    List<DetailService> detailService=  detailServiceService.getDetailServiceByAmount(String.valueOf(amount));
	    
	    if(detailService.size()==0) {
	    	throw new Exception("Aucun service n'est disponible à ce montant !!! ");
	    }
	    
		return new ResponseDto(detailService);
	}
	
	

	@Operation(summary = "BPM ***  ")
	@RequestMapping(value = "/rechargeClassique", produces = { "application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody RechargeClassiqueDto rechargeClassiqueDto)
			throws Exception {
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();

		if (rechargeClassiqueDto == null) {
			throw new Exception("Les Informations sur le formulaire sont vides ");
		}

		// TEST API key

		// TEST IP AUTHPORIZED

		// Disponibilite du service ou stock

		// Save Transaction

		responseRechargeDto = rechargeService.rechargeClassique(rechargeClassiqueDto);

		System.err.println("Response in the Controller : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	
	
	@RequestMapping(value = "/rechargeMargetingService", produces = { "application/json" }, method = RequestMethod.POST)
	public ResponseDto RechargeMargetingService(@RequestBody RechargeMarketingDto rechargeMarketingDto)
			throws Exception {

		// Test API key

	   // Test IP AUTHPORIZED
	
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		System.err.println("In rechargeMarketingDto   "    +  rechargeMarketingDto);
		
		logger.info(" RECHARGE MARKETING SERVICE [IN]  : " + rechargeMarketingDto);

		if (rechargeMarketingDto == null) {
			throw new Exception(" Les Informations sur le formulaire sont vides !!! ");
		}
         
		ServiceT serviceT = null;
		try {
			 serviceT = serviceService.findServiceByCodeService(rechargeMarketingDto.getService());
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		
        if(serviceT==null) {
        	throw new Exception("Ce service Mauritel n'existe pas ou n'est pas disponible !!! ");
        }
        
	    TransactionPayement transactionPayement=RechargeServiceHelper.bindTransactionPayement(rechargeMarketingDto);
	    transactionPayement.setService(serviceT);

	    System.err.println("In serviceT        : "    +  serviceT);
	    try {
	    	System.err.println("In Payement    : "    +  transactionPayement);
	    	transactionPayementService.save(transactionPayement);
		}catch (Exception e){
			throw new Exception(e.getMessage());
		}
		responseRechargeDto = rechargeService.rechargeParServiceMarketing(rechargeMarketingDto);

		logger.info(" RECHARGE MARKETING SERVICE [OUT]  : " + responseRechargeDto);
		
		return new ResponseDto(responseRechargeDto);
	}
	

}
