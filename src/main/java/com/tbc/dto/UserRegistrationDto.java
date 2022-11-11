package com.tbc.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

public class UserRegistrationDto {
	private String name;
	private String password;
	private String mail;
	@Column(nullable = false, length = 30)
	private String referedByPk;
	@Column(nullable = false, length = 30)
	private String referCode;
	@Column
	private double tbcBalance = 0.0;
	@Column
	private int isDeleted;
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	private int amount;
	@Transient
	private String outputCode;
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	private int price;
	private Date createdAt;
	private Date expireAt;
	private boolean enabled;
	@Column(name="verification_code",updatable=false)
	private String verificationCode;
	private String roles;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getReferedByPk() {
		return referedByPk;
	}
	public void setReferedByPk(String referedByPk) {
		this.referedByPk = referedByPk;
	}
	public String getReferCode() {
		return referCode;
	}
	public void setReferCode(String referCode) {
		this.referCode = referCode;
	}
	public double getTbcBalance() {
		return tbcBalance;
	}
	public void setTbcBalance(double tbcBalance) {
		this.tbcBalance = tbcBalance;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getOutputCode() {
		return outputCode;
	}
	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getExpireAt() {
		return expireAt;
	}
	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
}