package com.bank.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bank.model.Statement;

@Repository
public class BankStatementDAOImpl implements BankStatementsDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511398758441553962L;
	Logger logger = LogManager.getLogger(BankStatementDAOImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Statement> getListOfStatements(String accountNumber) {
		logger.info("Entering");
		Statement statement = new Statement();
		StringBuilder query = new StringBuilder(
				"SELECT t2.account_number accountNumber,t1.datefield as date,t1.amount FROM statement t1 inner join Account t2 on t1.account_id=t2.id ");
		query.append(" where t2.account_number='" + accountNumber + "'");

		SqlParameterSource parameter = new BeanPropertySqlParameterSource(statement);

		RowMapper<Statement> type = BeanPropertyRowMapper.newInstance(Statement.class);

		List<Statement> data = jdbcTemplate.query(query.toString(), parameter, type);
		logger.info("Leaving");
		return data;
	}

	public boolean isAccountExist(String accNum) {
		logger.info("Entering");
		String sql = "SELECT count(*) FROM Account WHERE account_number =:accNum";
		MapSqlParameterSource source = new MapSqlParameterSource();
		source.addValue("accNum", accNum);
		Integer count = jdbcTemplate.queryForObject(sql, source, Integer.class);
		logger.info("Leaving");
		return count > 0;

	}

}
