package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private Long id;
	  @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
	  List<DetailService>  detailServices;
	  private String codeService;
	  private String codeOperation;
}
