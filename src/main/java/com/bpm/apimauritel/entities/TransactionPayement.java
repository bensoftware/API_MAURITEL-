package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TransactionPayement implements Serializable {

	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;
	
	private ServiceT serviceT;
	
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
