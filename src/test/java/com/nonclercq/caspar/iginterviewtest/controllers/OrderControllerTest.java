package com.nonclercq.caspar.iginterviewtest.controllers;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nonclercq.caspar.iginterviewtest.FileUtils;
import com.nonclercq.caspar.iginterviewtest.configuration.ServiceConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationType;
import com.nonclercq.caspar.iginterviewtest.services.OrdersService;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class OrderControllerTest
{
	private static final String TEST_FILE = "interview-test-order.xml";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	public OrdersService ordersService;

	@Captor
	public ArgumentCaptor<DestinationConfiguration> configCaptor;

	@Before
	public void setup()
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void GIVEN_formInputTopic_WHEN_submitted_EXPECT_correctConfig() throws Exception
	{
		final String testContent = FileUtils.stringFrom(TEST_FILE);

		final MockMultipartFile file = new MockMultipartFile("file", testContent.getBytes());

		mockMvc.perform(fileUpload("/1.0/formsubmit")
			.file(file)
			.param("url", "tcp://localhost:61626")
			.param("user", "user")
			.param("password", "super secret")
			.param("destination", "sometopic")
			.param("type", "TOPIC")
			.content(testContent)
			.contentType(MediaType.APPLICATION_XML))
			.andDo(print())
			.andExpect(status().isOk());

		final DestinationConfiguration expected = new DestinationConfiguration("tcp://localhost:61626", "user", "super secret", "sometopic", DestinationType.TOPIC);

		verify(ordersService).submitOrders(configCaptor.capture(), eq(file));

		Assertions.assertThat(configCaptor.getValue()).isEqualToComparingFieldByField(expected);
	}

	@Test
	public void GIVEN_formInputQueue_WHEN_submitted_EXPECT_correctConfig() throws Exception
	{
		final String testContent = FileUtils.stringFrom(TEST_FILE);

		final MockMultipartFile file = new MockMultipartFile("file", testContent.getBytes());

		mockMvc.perform(fileUpload("/1.0/formsubmit")
			.file(file)
			.param("url", "tcp://localhost:61626")
			.param("user", "user")
			.param("password", "super secret")
			.param("destination", "sometopic")
			.param("type", "QUEUE")
			.content(testContent)
			.contentType(MediaType.APPLICATION_XML))
			.andDo(print())
			.andExpect(status().isOk());

		final DestinationConfiguration expected = new DestinationConfiguration("tcp://localhost:61626", "user", "super secret", "sometopic", DestinationType.QUEUE);

		verify(ordersService).submitOrders(configCaptor.capture(), eq(file));

		Assertions.assertThat(configCaptor.getValue()).isEqualToComparingFieldByField(expected);
	}
}