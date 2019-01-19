package net.jakartaee.bookshop.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Book {
	
//	public static enum SECTION { BIOGRAPHY(1), BUSINESS(2), CHILDREN(3), COOKBOOK(4), HISTORY(5), LITERATURE(6), MYSTERY(7),  SCIENCE(8), UNCLASSIFIED(0);
//		private int dbValue;
//		SECTION(int value) {dbValue=value;}
//		public static SECTION get(int iSection) {
//			for (SECTION section: values()) {
//				if (section.getValue() == iSection) {
//					return section;
//				}
//			}
//			return UNCLASSIFIED;		
//		}
//		public int getValue() { return dbValue; }
//		public String toString() { return ""+dbValue;}
//	}
																					// dateSold and isSold are not set on INSERT
	public static final String SQL_INSERT_FIELDS = " ( author, title, year, desc, comment, price, priceBought, priceMin, priceMax, dateBought, dateSold, sold) ";
	//public static final String SQL_INSERT_FIELDS = " ( author, title, year, desc, comment, price, priceBought, priceMin, priceMax, dateBought) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?,?,?,  ?,?,?,?,?, ?,?) ";
	
																					// dateSold and isSold are not set regular UPDATE
	public static final String SQL_UPDATE_FIELDS = " author=?, title=?, year=?, desc=?, comment=?, price=?, priceBought=?, priceMin=?, priceMax=?, dateBought=?, dateSold=?, sold=? ";
	//public static final String SQL_UPDATE_FIELDS = " authorId=?, title=?, year=?, desc=?, price=?, priceBought=?, priceMin=?, priceMax=?, dateBought=?";
	
	private int _id;
	private String _author;
	private String _title;
	private int _year;
	private String _desc;
	private String _comment;
	private Long _price;
	private Long _priceBought;
	private Long _priceMin;
	private Long _priceMax;
	private String _dateBought;
	private String _dateSold;
	private boolean _sold;
	
	private List<Reference> _references;
	private List<Tag> _tags;

	public Book() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)
	
	public Book(ResultSet rs) throws SQLException {
		_id =  rs.getInt("bookId");
		_author = rs.getString("author");
		_title = rs.getString("title");
		_year = rs.getInt("year");
		_desc = rs.getString("desc");
		_comment = rs.getString("comment");
		 
		_price = Optional.ofNullable(rs.getBigDecimal("price")).map(BigDecimal::longValue).orElse(null);
		_priceBought = Optional.ofNullable(rs.getBigDecimal("priceBought")).map(BigDecimal::longValue).orElse(null);
		_priceMin = Optional.ofNullable(rs.getBigDecimal("priceMin")).map(BigDecimal::longValue).orElse(null);
		_priceMax = Optional.ofNullable(rs.getBigDecimal("priceMax")).map(BigDecimal::longValue).orElse(null);
		
		_dateBought = rs.getString("dateBought");
		_dateSold = rs.getString("dateSold");
		_sold = rs.getBoolean("sold");
		
	}
	
	//
	// Generated Getters and Setters
	//
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		_id = id;
	}

	public String getAuthor() {
		return _author;
	}

	public void setAuthor(String author) {
		_author = author;
	}

	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		_title = title;
	}
	public int getYear() {
		return _year;
	}
	public void setYear(int year) {
		_year = year;
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

	public Long getPrice() {
		return _price;
	}

	public void setPrice(Long price) {
		_price = price;
	}

	public Long getPriceBought() {
		return _priceBought;
	}

	public void setPriceBought(Long priceBought) {
		_priceBought = priceBought;
	}

	public Long getPriceMin() {
		return _priceMin;
	}

	public void setPriceMin(Long priceMin) {
		_priceMin = priceMin;
	}

	public Long getPriceMax() {
		return _priceMax;
	}

	public void setPriceMax(Long priceMax) {
		_priceMax = priceMax;
	}

	public String getDateBought() {
		return _dateBought;
	}

	public void setDateBought(String dateBought) {
		_dateBought = dateBought;
	}

	public String getDateSold() {
		return _dateSold;
	}

	public void setDateSold(String dateSold) {
		_dateSold = dateSold;
	}


	public boolean isSold() {
		return _sold;
	}

	public void setSold(boolean sold) {
		_sold = sold;
	}

	public List<Reference> getReferences() {
		return _references;
	}

	public void setReferences(List<Reference> references) {
		_references = references;
	}

	public List<Tag> getTags() {
		return _tags;
	}

	public void setTags(List<Tag> tags) {
		_tags = tags;
	}


	
}
