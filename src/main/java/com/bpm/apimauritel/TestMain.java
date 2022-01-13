package com.bpm.apimauritel;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.bpm.apimauritel.dtos.TokenDto;
import com.bpm.apimauritel.securities.JWT;

public class TestMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		long expire= 1641416349;		
//		BigDecimal bigDec= new BigDecimal(expire*1000);
//		
//		Date dateExp=new Date(bigDec.longValue());
//		System.out.println(dateExp);
//		
//
//		Date dateActuel = new Date();
//		
//		Calendar c1= Calendar.getInstance();
//		c1.setTime(dateExp);
//		
//		Calendar c2= Calendar.getInstance();
//		c2.setTime(dateActuel);
//		
//		if(c2.after(c1)) {
//			System.out.println("Expiré");
//		}
//		else {
//			System.out.println("Actif");
//
//		}
		
		
//		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY0MTQ2OTQzMCwicm9sZXMiOlsiQWRtaW4iXSwiYWN0aW9ucyI6W3siYXV0aG9yaXR5IjoiY29tcHRhIn0seyJhdXRob3JpdHkiOiJleHBpbnQifSx7ImF1dGhvcml0eSI6ImV4cG9ydHJlY2gifSx7ImF1dGhvcml0eSI6ImdzYXBpIn0seyJhdXRob3JpdHkiOiJpbnR2b3VjaCJ9LHsiYXV0aG9yaXR5IjoiaXJjIn0seyJhdXRob3JpdHkiOiJpcnMifSx7ImF1dGhvcml0eSI6Im1hamV4In0seyJhdXRob3JpdHkiOiJub3RyZWNoIn0seyJhdXRob3JpdHkiOiJwYXJnbG8ifSx7ImF1dGhvcml0eSI6InBhcnJlY2gifSx7ImF1dGhvcml0eSI6InJyYyJ9LHsiYXV0aG9yaXR5IjoicnVsZXMifSx7ImF1dGhvcml0eSI6InRhIn0seyJhdXRob3JpdHkiOiJ1c2VycyJ9XX0.BGzSDGbS6ZIp0Eiey29sSndVFHvaCQA3rfHuGuz_JLM";
//
//		TokenDto tokenDto=new TokenDto();
//		tokenDto.setToken(token);
////		
////		
//		long exp= JWT.getExpirationTime(tokenDto);
//		
//		System.out.println(JWT.iSJwtTimeValid(exp));
		
	}

}
