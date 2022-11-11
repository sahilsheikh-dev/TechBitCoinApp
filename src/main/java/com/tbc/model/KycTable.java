package com.tbc.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name =  "kycTable", uniqueConstraints = @UniqueConstraint(columnNames="id"))
public class KycTable {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)//GenerationType.AUTO
	private long id;
	private String aadharBackName;
	private String aadharFrontName;
	private String address;
	private String status;
	public String getOutputCode() {
		return outputCode;
	}

	public void setOutputCode(String outputCode) {
		this.outputCode = outputCode;
	}

	@Transient
	private String outputCode;
	private String profileName;//picture name
	private String kycPath; // kycDocument/
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserId",referencedColumnName="id")
	private User user;
	public String getKycPath() {
		return kycPath;
	}

	public void setKycPath(String kycPath) {
		this.kycPath = kycPath;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
