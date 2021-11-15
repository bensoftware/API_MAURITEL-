package com.bpm.apimauritel.services;

import com.bpm.apimauritel.entities.TransactionPayement;

public interface TransactionPayementService {
	
	public void save(TransactionPayement transactionPayement) throws Exception;
	
	public void upadte(TransactionPayement transactionPayement) throws Exception;

}
