package com.bank.dao;

import java.io.Serializable;
import java.util.List;

import com.bank.model.Statement;

public interface BankStatementsDAO extends Serializable {

	List<Statement> getListOfStatements(String accountNumber);

	boolean isAccountExist(String accNum);
}
