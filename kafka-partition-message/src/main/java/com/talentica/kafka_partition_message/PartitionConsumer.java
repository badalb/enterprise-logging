package com.talentica.kafka_partition_message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import com.google.gson.Gson;

public class PartitionConsumer {

	public static void main(String[] args) {

		Map<String, String> messageBuffer = new HashMap<String, String>();

		KafkaConfig kafkaConfig = new KafkaConfig();

		ConsumerConnector kafkaConsumer = Consumer
				.createJavaConsumerConnector(createConsumerConfig(kafkaConfig));

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		Gson converter = new Gson();
		topicCountMap.put(kafkaConfig.getTopic(), new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = kafkaConsumer
				.createMessageStreams(topicCountMap);

		KafkaStream<byte[], byte[]> stream = consumerMap.get(
				kafkaConfig.getTopic()).get(0);

		ConsumerIterator<byte[], byte[]> it = stream.iterator();

		while (it.hasNext()) {
			String message = new String(it.next().message());
			PartMessage partMessage = converter.fromJson(message,
					PartMessage.class);
			if (partMessage.getSyncMarker() != null
					&& partMessage.getSyncMarker().equalsIgnoreCase("start")) {
				messageBuffer.put(partMessage.getUuid(),
						partMessage.getMessageBody());
			} else if (partMessage.getSyncMarker() != null
					&& partMessage.getSyncMarker().equalsIgnoreCase("end")) {
				String fullMessage = messageBuffer.get(partMessage.getUuid())
						.concat(partMessage.getMessageBody());
				LogDto logData = converter.fromJson(fullMessage, LogDto.class);
				System.out.println(logData.getMessage());
			} else {
				if (messageBuffer.get(partMessage.getUuid()) != null) {
					messageBuffer.put(
							partMessage.getUuid(),
							messageBuffer.get(partMessage.getUuid()).concat(
									partMessage.getMessageBody()));
				}
			}

		}

	}

	private static ConsumerConfig createConsumerConfig(KafkaConfig kafkaConfig) {
		Properties props = new Properties();
		props.put("zookeeper.connect", kafkaConfig.getZkConnect());
		props.put("group.id", KafkaProperties.groupId);
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(props);

	}
}
