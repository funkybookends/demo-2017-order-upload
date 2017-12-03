package com.nonclercq.caspar.iginterviewtest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nonclercq.caspar.iginterviewtest.controllers.OrderController;

@Configuration
public class ServiceConfiguration
{
	@Bean
	public OrderController orderController()
	{
		return new OrderController();
	}
}
