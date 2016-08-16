package com.test.restapi

import org.apache.logging.log4j.Level
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration

import spock.lang.Stepwise

import com.test.TestApplication
import com.test.dto.LogDto


@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = [TestApplication.class])
@WebAppConfiguration
@IntegrationTest("server.port:0")
@Stepwise
class LoggingControllerTest extends BaseSpecification {

	@Value('${local.server.port}')
	int port;


	def "test_log_get_method"() {

		setup:

		when:
		String uri="http://localhost:$port/test";
		ResponseEntity<String> entity=rt.getForEntity(uri, String.class);

		then:
		entity.statusCode == HttpStatus.OK
		entity.body=="success"
	}

	def "test_log_post_method"() {

		setup:
		LogDto model = new LogDto();
		model.setFileName("test_log_file");
		model.loglevel = org.apache.logging.log4j.Level.DEBUG.name();
		model.setMessage("Test sample log message performance test using a queue message no");
		model.setPartnerId("safdsgfdgsdfsfsdfdsf");
		model.setSiteId("sadfsgfhgjhfgdghrwetr");
		model.setSourceFileName("Test.php");
		model.setDate(new Date().toString());

		when:
		String uri="http://localhost:$port/log";

		ResponseEntity entity=rt.postForEntity(uri, model, null);

		then:
		entity.statusCode == HttpStatus.OK
	}
}
