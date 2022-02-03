package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.Detail;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long> {

	@Query("select u from Detail u where u.amount = ?1")
	public Detail findDetailByAmount(Long id);

}
