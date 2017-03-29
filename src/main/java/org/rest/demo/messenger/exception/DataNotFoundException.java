package org.rest.demo.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7237621762184878019L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
