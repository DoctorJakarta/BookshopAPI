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


public class DetailDAO extends SQLiteDAO{
	//private static final String SQL_GET_DETAIL_NAMES = "SELECT DISTINCT (name) FROM detail ORDER BY name";

	private static final String SQL_GET_DETAILS_BY_ATTRIBUTE_ID = "SELECT * FROM detail WHERE attributeId=? ORDER BY sort";
	
	private static final String SQL_GET_DETAIL_BY_NAME = "SELECT * FROM detail WHERE detailId=? ORDER BY sort";
	
	private static final String SQL_INSERT_DETAIL = "INSERT INTO detail"  + Detail.SQL_INSERT_FIELDS + Detail.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_DETAIL = "UPDATE detail SET "  + Detail.SQL_UPDATE_FIELDS + " WHERE detailId=?";
	private static final String SQL_DELETE_DETAIL = "DELETE FROM detail WHERE detailId=?";
	
	public List<Detail> getDetailsByAttributeId(Integer id) throws DatabaseException{
		List<Detail> details = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_DETAILS_BY_ATTRIBUTE_ID);){
			
			getPS.setInt( 	1, id);
			
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				details.add( new Detail(rs) );		// The name is the first item in the ResultSet row
			}
		} catch (SQLException e) {
			throw new DatabaseException("getAttributeNames was not successful.",e);
		}
		return details;
	}
	
//	public Detail getDetailById(Integer id) throws NotFoundException, DatabaseException{
//		Detail detail = null;
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_DETAIL_BY_ID);){
//			getPS.setInt( 	1, id);
//		
//			ResultSet rs = getPS.executeQuery();
//			if (!rs.next()) {
//				throw new NotFoundException("Detail not found for id: " + id);
//			}
//			else {
//				detail = new Detail(rs);		
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getTagById was not successful.",e);
//		}
//		return detail;
//	}
//	
//	public void insertDetail(Detail s) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_DETAIL);){
//		
//			insertPS.setString( 1, s.getParentName());
//			insertPS.setString( 2, s.getDetailName());
//			insertPS.setString( 3, s.getCode());
//		
//			int numRows = insertPS.executeUpdate();
//			//int newId = getNewId(conn);
//			//return newId;
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Insert Detail was not successful.",e);
//		}
//	}
//	
//	public void updateDetail(Detail b) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_DETAIL);){
//		
//			insertPS.setString( 1, b.getName());
//			insertPS.setString( 2, b.getDetailName());
//			insertPS.setString( 3, b.getCode());
//			
//			insertPS.setInt( 	4, b.getId());
//			int success = insertPS.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Update Detail was not successful.",e);
//		}
//	}
//	
//	public void deleteDetail(Integer id) throws NotDeletedException, DatabaseException {
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_DETAIL);){
//			
//			delPS.setInt( 	1, id);
//		
//			if ( delPS.executeUpdate() == 0 ) {
//				throw new NotDeletedException("Detail not deleted for id: " + id);
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("Delete Tag was not successful.",e);
//		}
//	}
//	

}
