package com.bpm.apimauritel.dtos;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResponseService implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bundleId;
	private String libelle;
	private String validite;
	private String description;
	
	public ResponseService(String bundleId, String libelle, String validite, String description) {
		super();
		this.bundleId = bundleId;
		this.libelle = libelle;
		this.validite = validite;
		this.description = description;
	}
	
	
}
