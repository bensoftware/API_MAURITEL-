package com.bpm.apimauritel.serviceImpl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bpm.apimauritel.services.SmsService;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	RestTemplate restTemplate;
	
	@Value("#{'${superviseur}'.split(';')}")
	List<String> telephones;

	@Override
	public int sendSms(String msg) throws Exception {

		if(telephones!=null && telephones.size()>0)
			for(String phone : telephones) {
				try {
					String url= "http://30.30.1.149/send_sms.php?Phonenumber="+phone+"&text="+msg+"&unicode=1";
					ResponseEntity<Object> response
					  = restTemplate.getForEntity(url, Object.class);
					if(response.getStatusCode().equals(HttpStatus.OK)) {
						return 0; 
					}
					
				} catch (Exception e) {
				}
			}
		return 1;
	}
	


}
