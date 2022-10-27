package com.begawosoft.tbcapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class PurchaseTbl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String purchasePk;
	
	@Column(nullable = false, length = 30)
	private String userPk;
	
	@Column(nullable = false, length = 30)
	private String referedId;
	
	@Column(nullable = false)
	private double purchasePrice;
	
	@Column(nullable = false)
	private double amountDeposited;
	
	@Column(nullable = false, length = 20)
	private String status;
	
	@Column(nullable = false)
	private String date;
	
	@Transient
	private String outputCode;

	public PurchaseTbl() {
		super();
	}

	public PurchaseTbl(Long id, String purchasePk, String userPk, String referedId, double purchasePrice,
			double amountDeposited, String status, String date, String outputCode) {
		super();
		this.id = id;
		this.purchasePk = purchasePk;
		this.userPk = userPk;
		this.referedId = referedId;
		this.purchasePrice = purchasePrice;
		this.amountDeposited = amountDeposited;
		this.status = status;
		this.date = date;
		this.outputCode = outputCode;
	}

	public PurchaseTbl(String purchasePk, String userPk, String referedId, double purchasePrice, double amountDeposited,
			String status, String date, String outputCode) {
		super();
		this.purchasePk = purchasePk;
		this.userPk = userPk;
		this.referedId = referedId;
		this.purchasePrice = purchasePrice;
		this.amountDeposited = amountDeposited;
		this.status = status;
		this.date = date;
		this.outputCode = outputCode;
	}

	public PurchaseTbl(Long id, String purchasePk, String userPk, String referedId, double purchasePrice,
			double amountDeposited, String status, String date) {
		super();
		this.id = id;
		this.purchasePk = purchasePk;
		this.userPk = userPk;
		this.referedId = referedId;
		this.purchasePrice = purchasePrice;
		this.amountDeposited = amountDeposited;
		this.status = status;
		this.date = date;
	}

	public PurchaseTbl(String purchasePk, String userPk, String referedId, double purchasePrice, double amountDeposited,
			String status, String date) {
		super();
		this.purchasePk = purchasePk;
		this.userPk = userPk;
		this.referedId = referedId;
		this.purchasePrice = purchasePrice;
		this.amountDeposited = amountDeposited;
		this.status = status;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPurchasePk() {
		return purchasePk;
	}

	public void setPurchasePk(String purchasePk) {
		this.purchasePk = purchasePk;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getReferedId() {
		return referedId;
	}

	public void setReferedId(String referedId) {
		this.referedId = referedId;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public double getAmountDeposited() {
		return amountDeposited;
	}

	public void setAmountDeposited(double amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}
	
}
