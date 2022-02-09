package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class ServiceMauritel implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	private String codeService; // Libelle
	private String codeOperation;// BundleId
	private boolean actif;

	@OneToMany(mappedBy = "serviceMauritel", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Transient
	@JsonProperty
	Set<Detail> listDetail;

	@OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	Set<TransactionPayement> transactionPayement;

	// @OneToMany(mappedBy = "serviceMauritel", fetch = FetchType.EAGER, cascade =
	// CascadeType.ALL, orphanRemoval = true)
	// Set<Detail> listDetail;

}
