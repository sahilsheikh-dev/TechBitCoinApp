package com.begawosoft.tbcapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.begawosoft.tbcapi.dao.BtcPurchaseDao;
import com.begawosoft.tbcapi.entity.BtcPurchaseTbl;

@Service
public class BtcPurchaseService {
	
	@Autowired
	private BtcPurchaseDao btcPurchaseDao;
	
	public List<BtcPurchaseTbl> getAllTransactionByUser(String userPk){
		return btcPurchaseDao.findByUserPk(userPk);
	}
	
	public BtcPurchaseTbl purchaseBtc(BtcPurchaseTbl btcPurchaseTbl) {
		return purchaseBtcGateway(btcPurchaseTbl);
	}
	
	private BtcPurchaseTbl purchaseBtcGateway(BtcPurchaseTbl btcPurchaseTbl) {
		BtcPurchaseTbl purchase = new BtcPurchaseTbl();
		
		purchase.setOutputCode("Success");
		
		return purchase;
	}
	
}
