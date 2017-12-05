package com.nonclercq.caspar.iginterviewtest.services;

import java.util.function.Function;

import org.springframework.jms.core.JmsTemplate;

import com.nonclercq.caspar.iginterviewtest.schemas.DestinationConfiguration;

public interface TemplateFactory extends Function<DestinationConfiguration, JmsTemplate>
{
}
