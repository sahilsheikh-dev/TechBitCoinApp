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
public class UserTbl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 30)
	private String userPk;
	
	@Column(nullable = false, length = 500)
	private String name;
	
	@Column(nullable = false, length = 500)
	private String email;
	
	@Column(nullable = false, length = 500)
	private String password;
	
	@Column(nullable = false, length = 30)
	private String referedByPk;
	
	@Column(nullable = false, length = 30)
	private String referCode;
	
	@Column
	private double tbcBalance = 0.0;
	
	@Column
	private int isAdmin = 0;
	
	@Column
	private int isDeleted;
	
	@Transient
	private String outputCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserPk() {
		return userPk;
	}

	public void setUserPk(String userPk) {
		this.userPk = userPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
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

	public UserTbl () {}

	public UserTbl(Long id, String userPk, String name, String email, String password, String referedByPk,
			String referCode, long tbcBalance, int isAdmin, int isDeleted) {
		super();
		this.id = id;
		this.userPk = userPk;
		this.name = name;
		this.email = email;
		this.password = password;
		this.referedByPk = referedByPk;
		this.referCode = referCode;
		this.tbcBalance = tbcBalance;
		this.isAdmin = isAdmin;
		this.isDeleted = isDeleted;
	}

	public UserTbl(String userPk, String name, String email, String password, String referedByPk, String referCode,
			long tbcBalance, int isAdmin, int isDeleted) {
		super();
		this.userPk = userPk;
		this.name = name;
		this.email = email;
		this.password = password;
		this.referedByPk = referedByPk;
		this.referCode = referCode;
		this.tbcBalance = tbcBalance;
		this.isAdmin = isAdmin;
		this.isDeleted = isDeleted;
	}

	public UserTbl(String userPk, String name, String email, String password, String referedByPk, String referCode,
			long tbcBalance, int isAdmin, int isDeleted, String outputCode) {
		super();
		this.userPk = userPk;
		this.name = name;
		this.email = email;
		this.password = password;
		this.referedByPk = referedByPk;
		this.referCode = referCode;
		this.tbcBalance = tbcBalance;
		this.isAdmin = isAdmin;
		this.isDeleted = isDeleted;
		this.outputCode = outputCode;
	}

	public UserTbl(Long id, String userPk, String name, String email, String password, String referedByPk,
			String referCode, long tbcBalance, int isAdmin, int isDeleted, String outputCode) {
		super();
		this.id = id;
		this.userPk = userPk;
		this.name = name;
		this.email = email;
		this.password = password;
		this.referedByPk = referedByPk;
		this.referCode = referCode;
		this.tbcBalance = tbcBalance;
		this.isAdmin = isAdmin;
		this.isDeleted = isDeleted;
		this.outputCode = outputCode;
	}

}
