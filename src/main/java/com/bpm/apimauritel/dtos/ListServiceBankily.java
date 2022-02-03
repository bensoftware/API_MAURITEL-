package com.bpm.apimauritel.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ListServiceBankily {
	
	List<ResponseDetail> ListServiceBankily;

	public ListServiceBankily(List<ResponseDetail> listServiceBankily) {
		super();
		ListServiceBankily = listServiceBankily;
	}
	
	
}
