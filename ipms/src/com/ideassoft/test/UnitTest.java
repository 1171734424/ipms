package com.ideassoft.test;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ideassoft.crm.service.InitialService;






@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTest extends AbstractJUnit4SpringContextTests {
	Logger log = Logger.getLogger(UnitTest.class);
	
	@Autowired
	InitialService initialService;

	
	@Test
	public void addCommentTest() {
		Boolean dd = initialService.checkRoomInDB("100001");
		System.out.println(dd);
	}


}
