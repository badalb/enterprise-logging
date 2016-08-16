package com.test.spring4test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.Application;
import com.test.dto.LogDto;

@RunWith(SpringJUnit4ClassRunner.class)   
@SpringApplicationConfiguration(classes = Application.class)   
@WebAppConfiguration   
@IntegrationTest("server.port:0")   
public class LoggingControllerTest {

    @Value("${local.server.port}")   
    int port;
    
    //@Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setUp() {
       
    }


    @Test
    public void logMessage() {
    	
    	LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.setLoglevel(org.apache.logging.log4j.Level.DEBUG.name());
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("test-partner-1");
		model.setSiteId("test-site-1");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());
     
		String uri="http://localhost:" +port+"/log";

		restTemplate.postForEntity(uri, model, null);
		

    	
    }

   
}