package com.talentica.kafkalog4j2.config;

import java.util.UUID;

public class MessageIdGenerator {

	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		return randomUUIDString;
	}
}