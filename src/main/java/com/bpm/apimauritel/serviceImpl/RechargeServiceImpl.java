package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.bpm.apimauritel.dtos.ServiceDtoList;
import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.dtos.UserDto;
import com.bpm.apimauritel.services.RechargeService;

@Service
public class RechargeServiceImpl implements RechargeService {

	final Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);
	
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
	public ServiceDto[] getMarketingServiceArray(TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization","Bearer " + tokenDto.getToken());
		
		System.err.println("TOKEN TO BE SEND : "  + "Bearer " + tokenDto.getToken());
		HttpEntity<String> entete = new HttpEntity<>(headers);
		
		String URL=host+"/"+"bm/api/services";
		ServiceDto[]  listService =null;
		try {
//			 ResponseEntity<ServiceDto[]> response = restTemplate.exchange("https://76.65.250.37:8086/bm/api/services",HttpMethod.GET,entete,ServiceDto[].class);
//			
//			if(response.getStatusCode()==HttpStatus.OK){
//				if(response.getBody()!=null ) {
//					System.err.println("Liste : "+response.getBody());
//					logger.info("Number of object in the array : "+response.getBody().length);
//					return response.getBody();
//				}
//					
//			}
			
			List<ServiceDto> serviceDtos= new ArrayList<>();
			
			 ResponseEntity<Object> response = restTemplate.exchange("https://76.65.250.37:8086/bm/api/services",HttpMethod.GET,entete,Object.class);
				
				if(response.getStatusCode()==HttpStatus.OK){
					if(response.getBody()!=null ) {
						
						List<Object> list = (List<Object>) response.getBody();
						
						for(Object x : list) {
							Map<String, String> map= (Map<String, String>) x;
							
							System.err.println("Service :"+map.get("Service"));
							System.err.println("CodeOperation :"+map.get("CodeOperation"));
							System.err.println("Service :"+map.get("Amount"));
							System.err.println("Service :"+map.get("Description"));
                            
							serviceDtos.add(new ServiceDto(map.get("Service"), map.get("CodeOperation"), map.get("Description"), map.get("Amount")));
							System.out.println("------------------------");
						//	System.out.println(map);
						}

						System.out.println(serviceDtos);
					//	System.err.println("Liste : "+response.getBody());
						
					}
						
				}
		} catch (Exception e) {
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


	@Override
	public ServiceDtoList getMarketingService(TokenDto tokenDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
