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
import net.jakartaee.bookshop.model.Reference;


public class ReferenceDAO extends SQLiteDAO{
	private static final String SQL_GET_REFERENCES_BY_BOOK_ID = "SELECT * FROM reference WHERE bookId=?;";
	private static final String SQL_GET_REFERENCE_BY_ID = "SELECT * FROM reference WHERE referenceId=?;";
	private static final String SQL_INSERT_REFERENCE = "INSERT INTO reference"  + Reference.SQL_INSERT_FIELDS + Reference.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_REFERENCE = "UPDATE reference SET "  + Reference.SQL_UPDATE_FIELDS + " WHERE referenceId=?;";
	private static final String SQL_DELETE_REFERENCE = "DELETE FROM reference WHERE referenceId=?;";
	
	public List<Reference> getBookReferences(int bookId) throws DatabaseException{
		List<Reference> references = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_REFERENCES_BY_BOOK_ID);){
			getPS.setInt( 	1, bookId);
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				references.add(new Reference(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getReferences was not successful.",e);
		}
		return references;
	}
	public Reference getReferenceById(Integer referencedId) throws NotFoundException, DatabaseException{
		Reference reference = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_REFERENCE_BY_ID);){
			getPS.setInt( 	1, referencedId);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Reference not found for id: " + referencedId);
			}
			else {
				reference = new Reference(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getReferenceById was not successful.",e);
		}
		return reference;
	}
	public int insertReference(Reference r) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_REFERENCE);){
		
			insertPS.setInt( 	1, r.getBookId());
			if ( r.getPrice() != null ) 		insertPS.setLong( 2, r.getPrice()); 
			else 								insertPS.setNull( 2, java.sql.Types.INTEGER);
			insertPS.setString( 3, r.getUrl());
			insertPS.setString( 4, r.getDesc());
			insertPS.setString( 5, r.getNotes());
			
			int numRows = insertPS.executeUpdate();
			int newId = getNewId(conn);
			return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Reference was not successful.",e);
		}
	}
	
	public void updateReference(Reference r) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_REFERENCE);){
		
			insertPS.setInt( 	1, r.getBookId());
			if ( r.getPrice() != null ) insertPS.setLong( 2, r.getPrice()); 
			else 						insertPS.setNull( 2, java.sql.Types.INTEGER);
			insertPS.setString( 3, r.getUrl());
			insertPS.setString( 4, r.getDesc());
			insertPS.setString( 5, r.getNotes());
			
			insertPS.setInt( 	6, r.getId());
			int success = insertPS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Reference was not successful.",e);
		}
	}
	public void deleteReference(Integer id) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_REFERENCE);){
			
			delPS.setInt( 	1, id);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Reference not deleted for id: " + id);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Reference was not successful.",e);
		}
	}
}
