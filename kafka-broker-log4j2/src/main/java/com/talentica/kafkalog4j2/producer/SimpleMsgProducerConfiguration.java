package com.talentica.kafkalog4j2.producer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.talentica.kafkalog4j2.config.KafkaConfig;

@Configuration
public class SimpleMsgProducerConfiguration {

	@Autowired
	private KafkaConfig kafkaConfig;

	@Bean(name = "simpleKafkaMessageProducer")
	public Producer<Object, Object> producer() {
		Properties props = new Properties();
		props.put("serializer.class", kafkaConfig.getSerializerClass());
		props.put("metadata.broker.list", kafkaConfig.getBrokerAddress());
		return new kafka.javaapi.producer.Producer<Object, Object>(
				new ProducerConfig(props));
	}

}
