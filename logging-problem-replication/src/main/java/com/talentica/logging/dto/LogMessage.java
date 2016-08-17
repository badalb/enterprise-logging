package com.talentica.logging.dto;

import java.io.Serializable;

public class LogMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1997771661686803655L;

	private String message;
	private String level;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}
