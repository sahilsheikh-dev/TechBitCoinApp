package com.begawosoft.tbcapi.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.begawosoft.tbcapi.entity.PurchaseTbl;

public interface PurchaseDao extends JpaRepository<PurchaseTbl, Long> {
	
	List<PurchaseTbl> findByUserPk(String userPk);
	List<PurchaseTbl> findByReferedId(String referedId);
}
