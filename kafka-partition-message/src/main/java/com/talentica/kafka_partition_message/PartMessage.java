package com.talentica.kafka_partition_message;

public class PartMessage {

	private String messageBody;

	private String uuid;

	private String syncMarker;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSyncMarker() {
		return syncMarker;
	}

	public void setSyncMarker(String syncMarker) {
		this.syncMarker = syncMarker;
	}

}
