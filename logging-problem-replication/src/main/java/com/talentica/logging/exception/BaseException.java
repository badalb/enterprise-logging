package com.talentica.logging.exception;

import java.io.Serializable;

public abstract class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public BaseException(String msg) {
		super(msg);
	}

	public BaseException(String msg, Exception e) {
		super(msg, e);
	}
}