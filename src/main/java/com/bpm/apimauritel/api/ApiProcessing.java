package com.bpm.apimauritel.api;

import java.util.Date;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import com.bpm.apimauritel.dtos.Recharge;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.entities.ServiceMauritel;
import com.bpm.apimauritel.entities.TransactionPayement;
import com.bpm.apimauritel.helpers.RechargeServiceHelper;
import com.bpm.apimauritel.messages.Message;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.SecurityService;
import com.bpm.apimauritel.services.ServiceMauritelService;
import com.bpm.apimauritel.services.TraitementService;
import com.bpm.apimauritel.services.TransactionPayementService;

@Component
public class ApiProcessing {

	private final Logger logger = LoggerFactory.getLogger(ApiProcessing.class);

	@Autowired
	ServiceMauritelService serviceMauritelService;

	@Autowired
	TransactionPayementService transactionPayementService;

	@Autowired
	RechargeService rechargeService;

	@Autowired
	TraitementService traitementService;

	@Autowired
	SecurityService securityService;

	public ResponseRechargeDto RechargeMargetingService(Recharge recharge) throws Exception {

		ResponseRechargeDto responseRechargeDto = new ResponseRechargeDto();
		logger.info("RECHARGE MARKETING SERVICE [IN]  : " + recharge);

		if (recharge == null) {
			throw new Exception("LES INFORMATIONS SUR LE FORMULAIRE SONT VIDES !!!");
		}

		ServiceMauritel serviceMauritel = null;
		try {
			serviceMauritel = serviceMauritelService.findByCodeService(recharge.getCodeService());
		} catch (Exception e) {
			// Check exception
			traitementService.responseException();
			logger.error(" EXCEPTION  : " + e.getMessage());
			throw new Exception(e.getMessage());
		}

		if (serviceMauritel == null) {
			throw new Exception(Message.ERROR_UNKNOWN_SERVICE);
		}

		TransactionPayement transactionPayement = RechargeServiceHelper.bindTransactionPayement(recharge, "MARKETING");
		transactionPayement.setStatusPayement("TA");
		transactionPayement.setService(serviceMauritel);
	
		// PERSISTANCE
	    transactionPayement = transactionPayementService.save(transactionPayement);

		System.err.println("IN SERVICET        : " + serviceMauritel);

		RechargeMarketingDto rechargeMarketingDto = RechargeServiceHelper.BindRequestMarketingDto(recharge);
		
		try {
			responseRechargeDto = rechargeService.rechargeParServiceMarketing(rechargeMarketingDto);

		} catch (Exception e) {
			// TODO: handle exception
			// TODO: handle exception
			logger.info(" " + e.getMessage());
			if(e!=null) {
				responseRechargeDto.setMessage(e.getMessage());		
			}
			responseRechargeDto.setSuccess(false);
			return responseRechargeDto;
		}
		
		try {
			System.err.println("IN PAYEMENT    : " + transactionPayement);
			if (responseRechargeDto.isSuccess()) {
				transactionPayement.setTransactionStatus("TS");
				transactionPayement.setSuccess(true);
				transactionPayement.setTransactionDate(new Date());
			//	transactionPayement.setTransactionId(recharge.getIdTransction());
				transactionPayementService.save(transactionPayement);
			} else {
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setTransactionStatus("TF");
				transactionPayementService.save(transactionPayement);
			}
		} catch (Exception e) {
			traitementService.responseException();
			transactionPayement.setId(transactionPayement.getId());
			transactionPayement.setTransactionStatus("TF");
			transactionPayement.setTransactionDate(new Date());
			transactionPayement.setErrorMessage(e.getMessage());
		//	transactionPayement.setTransactionId(recharge.getIdTransction());
			// transactionPayement.setService(serviceMauritel);
			transactionPayementService.upadte(transactionPayement);
			logger.info("EXCEPTION : " + e.getMessage());
			if(e!=null) {
				responseRechargeDto.setMessage(""+e.getMessage());
			}
			responseRechargeDto.setSuccess(false);
			return responseRechargeDto;
		}
		logger.info(" RECHARGE SERVICE [OUT]  : " + responseRechargeDto);
		return responseRechargeDto;
	}

	
	public ResponseRechargeDto  rechargeClassique(@Valid @RequestBody Recharge recharge) throws Exception {

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
			responseRechargeDto.setMessage(""+e.getMessage());
			responseRechargeDto.setSuccess(false);
			return responseRechargeDto;
		}

		TransactionPayement transactionPayement = null;
		transactionPayement = RechargeServiceHelper.bindClassicTransactionPayement(recharge,"CLASSIQUE");
		transactionPayement.setTransactionStatus("TA");
		transactionPayement.setService(serviceMauritel);
		transactionPayement.setTransactionId(recharge.getIdTransction());
		// PERSISTANCE
		transactionPayement = transactionPayementService.save(transactionPayement);

		// DISPONIBILITE DU SERVICE OU STOCK to ignore

		// APPEL DE L'API MAURITEL EXTERNE
		try {
			responseRechargeDto = rechargeService.rechargeClassique(recharge);
		} catch (Exception e) {
			// Check Exception
			System.err.println("RETOUR : " + transactionPayement);
			traitementService.responseException();
			transactionPayement.setId(transactionPayement.getId());
			transactionPayement.setTransactionStatus("TF");
			transactionPayement.setTransactionDate(new Date());
			transactionPayement.setErrorMessage(e.getMessage());
			transactionPayement.setTransactionId(recharge.getIdTransction());
			// transactionPayement.setService(serviceMauritel);
			transactionPayementService.upadte(transactionPayement);
			logger.info("EXCEPTION  : " + e.getMessage());
			responseRechargeDto.setMessage(""+e.getMessage());
			responseRechargeDto.setSuccess(false);
			return responseRechargeDto;
		}

		try {
			if (responseRechargeDto.isSuccess()) {
				// Check Success
				traitementService.responseSuccess();
				transactionPayement.setTransactionStatus("TS");
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setTransactionId(recharge.getIdTransction());
				transactionPayement.setSuccess(true);
				transactionPayementService.save(transactionPayement);
			} else {
				// Check Exception
				traitementService.responseException();
				transactionPayement.setTransactionStatus("TF");
				transactionPayement.setTransactionId(recharge.getIdTransction());
				transactionPayement.setTransactionDate(new Date());
				transactionPayement.setErrorMessage(responseRechargeDto.getMessage());
				transactionPayementService.save(transactionPayement);
			}
		}catch (Exception e){
			// Check exception
			traitementService.responseException();
			logger.info(e.getMessage());
			responseRechargeDto.setMessage(" " + e.getMessage());
			responseRechargeDto.setSuccess(false);
		}
		logger.info("RECHARGE CLASSIQUE [OUT] : " + responseRechargeDto);
		return responseRechargeDto;
	}

}
