package net.jakartaee.bookshop.exceptions;

import net.jakartaee.bookshop.model.ErrorResponse;

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 1L;

	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorResponse getErrorResponse() {
		return new ErrorResponse(this.getMessage(), this.getCause().getMessage(), 400);
	}


}
