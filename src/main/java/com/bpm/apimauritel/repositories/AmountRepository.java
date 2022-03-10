package com.bpm.apimauritel.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.Amount;

@Repository
public interface AmountRepository extends JpaRepository<Amount, Long> {

	@Query("select u from Amount u where u.actif=true ")
	public List<Amount> findAllActifAmounts();
	

	public  Amount findByAmount(double amount);

}
