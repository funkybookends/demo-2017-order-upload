package com.nonclercq.caspar.iginterviewtest;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonclercq.caspar.iginterviewtest.configuration.ObjectMapperConfiguration;
import com.nonclercq.caspar.iginterviewtest.schemas.Action;
import com.nonclercq.caspar.iginterviewtest.schemas.Order;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ObjectMapperConfiguration.class)
@RunWith(SpringRunner.class)
public class ObjectMapperTest
{
	@Autowired
	private ObjectMapper xmlMapper;

	@Test
	public void GIVEN_xml_WHEN_readValue_EXPECT_datesAndEnumsHandled() throws Exception
	{
		final String xml = "<Order>\n" +
			"\t<accont>AX001</accont>\n" +
			"\t<SubmittedAt>1507060723641</SubmittedAt>\n" +
			"\t<ReceivedAt>1507060723642</ReceivedAt>\n" +
			"\t<market>VOD.L</market>\n" +
			"\t<action>BUY</action>\n" +
			"\t<size>100</size>\n" +
			"</Order>";

		final Order expected = new Order(
			"AX001",
			new Date(1507060723641L),
			new Date(1507060723642L),
			"VOD.L",
			Action.BUY,
			100);

		final Order actual = xmlMapper.readValue(xml, Order.class);

		assertThat(actual).isEqualTo(expected);
	}
}
