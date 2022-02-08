package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class ResponseDto {
	
	 private Object response;
	 private String message;
	 private int codeError;
	 
	 public ResponseDto() {
			super();
		}
	 
	 public ResponseDto(Object response) {
			super();
			this.response = response;
		}
	
}
