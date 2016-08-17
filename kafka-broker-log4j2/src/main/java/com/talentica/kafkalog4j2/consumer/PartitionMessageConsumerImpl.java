package com.talentica.kafkalog4j2.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.talentica.kafkalog4j2.config.KafkaPartMessageConfig;
import com.talentica.kafkalog4j2.model.LogDto;
import com.talentica.kafkalog4j2.model.PartMessage;

@Component
public class PartitionMessageConsumerImpl implements PartitionMessageConsumer {

	private static final Logger logger = LogManager
			.getLogger(PartitionMessageConsumerImpl.class.getName());

	@Autowired
	private KafkaPartMessageConfig kafkaPartMessageConfig;

	@Autowired
	private ConsumerConnector kafkaPartMessageConsumer;

	public void consumePartitionMessage() {

		Map<String, String> messageBuffer = new HashMap<String, String>();

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		Gson converter = new Gson();
		topicCountMap.put(kafkaPartMessageConfig.getTopic(), new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = kafkaPartMessageConsumer
				.createMessageStreams(topicCountMap);

		KafkaStream<byte[], byte[]> stream = consumerMap.get(
				kafkaPartMessageConfig.getTopic()).get(0);

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
				//System.out.println(logData.getMessage());
				// remove message from map
				messageBuffer.remove(partMessage.getUuid());

				// log message
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

	private Level getLogLevel(String logLevel) {
		return Level.getLevel(logLevel);
	}
}
