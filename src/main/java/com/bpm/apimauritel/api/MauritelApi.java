package com.bpm.apimauritel.api;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.DetailServiceService;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;
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
	



	@RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP";
	}

	@Operation(summary = "BPM ***Get all services ")
	@RequestMapping(value = "/getAllMargetingService", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseDto getAllMargetingService() throws Exception {
		List<ServiceT> listServiceT=new ArrayList<>();
		try {
			listServiceT=serviceService.getAllServices();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		return new ResponseDto(listServiceT);
	}

	@RequestMapping(value = "/getDetailServices/", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseDto getDetailServicesByIdService(@RequestParam(name = "idService", required = true) Long idService)
			throws Exception {

		logger.info("GET DETAIL SERVICE IN : " + idService.longValue());

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

		logger.info("GET DETAIL SERVICE  OUT  : " + listDetailServices);

		return new ResponseDto(listDetailServices);
	}

	
	@RequestMapping(value = "/getDetailServicesByAmount/", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseDto getDetailServicesByAmount(@RequestParam(name = "amount", required = true) Long amount)
			throws Exception {

		logger.info("GET DETAIL SERVICE BY AMOUT IN : " + amount.longValue());

	
		logger.info("GET DETAIL SERVICE AMOUT OUT  : "  );

		return new ResponseDto(null);
	}
	
	
	@Operation(summary = "BPM ***  ")
	@RequestMapping(value = "/rechargeClassique", produces = { "application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody RechargeClassiqueDto rechargeClassiqueDto)
			throws Exception {
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();

		if (rechargeClassiqueDto == null) {
			throw new Exception("Les informations sur le formulaire sont null");
		}
	
		// Test API key

		// Test IP AUTHPORIZED

		responseRechargeDto = rechargeService.rechargeClassique(rechargeClassiqueDto);

		System.err.println("Response in the Controller : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}
	

	
	
	
	@GetMapping
	public void RechargeMargetingService() throws Exception {
            
	}

}
