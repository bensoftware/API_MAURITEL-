package com.bpm.apimauritel.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ListAmounts {

	private List<Double> Amounts;

	public ListAmounts(List<Double> listAmounts) {
		super();
		this.Amounts = listAmounts;
	}

}
