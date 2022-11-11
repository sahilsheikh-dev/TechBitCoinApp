package com.tbc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tbc.model.PurchaseTable;
import com.tbc.model.User;

@Repository
public interface PurchaseTableRepository extends JpaRepository<PurchaseTable, Long>{

	
	List<PurchaseTable> findByUserId(long id);
}
