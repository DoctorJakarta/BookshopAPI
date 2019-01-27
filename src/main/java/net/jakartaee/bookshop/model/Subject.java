package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject {
	public static final String SQL_INSERT_FIELDS = " ( parentName, subjectName, code) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?) ";
	public static final String SQL_UPDATE_FIELDS = " parentName=?, subjectName=?, code=? ";
    
	private int _id;
	private String _parentName;
	private String _subjectName;
	private String _code;

	public Subject() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Subject(ResultSet rs) throws SQLException {
		_id =  rs.getInt("subjectId");
		_parentName =  rs.getString("parentName");
		_subjectName =  rs.getString("subjectName");
		_code = rs.getString("code");
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

	public String getParentName() {
		return _parentName;
	}

	public void setParentName(String parentName) {
		_parentName = parentName;
	}

	public String getSubjectName() {
		return _subjectName;
	}

	public void setSubjectName(String subjectName) {
		_subjectName = subjectName;
	}

	public String getCode() {
		return _code;
	}

	public void setCode(String code) {
		_code = code;
	}
	

	

}
