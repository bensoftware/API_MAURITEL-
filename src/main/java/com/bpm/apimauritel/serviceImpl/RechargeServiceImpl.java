package com.bpm.apimauritel.serviceImpl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bpm.apimauritel.dtos.Recharge;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.helpers.RechargeServiceHelper;
import com.bpm.apimauritel.messages.Message;
import com.bpm.apimauritel.securities.JWT;
import com.bpm.apimauritel.services.RechargeService;


@Service
public class RechargeServiceImpl implements RechargeService {

	 ///Test Purpose 
	///http://30.30.1.140:8866/api/V1/classique
	
	public final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	@Autowired
	public RestTemplate restTemplate;

	@Value("${host.mauritel}")
	public String host;

	@Value("${username}")
	public String username;

	@Value("${password}")
	public String password;

	public TokenDto token ;

	@Override
	public String checkStatus() throws Exception {
		final String baseUrl = host + "/" + "bm/api/check";
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
			logger.info("MAURITEL RESPONSE STATUS : " + response.getBody());

			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}else{
				throw new Exception("EXCEPTION CODE ERREUR : " + response.getStatusCode());
			}
		} catch (Exception e) {
			logger.info("EXCEPTION  " + e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	
	@Override
	public TokenDto authentication() throws Exception {

		if (this.checkStatus().equals(Message.MAURITEL_SERVER_DOWN)) {
			logger.info("STATUS == " + this.checkStatus() + "  " +  Message.MESSAGE_MAURITEL_SERVER_DOWN);
			throw new Exception(Message.MESSAGE_MAURITEL_SERVER_DOWN);
		}

		UserDto userDto = new UserDto(username,password);
		String url = host + "/" + "bm/authenticate";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);

		try {
			ResponseEntity<TokenDto> response = restTemplate.postForEntity(url, request, TokenDto.class);
			logger.info("MAURITEL RESPONSE STATUS : " + response.getStatusCode());

			if (response.getStatusCode() == HttpStatus.OK) {
				if (response.getBody() != null) {
					token = response.getBody();
					logger.info("TOKEN VALUE : " + token);
				}
			}else {
				throw new Exception("EXCEPTION WHEN GETTING THE TOKEN: "+response.getStatusCode());
			}
		} catch (Exception e) {
			logger.info(Message.EXECPTION_CALL_MAURITEL_SERVER +  e.getMessage());
			throw new Exception(Message.EXECPTION_CALL_MAURITEL_SERVER +  e.getMessage());
		}
		return token;
	}

	
	@Override
	public List<ServiceDto> getMarketingServices() throws Exception {

		if (this.checkStatus().equals(Message.MAURITEL_SERVER_DOWN)) {
			logger.info("STATUS == " + this.checkStatus() + "  " +  Message.MESSAGE_MAURITEL_SERVER_DOWN);
			throw new Exception(Message.MESSAGE_MAURITEL_SERVER_DOWN);
		}

		if (this.token == null) {
			this.token = this.authentication();
			logger.info("RE-AUTHENTICATION");
		}

		if (!JWT.iSJwtTimeValid(JWT.getExpirationTime(this.token))){
			token = authentication();
		}

		HttpHeaders headers = RechargeServiceHelper.getHeaders(this.token);
		HttpEntity<String> entete = new HttpEntity<>(headers);

		System.err.println("TOKEN TO BE SEND : " + "Bearer " + token.getToken());

		String URL = host + "/" + "bm/api/services";
		List<ServiceDto> serviceDtos = new ArrayList<>();
		try {
			ResponseEntity<Object> response = restTemplate.exchange(URL, HttpMethod.GET, entete, Object.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				if (response.getBody() != null) {
					List<Object> listObjet = (List<Object>)response.getBody();

					for (Object x : listObjet) {
						Map<String, String> map = (Map<String,String>) x;

						System.err.println("" + map);

						serviceDtos.add(new ServiceDto(map.get("Service"), map.get("CodeOperation"),
								map.get("Description"), map.get("Amount")));
					}
				}
			}else{
				throw new Exception("Exception Code erreur : " + response.getStatusCode());
		    }
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new Exception(e.getMessage());
		}
		return serviceDtos;
	}

	
	
	
	@Override
	public ResponseRechargeDto rechargeParServiceMarketing(RechargeMarketingDto rechargeMarketingDto) throws Exception {

		if (this.checkStatus().equals(Message.MAURITEL_SERVER_DOWN)) {
			logger.info("STATUS == " + this.checkStatus() + "  " +  Message.MESSAGE_MAURITEL_SERVER_DOWN);
			throw new Exception(Message.MESSAGE_MAURITEL_SERVER_DOWN);
		}

		if (this.token == null) {
			this.token = this.authentication();
		}

		if (!JWT.iSJwtTimeValid(JWT.getExpirationTime(this.token))) {
			this.token = authentication();
		}

		HttpHeaders headers = RechargeServiceHelper.getHeaders(this.token);
		HttpEntity<String> entete = new HttpEntity<>(headers);
		Map<String, String> params = RechargeServiceHelper.getParametters(rechargeMarketingDto);

		String URL = host + "/bm/api/recharge/";

		ResponseRechargeDto rechargeDto = new ResponseRechargeDto();
		try {
			HttpEntity<ResponseRechargeDto> response = restTemplate.exchange(URL, HttpMethod.GET, entete,
					ResponseRechargeDto.class, params);
			if (response.getBody() != null) {
				rechargeDto = response.getBody();
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new Exception("Exception : " + e.getMessage());
		}
		return rechargeDto;
	}

	
	@Override
	public ResponseRechargeDto rechargeClassique(Recharge recharge) throws Exception {

		// Disponibilité du service
		if (this.checkStatus().equals(Message.MAURITEL_SERVER_DOWN)) {
			//logger.info(Message.MESSAGE_MAURITEL_SERVER_DOWN);
			throw new Exception(Message.MESSAGE_MAURITEL_SERVER_DOWN);
		}

		// TEST DE VALIDITE DU TOKEN
		if (this.token == null) {
			logger.info("AUTHENTICATION ");
			this.token = this.authentication();
			logger.info("AUTHENTICATED ");
		}

		long tokenTime=JWT.getExpirationTime(this.token);
		
		logger.info("TOKEN TIME EXPIRATION : "  + tokenTime);
		
		if(JWT.iSJwtTimeValid(tokenTime)==false){
			this.token =this.authentication();
			logger.info("TIME EXPIRED ,GET NEW TOKEN  : " + this.token);
		}
		
		HttpHeaders headers = RechargeServiceHelper.getHeaders(this.token);
		HttpEntity<String> entete = new HttpEntity<>(headers);

		String URL = host + "/bm/api/recharge/?sender="+recharge.getSender()+"&receiver="+recharge.getReceiver()+"&amount="+recharge.getAmount();
		
		try {
			logger.info("GLOBAL URL --- :  " + URL);
			
			ResponseEntity<ResponseRechargeDto> response = restTemplate.exchange(URI.create(URL) , HttpMethod.GET, entete,
					ResponseRechargeDto.class);
			
			logger.info("RESPONSE HTTP STATUS   :   " + response.getStatusCode());

			if (response.getStatusCode() == HttpStatus.OK){
				return response.getBody();
			}else{
				throw new Exception("EXCEPTION HTTP STATUS CODE  : " + response.getStatusCode());
			}
		}catch (Exception e) {
			throw new Exception("EXCEPTION WHEN CALLING MAURITEL : " + e.getMessage());
		}
	}

	
	@Override
	public Set<String> getMontants(List<DetailService> listDetailService) throws Exception {
		// TODO Auto-generated method stub
		Set<String> setOfAmount = new HashSet<>();
		for (DetailService detailService : listDetailService){
			setOfAmount.add(detailService.getAmount());
		}
		return setOfAmount;
	}

}
