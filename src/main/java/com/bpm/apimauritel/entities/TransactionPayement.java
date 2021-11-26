package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
public class TransactionPayement implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	@ToString.Exclude
	@ManyToOne
	@NotNull
	@Cascade(CascadeType.SAVE_UPDATE)
	@JsonIgnore
	private ServiceT service;
	
	private String sender;
    private String receiver;
    private double amountPay;
    private String statusPayement;
    private String transactionId;
    private String errorMessage;
    private Date   transactionDate;
    private String idTransaction;
    
    ///--OPTIONAL FOR NOW
    private String billNumber;
    private String billerId;
    private String comptabiliser;
    private String facturierId;
    private double solde;
    
    
    
}
