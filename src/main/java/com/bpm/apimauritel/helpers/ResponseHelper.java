package com.bpm.apimauritel.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bpm.apimauritel.dtos.ResponseDetail;
import com.bpm.apimauritel.entities.Detail;

public class ResponseHelper {
	
	public static List<ResponseDetail> getDetailResponse(Set<Detail> details) {
		
		List<ResponseDetail> listResponseDetails=new ArrayList<>();
		for (Detail detail : details) {
			ResponseDetail responseDetail=new ResponseDetail(detail.getServiceMauritel().getCodeService(),
					detail.getServiceMauritel().getCodeOperation(), 
					detail.getValidityFr(),detail.getDescriptionFr()
					);
			listResponseDetails.add(responseDetail);
		}
		return listResponseDetails;
	}
	
	
}
