package com.talentica.kafka_partition_message;

import java.io.Serializable;

public class LogDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -265402420578770424L;

	// @NotEmpty
	private String message;

	// @NotEmpty
	private String loglevel;

	// @NotEmpty
	private String date;

	// @NotEmpty
	private String siteId;

	// @NotEmpty
	private String fileName;

	// @NotEmpty
	private String partnerId;

	// @NotEmpty
	private String sourceFileName;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLoglevel() {
		return loglevel;
	}

	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

}
