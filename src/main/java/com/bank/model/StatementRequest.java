package com.bank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatementRequest implements Serializable {

	private static final long serialVersionUID = 7005952711155294309L;
	private Date fromDate;
	private Date toDate;
	private String accNumber;
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private boolean amountEnabled;
	private boolean dateEnabled;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public boolean isAmountEnabled() {
		return amountEnabled;
	}

	public void setAmountEnabled(boolean amountEnabled) {
		this.amountEnabled = amountEnabled;
	}

	public boolean isDateEnabled() {
		return dateEnabled;
	}

	public void setDateEnabled(boolean dateEnabled) {
		this.dateEnabled = dateEnabled;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

}
