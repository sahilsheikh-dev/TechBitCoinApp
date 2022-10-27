package com.begawosoft.tbcapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.begawosoft.tbcapi.entity.BtcPurchaseTbl;
import com.begawosoft.tbcapi.service.BtcPurchaseService;

@Controller
public class BtcPurchaseController {
	
	@Autowired
	private BtcPurchaseService btcPurchaseService;
	
	@GetMapping("/btcpurchases/{userId}")
	public List<BtcPurchaseTbl> getAllReferedPurchases(@PathVariable("userId") String userId) {
		return btcPurchaseService.getAllTransactionByUser(userId);
	}
	
	@PostMapping("/btcpurchase")
	public BtcPurchaseTbl btcPurchase(@RequestBody BtcPurchaseTbl purchase) {
		return btcPurchaseService.purchaseBtc(purchase);
	}
	
}
