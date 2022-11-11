package com.tbc.model;

public class TbcTransferToken {
	private String address;
	private String amount;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public TbcTransferToken(String address, String amount) {
		super();
		this.address = address;
		this.amount = amount;
	}
	public TbcTransferToken() {
		super();
		// TODO Auto-generated constructor stub
	}
}
