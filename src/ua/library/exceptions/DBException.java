package ua.library.exceptions;

public class DBException extends AppException {

	private static final long serialVersionUID = 5424026901780423835L;

	public DBException() {
		super();

	}

	public DBException(String message, Throwable cause) {
		super(message, cause);

	}

	public DBException(String message) {
		super(message);

	}

}
