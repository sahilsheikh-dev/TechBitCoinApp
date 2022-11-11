package com.tbc.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name =  "referral_table", uniqueConstraints = @UniqueConstraint(columnNames="id"))
public class ReferralTable {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)//GenerationType.AUTO
	private Long id;
	private String referralId;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserId",referencedColumnName="id")
	private User user;
	public Long getId() {
		return id;
	}
	public ReferralTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReferralTable(String referralId, User user) {
		super();
		this.referralId = referralId;
		this.user = user;
	}
	public String getReferralId() {
		return referralId;
	}
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
