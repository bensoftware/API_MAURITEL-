package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class ServiceDto {
	private String CodeOperation;
	private String Service;
	private double Amount;
	private String Description;
}
