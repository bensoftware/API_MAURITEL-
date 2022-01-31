package com.bpm.apimauritel.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ServiceMauritel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	private String codeService;//Libelle
	private String codeOperation;//Bundle
	private boolean actif;
}
