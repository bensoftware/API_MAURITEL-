package com.bpm.apimauritel.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.bpm.apimauritel.dtos.ResponseService;
import com.bpm.apimauritel.entities.Detail;

public class ResponseHelper {
	
	public static List<ResponseService> getDetailResponse(Set<Detail> details) {
		
		List<ResponseService> listResponseDetails=new ArrayList<>();
		for (Detail detail : details) {
			ResponseService responseService=new ResponseService(detail.getServiceMauritel().getCodeService(),
					detail.getServiceMauritel().getCodeOperation(), 
					detail.getValidityFr(),detail.getDescriptionFr()
					);
			listResponseDetails.add(responseService);
		}
		return listResponseDetails;
	}
	
}
