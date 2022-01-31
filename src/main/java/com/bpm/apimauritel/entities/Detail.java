package com.bpm.apimauritel.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Entity
@Data
public class Detail implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	private String validityFr;
	private String validityEn;
	private String validityAr;
	
	private String descriptionFr;
	private String descriptionEn;
	private String descriptionAr;
	
	@Cascade(CascadeType.SAVE_UPDATE)
	@JsonIgnore
	private ServiceMauritel serviceMauritel;
	
	
	@Cascade(CascadeType.SAVE_UPDATE)
	@JsonIgnore
	private Amount amount;
	
}
