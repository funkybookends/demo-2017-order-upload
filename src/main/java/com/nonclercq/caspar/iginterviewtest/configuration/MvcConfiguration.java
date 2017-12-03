package com.nonclercq.caspar.iginterviewtest.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@Import({ObjectMapperConfiguration.class})
public class MvcConfiguration extends DelegatingWebMvcConfiguration
{
	@Autowired
	private ObjectMapper xmlMapper;


	@Override
	protected void extendMessageConverters(final List<HttpMessageConverter<?>> converters)
	{
		final MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter(xmlMapper);
		converters.add(xmlConverter);
		super.extendMessageConverters(converters);
	}
}
