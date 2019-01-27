package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Tag {
	//public static final String SQL_INSERT_FIELDS = " ( tagKey, tagName ) ";
	//public static final String SQL_INSERT_VALUES = " VALUES (?, ?) ";
	public static final String SQL_INSERT_FIELDS = " ( name ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?) ";
	public static final String SQL_UPDATE_FIELDS = " name=? ";
	
	//private String _key;
	private int _id;
	private String _name;

	public Tag() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Tag(ResultSet rs) throws SQLException {
		_id =  rs.getInt("tagId");
		_name = rs.getString("name");
	}
	
	//
	//	Generated Getters/Setters
	//
	
	public int getId() {
		return _id;
	}

	public void setId(int id) {
		_id = id;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}




}
