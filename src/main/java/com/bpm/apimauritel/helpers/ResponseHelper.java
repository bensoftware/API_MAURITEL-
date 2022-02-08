package com.bpm.apimauritel.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.bpm.apimauritel.dtos.ResponseService;
import com.bpm.apimauritel.entities.Detail;

public class ResponseHelper {
	
	public static List<ResponseService> getDetailResponse(Set<Detail> details,int language) {
		
		List<ResponseService> listResponseDetails=new ArrayList<>();
		for (Detail detail : details) {
			if(language==2) {
				ResponseService responseService=new ResponseService(detail.getServiceMauritel().getCodeService(),
						detail.getServiceMauritel().getCodeOperation(), 
						detail.getValidityFr(),detail.getDescriptionFr()
						);
				listResponseDetails.add(responseService);
			}else if(language==1) {
				ResponseService responseService=new ResponseService(detail.getServiceMauritel().getCodeService(),
						detail.getServiceMauritel().getCodeOperation(), 
						detail.getValidityEn(),detail.getDescriptionEn()
						);
				listResponseDetails.add(responseService);
			}else if(language==3) {
				ResponseService responseService=new ResponseService(detail.getServiceMauritel().getCodeService(),
						detail.getServiceMauritel().getCodeOperation(), 
						detail.getValidityAr(),detail.getDescriptionAr()
						);
				listResponseDetails.add(responseService);
			}
		}
		return listResponseDetails;
	}
	
}
