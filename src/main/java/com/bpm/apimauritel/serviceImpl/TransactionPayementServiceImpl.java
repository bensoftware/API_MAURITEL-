package com.bpm.apimauritel.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.TransactionPayement;
import com.bpm.apimauritel.repositories.TransactionPayementRepository;
import com.bpm.apimauritel.services.TransactionPayementService;

@Service
public class TransactionPayementServiceImpl implements TransactionPayementService {

	final Logger logger = LoggerFactory.getLogger(TransactionPayementServiceImpl.class);
	
	@Autowired
	TransactionPayementRepository transactionPayementRepository;

	@Override
	public TransactionPayement save(TransactionPayement transactionPayement) throws Exception {
		try {
		return 	transactionPayementRepository.save(transactionPayement);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("EXCEPTION : " + e.getMessage());
		}
	}

	
	@Override
	public void upadte(TransactionPayement transactionPayement) throws Exception {
		try {
			transactionPayementRepository.save(transactionPayement);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new Exception("EXCEPTION : " + e.getMessage());
		}
	}

}
