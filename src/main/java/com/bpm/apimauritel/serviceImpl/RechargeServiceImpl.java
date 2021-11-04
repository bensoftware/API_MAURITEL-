package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.bpm.apimauritel.services.RechargeService;

@Service
public class RechargeServiceImpl implements RechargeService {

	public final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
	
	@Autowired
	public RestTemplate restTemplate;
	
	@Value("${host.mauritel}")
    public String host;

	@Override
	public String checkStatus() throws Exception {
		// TODO Auto-generated method stub
		final String baseUrl = host +"/"+"/bm/api/check";
		ResponseEntity<String> response=null;
		String result="";
		try {
		   response=restTemplate.exchange(baseUrl,HttpMethod.GET,null,String.class);
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
	public List<ServiceDto> getMarketingServices(TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization","Bearer " + tokenDto.getToken());
		
		HttpEntity<String> entete = new HttpEntity<>(headers);
		
		System.err.println("TOKEN TO BE SEND : "  + "Bearer " + tokenDto.getToken());
		
		String URL=host+"/"+"bm/api/services";
		List<ServiceDto> serviceDtos= new ArrayList<>();
		try {
			 ResponseEntity<Object> response = restTemplate.exchange(URL,HttpMethod.GET,entete,Object.class);
				
				if(response.getStatusCode()==HttpStatus.OK){
					if(response.getBody()!=null ) {
						
						List<Object> list = (List<Object>)response.getBody();
						
						for(Object x : list) {
							Map<String, String> map= (Map<String,String>) x;
							serviceDtos.add(new ServiceDto(map.get("Service"), map.get("CodeOperation"), map.get("Description"), map.get("Amount")));
							
						}
					}	
				}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return serviceDtos;
	}

	
	
	@Override
	public ResponseRechargeDto rechargeParServiceMarketing(RechargeMarketingDto rechargeMarketingDto,TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization","Bearer " + tokenDto.getToken());
		
		HttpEntity<String> entete = new HttpEntity<>(headers);
		
		Map<String, String> params = new HashMap();
		params.put("sender", rechargeMarketingDto.getSender());
		params.put("receiver",rechargeMarketingDto.getReceiver());
		params.put("amount", rechargeMarketingDto.getAmount());
		params.put("service", rechargeMarketingDto.getService());
		
		String URL=host+"/bm/api/recharge/";
		
		ResponseRechargeDto rechargeDto=new ResponseRechargeDto();
		try {
			HttpEntity<ResponseRechargeDto> response = restTemplate.exchange(URL, HttpMethod.GET, entete, ResponseRechargeDto.class, params);
			if(response.getBody()!=null) {
				rechargeDto=response.getBody();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Exception : " + e.getMessage());
		}
		return rechargeDto;
	}

	
	@Override
	public ResponseRechargeDto rechargeClassique(RechargeClassiqueDto rechargeClassiqueDto,TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization","Bearer " + tokenDto.getToken());
		
		HttpEntity<String> entete = new HttpEntity<>(headers);
		
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("sender", rechargeClassiqueDto.getSender());
		params.put("receiver",rechargeClassiqueDto.getReceiver());
		params.put("amount", rechargeClassiqueDto.getAmount());
		
		
		String URL=host+"/bm/api/recharge/";
		
		ResponseRechargeDto rechargeDto=new ResponseRechargeDto();
		try {
			HttpEntity<ResponseRechargeDto> response = restTemplate.exchange(URL, HttpMethod.GET, entete, ResponseRechargeDto.class, params);
			if(response.getBody()!=null) {
				rechargeDto=response.getBody();
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Exception : " + e.getMessage());
		}
		return rechargeDto;
	}

}
