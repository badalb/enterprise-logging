package com.talentica.kafka_partition_message;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class SimplePartitioner implements Partitioner {

	public SimplePartitioner(VerifiableProperties props) {
	}

	// @Override
	public int partition(Object arg0, int arg1) {
		return 0;
	}
}