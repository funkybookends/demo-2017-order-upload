package com.nonclercq.caspar.iginterviewtest.services.impl;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonclercq.caspar.iginterviewtest.exceptions.InvalidOrdersException;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.Order;
import com.nonclercq.caspar.iginterviewtest.services.OrdersService;
import com.nonclercq.caspar.iginterviewtest.services.TemplateFactory;

public class OrdersServiceImpl implements OrdersService
{
	private static final Logger LOG = LoggerFactory.getLogger(OrdersServiceImpl.class);

	private final TemplateFactory destinationCreator;
	private final ObjectMapper xmlMapper;

	public OrdersServiceImpl(final TemplateFactory producerCreator,
	                         final ObjectMapper xmlMapper)
	{
		this.destinationCreator = producerCreator;
		this.xmlMapper = xmlMapper;
	}

	@Override
	public void submitOrders(final DestinationConfiguration destinationConfiguration, final MultipartFile multipartFile) throws InvalidOrdersException
	{
		final JmsTemplate destination = destinationCreator.apply(destinationConfiguration);
		handleOrders(multipartFile, destination::convertAndSend);
	}

	// TODO consider moving to its own interface if required
	private void handleOrders(final MultipartFile xmlMultipartFile, final Consumer<Order> orderConsumer) throws InvalidOrdersException
	{
		try (final Scanner scanner = new Scanner(xmlMultipartFile.getInputStream()))
		{
			scanner.nextLine(); // skip xml declaration line

			while (scanner.hasNextLine())
			{
				final Order order = getNextOrder(scanner);
				orderConsumer.accept(order);
			}
		}
		catch (final IOException ioException) // TODO error handle better - needs better requirements
		{
			throw new InvalidOrdersException(ioException);
		}
	}

	private Order getNextOrder(final Scanner scanner) throws IOException
	{
		String orderString = "[unknown]";

		try
		{
			orderString = scanner.nextLine();
			return xmlMapper.readValue(orderString, Order.class);
		}
		catch (final Exception exception)
		{
			LOG.warn("Error with order: {}, {}", orderString, exception);
			LOG.debug("Stack Trace", exception);
			throw exception;
		}
	}
}
