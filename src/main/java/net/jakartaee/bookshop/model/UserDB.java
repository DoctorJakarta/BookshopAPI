package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB extends User{
	public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE user ( userId INTEGER PRIMARY KEY NOT NULL, username TEXT UNIQUE, pwdsalt TEXT NOT NULL, pwdhash TEXT NOT NULL, role INTEGER)";

	public static final String SQL_INSERT_FIELDS = " (username, pwdsalt, pwdhash, role) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?,?) ";
	public static final String SQL_UPDATE_FIELDS = " username=?, role=? ";
	public static final String SQL_UPDATE_PASSWORD = " pwdhash=? ";
	
	private String _pwdsalt;
	private String _pwdhash;
	
	public UserDB() {} // This is require dfor jersey meida=json-jackson binding for doPost
	
	public UserDB(String username, String pwdsalt, String pwdhash, ROLE role) {				// Used for inserting new Users
		setUsername(username);
		_pwdsalt = pwdsalt;
		_pwdhash = pwdhash;
		setRole(role);
	}

	public UserDB(ResultSet rs) throws SQLException {
		super(rs);
		_pwdsalt = rs.getString("pwdsalt");
		_pwdhash = rs.getString("pwdhash");
	}

	
	//
	// Getters and Setters
	//

	public String getPwdsalt() {
		return _pwdsalt;
	}
	public void setPwdsalt(String pwdsalt) {
		_pwdsalt = pwdsalt;
	}
	public String getPwdhash() {
		return _pwdhash;
	}
	public void setPwdhash(String pwdhash) {
		_pwdhash = pwdhash;
	}


}
