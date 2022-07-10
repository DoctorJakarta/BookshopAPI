package net.jakartaee.bookshop.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

//
// The Book Model includes only fields that are returned in the PUBLIC API
//

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
	
	//
	// The SALE_STATUS enum is only necessary for if/else checking 
	// Ex: if ( book.getStatus().equals(STATUS.SOLD)) 
	//
	public static enum SALE_STATUS{ PREP, REPAIR, INQUIRE, LIST, SALE, HOLD, KEEP, SOLD, NONE;
	      public static SALE_STATUS get(String sStatus){
	      for (SALE_STATUS status : values()) {
	      //System.out.println("Checking STATUS name ("+status.name()+" equals: " + sStatus);
	          if (status.name().equals(sStatus)) {
	              return status;
	          }
	      }
	      System.out.println("EEEEError get STATUS by value: " + sStatus);
	      return NONE; // TODO: This should not occur.
	      }
	}
	
	
																					// dateSold should also be onINSERT
	//public static final String SQL_INSERT_FIELDS = " ( author, title, year, desc, comment, price, priceBought, priceMin, priceMax, dateBought, dateSold, status) ";
	public static final String SQL_INSERT_FIELDS = " ( subjectId, title, author, publisher, publisherPlace, year, edition, printing, volume, size,  pages, binding, condition, details, contents, notes, rarity, reprints, priceBought, priceMin, priceMax, priceList, salePercent, dateBought, dateSold, urlRelative, status) ";
	//public static final String SQL_INSERT_FIELDS = " ( author, title, year, desc, comment, price, priceBought, priceMin, priceMax, dateBought) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?,?,?,  ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,  ?,? ) ";
	
																					// dateSold and isSold are not set regular UPDATE
	public static final String SQL_UPDATE_FIELDS = " subjectId=?, title=?, author=?, publisher=?, publisherPlace=?, year=?, edition=?, printing=?, volume=?, size=?, pages=?, binding=?, condition=?, details=?, contents=?, notes=?,  rarity=?, reprints=?, priceBought=?, priceMin=?, priceMax=?, priceList=?, salePercent=?, dateBought=?, dateSold=?, urlRelative=?, status=? ";
	//public static final String SQL_UPDATE_FIELDS = " authorId=?, title=?, year=?, desc=?, price=?, priceBought=?, priceMin=?, priceMax=?, dateBought=?";
    
	private int _id;
	private Long _subjectId;				// Can be null
	private String _author;
	private String _title;
	private String _publisher;
	private String _publisherPlace;
	private Long _year;						// Not used for sorting like books. Can be a span 1810-1820
	private String _edition;
	private String _printing;
	private String _volume;
	private String _size;
	private String _pages;			// Ex: xiv 222 pp. w/ 16 plates
	private String _binding;
	private String _condition;
	
	private String _details;
	private String _contents;
	
	private Long _priceList;
	
	private int _salePercent;
	private String _dateSold;

	private String _urlRelative;
	
	private String _status;
	
	private Subject _subject;
	
	private List<Tag> _tags;
	
	@JsonIgnore						// This is a dummy variable that is created on-demand by getPriceStr		
	public String priceStr;

	public Book() {} // This is required for jersey-media-json-jackson binding for the doPost (Book book)
	
	public Book(ResultSet rs) throws SQLException {
		_id =  rs.getInt("bookId");
		_subjectId = Optional.ofNullable(rs.getBigDecimal("subjectId")).map(BigDecimal::longValue).orElse(null);
		_author = rs.getString("author");
		_title = rs.getString("title");
		
		_publisher = rs.getString("publisher");
		_publisherPlace = rs.getString("publisherPlace");
		_year = Optional.ofNullable(rs.getBigDecimal("year")).map(BigDecimal::longValue).orElse(null);;
		//_year = rs.getString("year");
		_edition = rs.getString("edition");
		_printing = rs.getString("printing");
		_volume = rs.getString("volume");
		//_pages = Optional.ofNullable(rs.getBigDecimal("pages")).map(BigDecimal::longValue).orElse(null);
		_size = rs.getString("size");
		_pages = rs.getString("pages");
		_binding = rs.getString("binding");

		_condition = rs.getString("condition");
		_details = rs.getString("details");
		_contents = rs.getString("contents");
		 
		_priceList = Optional.ofNullable(rs.getBigDecimal("priceList")).map(BigDecimal::longValue).orElse(null);
		_salePercent = rs.getInt("salePercent");
		
		_urlRelative = rs.getString("urlRelative");
		
		_dateSold = rs.getString("dateSold");
		_status = rs.getString("status");

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

	public Long getSubjectId() {
		return _subjectId;
	}

	public void setSubjectId(Long subjectId) {
		_subjectId = subjectId;
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

	public String getPublisher() {
		return _publisher;
	}

	public void setPublisher(String publisher) {
		_publisher = publisher;
	}

	public String getPublisherPlace() {
		return _publisherPlace;
	}

	public void setPublisherPlace(String publisherPlace) {
		_publisherPlace = publisherPlace;
	}

//	public String getYear() {
//		return _year;
//	}
//
//	public void setYear(String year) {
//		_year = year;
//	}

	public Long getYear() {
		return _year;
	}

	public void setYear(Long year) {
		_year = year;
	}
	
	public String getEdition() {
		return _edition;
	}


	public void setEdition(String edition) {
		_edition = edition;
	}

	public String getPrinting() {
		return _printing;
	}

	public void setPrinting(String printing) {
		_printing = printing;
	}

	public String getSize() {
		return _size;
	}

	public void setSize(String size) {
		_size = size;
	}

	public String getPages() {
		return _pages;
	}

	public void setPages(String pages) {
		_pages = pages;
	}

	public String getBinding() {
		return _binding;
	}

	public void setBinding(String binding) {
		_binding = binding;
	}

	public String getCondition() {
		return _condition;
	}

	public void setCondition(String condition) {
		_condition = condition;
	}

	public String getDetails() {
		return _details;
	}

	public void setDetails(String details) {
		_details = details;
	}

	public String getContents() {
		return _contents;
	}

	public void setContents(String contents) {
		_contents = contents;
	}


	public String getDateSold() {
		return _dateSold;
	}

	public void setDateSold(String dateSold) {
		_dateSold = dateSold;
	}



	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public Subject getSubject() {
		return _subject;
	}

	public void setSubject(Subject subject) {
		_subject = subject;
	}

	public List<Tag> getTags() {
		return _tags;
	}

	public void setTags(List<Tag> tags) {
		_tags = tags;
	}

	public String getVolume() {
		return _volume;
	}

	public void setVolume(String volume) {
		_volume = volume;
	}

	public String getUrlRelative() {
		return _urlRelative;
	}

	public void setUrlRelative(String urlRelative) {
		_urlRelative = urlRelative;
	}

	
	

	public Long getPriceList() {
		return _priceList;
	}

	public void setPriceList(Long priceList) {
		_priceList = priceList;
	}

	public int getSalePercent() {
		return _salePercent;
	}

	public void setSalePercent(int salePercent) {
		_salePercent = salePercent;
	}

	//
	//
	//
	@JsonIgnore							// This is a dummy method to prevent error when JSON comes back with this READ ONLY field
	public void setPriceStr(String priceStr) {
	}
	@JsonIgnore							// This is a dummy method to prevent error when JSON comes back with this READ ONLY field
	public void setPrice(int price) {
	}



//	public boolean isSold() {
//		return _sold;
//	}

	
	//
	// Derived attributes
	//
	
	@JsonProperty							// This includes the derive field in the JSON output. 
	public boolean onSale() {
		return ( _salePercent > 0 ? true : false);
	}
	
	@JsonProperty							// This includes the derive field in the JSON output. 
	public Long price() {
		if ( _priceList == null ) return null;
		return (long) (_priceList - Math.ceil((_priceList * _salePercent)/100));			// Round discount up to nearest dollar
	}
	
	@JsonProperty							// This includes the derive field in the JSON output. 
	public String priceStr() {
		if ( _priceList == null ) return null;
		return price() + ".00";
	}

}
