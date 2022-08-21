package com.bank.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bank.abstactcontroller.AbstractController;
import com.bank.model.Statement;
import com.bank.model.StatementRequest;
import com.bank.service.BankStatementsService;

@RestController
@CrossOrigin
public class BankStatementController extends AbstractController {
	Logger logger = LogManager.getLogger(BankStatementController.class);

	@Autowired
	private BankStatementsService bankStatementsService;

	@RequestMapping(value = "/Statement", method = RequestMethod.POST)
	public ResponseEntity<?> getStatements(@RequestBody StatementRequest statementRequest) throws Exception {
		logger.info("Entering");
		if (!bankStatementsService.isAccountExist(statementRequest.getAccNumber())) {
			return ResponseEntity.ok("Account does not exist");
		} else if (statementRequest.getFromAmount() != null || statementRequest.getToAmount() != null) {
			statementRequest.setAmountEnabled(true);
			if (!(statementRequest.getFromAmount() != null && statementRequest.getToAmount() != null)) {
				return ResponseEntity.ok("From Amount and To Amount must provide at a time");
			} else if (statementRequest.getFromDate() != null || statementRequest.getToDate() != null) {
				statementRequest.setDateEnabled(true);
				if (!(statementRequest.getFromDate() != null && statementRequest.getToDate() != null)) {
					return ResponseEntity.ok("From Date and To Date must provide at a time");
				}
			}
		} else if (statementRequest.getFromDate() != null || statementRequest.getToDate() != null) {
			statementRequest.setDateEnabled(true);
			if (!(statementRequest.getFromDate() != null && statementRequest.getToDate() != null)) {
				return ResponseEntity.ok("Two Dates must provide at a time");
			}
		}
		logger.info("leaving");
		return ResponseEntity.ok(getStatement(statementRequest));
	}

	public List<Statement> getStatement(StatementRequest statementRequest) {
		return bankStatementsService.getListOfStatementsByUsersAndFilters(getUserWorkSpace(), statementRequest);

	}
}
