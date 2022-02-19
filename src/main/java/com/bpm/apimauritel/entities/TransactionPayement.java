package com.bpm.apimauritel.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
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
	@JsonIgnore
	ServiceMauritel service;

	private String sender;
	@Column(length = 30)
	private String receiver;
	private double amountPay;
	@Column(length = 2)
	private String statusPayement;
	@Column(length = 30)
	private String transactionId;
	@Column(columnDefinition = "TEXT")
	private String errorMessage;
	private Date transactionDate;
	private boolean success;
	private String typeRecharge;
	private Date dateRequest;
	private String transactionStatus;

}
