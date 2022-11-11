package com.tbc.model;

import java.util.Date;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames="mail"))
public class User {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)//GenerationType.AUTO
	private Long id;
	private String walletAddress;
	private String walletPrivateKey;
	public String getWalletAddress() {
		return walletAddress;
	}
	public void setWalletAddress(String walletAddress) {
		this.walletAddress = walletAddress;
	}
	public String getWalletPrivateKey() {
		return walletPrivateKey;
	}
	public void setWalletPrivateKey(String walletPrivateKey) {
		this.walletPrivateKey = walletPrivateKey;
	}
	private String name;
	private String password;
	private String mail;
	private String address;
	private String aadharBackName;
	private String aadharFrontName;
	public String getAadharBackName() {
		return aadharBackName;
	}
	public void setAadharBackName(String aadharBackName) {
		this.aadharBackName = aadharBackName;
	}
	public String getAadharFrontName() {
		return aadharFrontName;
	}
	public void setAadharFrontName(String aadharFrontName) {
		this.aadharFrontName = aadharFrontName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getKycPath() {
		return kycPath;
	}
	public void setKycPath(String kycPath) {
		this.kycPath = kycPath;
	}
	private String status;
	@Transient
	private String outputCode;
	private String profileName;//picture name
	private String kycPath; // kycDocument/
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	private String referedByPk;
	private String referCode;
	private String StripeId;
	private String hasPurchased;
	private String has100pack;
	private String has200pack;
	private String has500Pack;
	private String has1000pack;
	private String has1500pack;
	private String has2000pack;
	private String has5000pack;
	private String has10000pack;
	public String getHas100pack() {
		return has100pack;
	}
	public void setHas100pack(String has100pack) {
		this.has100pack = has100pack;
	}
	public String getHas200pack() {
		return has200pack;
	}
	public void setHas200pack(String has200pack) {
		this.has200pack = has200pack;
	}
	public String getHas5000pack() {
		return has5000pack;
	}
	public void setHas5000pack(String has5000pack) {
		this.has5000pack = has5000pack;
	}
	public String getHas10000pack() {
		return has10000pack;
	}
	public void setHas10000pack(String has10000pack) {
		this.has10000pack = has10000pack;
	}
	public String getHasPurchased() {
		return hasPurchased;
	}
	public void setHasPurchased(String hasPurchased) {
		this.hasPurchased = hasPurchased;
	}
	public String getHas500Pack() {
		return has500Pack;
	}
	public void setHas500Pack(String has500Pack) {
		this.has500Pack = has500Pack;
	}
	public String getHas1000pack() {
		return has1000pack;
	}
	public void setHas1000pack(String has1000pack) {
		this.has1000pack = has1000pack;
	}
	public String getHas1500pack() {
		return has1500pack;
	}
	public void setHas1500pack(String has1500pack) {
		this.has1500pack = has1500pack;
	}
	public String getHas2000pack() {
		return has2000pack;
	}
	public void setHas2000pack(String has2000pack) {
		this.has2000pack = has2000pack;
	}

	public String getStripeId() {
		return StripeId;
	}
	public void setStripeId(String stripeId) {
		StripeId = stripeId;
	}
	@Column
	private double tbcBalance = 0.0;
	@Column
	private int isDeleted;
	private Date createdAt;
	@Transient
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	private Date expireAt;
	private String myReferralCode;
	public String getMyReferralCode() {
		return myReferralCode;
	}
	public void setMyReferralCode(String myReferralCode) {
		this.myReferralCode = myReferralCode;
	}
	private boolean enabled;
	@Column(name="verification_code",updatable=false)
	private String verificationCode;
	private String roles;
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public User(String name, 
			String password, 
			String mail, 
			String referedByPk,
			String referCode, 
			double tbcBalance,
			int isDeleted, 
			String outputCode,
			String verificationCode,
			String roles,
			String myReferralCode) {
		super();
		this.name = name;
		this.password = password;
		this.mail = mail;
		this.referedByPk = referedByPk;
		this.referCode = referCode;
		this.tbcBalance = tbcBalance;
		this.isDeleted = isDeleted;
		this.outputCode = outputCode;
		this.createdAt = createdAt;
		this.expireAt = expireAt;
		this.enabled = enabled;
		this.verificationCode = verificationCode;
		this.roles = roles;
		this.myReferralCode=myReferralCode;
	}
	public Date getExpireAt() {
		return expireAt;
	}
	public void setExpireAt(Date expireAt) {
		this.expireAt = expireAt;
	}
	public User() {

	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
}