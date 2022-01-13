package com.bpm.apimauritel.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bpm.apimauritel.dtos.RechargeClassiqueDto;
import com.bpm.apimauritel.dtos.RechargeMarketingDto;


public class MauritelApiHelper {
	
	
	
	public static boolean verificationSuspect(Date exsitant,Long paiement, int minute) {

		
		Calendar ac= Calendar.getInstance();
		ac.setTime(exsitant);
		// incrementation par minute
		ac.add(Calendar.MINUTE, minute); 	
		
		
		Calendar actuel= Calendar.getInstance();
		actuel.setTime(new Date(paiement));
		if(actuel.after(ac)) {
			return true;
		}
		return false;
	}
	
       public static boolean isDetailException(Date dateEx, int minute) {

		
		Calendar ac= Calendar.getInstance();
		ac.setTime(dateEx);
		// incrementation par minute
		ac.add(Calendar.MINUTE, minute); 	
		
		
		Calendar actuel= Calendar.getInstance();
		if(actuel.after(ac)) {
			return true;
		}
		return false;
	}
	
	
	public static String removeSpecialCaracter(String p) {
		
		
		String res="";
		
		try {
		  res= p.replaceAll("\"", "");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return res;
		
		
	}
	
	
	public	static  String getAccountId(String id) {
		int size= id.length();
		int restant=0;
		String trestant="";
		if(size<8) {
			restant= 8-size;
			
			for(int i=0;i<restant;i++) {
				trestant+="0";
			}
		}
		
		return trestant+id;
	}
	
	
	public static  long getIdFacturierFromBillerId(String billerId) {
    	long idFacturier=0L;
    	switch(billerId) {
    	  case "000011":
    		  idFacturier= 2L;
    		  break;
      	  case "000012":
      	    	idFacturier= 1L;
      	    	break;
      	  case "000013":
    		  idFacturier= 3L;
    		  break;
    	}
    	return  idFacturier;
    }
	
	public static  double amountStrToDouble(String amount) {
		double doubleAmount =0;
		try {
			 String montant= amount.trim();
			 if(montant.equals(""))
				 return doubleAmount; 
			 doubleAmount = Double.valueOf(montant);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return doubleAmount;
	}
	
	
	
	public static  Date getDate(String dateStr2) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		Date date = format.parse(dateStr2);
		return date;
	}
	
	
	

	public static void checkRechargeClassique(RechargeClassiqueDto rechargeClassiqueDto) {
		List<String> listValidatorErrors = new ArrayList<>();

		// Amount
		if (rechargeClassiqueDto.getAmount().isEmpty() || rechargeClassiqueDto.getAmount() == null) {
             
		}
		
		//
		if(rechargeClassiqueDto.getReceiver().isEmpty() || rechargeClassiqueDto.getReceiver() == null) {
            
		}
		
        if(rechargeClassiqueDto.getSender().isEmpty() || rechargeClassiqueDto.getSender()== null) {
            
		}
	}
	
	public static void check(RechargeMarketingDto rechargeMarketingDto) {
		List<String> listValidatorErrors = new ArrayList<>();

		 // Amount
		if(rechargeMarketingDto.getAmount().isEmpty() || rechargeMarketingDto.getAmount() == null) {
             
		}
		
		//
		if(rechargeMarketingDto.getReceiver().isEmpty() || rechargeMarketingDto.getReceiver() == null) {
            
		}
		
		//
        if(rechargeMarketingDto.getSender().isEmpty() || rechargeMarketingDto.getSender()== null) {
            
		}  
	}
	
	
}
