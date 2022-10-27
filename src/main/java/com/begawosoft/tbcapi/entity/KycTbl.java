package com.begawosoft.tbcapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table
@Entity
public class KycTbl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30)
	private String kycPk;

	@Column(nullable = false, length = 30)
	private String userPk;

	@Column(nullable = false, length = 1000)
	private String aadharFrontName;

	@Column(nullable = false, length = 1000)
	private String aadharBackName;

	@Column(nullable = false, length = 1000)
	private String kycPath;

	@Column(nullable = false, length = 1000)
	private String address;

	@Column(nullable = false, length = 20)
	private String status;

	@Transient
	private String outputCode;

	public KycTbl() {
		super();
	}

	public KycTbl(Long id, String kycPk, String userPk, String aadharFrontName, String aadharBackName, String kycPath,
			String address, String status, String outputCode) {
		super();
		this.id = id;
		this.kycPk = kycPk;
		this.userPk = userPk;
		this.aadharFrontName = aadharFrontName;
		this.aadharBackName = aadharBackName;
		this.kycPath = kycPath;
		this.address = address;
		this.status = status;
		this.outputCode = outputCode;
	}

	public KycTbl(String kycPk, String userPk, String aadharFrontName, String aadharBackName, String kycPath,
			String address, String status, String outputCode) {
		super();
		this.kycPk = kycPk;
		this.userPk = userPk;
		this.aadharBackName = aadharBackName;
		this.kycPath = kycPath;
		this.address = address;
		this.status = status;
		this.outputCode = outputCode;
	}

	public KycTbl(Long id, String kycPk, String userPk, String aadharFrontName, String aadharBackName, String kycPath,
			String address, String status) {
		super();
		this.id = id;
		this.kycPk = kycPk;
		this.userPk = userPk;
		this.aadharFrontName = aadharFrontName;
		this.aadharBackName = aadharBackName;
		this.kycPath = kycPath;
		this.address = address;
		this.status = status;
	}

	public KycTbl(String kycPk, String userPk, String aadharFrontName, String aadharBackName, String kycPath,
			String address, String status) {
		super();
		this.kycPk = kycPk;
		this.userPk = userPk;
		this.aadharFrontName = aadharFrontName;
		this.aadharBackName = aadharBackName;
		this.kycPath = kycPath;
		this.address = address;
		this.status = status;
	}

	public KycTbl(String kycPk, String userPk, String kycPath, String address) {
		super();
		this.kycPk = kycPk;
		this.userPk = userPk;
		this.kycPath = kycPath;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKycPk() {
		return kycPk;
	}

	public void setKycPk(String kycPk) {
		this.kycPk = kycPk;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getAadharFrontName() {
		return aadharFrontName;
	}

	public void setAadharFrontName(String aadharFrontName) {
		this.aadharFrontName = aadharFrontName;
	}

	public String getAadharBackName() {
		return aadharBackName;
	}

	public void setAadharBackName(String aadharBackName) {
		this.aadharBackName = aadharBackName;
	}

	public String getKycPath() {
		return kycPath;
	}

	public void setKycPath(String kycPath) {
		this.kycPath = kycPath;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}

}
