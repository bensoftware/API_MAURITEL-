package com.bpm.apimauritel.serviceImpl;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.Amount;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceMauritel;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.helpers.CashHelper;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.CashService;
import com.bpm.apimauritel.services.DetailServiceServiceT;
import com.bpm.apimauritel.services.RechargeService;
import com.bpm.apimauritel.services.ServiceMauritelService;
import com.bpm.apimauritel.services.ServiceService;

@Service
public class CashServiceImpl implements CashService {

	final Logger logger = LoggerFactory.getLogger(CashServiceImpl.class);

	@Autowired
	ServiceService serviceService;

	@Autowired
	RechargeService rechargeService;

	@Autowired
	DetailServiceServiceT detailServiceServiceT;

	List<ServiceDto> listServiceFromMauritel;

	@Autowired
	ServiceMauritelService serviceMauritelService;

	AmountService amountService;

	@Override
	public void saveService() throws Exception {

		ServiceT serviceT = null;

		listServiceFromMauritel = rechargeService.getMarketingServices();

		Hashtable<String, String> serviceSaved = new Hashtable<>();

		for (ServiceDto serviceDto : listServiceFromMauritel) {
			// CodeService == au code unique qui identifie un service venant de l'API
			// MAURITEL
			if (!serviceSaved.contains(serviceDto.getService())) {
				serviceT = new ServiceT();
				serviceT.setCodeOperation(serviceDto.getCodeOperation());
				serviceT.setCodeService(serviceDto.getService());
				ServiceT service = serviceService.findServiceByCodeService(serviceDto.getService());
				if (service == null) {

					// Lorsqu'on ne le trouve pas........................: Comment bloquer ?

					// Si ce service n'existe plus dans Mauritel service : Comment le savoir ?

					// Comparaison entre ce qui est dans la base de donnée et ce qui vient de
					// Mauritel Service.

					// Est ce que ce qui est dans la base de donnée correspond à ce qui viens de
					// Mauritel.

					serviceT = serviceService.save(serviceT);
					saveDetailService(serviceT);
				} else {
					service.setCodeOperation(serviceDto.getCodeOperation());
					service = serviceService.save(service);
					this.saveDetailService(service);
				}
			}
			serviceSaved.put(serviceDto.getCodeOperation(), serviceDto.getService());
		}
	}

