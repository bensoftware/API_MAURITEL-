package com.bpm.apimauritel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.bpm.apimauritel.entities.Amount;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.entities.ServiceMauritel;
import com.bpm.apimauritel.repositories.ServiceMauritelRepository;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailService;
import com.bpm.apimauritel.services.ServiceMauritelService;

@Component
public class TestMain implements ApplicationRunner {

	@Autowired
	AmountService amountService;

	@Autowired
	ServiceMauritelRepository serviceMauritelRepository;

	@Autowired
	DetailService detailService;

	@Autowired
	ServiceMauritelService serviceServiceMauritelService;

	@Autowired
	CashService cashService;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String text= "50UM/Voix:60min///SMS:50/Data:50Mo/////Validité::::72H";

		String[] tab= text.split("/+");
		
		for(String p : tab) {
			System.out.println(p);
		}
		
		
		String validite= tab[tab.length-1];
		
		validite=validite.replaceAll("Validité:*", "");
		
		System.out.println(validite);
		
		String description="";
		
		int size=tab.length-1;
		for(int i=0;i<tab.length;i++) {
			String p= tab[i];
			if(i!=0 && i!= size) {
				if(description.equals(""))
				    description+=p;
				else
					description+=" "+p;

			}
		}

		System.out.println(description);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
//		ServiceMauritel serviceMauritel = new ServiceMauritel();
//		serviceMauritel.setActif(true);
//		serviceMauritel.setCodeOperation("d1d5");
//		serviceMauritel.setCodeService("55");
//		serviceMauritel.setId(268L);
//		// serviceServiceMauritelService.save(serviceMauritel);
//
//		Amount amount = new Amount();
//		amount.setActif(true);
//		amount.setAmount(14523);
//		amount.setId(270L);
//		// amountService.save(amount);
//
//		Detail detail = new Detail();
//		detail.setAmount(amount);
//		detail.setServiceMauritel(serviceMauritel);
//		detail.setDescriptionAr("Ar");
//		detail.setDescriptionAr("En");
//		detail.setDescriptionAr("Fr");
//
//		detail.setValidityAr("24h");
//		detail.setValidityEn("15H");
//		detail.setValidityFr("4512");
//
//		// detailService.save(detail);
//		cashService.savePureData();
//		
//		System.err.println("Je suis ici pour la....... : " );
		
		String text= "50UM/Voix:60min/SMS:50/Data:50Mo//Validité:72H";
		System.out.println(text);

		String[] tab= text.split("/");
		
		for(String p : tab) {
			System.out.println(p);
		}

	}

}
