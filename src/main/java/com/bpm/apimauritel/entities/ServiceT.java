package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class ServiceT implements Serializable {

	  private static final long serialVersionUID = 1L;
	 
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Id
	  private Long id;
	  @OneToMany(mappedBy = "service",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  List<DetailService>  detailServices;
	  private String codeService;
	  private String codeOperation;
}
