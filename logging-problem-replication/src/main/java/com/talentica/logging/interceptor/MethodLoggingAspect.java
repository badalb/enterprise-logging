package com.talentica.logging.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLoggingAspect {

	private static final Logger logger = LogManager
			.getLogger(MethodLoggingAspect.class.getName());

	@Before("within(com.talentica.logging..*))")
	public void logBefore(JoinPoint joinPoint) {
		logger.info("Hijacked execution {}: started."
				+ joinPoint.getSignature().getName());
	}

	@After("within(com.talentica.logging..*))")
	public void logAfter(JoinPoint joinPoint) {
		logger.info("Hijacked execution {}: finished successfully."
				+ joinPoint.getSignature().getName());
	}
}