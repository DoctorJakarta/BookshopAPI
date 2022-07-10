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
import net.jakartaee.bookshop.model.Size;


public class SizeDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_SIZES = "SELECT * FROM size ";

	private static final String SQL_GET_SIZE = "SELECT * FROM size WHERE sizeId=?";
	private static final String SQL_INSERT_SIZE = "INSERT INTO size"  + Size.SQL_INSERT_FIELDS + Size.SQL_INSERT_VALUES;
	private static final String SQL_DELETE_SIZE = "DELETE FROM size WHERE sizeId=?";
	
	public List<Size> getSizes() throws DatabaseException{
		List<Size> sizes = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_SIZES);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				sizes.add(new Size(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getSizes was not successful.",e);
		}
		return sizes;
	}
	
//	public Size getSizeById(Integer id) throws NotFoundException, DatabaseException{
//		Size size = null;
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_SIZE);){
//			getPS.setInt( 	1, id);
//		
//			ResultSet rs = getPS.executeQuery();
//			if (!rs.next()) {
//				throw new NotFoundException("Size not found for id: " + id);
//			}
//			else {
//				size = new Size(rs);		
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getSizeById was not successful.",e);
//		}
//		return size;
//	}
//	
//	public void insertSize(Size b) throws DatabaseException{
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_SIZE);){
//		
//			//insertPS.setString( 1, b.getKey());
//			insertPS.setString( 1, b.getDimensions());
//
//			
//			int numRows = insertPS.executeUpdate();
//			//int newId = getNewId(conn);
//			//return newId;
//
//		} catch (SQLException e) {
//			throw new DatabaseException("Insert Size was not successful.",e);
//		}
//	}
//
//	public void deleteSize(Integer id) throws NotDeletedException, DatabaseException {
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_SIZE);){
//			
//			delPS.setInt( 	1, id);
//		
//			if ( delPS.executeUpdate() == 0 ) {
//				throw new NotDeletedException("Size not deleted for id: " + id);
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("Delete Size was not successful.",e);
//		}
//	}
//	

}
