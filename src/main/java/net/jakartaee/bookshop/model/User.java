package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;


public class User {
	
	public static enum ROLE{ADMIN, PUBLIC;
		public static ROLE get(String sRole) {
			for (ROLE role : values()) {
				if (role.name().equals(sRole)) return role;
			}
			System.out.println("Error getting ROLE by value: " + sRole);
			return null;
		}
	
	}
	
	private int _id;
	private String _username;
	private String _password;		// This is populated by the JSON binding from the client during login & registration
	private ROLE _role;
	private String _jwtAccess;
	
	public User() {} 				// This is require dfor jersey meida=json-jackson binding for doPost
	
	
	public User(UserDB dbUser, String jwtAccess) {	// This the User returned on login or registration
		_id = dbUser.getId();
		_username = dbUser.getUsername();
		_role = dbUser.getRole();
		_jwtAccess = jwtAccess;
	}

	public User(ResultSet rs) throws SQLException {
		_id = rs.getInt("userId");
		_username = rs.getString("username");
		_role = ROLE.get(rs.getString("role"));
	}
	
	//
	// Getters and Setters
	//
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		_id = id;
	}
	public String getUsername() {
		return _username;
	}
	public void setUsername(String username) {
		_username = username;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String password) {
		_password = password;
	}
	public ROLE getRole() {
		return _role;
	}
	public void setRole(ROLE role) {
		_role = role;
	}
	public String getJwtAccess() {
		return _jwtAccess;
	}
	public void setJwtAccess(String jwtAccess) {
		_jwtAccess = jwtAccess;
	}
}