	@Override
	public void saveDetailService(ServiceT serviceT) throws Exception {
		DetailService detailService = null;
		for (ServiceDto serviceDto : listServiceFromMauritel) {
			// System.err.println("");
			if (serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
				detailService = new DetailService();
				detailService.setAmount(serviceDto.getAmount());
				detailService.setDescription(serviceDto.getDescription());
				detailService.setService(serviceT);
				try {
					DetailService detailServiceTest = detailServiceServiceT
							.findDetailServiceByDescription(serviceDto.getDescription());
					if (detailServiceTest == null) {
						// On insert le DetailService pour ce service,serviceT.
						detailServiceServiceT.save(detailService);
					} else {
						// On insert le DetailService pour ce service.
						// Mise à jour des informations modifiées.
						detailService.setAmount(serviceDto.getAmount());
						detailService.setDescription(serviceDto.getDescription());
						detailService.setService(serviceT);
						detailServiceServiceT.save(detailService);
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		}
	}

	@Override
	public void Upadate() throws Exception {

		List<ServiceT> listServicesFromDatabase = serviceService.getAllServices();
		// Verifier si un Service dans la base de donnée existe chez MAURITEL.
		Hashtable<String, ServiceT> serviceTempo = new Hashtable<>();
		Hashtable<String, ServiceT> serviceLoop = CashHelper.listToHashTableServiceT(listServicesFromDatabase);
		// Getting keySet() into Set
		Set<String> setOfServiceCodeService = serviceLoop.keySet();

		listServiceFromMauritel = rechargeService.getMarketingServices();

		ServiceT serviceTForUpdate = null;

		// Mise à Jour
		if (listServicesFromDatabase.size() == listServiceFromMauritel.size()
				|| listServiceFromMauritel.size() > listServicesFromDatabase.size()) {
			// Save And Update
		} else if (listServicesFromDatabase.size() > listServiceFromMauritel.size()) {
			// Blocage du service au niveau de la base de donnée Local si le service
			// n'existe plus chez MAURITEL
			this.deActivateService();
		}
	}

	private void deActivateService() throws Exception {
		List<ServiceT> listServicesFromDatabase = serviceService.getAllServices();
		// Verifier si un Service dans la base de donnée existe chez MAURITEL.
		listServiceFromMauritel = rechargeService.getMarketingServices();
		Hashtable<String, ServiceDto> HtableFromMauritel = CashHelper
				.listToHashTableServiceDto(listServiceFromMauritel);
		
		Hashtable<String, ServiceT> serviceLoop = CashHelper.listToHashTableServiceT(listServicesFromDatabase);
		
		for (ServiceT serviceT : listServicesFromDatabase) {
			if (!HtableFromMauritel.contains(serviceT.getCodeService())) {
				ServiceT serviceUPDATE = serviceLoop.get(serviceT.getCodeService());
				serviceUPDATE.setNotActivated(false);
				serviceService.save(serviceUPDATE);
			}
		}
	}

	
	
	@Override
	public void savePureData() throws Exception {
		// TODO Auto-generated method stub
		List<DetailService> listDetailServices = detailServiceServiceT.findAllDetailService();

		for (DetailService detailService : listDetailServices) {
			Double amoun = Double.valueOf(detailService.getAmount());
			Amount amount = new Amount();
			ServiceMauritel serviceMauritel = new ServiceMauritel();
			Detail detail = new Detail();
			try {
				// Test if the amount exist
				amount = amountService.findByAmount(amoun);
				// continue;
				// System.err.println("Yep");
			} catch (Exception e) {
				// TODO: handle exception
				// Si c'est null c'est que xq n'existe pas ainsi on l'ajoute.
				amount.setAmount(Double.valueOf(detailService.getAmount()));
				amount.setActif(true);
				// amount= amountService.save(amount);
				serviceMauritel.setActif(true);
				serviceMauritel.setCodeOperation(detailService.getService().getCodeOperation());
				serviceMauritel.setCodeService(detailService.getService().getCodeService());
				// serviceMauritel=serviceMauritelService.save(serviceMauritel);

				detail.setAmount(amount);
				detail.setServiceMauritel(serviceMauritel);
				// detail.setValidityAr(null);

				String codeOperation = detailService.getService().getCodeOperation();
				String validity = "";
				
				if (codeOperation.equals("GRATI-MEDIA")) {
					validity = detailService.getDescription().split("/")[2];
					String val = validity.split(":")[1];
					String description = detailService.getDescription().split("/")[1];
					System.err.println("GRATI-MEDIA : - " + val);
				}

				if (codeOperation.equals("GRATI-PLUS")) {
					String validity1 = detailService.getDescription().split("//|/.")[4];
					String val = validity1.split(":")[1];
					String description = detailService.getDescription().split("/")[1] + " "
							+ detailService.getDescription().split("/")[2] + " "
							+ detailService.getDescription().split("/")[3];
					// String validity5[] = detailService.getDescription().split("/");
					System.err.println("GRATI-PLUS : - " + val);
				}

				if (codeOperation.equals("GRATI-DAWLY")) {
					String validity3 = "";
					validity3 = detailService.getDescription().split("/")[2];
					String description = detailService.getDescription().split("/")[1];
					String val = validity3.split(":")[1];
					System.err.println("GRATI-DAWLY: - " + val);
				}

				if (codeOperation.equals("GRATI-NET")) {
					String validity1 = detailService.getDescription().split("/")[3];
					String description = detailService.getDescription().split("/")[1] + " "
							+ detailService.getDescription().split("/")[2];
					// String validity5[] = detailService.getDescription().split("/");
					String val = validity1.split(":")[1];
					System.err.println("GRATI-NET : - : " + val);
				}

				if (codeOperation.equals("GRATI-SMS")) {
					String validity1 = detailService.getDescription().split("/")[2];
					String description = detailService.getDescription().split("/")[1] + " "
							+ detailService.getDescription().split("/")[2];
					// String validity5[] = detailService.getDescription().split("/");
					String val = validity1.split(":")[1];
					System.err.println("GRATI-SMS : - : " + val);
				}

				if (codeOperation.equals("GRATI-FLEX")) {
					String validity1 = detailService.getDescription().split("/")[3];
					String description = detailService.getDescription().split("/")[1] + " "
							+ detailService.getDescription().split("/")[2];
					String val = "";
					if (!validity1.contains(":")) {
						val = validity1.substring(8, 12);
					} else {
						val = validity1.split(":")[1];
					}
					System.err.println("GRATI-FLEX : - : " + val);
				}

				if (codeOperation.equals("GRATI-ROAMING")) {
					String validity1 = detailService.getDescription().split("/")[4];
					String description = detailService.getDescription().split("/")[1] + " "
							+ detailService.getDescription().split("/")[2];
					String val = validity1.split(":")[1];
					System.err.println("GRATI-ROAMING : - : " + val);
				}

				if (codeOperation.equals("GRATI-NET-ROAMING")){
					String validity1 = detailService.getDescription().split("/")[2];
					String description = detailService.getDescription().split("/")[1];
					String val = validity1.split(":")[1];
					System.err.println("GRATI-NET-ROAMING : - : " + val);
				}

			}
		}
	}

}
