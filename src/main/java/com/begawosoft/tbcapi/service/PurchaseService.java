package com.begawosoft.tbcapi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.begawosoft.tbcapi.dao.PurchaseDao;
import com.begawosoft.tbcapi.entity.PurchaseTbl;
import com.begawosoft.tbcapi.entity.UserTbl;

@Service
public class PurchaseService {
	
	@Autowired
	private PurchaseDao purchaseDao;
	
	public List<PurchaseTbl> getAllPurchases() {
		List<PurchaseTbl> purchases = purchaseDao.findAll();
		return purchases;
	}
	
	public List<PurchaseTbl> getAllPurchasesByUserPk(String userPk) {
		List<PurchaseTbl> purchases = purchaseDao.findByUserPk(userPk);
		return purchases;
	}
	
	public List<PurchaseTbl> getAllReferedPurchases(String userPk) {
		List<PurchaseTbl> purchases = new ArrayList<>();
		List<PurchaseTbl> allPurchases = getAllPurchases();
		
		UserService userService = new UserService();
		UserTbl user = userService.findByUserPk(userPk);
		
		for(PurchaseTbl purchase: allPurchases)
			if (purchase.getReferedId().equals(user.getReferCode())) purchases.add(purchase);
		
		return purchases;
	}

	public PurchaseTbl purchasePlan(PurchaseTbl purchaseTbl) {
		PurchaseTbl purchase = purchaseDao.save(purchaseTbl);
		return purchasePaymentGateway(purchase);
	}
	
	private PurchaseTbl purchasePaymentGateway(PurchaseTbl purchaseTbl) {
		PurchaseTbl purchase = purchaseTbl;
		purchase.setOutputCode("Success");
		return purchase;
	}
	
}
