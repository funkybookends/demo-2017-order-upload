package com.nonclercq.caspar.iginterviewtest.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nonclercq.caspar.iginterviewtest.schemas.Order;

@Controller
public class OrderController
{
	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

	@RequestMapping(path = "/1.0/submit/order", consumes = MediaType.APPLICATION_XML_VALUE, method = RequestMethod.POST)
	public ResponseEntity<?> sendOrder(@Valid @RequestBody final Order orders)
	{
		LOG.info("Received: {}", orders);

		return ResponseEntity.ok().build();
	}
}
