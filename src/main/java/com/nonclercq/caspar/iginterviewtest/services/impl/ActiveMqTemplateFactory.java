package com.nonclercq.caspar.iginterviewtest.services.impl;

import java.util.function.Supplier;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationType;
import com.nonclercq.caspar.iginterviewtest.services.TemplateFactory;

public class ActiveMqTemplateFactory implements TemplateFactory
{
	private static final Logger LOG = LoggerFactory.getLogger(ActiveMqTemplateFactory.class);

	private static final boolean TRANSACTED = false;
	private static final int ACKNOWLEDGE_MODE = Session.AUTO_ACKNOWLEDGE;

	// For unit testing
	private final Supplier<ActiveMQConnectionFactory> connectionFactorySupplier;
	private final Supplier<JmsTemplate> jmsTemplateSupplier;

	public ActiveMqTemplateFactory()
	{
		this.connectionFactorySupplier = ActiveMQConnectionFactory::new;
		this.jmsTemplateSupplier = JmsTemplate::new;
	}

	public ActiveMqTemplateFactory(final Supplier<ActiveMQConnectionFactory> connectionFactorySupplier,
	                               final Supplier<JmsTemplate> jmsTemplateSupplier)
	{
		this.connectionFactorySupplier = connectionFactorySupplier;
		this.jmsTemplateSupplier = jmsTemplateSupplier;
	}

	@Override
	public JmsTemplate apply(final DestinationConfiguration config)
	{
		try
		{
			final ActiveMQConnectionFactory connectionFactory = connectionFactorySupplier.get();

			connectionFactory.setBrokerURL(config.getUrl());
			connectionFactory.setPassword(config.getPassword());
			connectionFactory.setUserName(config.getUser());

			final Connection connection = connectionFactory.createConnection();

			final Session session = connection.createSession(TRANSACTED, ACKNOWLEDGE_MODE);

			final Destination destination = config.getType() == DestinationType.TOPIC
				? session.createTopic(config.getDestination())
				: session.createQueue(config.getDestination());

			final JmsTemplate jmsTemplate = jmsTemplateSupplier.get();

			jmsTemplate.setDefaultDestination(destination);

			return jmsTemplate;
		}
		catch (final JMSException cause)
		{
			LOG.warn("Could not create connection using config: {}: {}", config, cause);
			throw new RuntimeException(cause);
		}
	}
}
