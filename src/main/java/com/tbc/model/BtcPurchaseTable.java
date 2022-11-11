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
@Table(name =  "btc_purchase_tbl", uniqueConstraints = @UniqueConstraint(columnNames="id"))
public class BtcPurchaseTable {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)//GenerationType.AUTO
	private long id;
	private Date date;
	private double price;
	private int quantity;
	private String status;
	private long transactionId;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UserId",referencedColumnName="id")
	private User user;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
