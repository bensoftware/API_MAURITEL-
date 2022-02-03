package com.bpm.apimauritel;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.repositories.ServiceMauritelRepository;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.CashDataService;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceData;
import com.bpm.apimauritel.services.ServiceMauritelService;

@Component
public class TestMain implements CommandLineRunner {

	@Autowired
	AmountService amountService;

	@Autowired
	ServiceMauritelRepository serviceMauritelRepository;

	@Autowired
	DetailServiceData detailServiceData;

	@Autowired
	ServiceMauritelService serviceServiceMauritelService;

	@Autowired
	CashService cashService;

	@Autowired
	CashDataService cashDataService;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		// TODO Auto-generated method stub
////		ServiceMauritel serviceMauritel = new ServiceMauritel();
////		serviceMauritel.setActif(true);
////		serviceMauritel.setCodeOperation("d1d5");
////		serviceMauritel.setCodeService("55");
////		serviceMauritel.setId(268L);
////		// serviceServiceMauritelService.save(serviceMauritel);
////
////		Amount amount = new Amount();
////		amount.setActif(true);
////		amount.setAmount(14523);
////		amount.setId(270L);
////		// amountService.save(amount);
////
////		Detail detail = new Detail();
////		detail.setAmount(amount);
////		detail.setServiceMauritel(serviceMauritel);
////		detail.setDescriptionAr("Ar");
////		detail.setDescriptionAr("En");
////		detail.setDescriptionAr("Fr");
////
////		detail.setValidityAr("24h");
////		detail.setValidityEn("15H");
////		detail.setValidityFr("4512");
////
////		// detailService.save(detail);
////		cashService.savePureData();
////		
////		System.err.println("Je suis ici pour la....... : " );
//
////		String text = "50UM/Voix:60min/SMS:50/Data:50Mo//Validité:72H";
////		System.out.println(text);
////
////		String[] tab = text.split("/");
////
////		for (String p : tab) {
////			System.out.println(p);
////		}
//
//		
//		// System.err.println("OBJECT : "+
//		// serviceServiceMauritelService.findByCodeService("6"));
//	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		String text = "50UM/Voix:60min///SMS:50/Data:50Mo/////Validité::::72H";

		//cashDataService.savePureData();
		//cashService.saveService();
	
//		for (Detail detail : detailServiceData.findAllDetailServices()) {
//			System.err.println(""+detail.getDescription());
//			System.err.println(""+detail.getAmount().getAmount());
//			System.err.println(""+detail.getServiceMauritel().getCodeOperation());
//			System.err.println(""+detail.getServiceMauritel().getCodeService());
//			break;
//		}

	//System.err.println(""+detailServiceData.findAllDetailServices());
		
Set<Detail> listdetaDetails	=	amountService.findByAmount(10D).getDetail();


//System.err.println("Amount" +detailServiceData.findDetailByAmount(3719));



//System.err.println("Liste des montants actifs :"+amountService.findAllActifAmounts());
//System.err.println("Liste des détails : " +listdetaDetails);
//cashDataService.savePureData();
	}

}
