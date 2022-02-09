package com.bpm.apimauritel.helpers;

import java.util.HashSet;
import java.util.Set;
import com.bpm.apimauritel.entities.Detail;

public class ServiceHelper {

	public static Set<Detail> bindDetail(Set<Detail> listServi,int language) throws Exception {

		Set<Detail> listServicesEn = new HashSet<>();

		for (Detail detail : listServi) {
			Detail detailT = new Detail();
			if(language==1) {
				//English
				detailT.setId(detail.getId());
				detailT.setAmount(detail.getAmount());
				detailT.setValidityEn(detail.getValidityEn());
				detailT.setDescriptionEn(detail.getDescriptionEn());
				detailT.setServiceMauritel(detail.getServiceMauritel());
				listServicesEn.add(detail);
			}else if(language==2) {
				//French
				detailT.setId(detail.getId());
				detailT.setAmount(detail.getAmount());
				detailT.setValidityFr(detail.getValidityFr());
				detailT.setDescriptionFr(detail.getDescriptionFr());
				detailT.setServiceMauritel(detail.getServiceMauritel());
				listServicesEn.add(detail);
			}else if(language==3) {
				//Arabic
				detailT.setId(detail.getId());
				detailT.setAmount(detail.getAmount());
				detailT.setValidityAr(detail.getValidityAr());
				detailT.setDescriptionAr(detail.getDescriptionAr());
				detailT.setServiceMauritel(detail.getServiceMauritel());
				listServicesEn.add(detail);
			}
		}
		return listServicesEn;
	}
}
