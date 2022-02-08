package com.bpm.apimauritel.dtos;

import java.util.List;
import com.bpm.apimauritel.entities.Amount;
import lombok.Data;

@Data
public class ListAmounts {

	private	List<Double> listAmounts;

	public ListAmounts(List<Double> listAmounts) {
		super();
		this.listAmounts = listAmounts;
	}

}
