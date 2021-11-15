package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bpm.apimauritel.entities.TransactionPayement;

@Repository
public interface TransactionPayementRepository extends JpaRepository<TransactionPayement, Long> {

}
