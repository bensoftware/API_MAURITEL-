package com.bpm.apimauritel.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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

	@Autowired
	RestTemplate restTemplate;
	
	@Value("${host.mauritel}")
    String host;

	@Override
	public String checkStatus() throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
	     //	headers.set("Accept", "application/json");
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    
		final String baseUrl = host + "/check";
		
		ResponseEntity<String> response=null;
		String result="";
		try {
		   response=restTemplate.exchange("https://76.65.250.37:8086/bm/api/check", HttpMethod.GET, null, String.class);
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
	public TokenDto authentication(UserDto usrDto) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceDto getMarketingService() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
