package com.bpm.apimauritel.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.Amount;
import com.bpm.apimauritel.repositories.AmountRepository;
import com.bpm.apimauritel.services.AmountService;

@Service
public class AmountServiceImpl implements AmountService {

	final Logger logger = LoggerFactory.getLogger(AmountServiceImpl.class);

	@Autowired
	AmountRepository amountRepository;
	
	@Override
	public Amount save(Amount amount) throws Exception {
		try {
			amount=	amountRepository.save(amount);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION SAVE AMOUNT" + e.getMessage());
		}
		return amount;
	}

	@Override
	public void update(Amount amount) throws Exception {
		// TODO Auto-generated method stub
		try {
			amount=amountRepository.save(amount);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION UPDATE AMOUNT" + e.getMessage());
		}
	}

	@Override
	public List<Amount> findAllAmounts() throws Exception {
		// TODO Auto-generated method stub
		List<Amount> listOfAmounts=new ArrayList<>();
		try {
		    listOfAmounts=amountRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION FIND ALL : " +e.getMessage());
		}
		return listOfAmounts;
	}

	@Override
	public Amount findByAmount(Double amount) throws Exception {
		// TODO Auto-generated method stub
		Amount amount2=new Amount();
		try {
			amount2 =amountRepository.findByAmount(amount);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION FIND BY AMOUNT : " +e.getMessage());
		}
		return amount2;
	}

	@Override
	public List<Double> findAllActifAmounts() throws Exception {
		// TODO Auto-generated method stub
		List<Double> listAmounts=new ArrayList<>();
		List<Amount> listAmount=amountRepository.findAllActifAmounts();
		for (Amount amount : listAmount) {
			listAmounts.add(amount.getAmount());
		}
		return listAmounts;
	}

}
