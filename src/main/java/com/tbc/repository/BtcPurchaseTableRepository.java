package com.tbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tbc.model.BtcPurchaseTable;

@Repository
public interface BtcPurchaseTableRepository extends JpaRepository<BtcPurchaseTable,Long>{

}
