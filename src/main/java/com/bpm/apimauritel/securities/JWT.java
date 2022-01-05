package com.bpm.apimauritel.securities;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bpm.apimauritel.dtos.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWT {

	public static final Logger logger = LoggerFactory.getLogger(JWT.class);

	public static boolean iSJwtTimeValid(int exp){
		
		/*
		 * if (exp < new Date().getTime() / 1000){ logger.info(" TIME EXPIRED : TRUE ");
		 * return true; }
		 */
		if (new Date().getTime() >= exp * 1000) {	 
			  return true;
			}
		 logger.info(" TIME EXPIRED : TRUE ");
		return false;
	}
	

	public static int getExpirationTime(TokenDto tokenDto) throws Exception {

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
