package com.bpm.apimauritel.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.TransactionPayement;
import com.bpm.apimauritel.repositories.TransactionPayementRepository;
import com.bpm.apimauritel.services.TransactionPayementService;


@Service
public class TransactionPayementServiceImpl implements TransactionPayementService {

	@Autowired
	TransactionPayementRepository transactionPayementRepository;

	@Override
	public void save( TransactionPayement transactionPayement) throws Exception {
		try {
			transactionPayementRepository.save(transactionPayement);
		} catch (Exception e) {
			throw new Exception("" + e.getMessage());
		}
	}

	@Override
	public void upadte( TransactionPayement transactionPayement) throws Exception {
		try {
			transactionPayementRepository.save(transactionPayement);
		} catch (Exception e) {
			throw new Exception("" +e.getMessage());
		}
	}
	

}
