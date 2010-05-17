package com.blogspot.duanni.mina.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OtherTest {

	public final Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void logTest() {
		log.info("hello world");
	}
}
