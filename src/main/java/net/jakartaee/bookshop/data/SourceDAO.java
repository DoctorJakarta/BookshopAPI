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
import net.jakartaee.bookshop.model.Source;


public class SourceDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_SOURCE = "SELECT * FROM source ORDER BY title";

	private static final String SQL_GET_SOURCE_BY_ID = "SELECT * FROM source WHERE sourceId=?";
	private static final String SQL_INSERT_SOURCE = "INSERT INTO source"  + Source.SQL_INSERT_FIELDS + Source.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_SOURCE = "UPDATE source SET "  + Source.SQL_UPDATE_FIELDS + " WHERE sourceId=?";
	private static final String SQL_DELETE_SOURCE = "DELETE FROM source WHERE sourceId=?";
	
	public List<Source> getSources() throws DatabaseException{
		List<Source> sources = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_SOURCE);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				sources.add(new Source(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getSources was not successful.",e);
		}
		return sources;
	}
	
//	public Source getSourceById(Long id) throws NotFoundException, DatabaseException{
//		Source source = null;
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_SOURCE_BY_ID);){
//			getPS.setLong( 	1, id);
//		
//			ResultSet rs = getPS.executeQuery();
//			if (!rs.next()) {
//				throw new NotFoundException("Source not found for id: " + id);
//			}
//			else {
//				source = new Source(rs);		
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getTagById was not successful.",e);
//		}
//		return source;
//	}
//	
//	public void insertSource(Source s) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_SOURCE);){
//		
//			insertPS.setString( 1, s.getParentName());
//			insertPS.setString( 2, s.getSourceName());
//			insertPS.setString( 3, s.getCode());
//		
//			int numRows = insertPS.executeUpdate();
//			//int newId = getNewId(conn);
//			//return newId;
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Insert Source was not successful.",e);
//		}
//	}
//	
//	public void updateSource(Source b) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_SOURCE);){
//		
//			insertPS.setString( 1, b.getParentName());
//			insertPS.setString( 2, b.getSourceName());
//			insertPS.setString( 3, b.getCode());
//			
//			insertPS.setInt( 	4, b.getId());
//			int success = insertPS.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Update Source was not successful.",e);
//		}
//	}
//	
//	public void deleteSource(Integer id) throws NotDeletedException, DatabaseException {
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_SOURCE);){
//			
//			delPS.setInt( 	1, id);
//		
//			if ( delPS.executeUpdate() == 0 ) {
//				throw new NotDeletedException("Source not deleted for id: " + id);
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("Delete Tag was not successful.",e);
//		}
//	}
	

}
