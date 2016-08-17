package com.talentica.kafkalog4j2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

	public static final String TEST_TOPIC_ID = "log-stream";

	@Value("${kafka.topic:" + TEST_TOPIC_ID + "}")
	private String topic;

	@Value("${kafka.address:localhost:9092}")
	private String brokerAddress;

	@Value("${zookeeper.address:localhost:2181}")
	private String zookeeperAddress;

	@Value("${serializer.class:kafka.serializer.StringEncoder}")
	private String serializerClass;

	@Value("${zookeeper.connect:127.0.0.1:2181}")
	private String zkConnect;

	KafkaConfig() {
	}

	public KafkaConfig(String t, String b, String zk, String serializer,
			String zkConnect) {
		this.topic = t;
		this.brokerAddress = b;
		this.zookeeperAddress = zk;
		this.serializerClass = serializer;
		this.zkConnect = zkConnect;
	}

	public String getTopic() {
		return topic;
	}

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public String getZookeeperAddress() {
		return zookeeperAddress;
	}

	public String getSerializerClass() {
		return serializerClass;
	}

	public String getZkConnect() {
		return zkConnect;
	}

}