package ua.library.exceptions;

public class AppException extends Exception {

	private static final long serialVersionUID = 5142737227660758063L;

	public AppException() {
		super();
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppException(String message) {
		super(message);
	}

}
