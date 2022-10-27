package com.begawosoft.tbcapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.begawosoft.tbcapi.entity.PurchaseTbl;
import com.begawosoft.tbcapi.service.PurchaseService;

@RestController
public class PurchaseController {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@GetMapping("/packpurchases/{userId}")
	public List<PurchaseTbl> getAllPurchasesByUserPk(@PathVariable("userId") String userId) {
		return purchaseService.getAllPurchasesByUserPk(userId);
	}
	
	@GetMapping("/refers/{userId}")
	public List<PurchaseTbl> getAllReferedPurchases(@PathVariable("userId") String userId) {
		return purchaseService.getAllReferedPurchases(userId);
	}
	
	@PostMapping("/packpurchase")
	public PurchaseTbl purchase(@RequestBody PurchaseTbl purchase) {
		return purchaseService.purchasePlan(purchase);
	}
	
}
