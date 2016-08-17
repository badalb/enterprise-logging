package com.talentica.logging.rabbitmq;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Receiver {

	private static final Logger logger = LogManager.getLogger(Receiver.class
			.getName());

	private CountDownLatch latch = new CountDownLatch(1);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		logger.info("Received message {}", message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}