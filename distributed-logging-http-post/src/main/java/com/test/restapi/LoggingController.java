package com.test.restapi;

import javax.validation.Valid;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.LogDto;

@RestController
public class LoggingController {

	private static final Logger logger = LogManager
			.getLogger(LoggingController.class.getName());

	@RequestMapping(name = "/log", method = RequestMethod.POST)
	public void log(@Valid @RequestBody LogDto logData) {

		String fileName = "profilling_" + logData.getSiteId() + "_"
				+ logData.getFileName();
		ThreadContext.put("logFileName", fileName);
		String message = "Date:" + logData.getDate() + "Site Id:"
				+ logData.getSiteId() + "Partner Id :" + logData.getPartnerId()
				+ "Source File: " + logData.getSourceFileName() + "Message: "
				+ logData.getMessage();
		logger.log(getLogLevel(logData.getLoglevel()), message);
		ThreadContext.remove("logFileName");

	}

	private Level getLogLevel(String logLevel) {

		return Level.getLevel(logLevel);
	}
	
	@RequestMapping(name = "log/test", method = RequestMethod.GET)
	public String log() {
		
		ThreadContext.put("logFileName", "David");
		logger.log(Level.DEBUG, "test message" );
		ThreadContext.put("logFileName", "Mark");
		logger.info("test message");
		ThreadContext.remove("logFileName");
		
		return "success";
	}
	
}
