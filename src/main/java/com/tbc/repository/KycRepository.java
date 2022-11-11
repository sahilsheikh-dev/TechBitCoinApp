package com.tbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tbc.model.KycTable;
@Repository
public interface KycRepository extends JpaRepository<KycTable, Long>{

	KycTable findById(String referedByPk);

	
	
}
