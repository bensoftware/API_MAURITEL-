package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class ResponseDto {
	
	 private Object response;
	 
	 public ResponseDto() {
			super();
		}
	 
	 public ResponseDto(Object response) {
			super();
			this.response = response;
		}
	
}
