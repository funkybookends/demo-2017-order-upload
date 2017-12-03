package com.nonclercq.caspar.iginterviewtest.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nonclercq.caspar.iginterviewtest.FileUtils;
import com.nonclercq.caspar.iginterviewtest.configuration.MvcConfiguration;
import com.nonclercq.caspar.iginterviewtest.configuration.ServiceConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceConfiguration.class, MvcConfiguration.class})
public class OrderControllerTest
{
	private static final String TEST_FILE = "interview-test-order.xml";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void GIVEN_xml_WHEN_upload_EXPECT_parsedCorrectly() throws Exception
	{
		final String testContent = FileUtils.stringFrom(TEST_FILE);

		mockMvc.perform(post("/1.0/submit/order")
			.content(testContent)
			.contentType(MediaType.APPLICATION_XML))
			.andDo(print())
			.andExpect(status().isOk());
	}
}