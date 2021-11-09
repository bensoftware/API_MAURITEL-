package com.bpm.apimauritel.api;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin("*")
public class MauritelApi {

	@Autowired
	ServiceService serviceService;

	@Autowired
	RechargeService rechargeService;

	@RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
	public String Welcome() {
		return "MAURITEL API is UP";
	}

	@Operation(summary ="BPM ***Get all services ")
	@RequestMapping(value = "/getAllMargetingService", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseDto getAllMargetingService() throws Exception {
		return new ResponseDto(serviceService.getAllServices());
	}
	
	@Operation(summary ="BPM ***Get Detail Service ")
	@RequestMapping(value = "/getDetailService/{idService}", produces = { "application/json" }, method = RequestMethod.GET)
	public @ResponseBody ResponseDto getDetailService(@PathVariable String idService) throws Exception {
		return null;
	}
	
	
	
	@Operation(summary ="BPM ***  ")
	@RequestMapping(value = "/rechargeClassique", produces = { "application/json" }, method = RequestMethod.POST)
	public @ResponseBody ResponseDto rechargeClassique(@Valid @RequestBody RechargeClassiqueDto rechargeClassiqueDto) throws Exception {
		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();

		if (rechargeClassiqueDto == null) {
			throw new Exception("Les informations sur le formulaire sont null");
		}
		// Test all attributes

		// Test key

		// Test IP

		responseRechargeDto = rechargeService.rechargeClassique(rechargeClassiqueDto);

		System.err.println("Response in the Controller : " + responseRechargeDto);
		return new ResponseDto(responseRechargeDto);
	}
	
	

	@GetMapping
	public void RechargeMargetingService() {

	}

}
