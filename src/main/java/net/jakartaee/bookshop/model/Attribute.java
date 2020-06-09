package net.jakartaee.bookshop.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Attribute {
	public static final String SQL_INSERT_FIELDS = " ( name ) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?) ";
	public static final String SQL_UPDATE_FIELDS = " name=? ";
	
	// Book model currently supports 
	// getBinding
	// getSize
	// getCondition
	// getRarity
	// isReprinted
    
	private int _id;
	private String _name;	
	private List<Detail> details;	
	//private List<String> values = Arrays.asList("New", "As New", "Fine", "Near Fine", "Very Good", "Good", "Fair", "Poor");	

	public Attribute() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)


	public Attribute(ResultSet rs) throws SQLException {
		_id = rs.getInt("attributeId");
		_name = rs.getString("name");
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
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	public List<Detail> getDetails() {
		return details;
	}
	public void setDetails(List<Detail> details) {
		this.details = details;
	}
	

}
