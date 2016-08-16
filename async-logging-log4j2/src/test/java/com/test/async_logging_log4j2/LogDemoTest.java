package com.test.async_logging_log4j2;

import java.util.Date;

import org.apache.logging.log4j.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.test.asynclogging.log4j2.AsyncLogger;
import com.test.asynclogging.log4j2.LogDto;

public class LogDemoTest {

	private AsyncLogger logger = null;
	
	@Before
	public void setUp() throws Exception {
		logger = new AsyncLogger();
	}

	@After
	public void tearDown() throws Exception {
		logger = null;
	}

	@Test
	public void testLog() {
		
		LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.setLoglevel(Level.DEBUG.name());
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("test-partner-1");
		model.setSiteId("test-site-1");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());
		
		logger.log(model);
	}

}
