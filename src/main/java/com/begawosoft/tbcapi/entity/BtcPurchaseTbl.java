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
public class BtcPurchaseTbl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String btnPurchasePk;
	
	@Column(nullable = false, length = 30)
	private String userPk;
	
	@Column(nullable = false, length = 30)
	private String tranasactionsId;
	
	@Column(nullable = false)
	private double quantity;
	
	@Column(nullable = false)
	private double price;
	
	@Column(nullable = false, length = 20)
	private String status;
	
	@Column(nullable = false)
	private String date;
	
	@Transient
	private String outputCode;

	public BtcPurchaseTbl() {
		super();
	}

	public BtcPurchaseTbl(Long id, String btnPurchasePk, String userPk, String tranasactionsId, double quantity,
			double price, String status, String date, String outputCode) {
		super();
		this.id = id;
		this.btnPurchasePk = btnPurchasePk;
		this.userPk = userPk;
		this.tranasactionsId = tranasactionsId;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.date = date;
		this.outputCode = outputCode;
	}

	public BtcPurchaseTbl(Long id, String btnPurchasePk, String userPk, String tranasactionsId, double quantity,
			double price, String status, String date) {
		super();
		this.id = id;
		this.btnPurchasePk = btnPurchasePk;
		this.userPk = userPk;
		this.tranasactionsId = tranasactionsId;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.date = date;
	}

	public BtcPurchaseTbl(String btnPurchasePk, String userPk, String tranasactionsId, double quantity, double price,
			String status, String date, String outputCode) {
		super();
		this.btnPurchasePk = btnPurchasePk;
		this.userPk = userPk;
		this.tranasactionsId = tranasactionsId;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.date = date;
		this.outputCode = outputCode;
	}

	public BtcPurchaseTbl(String btnPurchasePk, String userPk, String tranasactionsId, double quantity, double price,
			String status, String date) {
		super();
		this.btnPurchasePk = btnPurchasePk;
		this.userPk = userPk;
		this.tranasactionsId = tranasactionsId;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBtnPurchasePk() {
		return btnPurchasePk;
	}

	public void setBtnPurchasePk(String btnPurchasePk) {
		this.btnPurchasePk = btnPurchasePk;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getTranasactionsId() {
		return tranasactionsId;
	}

	public void setTranasactionsId(String tranasactionsId) {
		this.tranasactionsId = tranasactionsId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
