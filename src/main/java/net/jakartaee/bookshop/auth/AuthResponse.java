package net.jakartaee.bookshop.auth;

public class AuthResponse {
	
	private String _jwt;
	private String _email;
	private boolean _authenticated;

	
	public AuthResponse() {}    // This is required for jersey-media-json-jackson binding for doPost(Practice practice)

	public AuthResponse(String jwt, String email, boolean authenticated) {
		super();
		_jwt = jwt;
		_email = email;
		_authenticated = authenticated;
	}

	public String getJwt() {
		return _jwt;
	}

	public String getEmail() {
		return _email;
	}

	public boolean isAuthenticated() {
		return _authenticated;
	}
	
}
