package com.bank.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.abstactcontroller.AbstractController;
import com.bank.dao.BankStatementsDAO;
import com.bank.model.Statement;
import com.bank.model.StatementRequest;

@Service
public class BankStatementsServiceImpl extends AbstractController implements BankStatementsService {

	private static final long serialVersionUID = 6226777990873844718L;

	Logger logger = LogManager.getLogger(BankStatementsServiceImpl.class);
	@Autowired
	private BankStatementsDAO bankStatementsDAO;

	@Override
	public List<Statement> getListOfStatementsByUsersAndFilters(String loginUser, StatementRequest statement) {
		logger.info("Entering");
		List<Statement> data = convertStringToDate(statement.getAccNumber());
		Date maxDate = data.stream().map(u -> u.getDateField()).max(Date::compareTo).get();
		if (loginUser.equalsIgnoreCase("Admin")) {
			logger.info("Leaving");
			return sortDataInAscOrder(data, statement, maxDate);
		} else if (loginUser.equalsIgnoreCase("user")) {
			logger.info("Leaving");
			return sortDataInDescOrder(data, maxDate);
		}
		logger.info("Leaving");
		return null;

	}

	public List<Statement> convertStringToDate(String accountNumber) {
		List<Statement> finalData = new ArrayList<Statement>();

		List<Statement> data = bankStatementsDAO.getListOfStatements(accountNumber);

		for (Statement transaction : data) {
			if (transaction.getDate() != null && transaction.getAmount() != null
					&& transaction.getAccountNumber() != null) {
				try {
					transaction.setDateField(parseStringToDate(StringUtils.replace(transaction.getDate(), ".", "-")));
					transaction.setTxAmount(new BigDecimal(transaction.getAmount()));
					transaction.setAccountNumber(encryptAccountNumber(transaction.getAccountNumber()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				finalData.add(transaction);
			}
		}
		return data;
	}

	public List<Statement> sortDescTransactionData(List<Statement> data) {
		Collections.sort(data, new sortDates());
		return data;

	}

	class sortDates implements Comparator<Statement> {
		@Override
		public int compare(Statement a, Statement b) {
			/* Returns sorted data in Descending order */
			return b.getDateField().compareTo(a.getDateField());
		}
	}

	public List<Statement> sortAscTransactionData(List<Statement> data) {
		Collections.sort(data, new sortAscDates());
		return data;

	}

	class sortAscDates implements Comparator<Statement> {
		@Override
		public int compare(Statement a, Statement b) {
			/* Returns sorted data in ascending order */
			return a.getDateField().compareTo(b.getDateField());
		}
	}

	@Override
	public boolean isAccountExist(String accNum) {
		return bankStatementsDAO.isAccountExist(accNum);
	}

	public List<Statement> sortDataInDescOrder(List<Statement> data, Date maxDate) {

		List<Statement> descSortedData = sortDescTransactionData(data);

		List<Statement> threeMonthsData = new ArrayList<Statement>();
		for (Statement threeMonthsTx : descSortedData) {
			if (threeMonthsTx.getDateField().before(addDays(maxDate, -90))) {
				break;
			}
			threeMonthsData.add(threeMonthsTx);
		}
		return threeMonthsData;

	}

	public List<Statement> sortDataInAscOrder(List<Statement> data, StatementRequest statement, Date maxDate) {
		List<Statement> ascSortedData = sortAscTransactionData(data);
		List<Statement> filterData = new ArrayList<Statement>();
		for (Statement filter : ascSortedData) {
			if (statement.isDateEnabled() && statement.isAmountEnabled()) {
				if (filter.getDateField().after(statement.getFromDate())) {
					if (filter.getTxAmount().compareTo(statement.getFromAmount()) > 0) {
						if (filter.getDateField().before(statement.getToDate())) {
							if (filter.getTxAmount().compareTo(statement.getToAmount()) < 0) {
								filterData.add(filter);
							}
						}
					}
				}
			} else if (statement.isDateEnabled() || statement.isAmountEnabled()) {
				if (statement.isDateEnabled()) {
					if (filter.getDateField().after(statement.getFromDate())) {
						if (filter.getDateField().before(statement.getToDate())) {
							filterData.add(filter);
						}
					}
				} else if (statement.isAmountEnabled()) {
					if (filter.getTxAmount().compareTo(statement.getFromAmount()) > 0) {
						if (filter.getTxAmount().compareTo(statement.getToAmount()) < 0) {
							filterData.add(filter);
						}
					}
				}
			}

			if (!(statement.isDateEnabled() && statement.isAmountEnabled())) {
				boolean amountEnabled = false;
				boolean dateEnabled = false;
				if (!statement.isAmountEnabled()) {
					amountEnabled = true;
				}

				if (!statement.isDateEnabled()) {
					dateEnabled = true;
				}
				if (amountEnabled && dateEnabled) {
					return sortDataInDescOrder(data, maxDate);

				}
			}

		}
		return filterData;

	}

}
