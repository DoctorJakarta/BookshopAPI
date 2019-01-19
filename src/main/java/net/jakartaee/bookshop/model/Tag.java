package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tag {
	public static final String SQL_INSERT_FIELDS = " ( tagKey, tagName ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?, ?) ";
	public static final String SQL_UPDATE_FIELDS = " tagName=? ";
	
	private String _key;
	private String _name;

	public Tag() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Tag(ResultSet rs) throws SQLException {
		_key =  rs.getString("tagKey");
		_name = rs.getString("tagName");
	}
	
	//
	//	Generated Getters/Setters
	//
	
	public String getKey() {
		return _key;
	}
	public void setKey(String key) {
		_key = key;
	}
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}



}
