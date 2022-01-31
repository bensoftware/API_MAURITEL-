package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bpm.apimauritel.entities.ServiceMauritel;

@Repository
public interface ServiceMauritelRepository extends JpaRepository<ServiceMauritel,Long> {

}
