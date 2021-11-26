package com.bpm.apimauritel.serviceImpl;

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
import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;
import com.bpm.apimauritel.dtos.ResponseRechargeDto;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.helpers.RechargeServiceHelper;
import com.bpm.apimauritel.securities.JWT;
import com.bpm.apimauritel.services.RechargeService;

@Service
public class RechargeServiceImpl implements RechargeService {

	public final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

	@Autowired
	public RestTemplate restTemplate;

	@Value("${host.mauritel}")
	public String host;

	@Value("${username}")
	public String username;

	@Value("${password}")
	public String password;

	public TokenDto token;

	@Override
	public String checkStatus() throws Exception {
		final String baseUrl = host + "/" + "bm/api/check";
		ResponseEntity<String> response = null;
		String result = "";
		try {
			response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				return response.getBody();
			}
			logger.info("MAURITEL RESPONSE STATUS : " + response.getStatusCode());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return response.getBody();
	}

	
	@Override
	public TokenDto authentication() throws Exception {
		
		if(!this.checkStatus().equals("1")) {
			throw new Exception("MAURITEL SERVICE IS DOWN ");
		}
		
		UserDto userDto = new UserDto(username, password);

		String url = host + "/" + "bm/authenticate";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserDto> request = new HttpEntity<>(userDto, headers);

		try {
			ResponseEntity<TokenDto> response = restTemplate.postForEntity(url, request, TokenDto.class);
			if (response.getStatusCode() == HttpStatus.OK) {
				if (response.getBody() != null) {
					token = response.getBody();
					logger.info("TOKEN : " + token);
				}
			}
		} catch (Exception e) {
			throw new Exception("Exception when calling MAURITEL API : " + e.getMessage());
		}
		return token;
	}
	
	
	

	@Override
	public List<ServiceDto> getMarketingServices() throws Exception {
	
		if(!this.checkStatus().equals("1")) {
			throw new Exception("MAURITEL SERVICE IS DOWN ");
		}
		
		if(this.token==null) {
			this.token=this.authentication();
		}
		
		if(!JWT.iSJwtTimeValid(JWT.getExpirationTime(this.token))) {
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
					List<Object> listObjet = (List<Object>) response.getBody();

					for (Object x : listObjet) {
						Map<String, String> map = (Map<String, String>) x;
						
						System.err.println(""+map);
						
						serviceDtos.add(new ServiceDto(map.get("Service"), map.get("CodeOperation"),
								map.get("Description"), map.get("Amount")));
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return serviceDtos;
	}

	
	
	
	
	@Override
	public ResponseRechargeDto rechargeParServiceMarketing(RechargeMarketingDto rechargeMarketingDto) throws Exception {
	
		if(!this.checkStatus().equals("1")) {
			throw new Exception("MAURITEL SERVICE IS DOWN ");
		}
		
		if(this.token==null) {
			this.token=this.authentication();
		}
		
		if (!JWT.iSJwtTimeValid(JWT.getExpirationTime(this.token))){
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
			throw new Exception("Exception : " + e.getMessage());
		}
		return rechargeDto;
	}

	
	@Override
	public ResponseRechargeDto rechargeClassique(RechargeClassiqueDto rechargeClassiqueDto) throws Exception {
	
		if(!this.checkStatus().equals("1")) {
			throw new Exception("MAURITEL SERVICE IS DOWN ");
		}
		
		if(this.token==null) {
			this.token=this.authentication();
		}
		
		if (!JWT.iSJwtTimeValid(JWT.getExpirationTime(this.token))) {
			this.token = authentication();
		}
		
		HttpHeaders headers = RechargeServiceHelper.getHeaders(this.token);

		HttpEntity<String> entete = new HttpEntity<>(headers);

		Map<String, String> params = RechargeServiceHelper.getParamettersRechargeClassique(rechargeClassiqueDto);

		String URL = host + "/bm/api/recharge/";

		ResponseRechargeDto rechargeDto = new ResponseRechargeDto();

		try {
			HttpEntity<ResponseRechargeDto> response = restTemplate.exchange(URL,HttpMethod.GET, entete,
					ResponseRechargeDto.class, params);
			if (response.getBody() != null) {
				rechargeDto = response.getBody();
				System.err.println("Service rechargeClassique : " + rechargeDto);
			}
		}catch (Exception e) {
			throw new Exception("Exception : " + e.getMessage());
		}
		return rechargeDto;
	}


	@Override
	public Set<String> getMontants(List<DetailService> listDetailService) throws Exception {
		// TODO Auto-generated method stub
		Set<String> setOfAmount=new HashSet<>();
		for (DetailService detailService : listDetailService) {
			setOfAmount.add(detailService.getAmount());
		}
		return setOfAmount;
	}
	
	
	

}
