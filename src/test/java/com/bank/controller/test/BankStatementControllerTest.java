package com.bank.controller.test;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
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

	public StatementRequest createRequest() {
		StatementRequest statement = new StatementRequest();
		statement.setAccNumber("0012250016005");
		return statement;
	}

	public List<Statement> getStatement() {
		return new ArrayList<Statement>();

	}
}
