package com.tbc.model;

import java.util.Date;

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
@Table(name =  "referralTransaction", uniqueConstraints = @UniqueConstraint(columnNames="id"))
public class ReferralTransactions {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)//GenerationType.AUTO	
	private long id;
	private Double planOfPurchase;
	private double btcEarned;
	public double getBtcEarned() {
		return btcEarned;
	}
	public void setBtcEarned(double btcEarned) {
		this.btcEarned = btcEarned;
	}
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GotFrom",referencedColumnName="id")
	private User gotFrom;
	public User getGotFrom() {
		return gotFrom;
	}
	public void setGotFrom(User gotFrom) {
		this.gotFrom = gotFrom;
	}
	private Date time;
	private int level;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserId",referencedColumnName="id")
	private User user;
	public ReferralTransactions(Double planOfPurchase, Date time, int level, User user) {
		super();
		this.planOfPurchase = planOfPurchase;
		this.time = time;
		this.level = level;
		this.user = user;
	}
	public ReferralTransactions() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Double getPlanOfPurchase() {
		return planOfPurchase;
	}
	public void setPlanOfPurchase(Double purchasePrice) {
		this.planOfPurchase = purchasePrice;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
