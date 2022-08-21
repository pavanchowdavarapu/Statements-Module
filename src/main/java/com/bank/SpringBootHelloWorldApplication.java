package com.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootHelloWorldApplication {

	Logger logger = LogManager.getLogger(SpringBootHelloWorldApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception { Statement
	 * statement = new Statement(); StringBuilder query = new StringBuilder(
	 * "SELECT t2.account_number accountNumber,t1.account_id,t2.id, t1.datefield as date,t1.amount FROM statement t1 inner join Account t2 on t1.account_id=t2.id"
	 * ); SqlParameterSource parameter = new
	 * BeanPropertySqlParameterSource(statement);
	 * 
	 * RowMapper<Statement> type =
	 * BeanPropertyRowMapper.newInstance(Statement.class);
	 * 
	 * List<Statement> data = jdbcTemplate.query(query.toString(), parameter, type);
	 * 
	 * data.forEach(p -> { System.out.println("account: " + p.getAccountNumber() +
	 * " Date: " + p.getDate() + " amount:" + p.getAmount() + " account_id:" +
	 * p.getAccount_id() + " id " + p.getId()); });
	 * 
	 * }
	 */

}