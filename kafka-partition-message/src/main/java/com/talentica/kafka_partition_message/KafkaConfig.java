package com.talentica.kafka_partition_message;

public class KafkaConfig {

	public static final String TEST_TOPIC_ID = "event-stream-part";

	private String topic = TEST_TOPIC_ID;

	private String brokerAddress = "localhost:9092";

	private String zookeeperAddress = "localhost:2181";

	private String serializerClass = "kafka.serializer.StringEncoder";

	private String zkConnect = "127.0.0.1:2181";

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