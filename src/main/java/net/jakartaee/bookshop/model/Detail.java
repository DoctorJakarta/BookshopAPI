package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Detail {
	public static final String SQL_INSERT_FIELDS = " ( attributeId, value, sort ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?) ";
	public static final String SQL_UPDATE_FIELDS = " value=?, sort=? ";
    
	private int _id;
	private int _attributeId;
	private String _value;	
	private int _sort;

	public Detail() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)


	public Detail(ResultSet rs) throws SQLException {
		_id = rs.getInt("detailId");
		_attributeId = rs.getInt("attributeId");
		_value = rs.getString("value");
		_sort = rs.getInt("sort");
	}


	
	//
	// Generated Getters/Setters
	//
	

	public int getId() {
		return _id;
	}
	public void setId(int id) {
		_id = id;
	}
	public int getAttributeId() {
		return _attributeId;
	}
	public void setAttributeId(int attributeId) {
		_attributeId = attributeId;
	}
	public String getValue() {
		return _value;
	}
	public void setValue(String value) {
		_value = value;
	}
	public int getSort() {
		return _sort;
	}
	public void setSort(int sort) {
		_sort = sort;
	}

	

}
