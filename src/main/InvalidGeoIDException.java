package main;

public class InvalidGeoIDException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidGeoIDException() {
	}

	public InvalidGeoIDException(String message) {
		super(message);
	}

	public InvalidGeoIDException(Throwable cause) {
		super(cause);
	}

	public InvalidGeoIDException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidGeoIDException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
