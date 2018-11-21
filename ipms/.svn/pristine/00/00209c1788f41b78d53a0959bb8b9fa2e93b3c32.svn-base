package com.ideassoft.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UnitTestImpl extends UnitTest {

	@Autowired
	private TestService testService;

	@Test
	public void abcTest() throws InterruptedException {
		try {
			System.out.println("第一次调用：" + testService.abc("abc", "1"));
			Thread.sleep(2000);
			System.out.println("2秒之后调用：" + testService.abc("abc", "2"));
			Thread.sleep(5000);
			System.out.println("再过5秒之后调用：" + testService.abc("abc", "3"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
