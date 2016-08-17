package com.talentica.kafka_partition_message;

import java.util.UUID;

public class MessageIdGenerator {

	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString();
		return randomUUIDString;
	}
}