package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.Amount;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceMauritel;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.helpers.CashHelper;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.CashDataService;
import com.bpm.apimauritel.services.DetailServiceData;
import com.bpm.apimauritel.services.ServiceMauritelService;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class CashDataServiceImpl implements CashDataService {

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private ServiceMauritelService serviceMauritelService;

	@Autowired
	private AmountService amountService;

	@Autowired
	private DetailServiceData detailServiceData;

	@Override
	public void savePureData() throws Exception {
		// TODO Auto-generated method stub
		List<ServiceT> listServices = serviceService.getAllServices();
		List<DetailService> listDetailServices = null;

		ServiceMauritel serviceMauritel = null;
		List<Detail> listDetails = new ArrayList<>();
		for (ServiceT serviceT : listServices) {
			// Double amoun = Double.valueOf(detailService.getAmount());

			// Verifie si le service existe deja
			serviceMauritel = serviceMauritelService.findByCodeService(serviceT.getCodeService());

			// SAVE
			if (serviceMauritel == null) {
				serviceMauritel = new ServiceMauritel();
				serviceMauritel.setActif(true);
				serviceMauritel.setCodeOperation(serviceT.getCodeOperation());
				serviceMauritel.setCodeService(serviceT.getCodeService());
				serviceMauritel = serviceMauritelService.save(serviceMauritel);
			} else if(serviceMauritel!=null) {
				// UPDATE
				serviceMauritel.setActif(true);
				serviceMauritel.setCodeOperation(serviceT.getCodeOperation());
				serviceMauritel.setCodeService(serviceT.getCodeService());
				serviceMauritel = serviceMauritelService.save(serviceMauritel);
			}

			Detail detail = null;

			listDetailServices = serviceT.getDetailServices();

			for (DetailService detailService : listDetailServices) {

				Double amoun = Double.valueOf(detailService.getAmount());
				Amount amount = new Amount();
				amount = amountService.findByAmount(amoun);
				// System.err.println("le montant est " + amount);

				if (amount == null) {
					amount = new Amount();
					amount.setAmount(Double.valueOf(detailService.getAmount()));
					amount.setActif(true);

					amount = amountService.save(amount);

					// System.err.println("LE MONTANT APRES" +amount);
					detail = new Detail();
					detail.setAmount(amount);
					detail.setServiceMauritel(serviceMauritel);

					detail.setDescription(detailService.getDescription());
					String validity = CashHelper.getValidity(detailService.getDescription());
					detail.setValidityAr(validity);
					detail.setValidityEn(validity);
					detail.setValidityFr(validity);
					String description = CashHelper.getDescription(detailService.getDescription());
					detail.setDescriptionAr(description);
					detail.setDescriptionEn(description);
					detail.setDescriptionFr(description);

					listDetails.add(detail);
				} else if (amount != null) {
					
					//Comment differencier la mise à jour de Detail et la sauvegarde ?
					detail = new Detail();
					detail.setAmount(amount);
					detail.setServiceMauritel(serviceMauritel);
					detail.setDescription(detailService.getDescription());
					
					String validity = CashHelper.getValidity(detailService.getDescription());
					detail.setValidityAr(validity);
					detail.setValidityEn(validity);
					detail.setValidityFr(validity);
					String description = CashHelper.getDescription(detailService.getDescription());
					detail.setDescriptionAr(description);
					detail.setDescriptionEn(description);
					detail.setDescriptionFr(description);

					listDetails.add(detail);
				}
			}
			detailServiceData.saveAll(listDetails);
			listDetails.clear();
		}

	}

}
