package com.bpm.apimauritel.helpers;

import java.util.Hashtable;
import java.util.List;
import com.bpm.apimauritel.dtos.ServiceDto;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.entities.ServiceT;
import com.bpm.apimauritel.services.DetailServiceServiceT;

public class CashHelper {

	public static void SaveDetailService(ServiceT serviceT, List<ServiceDto> listService,
			DetailServiceServiceT detailServiceServiceT) throws Exception {

		DetailService detailService = null;
		for (ServiceDto serviceDto : listService) {
			// System.err.println("");
			if (serviceDto.getService().equalsIgnoreCase(serviceT.getCodeService())) {
				detailService = new DetailService();
				detailService.setAmount(serviceDto.getAmount());
				detailService.setDescription(serviceDto.getDescription());
				detailService.setService(serviceT);
				try {
					detailService = detailServiceServiceT.findDetailServiceByDescription(serviceDto.getDescription());
					if (detailService == null) {
						detailServiceServiceT.save(detailService);
					} else {
						// logger.info('');
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		}
	}

	public static Hashtable<String, ServiceT> listToHashTableServiceT(List<ServiceT> listServiceT) {
		Hashtable<String, ServiceT> serviceTempo = new Hashtable<>();
		for (ServiceT serviceT : listServiceT) {
			serviceTempo.put(serviceT.getCodeService(), serviceT);
		}
		return serviceTempo;
	}

	public static Hashtable<String, ServiceDto> listToHashTableServiceDto(List<ServiceDto> listServiceDto) {
		Hashtable<String, ServiceDto> serviceTempo = new Hashtable<>();
		for (ServiceDto serviceDto : listServiceDto) {
			serviceTempo.put(serviceDto.getService(), serviceDto);
		}
		return serviceTempo;
	}

	public static Hashtable<String, ServiceDto> listToHashTableServiceDtoDesciption(List<ServiceDto> listServiceDto) {
		Hashtable<String, ServiceDto> serviceTempo = new Hashtable<>();
		for (ServiceDto serviceDto : listServiceDto) {
			serviceTempo.put(serviceDto.getDescription(), serviceDto);
		}
		return serviceTempo;
	}

	public static String getValidity(String description) {

		String[] tab = description.split("/+");

		for (String p : tab) {
			System.out.println(p);
		}

		String validite = tab[tab.length - 1];

		validite = validite.replaceAll("Validité:*", "");

		System.out.println("Validity : " + validite);

		// System.out.println(validite);
		return validite;

	}

	public static String getDescription(String description) {

		String descriptions = "";
		String[] tab = description.split("/+");
		int size = tab.length - 1;
		for (int i = 0; i < tab.length; i++) {
			String p = tab[i];
			if (i != 0 && i != size) {
				if (descriptions.equals(""))
					descriptions += p;
				else
					descriptions += " " + p;
			}
		}

		System.out.println(descriptions);
		return descriptions;
	}

}
