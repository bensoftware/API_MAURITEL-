package com.bpm.apimauritel.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
//@ToString
public class Detail implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
   
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "service_Mauritel")
	private ServiceMauritel serviceMauritel;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "amount")
//	@Transient
	private Amount amount;

	private String validityFr;
	private String validityEn;
	private String validityAr;
	private String descriptionFr;
	private String descriptionEn;
	private String descriptionAr;
	private String description;

}
