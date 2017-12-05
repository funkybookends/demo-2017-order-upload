package com.nonclercq.caspar.iginterviewtest.services.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonclercq.caspar.iginterviewtest.configuration.ObjectMapperConfiguration;
import com.nonclercq.caspar.iginterviewtest.exceptions.InvalidOrdersException;
import com.nonclercq.caspar.iginterviewtest.schemas.Action;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationType;
import com.nonclercq.caspar.iginterviewtest.schemas.Order;
import com.nonclercq.caspar.iginterviewtest.services.OrdersService;
import com.nonclercq.caspar.iginterviewtest.services.TemplateFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ObjectMapperConfiguration.class})
public class OrdersServiceImplTest
{
	private static final String TEST_FILE = "interview-test-orders-1.xml";
	private static final String URL = "tcp://url.com";
	private static final String USER = "user";
	private static final String PASSWORD = "super secret";
	private static final String DESTINATION = "destination";

	private OrdersService ordersService;

	@Autowired
	private ObjectMapper xmlMapper;

	private TemplateFactory templateCreator;

	private DestinationConfiguration config;


	@Before
	public void setUp() throws Exception
	{
		this.config = new DestinationConfiguration(URL, USER, PASSWORD, DESTINATION, DestinationType.TOPIC);
		templateCreator = mock(TemplateFactory.class);
		ordersService = new OrdersServiceImpl(templateCreator, xmlMapper);
	}

	@Test
	public void GIVEN_multipartFile_WHEN_submitOrders_EXPECT_submitted() throws Exception, InvalidOrdersException
	{
		final Resource resource = new DefaultResourceLoader().getResource(TEST_FILE);
		final MultipartFile multipartFile = new MockMultipartFile("name", resource.getInputStream());

		final JmsTemplate jmsTemplate = mock(JmsTemplate.class);

		when(templateCreator.apply(config)).thenReturn(jmsTemplate);

		ordersService.submitOrders(config, multipartFile);

		final Order firstOrder = new Order("AX001", new Date(1507060723641L), new Date(1507060723642L), "VOD.L", Action.BUY, 100L);
		final Order secondOrder = new Order("AX002", new Date(1507060723641L), new Date(1507060723642L), "VOD.L", Action.BUY, 100L);

		verify(jmsTemplate).convertAndSend(firstOrder);
		verify(jmsTemplate).convertAndSend(secondOrder);

		verifyNoMoreInteractions(jmsTemplate);
	}

	// TODO add negative tests when exception handling expectations are better known
}