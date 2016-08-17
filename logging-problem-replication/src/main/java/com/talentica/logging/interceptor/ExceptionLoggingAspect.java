package com.talentica.logging.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {
	private static final Logger logger = LogManager
			.getLogger(ExceptionLoggingAspect.class.getName());

	@AfterThrowing(pointcut = "within(com.talentica.logging..*))", throwing = "ex")
	public void errorInterceptor(Exception ex) {
		// if (logger.isDebugEnabled()) {
		// logger.debug("Error Message Interceptor started");
		// }

		logger.debug("Error occurred with root cause {}", ex.getMessage());

		// if (logger.isDebugEnabled()) {
		// logger.debug("Error Message Interceptor finished.");
		// }
	}
}