package com.nonclercq.caspar.iginterviewtest.services.impl;

import java.util.function.Supplier;

import javax.jms.Connection;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.DestinationType;
import com.nonclercq.caspar.iginterviewtest.services.TemplateFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ActiveMqTemplateCreatorTest
{
	private static final String URL = "tcp://localhost:61626";
	private static final String USER = "user";
	private static final String PASSWORD = "super secret";
	private static final String DESTINATION = "destination";

	private TemplateFactory templateFactory;
	private Supplier<ActiveMQConnectionFactory> connectionSupplier;
	private Supplier<JmsTemplate> jmsTemplateSupplier;
	private DestinationConfiguration config;

	@Before
	public void setUp() throws Exception
	{
		connectionSupplier = mock(Supplier.class);
		jmsTemplateSupplier = mock(Supplier.class);
		templateFactory = new ActiveMqTemplateFactory(connectionSupplier, jmsTemplateSupplier);
	}

	@Test
	public void GIVEN_topic_WHEN_apply_EXPECT_topicProduced() throws Exception
	{
		this.config = new DestinationConfiguration(URL, USER, PASSWORD, DESTINATION, DestinationType.TOPIC);

		final ActiveMQConnectionFactory connectionFactory = mock(ActiveMQConnectionFactory.class, "Topic ActiveMQConnectionFactory");
		final Connection connection = mock(Connection.class, "Topic Connection");
		final Session session = mock(Session.class, "Topic Session");
		final Topic destination = mock(Topic.class, "Topic Queue");
		final JmsTemplate jmsTemplate = mock(JmsTemplate.class, "Topic JmsTemplate");

		when(connectionSupplier.get()).thenReturn(connectionFactory);
		when(connectionFactory.createConnection()).thenReturn(connection);
		when(connection.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
		when(session.createTopic(DESTINATION)).thenReturn(destination);
		when(jmsTemplateSupplier.get()).thenReturn(jmsTemplate);

		final JmsTemplate actual = templateFactory.apply(config);

		verify(connectionSupplier).get();
		verify(connectionFactory).setBrokerURL(URL);
		verify(connectionFactory).setUserName(USER);
		verify(connectionFactory).setPassword(PASSWORD);
		verify(connectionFactory).createConnection();
		verify(connection).createSession(false, Session.AUTO_ACKNOWLEDGE);
		verify(session).createTopic(DESTINATION);
		verify(jmsTemplateSupplier).get();
		verify(jmsTemplate).setDefaultDestination(destination);

		Assertions.assertThat(actual).isSameAs(jmsTemplate);

		verifyNoMoreInteractions(connectionFactory, connection, session, destination, jmsTemplate, jmsTemplateSupplier);
	}

	@Test
	public void GIVEN_queue_WHEN_apply_EXPECT_queueProduced() throws Exception
	{
		this.config = new DestinationConfiguration(URL, USER, PASSWORD, DESTINATION, DestinationType.QUEUE);

		final ActiveMQConnectionFactory connectionFactory = mock(ActiveMQConnectionFactory.class, "Queue ActiveMQConnectionFactory");
		final Connection connection = mock(Connection.class, "Queue Connection");
		final Session session = mock(Session.class, "Queue Session");
		final Queue destination = mock(Queue.class, "Queue Queue");
		final JmsTemplate jmsTemplate = mock(JmsTemplate.class, "Queue JmsTemplate");

		when(connectionSupplier.get()).thenReturn(connectionFactory);
		when(connectionFactory.createConnection()).thenReturn(connection);
		when(connection.createSession(false, Session.AUTO_ACKNOWLEDGE)).thenReturn(session);
		when(session.createQueue(DESTINATION)).thenReturn(destination);
		when(jmsTemplateSupplier.get()).thenReturn(jmsTemplate);

		final JmsTemplate actual = templateFactory.apply(config);

		verify(connectionSupplier).get();
		verify(connectionFactory).setBrokerURL(URL);
		verify(connectionFactory).setUserName(USER);
		verify(connectionFactory).setPassword(PASSWORD);
		verify(connectionFactory).createConnection();
		verify(connection).createSession(false, Session.AUTO_ACKNOWLEDGE);
		verify(session).createQueue(DESTINATION);
		verify(jmsTemplateSupplier).get();
		verify(jmsTemplate).setDefaultDestination(destination);

		Assertions.assertThat(actual).isSameAs(jmsTemplate);

		verifyNoMoreInteractions(connectionFactory, connection, session, destination, jmsTemplate, jmsTemplateSupplier);
	}
}