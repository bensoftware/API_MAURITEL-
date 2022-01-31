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

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		ServiceMauritel serviceMauritel = new ServiceMauritel();
		serviceMauritel.setActif(true);
		serviceMauritel.setCodeOperation("d1d5");
		serviceMauritel.setCodeService("55");
		serviceMauritel.setId(268L);
		// serviceServiceMauritelService.save(serviceMauritel);

		Amount amount = new Amount();
		amount.setActif(true);
		amount.setAmount(14523);
		amount.setId(270L);
		// amountService.save(amount);

		Detail detail = new Detail();
		detail.setAmount(amount);
		detail.setServiceMauritel(serviceMauritel);
		detail.setDescriptionAr("Ar");
		detail.setDescriptionAr("En");
		detail.setDescriptionAr("Fr");

		detail.setValidityAr("24h");
		detail.setValidityEn("15H");
		detail.setValidityFr("4512");

		// detailService.save(detail);

		System.err.println("Je suis ici pour la....... : " + detailService.findAllDetailServices());

	}

}
