package com.bank.service;

import java.io.Serializable;
import java.util.List;

import com.bank.model.Statement;
import com.bank.model.StatementRequest;

public interface BankStatementsService extends Serializable {

	List<Statement> getListOfStatementsByUsersAndFilters(String loginUser,StatementRequest statement);
	
	boolean isAccountExist(String accNum);
}
