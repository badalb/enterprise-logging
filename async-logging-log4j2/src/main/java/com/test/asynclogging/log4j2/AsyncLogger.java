package com.test.asynclogging.log4j2;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class AsyncLogger {

	private static final Logger logger = LogManager.getLogger(AsyncLogger.class.getName());

	public void log(LogDto logData) {

		String fileName = "profilling_" + logData.getSiteId() + "_" + logData.getFileName();
		ThreadContext.put("logFileName", fileName);
		String message = "Date:" + logData.getDate() + "Site Id:" + logData.getSiteId() + "Partner Id :"
				+ logData.getPartnerId() + "Source File: " + logData.getSourceFileName() + "Message: "
				+ logData.getMessage();
		logger.log(getLogLevel(logData.getLoglevel()), message);
		ThreadContext.remove("logFileName");

	}

	private Level getLogLevel(String logLevel) {

		return Level.getLevel(logLevel);
	}
}