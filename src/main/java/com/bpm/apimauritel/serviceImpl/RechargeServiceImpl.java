package com.bpm.apimauritel.serviceImpl;

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
import com.bpm.apimauritel.dtos.ServiceDtoList;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.services.RechargeService;



@Service
public class RechargeServiceImpl implements RechargeService {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${host.mauritel}")
    String host;

	@Override
	public String checkStatus() throws Exception {
		// TODO Auto-generated method stub
		final String baseUrl = host +"/"+"/bm/api/check";
		ResponseEntity<String> response=null;
		String result="";
		try {
		   response=restTemplate.exchange(baseUrl, HttpMethod.GET, null, String.class);
		   if(response.getStatusCode()==HttpStatus.OK){
			   return response.getBody();
		   }
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		  return response.getBody();
	}
	
	
	@Override
	public TokenDto authentication(UserDto userDto) throws Exception{
		// TODO Auto-generated method stub
		final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
		String url=host+"/"+"bm/authenticate";
	    TokenDto token =new TokenDto();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserDto> request = new HttpEntity<>(userDto,headers);
		try {
			ResponseEntity<TokenDto> response = restTemplate.postForEntity(url,request,TokenDto.class);
			if(response.getStatusCode()==HttpStatus.OK){
				if(response.getBody()!=null) {
					token=response.getBody();
					logger.info("TOKEN : " +token);
				}
				return token;
			}
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Exception when calling MAURITEL API : "+e.getMessage());
		}
		return token;
	}
	
	
	

	@Override
	public ServiceDtoList getMarketingService(TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tokenDto);
		
		HttpEntity<String> entete = new HttpEntity<String>("parameters", headers);
		
		 
		String URL=host+"/"+"bm/api/services";
		ServiceDtoList listService =null;
		try {
			 ResponseEntity<ServiceDtoList> response = restTemplate.exchange(URL, HttpMethod.GET, entete, ServiceDtoList.class);
			//ResponseEntity<ServiceDtoList> response =restTemplate.getForEntity(URL,ServiceDtoList.class);
			if(response.getStatusCode()==HttpStatus.OK){
				if(response.getBody()!=null ) ;
					 listService=response.getBody();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
		return listService;
	}

	
	
	@Override
	public ResponseRechargeDto rechargeParServiceMarketing(RechargeMarketingDto rechargeMarketingDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public ResponseRechargeDto rechargeClassique(RechargeClassiqueDto rechargeClassiqueDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
