package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class RechargeClassiqueDto {
	private String sender;
	private String receiver;
	private String amount;
}
