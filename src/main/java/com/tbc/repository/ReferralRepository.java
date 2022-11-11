package com.tbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tbc.model.ReferralTable;

@Repository
public interface ReferralRepository extends JpaRepository<ReferralTable, Long>{

	ReferralTable findByReferralId(String referedByPk);

	
	
}
