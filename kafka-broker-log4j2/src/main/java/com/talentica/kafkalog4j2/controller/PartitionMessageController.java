package com.talentica.kafkalog4j2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentica.kafkalog4j2.consumer.PartitionMessageConsumer;
import com.talentica.kafkalog4j2.producer.PartitionMessageProducer;

@RestController
public class PartitionMessageController {

	@Autowired
	private PartitionMessageProducer partitionMessageProducer;

	@Autowired
	private PartitionMessageConsumer partitionMessageConsumer;

	@RequestMapping("/partition/producer")
	public String producer() {
		System.out.println("### Starting Partition Producer ####");
		partitionMessageProducer.producePartitionedMessage();
		return "started";
	}

	@RequestMapping("/partition/consumer")
	public String startConsumer() {
		System.out.println("### Starting Partition Consumer ####");
		partitionMessageConsumer.consumePartitionMessage();;
		return "started";
	}
}
