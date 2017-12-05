package com.nonclercq.caspar.iginterviewtest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ObjectMapperConfiguration
{
	@Bean
	public ObjectMapper xmlMapper()
	{
		return new XmlMapper().configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
	}
}
