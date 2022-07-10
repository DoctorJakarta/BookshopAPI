package net.jakartaee.bookshop.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

//
//The BookAdmin Model includes additional fields that are returned in the ADMIN API
//

public class BookAdmin extends Book {
	
	private String _notes;

	private String _rarity;
	private String _reprints;
	private List<Reference> _references;
	
	private Long _priceBought;
	private Long _priceMin;
	private Long _priceMax;
	private String _dateBought;

	@JsonIgnore						// This is a dummy variable that is created on-demand by getPriceStr		
	public String onSale;

	public BookAdmin() {
	}

	public BookAdmin(ResultSet rs) throws SQLException {
		super(rs);
		
		_notes = rs.getString("notes");
		_rarity = rs.getString("rarity");
		_reprints = rs.getString("reprints");
		_priceBought = Optional.ofNullable(rs.getBigDecimal("priceBought")).map(BigDecimal::longValue).orElse(null);
		_priceMin = Optional.ofNullable(rs.getBigDecimal("priceMin")).map(BigDecimal::longValue).orElse(null);
		_priceMax = Optional.ofNullable(rs.getBigDecimal("priceMax")).map(BigDecimal::longValue).orElse(null);
		_dateBought = rs.getString("dateBought");
		

	}
	
	//
	// Getters and Setters
	//
	

	public String getNotes() {
		return _notes;
	}

	public void setNotes(String notes) {
		_notes = notes;
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

	public String getRarity() {
		return _rarity;
	}

	public void setRarity(String rarity) {
		_rarity = rarity;
	}

	public String getReprints() {
		return _reprints;
	}

	public void setReprints(String reprints) {
		_reprints = reprints;
	}

	public List<Reference> getReferences() {
		return _references;
	}

	public void setReferences(List<Reference> references) {
		_references = references;
	}
	

	

}
