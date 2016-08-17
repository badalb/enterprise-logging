package com.talentica.kafkalog4j2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talentica.kafkalog4j2.consumer.SimpleMessageConsumer;
import com.talentica.kafkalog4j2.producer.SimpleMessageProducer;

@RestController
public class SimpleMessageController {

	@Autowired
	private SimpleMessageProducer simpleMessageProducer;

	@Autowired
	private SimpleMessageConsumer simpleMessageConsumer;

	@RequestMapping("/producer")
	public String producer() {
		System.out.println("### Starting Producer ####");
		simpleMessageProducer.produce();
		return "started";
	}

	@RequestMapping("/consumer")
	public String startConsumer() {
		System.out.println("### Starting Consumer ####");
		simpleMessageConsumer.consume();
		return "started";
	}
}