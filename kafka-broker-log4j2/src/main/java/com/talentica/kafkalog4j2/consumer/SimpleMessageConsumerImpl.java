package com.talentica.kafkalog4j2.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.talentica.kafkalog4j2.config.KafkaConfig;
import com.talentica.kafkalog4j2.model.LogDto;

@Service("simpleMessageConsumer")
public class SimpleMessageConsumerImpl implements SimpleMessageConsumer {

	@Autowired
	private Producer<Object, Object> simpleMessageProducer;

	@Autowired
	private KafkaConfig kafkaConfig;

	@Autowired
	private ConsumerConnector simpleKafkaMessageConsumer;

	private static final Logger logger = LogManager
			.getLogger(SimpleMessageConsumerImpl.class.getName());

	public void consume() {
		System.out.println("####### Started Consuming messages #######");

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		Gson converter = new Gson();

		topicCountMap.put(kafkaConfig.getTopic(), new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = simpleKafkaMessageConsumer
				.createMessageStreams(topicCountMap);

		KafkaStream<byte[], byte[]> stream = consumerMap.get(
				kafkaConfig.getTopic()).get(0);

		ConsumerIterator<byte[], byte[]> it = stream.iterator();

		while (it.hasNext()) {
			// System.out.println("####### Consuming messages #######");
			String message = new String(it.next().message());
			LogDto logData = converter.fromJson(message, LogDto.class);

			String fileName = "profilling_" + logData.getSiteId() + "_"
					+ logData.getFileName();
			ThreadContext.put("logFileName", fileName);
			String logmessage = "Date:" + logData.getDate() + "Site Id:"
					+ logData.getSiteId() + "Partner Id :"
					+ logData.getPartnerId() + "Source File: "
					+ logData.getSourceFileName() + "Message: "
					+ logData.getMessage();
			logger.log(getLogLevel(logData.getLoglevel()), logmessage);

			ThreadContext.remove("logFileName");

		}
	}

	private Level getLogLevel(String logLevel) {
		return Level.getLevel(logLevel);
	}
}
