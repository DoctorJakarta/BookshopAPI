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
import net.jakartaee.bookshop.model.Attribute;
import net.jakartaee.bookshop.model.Detail;
import net.jakartaee.bookshop.model.Tag;


public class AttributeDAO extends SQLiteDAO{
	private static final String SQL_GET_ATTRIBUTES = "SELECT * FROM attribute ORDER BY name";
	private static final String SQL_INSERT_ATTRIBUTE = "INSERT INTO attribute"  + Attribute.SQL_INSERT_FIELDS + Attribute.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_ATTRIBUTE = "UPDATE attribute SET "  + Attribute.SQL_UPDATE_FIELDS + " WHERE attributeId=?";
	private static final String SQL_DELETE_ATTRIBUTE = "DELETE FROM attribute WHERE attributeId=?";
	
	public List<Attribute> getAttributeNames() throws DatabaseException{
		List<Attribute> names = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ATTRIBUTES);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {							// Note: Cannot open another DAO when the single Connection is still open.  It will simply hang waiting for a pool
				Attribute attr = new Attribute(rs);
				names.add( attr );		// The name is the first item in the ResultSet row
			}
		} catch (SQLException e) {
			throw new DatabaseException("getAttributes was not successful.",e);
		}
		return names;
	}
	
//	public Attribute getAttributeById(Integer id) throws NotFoundException, DatabaseException{
//		Attribute attribute = null;
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ATTRIBUTE_BY_ID);){
//			getPS.setInt( 	1, id);
//		
//			ResultSet rs = getPS.executeQuery();
//			if (!rs.next()) {
//				throw new NotFoundException("Attribute not found for id: " + id);
//			}
//			else {
//				attribute = new Attribute(rs);		
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getTagById was not successful.",e);
//		}
//		return attribute;
//	}
//	
//	public void insertAttribute(Attribute s) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_ATTRIBUTE);){
//		
//			insertPS.setString( 1, s.getParentName());
//			insertPS.setString( 2, s.getAttributeName());
//			insertPS.setString( 3, s.getCode());
//		
//			int numRows = insertPS.executeUpdate();
//			//int newId = getNewId(conn);
//			//return newId;
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Insert Attribute was not successful.",e);
//		}
//	}
//	
//	public void updateAttribute(Attribute b) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_ATTRIBUTE);){
//		
//			insertPS.setString( 1, b.getName());
//			insertPS.setString( 2, b.getAttributeName());
//			insertPS.setString( 3, b.getCode());
//			
//			insertPS.setInt( 	4, b.getId());
//			int success = insertPS.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Update Attribute was not successful.",e);
//		}
//	}
//	
//	public void deleteAttribute(Integer id) throws NotDeletedException, DatabaseException {
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_ATTRIBUTE);){
//			
//			delPS.setInt( 	1, id);
//		
//			if ( delPS.executeUpdate() == 0 ) {
//				throw new NotDeletedException("Attribute not deleted for id: " + id);
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("Delete Tag was not successful.",e);
//		}
//	}
//	

}
