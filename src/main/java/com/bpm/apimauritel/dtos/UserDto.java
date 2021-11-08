package com.bpm.apimauritel.dtos;

import lombok.Data;

@Data
public class UserDto{
 public String 	username;
 public String	password;
 

	public UserDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	
	
}
