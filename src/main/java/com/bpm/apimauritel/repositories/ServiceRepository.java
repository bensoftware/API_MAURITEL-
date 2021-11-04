package com.bpm.apimauritel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bpm.apimauritel.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long>{	
}
