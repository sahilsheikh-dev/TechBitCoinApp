package com.tbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tbc.model.ReferralTransactions;

@Repository
public interface ReferTranRepo extends JpaRepository<ReferralTransactions, Long>{

	

	List<ReferralTransactions> findByUserId(long id);

	
	
}