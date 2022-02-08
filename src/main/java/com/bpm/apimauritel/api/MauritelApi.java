package com.bpm.apimauritel.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.bpm.apimauritel.dtos.ListServices;
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseService;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.entities.TransactionPayement;
import com.bpm.apimauritel.helpers.RechargeServiceHelper;
import com.bpm.apimauritel.helpers.ResponseHelper;
import com.bpm.apimauritel.messages.Message;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.DetailServiceServiceT;
import com.bpm.apimauritel.services.ProcessingService;
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
	DetailServiceServiceT detailServiceServiceT;

	@Autowired
	SecurityService securityService;

	@Autowired
	ProcessingService processingService;

	@Autowired
	TransactionPayementService transactionPayementService;

	@Autowired
	AmountService amountService;

	@RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
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
			if (responseRechargeDto.isSuccess()) {
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			} else {
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayementService.save(transactionPayement);
			}
		} catch (Exception e) {
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		logger.info(" RECHARGE MARKETING SERVICE [OUT]  : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	@Operation(summary = "BPM ** List Of All Amounts ")
	@GetMapping(value = "/amounts", produces = { "application/json" })
	public ResponseDto getListAmounts() throws Exception {
		ResponseDto responseDto = new ResponseDto();
		logger.info("LIST OF AMOUNTS [IN]    : ");

		// TEST API key

		// TEST IP AUTHPORIZED
		List<Double> listAmounts = null;
		try {
			listAmounts = amountService.findAllActifAmounts();
			responseDto.setResponse(listAmounts);
			responseDto.setMessage("SUCCESS ");
		} catch (Exception e) {
			responseDto.setMessage("Failed : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		// responseDto.setResponse(listDetailServices);
		logger.info("LIST OF AMOUNTS  [OUT]   : " + listAmounts);
		return responseDto;
	}

	@Operation(summary = "BPM ***")
	@PostMapping(value = "/classique", produces = { "application/json" })
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody RechargeClassiqueDto rechargeClassiqueDto)
			throws Exception {

		// TEST IP AUTHPORIZED
		// TEST API key
		if (!securityService.checkIP(httpServletRequest.getRemoteHost())) {
			throw new Exception("IP ADRESS NOT ALLOWED");
		}

		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info("RECHARGE CLASSIQUE [IN] : " + rechargeClassiqueDto);

		if (rechargeClassiqueDto == null) {
			throw new Exception("Les Informations sur le formulaire sont vides");
		}

		ServiceT serviceT = null;
		try {
			// SERVICE RECHARGE-CLASSIQUE
			serviceT = serviceService.findServiceByCodeService("100");
		} catch (Exception e) {
			// Check exception
			traitementService.responseException();
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		TransactionPayement transactionPayement = null;
		transactionPayement = RechargeServiceHelper.bindClassicTransactionPayement(rechargeClassiqueDto, "CLASSIQUE");
		transactionPayement.setTransactionStatus("TA");
		transactionPayement.setService(serviceT);
		// persistance
		transactionPayement = transactionPayementService.save(transactionPayement);

		// DISPONIBILITE DU SERVICE OU STOCK to ignore

		// Appel de l'api mauritel externe
		try {
			responseRechargeDto = rechargeService.rechargeClassique(rechargeClassiqueDto);
		} catch (Exception e) {
			// Check exception
			traitementService.responseException();
			transactionPayement.setTransactionStatus("TF");
			transactionPayement.setTransactionDate(new Date());
			transactionPayement.setErrorMessage(e.getMessage());
			transactionPayementService.save(transactionPayement);

			logger.info("EXCEPTION : " + e.getMessage());
		}

		try {
			if (responseRechargeDto.isSuccess()) {
				// Check success
				traitementService.responseSuccess();
				transactionPayement.setTransactionStatus("TS");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			} else {
				// Check exception
				traitementService.responseException();
				transactionPayement.setTransactionStatus("TF");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayementService.save(transactionPayement);
			}
		} catch (Exception e) {
			// Check exception
			traitementService.responseException();
			logger.info(e.getMessage());
		}
		logger.info("RECHARGE CLASSIQUE [OUT] : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}

	
	@GetMapping(value = "/services", produces = { "application/json" })
	public ResponseDto getServicesByAmount(@RequestParam(name = "language", required = true) int language,
			@RequestParam(name = "amount", required = true) Long amount) throws Exception {

		// TEST API KEY
		// TEST IP AUTHPORIZED

		ResponseDto responseDto = new ResponseDto();

		logger.info("GET DETAIL SERVICE BY AMOUNT [IN]   : AMOUNT  : " + amount);
		logger.info("GET DETAIL SERVICE BY AMOUNT [IN]   : LANGUAGE  : " + language);

		Set<Detail> listdetailDetails = new HashSet<>();
		try {
			
			if(language==0) {
				listdetailDetails = amountService.findByAmount(amount.doubleValue()).getDetail();
			}else if(language==1) {
				
			}else if(language==2) {
				
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			responseDto.setResponse(listdetailDetails);
			responseDto.setCodeError(1);
			responseDto.setMessage("THERE ARE NO SERVICES RELATED TO : " + amount);
			return responseDto;
		}

		List<ResponseService> listr = ResponseHelper.getDetailResponse(listdetailDetails);
		ListServices listServices = new ListServices(listr);
		responseDto.setMessage("SUCCES");
		responseDto.setCodeError(0);
		responseDto.setResponse(listServices);
		logger.info("GET DETAIL SERVICE BY AMOUNT [OUT]   : " + responseDto);
		return responseDto;
	}
	
	
	@Operation(summary = "GET PURE FROM MAURITEL")
	@GetMapping(value = "/marketingService", produces = { "application/json" })
	public @ResponseBody ResponseDto getAllPureMargetingService() throws Exception {
		List<ServiceDto> listServiceDtos=rechargeService.getMarketingServices();
		return new ResponseDto(listServiceDtos);
	}
	

}
