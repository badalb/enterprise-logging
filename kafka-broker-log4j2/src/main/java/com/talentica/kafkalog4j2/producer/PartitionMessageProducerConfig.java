package com.talentica.kafkalog4j2.producer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.talentica.kafkalog4j2.config.KafkaPartMessageConfig;

@Configuration
public class PartitionMessageProducerConfig {

	@Autowired
	private KafkaPartMessageConfig kafkaPartMessageConfig;

	@Bean(name = "kafkaPartionProducer")
	public Producer<String, String> producer() {
		Properties props = new Properties();
		props.put("serializer.class",
				kafkaPartMessageConfig.getSerializerClass());
		props.put("metadata.broker.list",
				kafkaPartMessageConfig.getBrokerAddress());
		props.put("metadata.broker.list",
				kafkaPartMessageConfig.getBrokerAddress());
		props.put("partitioner.class",
				"com.talentica.kafkalog4j2.config.SimplePartitioner");
		return new kafka.javaapi.producer.Producer<String, String>(
				new ProducerConfig(props));
	}
}
