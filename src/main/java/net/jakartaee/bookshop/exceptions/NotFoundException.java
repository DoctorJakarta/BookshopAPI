package net.jakartaee.bookshop.exceptions;

import net.jakartaee.bookshop.model.ErrorResponse;

public class NotFoundException extends DatabaseException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}


}
