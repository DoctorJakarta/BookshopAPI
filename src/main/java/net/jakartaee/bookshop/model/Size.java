package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Size {
	//public static final String SQL_INSERT_FIELDS = " ( tagKey, tagName ) ";
	//public static final String SQL_INSERT_VALUES = " VALUES (?, ?) ";
	public static final String SQL_INSERT_FIELDS = " ( dimensions ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?) ";
	public static final String SQL_UPDATE_FIELDS = " dimensions=? ";
	
	//private String _key;
	private int _id;
	private String _dimensions;

	public Size() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Size(ResultSet rs) throws SQLException {
		_id =  rs.getInt("sizeId");
		_dimensions = rs.getString("dimensions");
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

	public String getDimensions() {
		return _dimensions;
	}

	public void setDimensions(String dimensions) {
		_dimensions = dimensions;
	}



}
