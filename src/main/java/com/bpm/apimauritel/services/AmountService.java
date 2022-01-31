package com.bpm.apimauritel.services;

import java.util.List;
import com.bpm.apimauritel.entities.Amount;

public interface AmountService {
	
	public Amount save(Amount amount) throws Exception;

	public void update(Amount amount) throws Exception;
	
	public List<Amount> findAllAmounts() throws Exception;
}
