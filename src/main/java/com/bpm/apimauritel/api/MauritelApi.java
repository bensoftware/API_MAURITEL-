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
import com.bpm.apimauritel.dtos.ListAmounts;
import com.bpm.apimauritel.dtos.ListServices;
import com.bpm.apimauritel.dtos.Recharge;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.ResponseService;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.entities.ServiceMauritel;
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
import com.bpm.apimauritel.services.ServiceMauritelService;
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
	ServiceMauritelService serviceMauritelService;

	@Autowired
	TransactionPayementService transactionPayementService;

	@Autowired
	AmountService amountService;

	@RequestMapping(value = "", produces = { "application/json" }, method = RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP...V1";
	}

	
	
	@Operation(summary = "BPM ***GET ALL SERVICES ")
	@GetMapping(value = "/marketingServices", produces ={"application/json"})
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

	
	
	@Operation(summary = "BPM *** CREATE A TRANSACTION ")
	@PostMapping(value = "/marketing", produces={"application/json"})
	public ResponseDto RechargeMargetingService(@RequestBody RechargeMarketingDto rechargeMarketingDto)
			throws Exception {

		// Test IP AUTHPORIZED
		if (!securityService.checkIP(httpServletRequest.getRemoteHost())) {
			throw new Exception("IP ADRESS NOT ALLOWED");
		}

		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info("RECHARGE MARKETING SERVICE [IN]  : " + rechargeMarketingDto);

		if (rechargeMarketingDto == null) {
			throw new Exception("LES INFORMATIONS SUR LE FORMULAIRE SONT VIDES !!!");
		}

		ServiceMauritel serviceMauritel = null;
		try {
			serviceMauritel = serviceMauritelService.findByCodeService(rechargeMarketingDto.getService());
		} catch (Exception e) {
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		if (serviceMauritel == null) {
			throw new Exception(Message.ERROR_UNKNOWN_SERVICE);
		}

		TransactionPayement transactionPayement = RechargeServiceHelper.bindTransactionPayement(rechargeMarketingDto,
				"MARKETING");
		transactionPayement.setService(serviceMauritel);

		System.err.println("IN SERVICET        : " + serviceMauritel);

		responseRechargeDto = rechargeService.rechargeParServiceMarketing(rechargeMarketingDto);

		try {
			System.err.println("IN PAYEMENT    : " + transactionPayement);
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

	
	
	@Operation(summary = "BPM ***")
	@PostMapping(value = "/classique", produces = { "application/json" })
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody Recharge recharge)
			throws Exception {

		// TEST IP AUTHPORIZED
		// TEST API key
		if (!securityService.checkIP(httpServletRequest.getRemoteHost())) {
			throw new Exception("IP ADRESS NOT ALLOWED");
		}

		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info("RECHARGE CLASSIQUE [IN] : " + recharge);

		if (recharge == null) {
			throw new Exception("Les Informations sur le formulaire sont vides");
		}

		ServiceMauritel serviceMauritel = null;
		try {
			// SERVICE RECHARGE-CLASSIQUE
			serviceMauritel = serviceMauritelService.findByCodeService("100");
		} catch (Exception e) {
			// Check exception
			traitementService.responseException();
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		TransactionPayement transactionPayement = null;
		transactionPayement = RechargeServiceHelper.bindClassicTransactionPayement(recharge, "CLASSIQUE");
		transactionPayement.setTransactionStatus("TA");
		transactionPayement.setService(serviceMauritel);
		// PERSISTANCE
		transactionPayement = transactionPayementService.save(transactionPayement);

		// DISPONIBILITE DU SERVICE OU STOCK to ignore

		// APPEL DE L'API MAURITEL EXTERNE
		try {
			responseRechargeDto = rechargeService.rechargeClassique(recharge);
		}catch (Exception e) {
			// Check Exception
			System.err.println("RETOUR : " + transactionPayement);
			traitementService.responseException();
			transactionPayement.setId(transactionPayement.getId());
			transactionPayement.setTransactionStatus("TF");
			transactionPayement.setTransactionDate(new Date());
			transactionPayement.setErrorMessage(e.getMessage());
		//	transactionPayement.setService(serviceMauritel);
			transactionPayementService.upadte(transactionPayement);
			logger.info("EXCEPTION : " + e.getMessage());
		}

		try {
			if (responseRechargeDto.isSuccess()) {
				// Check Success
				traitementService.responseSuccess();
				transactionPayement.setTransactionStatus("TS");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			} else {
				// Check Exception
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

	
	
	@Operation(summary = "BPM ** List Of All Amounts ")
	@GetMapping(value="/amounts", produces={"application/json"})
	public ResponseDto getListAmounts() throws Exception {
		ResponseDto responseDto = new ResponseDto();
		logger.info("LIST OF AMOUNTS [IN]    : ");
		
		// TEST API key

		// TEST IP AUTHPORIZED
		List<Double> listAmounts = null;
		try {
			listAmounts = amountService.findAllActifAmounts();
			ListAmounts trueListAmounts = new ListAmounts(listAmounts);
			responseDto.setResponse(trueListAmounts);
			responseDto.setMessage("SUCCESS ");
		} catch (Exception e) {
			responseDto.setMessage("Failed : " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		// responseDto.setResponse(listDetailServices);
		logger.info("LIST OF AMOUNTS  [OUT]   : " + listAmounts);
		return responseDto;
	}
	
	@GetMapping(value = "/services", produces = { "application/json" })
	public ResponseDto getServicesByAmount(@RequestParam(name = "language", required = true) int language,
			@RequestParam(name = "amount", required = true) Long amount) throws Exception {

		// TEST IP AUTHPORIZED
		if (!securityService.checkIP(httpServletRequest.getRemoteHost())){
			throw new Exception("IP ADRESS NOT ALLOWED");
		}

		ResponseDto responseDto = new ResponseDto();

		logger.info("GET DETAIL SERVICE BY AMOUNT [IN]   : AMOUNT    : " + amount);
		logger.info("GET DETAIL SERVICE BY AMOUNT [IN]   : LANGUAGE  : " + language);

		Set<Detail> listdetailDetails = new HashSet<>();
		try {
			listdetailDetails = amountService.findByAmount(amount.doubleValue()).getDetail();
			// listdetailDetails=ServiceHelper.bindDetail(listdetailDetails,language);
		} catch (Exception e) {
			logger.info(e.getMessage());
			responseDto.setResponse(listdetailDetails);
			responseDto.setCodeError(1);
			responseDto.setMessage("THERE ARE NO SERVICES RELATED TO  :  " + amount);
			return responseDto;
		}
		List<ResponseService> listr = ResponseHelper.getDetailResponse(listdetailDetails, language);
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
		List<ServiceDto> listServiceDtos = rechargeService.getMarketingServices();
		return new ResponseDto(listServiceDtos);
	}

	
}
