package com.talentica.kafkalog4j2.consumer;

import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.talentica.kafkalog4j2.config.KafkaConfig;
import com.talentica.kafkalog4j2.config.KafkaProperties;

@Configuration
public class SimpleConsumerConfiguration {

	@Autowired
	private KafkaConfig kafkaConfig;

	@Bean(name = "simpleKafkaMessageConsumer")
	public ConsumerConnector consumer() {

		return kafka.consumer.Consumer
				.createJavaConsumerConnector(createConsumerConfig());

	}

	private ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", kafkaConfig.getZkConnect());
		props.put("group.id", KafkaProperties.groupId);
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		return new ConsumerConfig(props);

	}

}
