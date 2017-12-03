package com.nonclercq.caspar.iginterviewtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nonclercq.caspar.iginterviewtest.configuration.ServiceConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class IginterviewtestApplicationTests {

	@Test
	public void contextLoads() {
	}

}
