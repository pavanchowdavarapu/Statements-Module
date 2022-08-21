package com.bank.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Statement {

	private String accountNumber;
	@JsonIgnore
	private String amount;
	@JsonIgnore
	private String date;
	private Date dateField;
	@JsonIgnore
	private Date fromDate;
	@JsonIgnore
	private Date toDate;
	private BigDecimal txAmount;
	@JsonIgnore
	private long id;
	@JsonIgnore
	private long account_id;

	public String getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getTxAmount() {
		return txAmount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public void setTxAmount(BigDecimal txAmount) {
		this.txAmount = txAmount;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
