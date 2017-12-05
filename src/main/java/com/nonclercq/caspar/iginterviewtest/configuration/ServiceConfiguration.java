package com.nonclercq.caspar.iginterviewtest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonclercq.caspar.iginterviewtest.controllers.OrderController;
import com.nonclercq.caspar.iginterviewtest.services.TemplateFactory;
import com.nonclercq.caspar.iginterviewtest.services.OrdersService;
import com.nonclercq.caspar.iginterviewtest.services.impl.ActiveMqTemplateFactory;
import com.nonclercq.caspar.iginterviewtest.services.impl.OrdersServiceImpl;

@Configuration
@Import
	({
		ObjectMapperConfiguration.class,
	})
public class ServiceConfiguration
{
	@Bean
	public OrderController orderController(final OrdersService ordersService)
	{
		return new OrderController(ordersService);
	}

	@Bean
	public OrdersService ordersService(final TemplateFactory templateCreator, final ObjectMapper xmlMapper)
	{
		return new OrdersServiceImpl(templateCreator, xmlMapper);
	}

	@Bean
	public TemplateFactory templateCreator()
	{
		return new ActiveMqTemplateFactory();
	}

}
