package net.jakartaee.bookshop.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Listing;


public class ListingDAO extends SQLiteDAO{
	// public static enum LISTING_ITEM { BOOK, PLATE}				// TODO: Support Plate Listings

	private static final String SQL_GET_ALL_LISTINGS = "SELECT * FROM listing ";
	private static final String SQL_GET_BOOK_LISTINGS = "SELECT * FROM listing JOIN xref_book_listing USING ( listingId ) WHERE bookId=?";

	private static final String SQL_GET_LISTING = "SELECT * FROM listing WHERE listingId=?";
	private static final String SQL_INSERT_LISTING = "INSERT INTO listing"  + Listing.SQL_INSERT_FIELDS + Listing.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_LISTING = "UPDATE listing SET "  + Listing.SQL_UPDATE_FIELDS + " WHERE listingId=?";
	private static final String SQL_DELETE_LISTING = "DELETE FROM listing WHERE listingId=?";

	private static final String SQL_INSERT_BOOK_LISTING = "INSERT INTO xref_book_listing ( bookId, listingId ) VALUES (?,?)";
	private static final String SQL_DELETE_BOOK_LISTINGS = "DELETE FROM xref_book_listing WHERE bookId=?";
	
	public List<Listing> getAllListings() throws DatabaseException{
		List<Listing> listings = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_LISTINGS);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				listings.add(new Listing(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getListings was not successful.",e);
		}
		return listings;
	}
	public List<Listing> getBookListings(Integer bookId) throws DatabaseException{
		List<Listing> listings = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOK_LISTINGS);){
		
			getPS.setInt( 	1, bookId);
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				listings.add(new Listing(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getListings was not successful.",e);
		}
		return listings;
	}

	
	public Listing getListingById(Integer id) throws NotFoundException, DatabaseException{
		Listing listing = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_LISTING);){
			getPS.setInt( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Listing not found for id: " + id);
			}
			else {
				listing = new Listing(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getListingById was not successful.",e);
		}
		return listing;
	}
	
	public void insertListing(Listing l) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_LISTING);){
		
			//insertPS.setString( 1, b.getKey());
			insertPS.setString( 1, l.getName());
			insertPS.setString( 2, l.getUrl());

			
			int numRows = insertPS.executeUpdate();
			//int newId = getNewId(conn);
			//return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Listing was not successful.",e);
		}
	}
	
	public void updateListing(Listing l) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_LISTING);){
		
			insertPS.setString( 1, l.getName());
			insertPS.setString( 2, l.getUrl());
			
			insertPS.setInt( 3, l.getId());
			int success = insertPS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Listing was not successful.",e);
		}
	}
	public void deleteListing(Integer id) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_LISTING);){
			
			delPS.setInt( 	1, id);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Listing not deleted for id: " + id);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Listing was not successful.",e);
		}
	}
	
	
	//
	// XREF Methods
	//
	public int deleteBookListings(Integer bookId) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_BOOK_LISTINGS);){
			
			delPS.setInt( 	1, bookId);
			return delPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Delete BookListings was not successful.",e);
		}
	}
	public int insertBookListing(Integer bookId, Integer listingId) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_BOOK_LISTING);){
		
			insertPS.setInt( 1, bookId);
			insertPS.setInt( 2, listingId);
			
			return insertPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Insert BookListing was not successful.",e);
		}
	}
}
