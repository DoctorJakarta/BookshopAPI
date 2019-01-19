package net.jakartaee.bookshop.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class Reference {
    
	public static final String SQL_INSERT_FIELDS = " ( bookId, price, url, desc, comment ) ";
	public static final String SQL_INSERT_VALUES = " VALUES ( ?,?,?,?,? ) ";
	public static final String SQL_UPDATE_FIELDS = " bookId=?, price=?, url=?, desc=?, comment=? ";
	
	private int _id;
	private int _bookId;
	private Long _price;
	private String _url;
	private String _desc;
	private String _comment;

	public Reference() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)
	
	public Reference(ResultSet rs) throws SQLException {
		_id =  rs.getInt("referenceId");
		_bookId = rs.getInt("bookId");
		_price = Optional.ofNullable(rs.getBigDecimal("price")).map(BigDecimal::longValue).orElse(null);
		_url = rs.getString("url");
		_desc = rs.getString("desc");
		_comment = rs.getString("comment");		
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		_id = id;
	}

	public int getBookId() {
		return _bookId;
	}

	public void setBookId(int bookId) {
		_bookId = bookId;
	}

	public Long getPrice() {
		return _price;
	}

	public void setPrice(Long price) {
		_price = price;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public String getDesc() {
		return _desc;
	}

	public void setDesc(String desc) {
		_desc = desc;
	}

	public String getComment() {
		return _comment;
	}

	public void setComment(String comment) {
		_comment = comment;
	}
	

	
}
