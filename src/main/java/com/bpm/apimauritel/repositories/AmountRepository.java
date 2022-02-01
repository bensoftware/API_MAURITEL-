package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.Amount;

@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {

	public  Amount findByAmount(double amount);
}
