package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Source {
	public static final String SQL_INSERT_FIELDS = " (title, author, year, details) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?,?) ";
	public static final String SQL_UPDATE_FIELDS = " title=?, author=?, year=?, details=? ";
    
	private int _id;
	private String _title;
	private String _author;
	private String _year;
	private String _details;

	public Source() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)

	public Source(ResultSet rs) throws SQLException {
		_id =  rs.getInt("sourceId");
		_title =  rs.getString("title");
		_author =  rs.getString("author");
		_year = rs.getString("year");
		_details = rs.getString("details");
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

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getYear() {
		return _year;
	}

	public void setYear(String year) {
		_year = year;
	}

	public String getDetails() {
		return _details;
	}

	public void setDetails(String details) {
		_details = details;
	}

	

	

}
