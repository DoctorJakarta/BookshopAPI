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

public class Plate {
	
	public static enum SALE_STATUS{ PREP, REPAIR, LIST, SALE, HOLD, KEEP, SOLD, NONE;
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
	
	public static final String SQL_INSERT_FIELDS = " ( subjectId, sourceId, sizeId, name, condition, details, priceList, salePercent, dateSold, urlRelative, status) ";
	public static final String SQL_INSERT_VALUES = " VALUES (?,?,?,?,?,  ?,?,?,?,?, ? ) ";
	
																					// dateSold and isSold are not set regular UPDATE
	public static final String SQL_UPDATE_FIELDS = " subjectId=?, sourceId=?, sizeId=?, name=?, condition=?, details=?, priceList=?, salePercent=?, dateSold=?, urlRelative=?, status=? ";
    
	private int _id;
	private Long _subjectId;			// Can be null.  Redundant, but makes setting Object easier
	private Long _sourceId;				// Can be null./  Redundant, but makes setting Object easier
	private Long _sizeId;				// Can be null.  Redundant, but makes setting Object easier
	private String _name;
	private String _condition;	
	private String _details;	
	private Long _priceList;	
	private int _salePercent;
	private String _dateSold;
	private String _urlRelative;
	private String _status;
	
	private Subject _subject;
	private Size _size;
	private Source _source;
	
	@JsonIgnore						// This is a dummy variable that is created on-demand by getPriceStr		
	public String priceStr;
	@JsonIgnore						// This is a dummy variable that is created on-demand by getPriceStr		
	public String onSale;

	public Plate() {} // This is required for jersey-media-json-jackson binding for the doPost (Plate plate)
	
	public Plate(ResultSet rs) throws SQLException {
		_id =  rs.getInt("plateId");
		_subjectId = Optional.ofNullable(rs.getBigDecimal("subjectId")).map(BigDecimal::longValue).orElse(null);
		_sourceId = Optional.ofNullable(rs.getBigDecimal("sourceId")).map(BigDecimal::longValue).orElse(null);
		_sizeId = Optional.ofNullable(rs.getBigDecimal("sizeId")).map(BigDecimal::longValue).orElse(null);
		_name = rs.getString("name");

		_condition = rs.getString("condition");
		_details = rs.getString("details");
		_priceList = Optional.ofNullable(rs.getBigDecimal("priceList")).map(BigDecimal::longValue).orElse(null);
		_salePercent = rs.getInt("salePercent");
		
		_urlRelative = rs.getString("urlRelative");
		
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

	public Long getSourceId() {
		return _sourceId;
	}

	public void setSourceId(Long sourceId) {
		_sourceId = sourceId;
	}

	public Long getSizeId() {
		return _sizeId;
	}

	public void setSizeId(Long sizeId) {
		_sizeId = sizeId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
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

	public String getDateSold() {
		return _dateSold;
	}

	public void setDateSold(String dateSold) {
		_dateSold = dateSold;
	}

	public String getUrlRelative() {
		return _urlRelative;
	}

	public void setUrlRelative(String urlRelative) {
		_urlRelative = urlRelative;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
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
	// SET Objects
	//
	public Source getSource() {
		return _source;
	}

	public void setSource(Source source) {
		_source = source;
	}


	public Size getSize() {
		return _size;
	}

	public void setSize(Size size) {
		_size = size;
	}
	

	public Subject getSubject() {
		return _subject;
	}

	public void setSubject(Subject subject) {
		_subject = subject;
	}
	
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
