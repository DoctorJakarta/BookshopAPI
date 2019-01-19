package net.jakartaee.bookshop.model;

public class ErrorResponse {
	private String _message;
	private String _cause;
	private int _status;
	public ErrorResponse(String message, String cause, int status) {
		super();
		_message = message;
		_cause = cause;
		_status = status;
	}
	public String getMessage() {
		return _message;
	}
	public String getCause() {
		return _cause;
	}
	public int getStatus() {
		return _status;
	}
	
	
}
