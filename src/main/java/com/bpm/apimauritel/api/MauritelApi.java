package com.bpm.apimauritel.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.bpm.apimauritel.messages.Message;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.SecurityService;
import com.bpm.apimauritel.services.ServiceService;
import com.bpm.apimauritel.services.TraitementService;
import com.bpm.apimauritel.services.TransactionPayementService;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/V1")
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
	TraitementService traitementService;
	
	@Autowired
	DetailServiceService detailServiceService;

	@Autowired
	SecurityService securityService;

	@Autowired
	TransactionPayementService transactionPayementService;

	@RequestMapping(value = "", produces ={"application/json" }, method = RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP...V1";
	}

	
	@Operation(summary = "BPM ***GET ALL SERVICES ")
	@GetMapping(value = "/marketingServices", produces = { "application/json" })
	public @ResponseBody ResponseDto getAllMargetingService() throws Exception {
		List<ServiceT> listServiceT = new ArrayList<>();
		
		// TEST API key
		
		// TEST IP AUTHPORIZED

		// IP :If Ip Adress is not allowed
		if (!securityService.checkIP(httpServletRequest.getRemoteHost())) {
			throw new Exception("IP ADRESS NOT ALLOWED");
		}
		try {
			listServiceT = serviceService.getAllServices();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return new ResponseDto(listServiceT);
	}

	
	
	@Operation(summary = "BPM *** Get Details Service By using the service ID")
	@GetMapping(value = "/detailServices", produces = { "application/json" })
	public ResponseDto getDetailServicesByIdService(@RequestParam(name = "idService", required = true) Long idService)
			throws Exception {
		logger.info("GET DETAIL SERVICE  [IN]  : " + idService.longValue());
		
		// TEST API key
		
		// TEST IP AUTHPORIZED
		if (idService == 0.0) {
			logger.error("Service's ID is NULL");
			throw new Exception("ID is NULL");
		}
		ServiceT serviceT = new ServiceT();
		serviceT.setId(idService);
		List<DetailService> listDetailServices = new ArrayList<>();
		try {
			listDetailServices = detailServiceService.findByService(serviceT);
			logger.info("LISTE DES DETAILS SERVICE OU TYPE DE SERVICE " + listDetailServices);
		} catch (Exception e) {
			logger.warn(e.getMessage());
			throw new Exception(e.getMessage());
		}
		logger.info("GET DETAIL SERVICE  [OUT]  : " + listDetailServices);
		return new ResponseDto(listDetailServices);
	}

	
	@Operation(summary = "BPM ** List of all amounts ")
	@GetMapping(value = "/amounts", produces = { "application/json" })
	public ResponseDto getAmounts() throws Exception {
		ResponseDto responseDto = new ResponseDto();
		logger.info("GET DETAIL SERVICE  AMOUNT [IN]    : ");
		
		// TEST API key
		
		// TEST IP AUTHPORIZED
		try {
			List<DetailService> listDetailServices = detailServiceService.findAllDetailService();
			responseDto.setResponse(listDetailServices);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		// responseDto.setResponse(listDetailServices);
		logger.info("GET DETAIL SERVICE AMOUNT  [OUT]   : ");
		return responseDto;
	}

	
	@GetMapping(value = "/detailServices/{amount}", produces = { "application/json" })
	public ResponseDto getDetailServicesByAmount(@RequestParam(name = "amount", required = true) Long amount)
			throws Exception {
		logger.info("GET DETAIL SERVICE BY AMOUT [IN]   : " + amount.longValue());
		
		// TEST API key
		
		// TEST IP AUTHPORIZED
		ResponseDto responseDto = new ResponseDto();
		List<DetailService> detailService = detailServiceService.getDetailServiceByAmount(String.valueOf(amount));

		if (detailService.size() == 0) {
			throw new Exception("Aucun service n'est disponible à ce montant !!! ");
		}
		logger.info("GET DETAIL SERVICE BY AMOUT [OUT]   : " + detailService);
		return new ResponseDto(detailService);
	}

	
	
	@Operation(summary = "BPM ***Create a transaction ")
	@PostMapping(value = "/marketing", produces = { "application/json" })
	public ResponseDto RechargeMargetingService(@RequestBody RechargeMarketingDto rechargeMarketingDto)
			throws Exception {
		
		// Test API key
		
		// Test IP AUTHPORIZED
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info(" RECHARGE MARKETING SERVICE [IN]  : " + rechargeMarketingDto);

		if (rechargeMarketingDto == null) {
			throw new Exception("Les Informations sur le formulaire sont vides !!! ");
		}
		
		ServiceT serviceT = new ServiceT();
		try {
			serviceT = serviceService.findServiceByCodeService(rechargeMarketingDto.getService());
		} catch (Exception e) {
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		if (serviceT == null) {
			throw new Exception(Message.ERROR_UNKNOWN_SERVICE);
		}

		TransactionPayement transactionPayement = RechargeServiceHelper.bindTransactionPayement(rechargeMarketingDto,
				"MARKETING");
		transactionPayement.setService(serviceT);
		
		System.err.println("In serviceT        : " + serviceT);
		
		responseRechargeDto = rechargeService.rechargeParServiceMarketing(rechargeMarketingDto);
		
		try {
			System.err.println("In Payement    : " + transactionPayement);
			if (responseRechargeDto.isSuccess()){
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			}else{
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayementService.save(transactionPayement);
			}
		}catch(Exception e){
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		logger.info(" RECHARGE MARKETING SERVICE [OUT]  : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	
	@Operation(summary = "BPM ***")
	@PostMapping(value = "/classique", produces = { "application/json" })
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody RechargeClassiqueDto rechargeClassiqueDto)
			throws Exception {
		
		// TEST IP AUTHPORIZED 
	   if(!securityService.checkIP(httpServletRequest.getRemoteHost())) {
			throw new Exception("IP ADRESS NOT ALLOWED");
		  }
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info("RECHARGE CLASSIQUE [IN] : " + rechargeClassiqueDto);
		
		if (rechargeClassiqueDto == null) {
			throw new Exception("Les Informations sur le formulaire sont vides");
		}
		 // TEST API key
		
		ServiceT serviceT =null;
		try {
			//SERVICE RECHARGE-CLASSIQUE
			serviceT = serviceService.findServiceByCodeService("100");
		} catch (Exception e) {
			// check exception
			traitementService.responseException();
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		
		TransactionPayement transactionPayement=null;
		transactionPayement = RechargeServiceHelper
				.bindClassicTransactionPayement(rechargeClassiqueDto, "CLASSIQUE");
		transactionPayement.setTransactionStatus("TA");
		transactionPayement.setService(serviceT);
		// persistance
		transactionPayement=transactionPayementService.save(transactionPayement);
		
		// DISPONIBILITE DU SERVICE OU STOCK  to ignore
		
		
		// appel de l'api mauritel externe
		try {
			responseRechargeDto = rechargeService.rechargeClassique(rechargeClassiqueDto);
		} catch (Exception e) {
			// check exception
		    traitementService.responseException();
			transactionPayement.setTransactionStatus("TF");
			transactionPayement.setTransactionDate(new Date());
			transactionPayement.setErrorMessage(e.getMessage());
			transactionPayementService.save(transactionPayement);
			
			logger.info("EXCEPTION : " + e.getMessage());
		}
		
		try {
			if(responseRechargeDto.isSuccess()){	
				// check success
				traitementService.responseSuccess();
				transactionPayement.setTransactionStatus("TS");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			}else{
				// check exception
				traitementService.responseException();
				transactionPayement.setTransactionStatus("TF");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayementService.save(transactionPayement);
			}
		}catch (Exception e) {
			// check exception
			traitementService.responseException();
			logger.info(e.getMessage());
		}
		logger.info("RECHARGE CLASSIQUE [OUT] : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	
}
