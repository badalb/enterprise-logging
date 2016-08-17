package com.talentica.logging.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.talentica.logging.dto.LogMessage;

@RestController
public class LoggingController {

	private static final Logger logger = LogManager
			.getLogger(UserController.class.getName());

	@RequestMapping(name = "/log", method = RequestMethod.POST)
	public void log(@RequestBody LogMessage logMessage) {
		// check log level
		logger.info(logMessage.getMessage());

	}

}
