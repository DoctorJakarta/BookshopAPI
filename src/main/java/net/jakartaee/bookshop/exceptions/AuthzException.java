package net.jakartaee.bookshop.exceptions;

public class AuthzException extends Exception {
	private static final long serialVersionUID = 1L;

	public AuthzException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AuthzException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
