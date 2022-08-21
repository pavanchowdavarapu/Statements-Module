package com.bank.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bank.model.JwtRequest;
import com.bank.model.Statement;
import com.bank.model.StatementRequest;

public class BankStatementControllerTest extends AbstractTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	String jwtToken;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}

	@Test
	public void getStatementsUsingAccountIdWithAdminUser() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("admin");
		jwtRequest.setPassword("admin");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		StatementRequest statementRequest = new StatementRequest();
		statementRequest.setAccNumber("0012250016005");
		String inputJson1 = super.mapToJson(statementRequest);
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void getStatementsUsingWithDateFieldFilterAdminUser() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("admin");
		jwtRequest.setPassword("admin");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithDateAndAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void getStatementsUsingWithAmountFieldAdminUser() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("admin");
		jwtRequest.setPassword("admin");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithAmountAndAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void getStatementsUsingWithAmountAndDateFieldByAdminUser() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("admin");
		jwtRequest.setPassword("admin");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithAmountAndDateAndAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void getStatementsUsingWithAccIdByUser() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("user");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithOnlyAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	public void InvalidUserDetails() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("admin1");
		jwtRequest.setPassword("admin1");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(401, status);
	}

	@Test
	public void InvalidAccounTid() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("user");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createInvalidDeatail());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.content().string("Account does not exist")).andReturn();
	}

	@Test
	public void onlyFromAmountRequest() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("user");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithFromAmountAndAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.content().string("From Amount and To Amount must provide at a time"))
				.andReturn();
	}

	@Test
	public void onlyFromDateRequest() throws Exception {

		JwtRequest jwtRequest = new com.bank.model.JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("user");
		String inputJson = super.mapToJson(jwtRequest);
		MvcResult mvcResult = mvc
				.perform(
						MockMvcRequestBuilders.post("/authenticate").contentType("application/json").content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		jwtToken = mvcResult.getResponse().getContentAsString();
		JSONObject obj = new JSONObject(jwtToken);
		assertEquals(200, status);

		String inputJson1 = super.mapToJson(createRequestWithFromDateAndAccId());
		MvcResult result = mvc
				.perform(post("/Statement").header("Authorization", "Bearer " + obj.getString("token"))
						.contentType("application/json").content(inputJson1))
				.andExpect(MockMvcResultMatchers.content().string("Two Dates must provide at a time"))
				.andReturn();
	}

	public StatementRequest createRequestWithDateAndAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		statement.setFromDate(parseStringToDate("2020-10-01"));
		statement.setToDate(parseStringToDate("2021-01-09"));
		return statement;
	}

	public StatementRequest createRequestWithFromDateAndAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		statement.setFromDate(parseStringToDate("2020-10-01"));
		return statement;
	}

	public StatementRequest createRequestWithOnlyAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");

		return statement;
	}

	public StatementRequest createInvalidDeatail() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("001225dsg0016005");

		return statement;
	}

	public StatementRequest createRequestWithFromAmountAndAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		statement.setFromAmount(new BigDecimal(75));
		return statement;
	}

	public StatementRequest createRequestWithAmountAndAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		statement.setFromAmount(new BigDecimal(75));
		statement.setToAmount(new BigDecimal(1000));
		return statement;
	}

	public StatementRequest createRequestWithAmountAndDateAndAccId() throws ParseException {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		statement.setFromAmount(new BigDecimal(75));
		statement.setToAmount(new BigDecimal(1000));
		statement.setFromDate(parseStringToDate("2020-10-01"));
		statement.setToDate(parseStringToDate("2021-01-09"));
		return statement;
	}

	public Date parseStringToDate(String stringDate) throws ParseException {

		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(stringDate);
		return date;

	}
}
