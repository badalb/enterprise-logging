package com.talentica.logging.exception;

public class UserNotFoundException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4562394796050562656L;

	public UserNotFoundException(String msg) {
		super(msg);
	}

	public UserNotFoundException(String msg, Exception e) {
		super(msg, e);
	}

}
