package com.bpm.apimauritel.securities;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bpm.apimauritel.dtos.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {

	public static final Logger logger = LoggerFactory.getLogger(JWT.class);

	public static boolean iSJwtTimeValid(long expire){
				
		BigDecimal bigDec= new BigDecimal(expire*1000);
		Date dateExp=new Date(bigDec.longValue());
		Calendar c1= Calendar.getInstance();
		c1.setTime(dateExp);
		
		Date dateActuel = new Date();			
		Calendar c2= Calendar.getInstance();
		c2.setTime(dateActuel);
		
		if(c2.after(c1)) {
			 logger.info(" TIME IS  EXPIRED : THE TOKEN IS NO LONGER VALID ");
				return false;
		}
		else {
			 logger.info(" TIME IS NOT YET EXPIRED : THE TOKEN IS VALID  ");
			  return true;

		}

	}
	

	public static long getExpirationTime(TokenDto tokenDto) throws Exception {

		if (tokenDto == null) {
			throw new Exception("The token is null,try to see if MAURITEL SERVICE IS UP");
		}

		String jwtToken = tokenDto.getToken();

		String[] split_string = jwtToken.split("\\.");
		String base64EncodedBody = split_string[1];

		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Integer> map = null;
		try {
			map = mapper.readValue(body, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map.get("exp");
	}

	public void testDecodeJWT(TokenDto tokenDto) {
		String jwtToken = tokenDto.getToken();
		System.out.println("------------ Decode JWT ------------");
		String[] split_string = jwtToken.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		String base64EncodedSignature = split_string[2];

		System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		System.out.println("JWT Header : " + header);

		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
		String body = new String(base64Url.decode(base64EncodedBody));

		ObjectMapper mapper = new ObjectMapper();

		Map<String, Integer> map = null;
		try {
			map = mapper.readValue(body, Map.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map.get("exp"));
	}

}
