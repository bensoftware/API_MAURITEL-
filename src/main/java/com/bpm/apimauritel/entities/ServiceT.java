package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString //Was Added Recently
@Entity
public class ServiceT implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	@OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	// @JsonIgnore
	List<DetailService> detailServices;

	

	private String codeService;
	private String codeOperation;
	private boolean notActivated;

}
