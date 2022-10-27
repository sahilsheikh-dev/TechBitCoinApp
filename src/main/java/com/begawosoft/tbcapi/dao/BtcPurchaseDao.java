package com.begawosoft.tbcapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.begawosoft.tbcapi.entity.BtcPurchaseTbl;

@Repository
public interface BtcPurchaseDao extends JpaRepository<BtcPurchaseTbl, Long>{
	
	List<BtcPurchaseTbl> findByUserPk(String userPk);
	
}
