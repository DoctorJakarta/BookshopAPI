package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Listing {
	//public static final String SQL_INSERT_FIELDS = " ( tagKey, tagName ) ";
	//public static final String SQL_INSERT_VALUES = " VALUES (?, ?) ";
	public static final String SQL_INSERT_FIELDS = " ( name, url ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?) ";
	public static final String SQL_UPDATE_FIELDS = " name=?, url=? ";
	
	//private String _key;
	private int _id;
	private String _name;
	private String _url;

	public Listing() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Listing(ResultSet rs) throws SQLException {
		_id =  rs.getInt("listingId");
		_name = rs.getString("name");
		_url = rs.getString("url");
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

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

}
