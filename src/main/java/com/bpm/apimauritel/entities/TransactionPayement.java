package com.bpm.apimauritel.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
//@Entity
public class TransactionPayement {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	private ServiceT serviceT;
	
    private double amountPay;
    private String billNumber;
    private String billerId;
    private String comptabiliser;
    private String statusPayement;
    private String transactionId;
    private String userId;
    private String errorMessage;
    private String facturierId;
    private double solde;
    
}
