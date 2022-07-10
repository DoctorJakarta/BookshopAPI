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
import net.jakartaee.bookshop.model.Plate;
import net.jakartaee.bookshop.model.Plate.SALE_STATUS;



public class PlateDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_PLATES = "SELECT * FROM plate ORDER BY priceList DESC";
	private static final String SQL_GET_PLATES_BY_FIELD = "SELECT * FROM plate WHERE ";
	private static final String SQL_GET_SALE_PLATES = "SELECT * FROM plate WHERE salePercent > 0 ORDER BY salePercent";
	
	private static final String SQL_GET_PLATE_BY_ID = "SELECT * FROM plate WHERE plateId=?";
	private static final String SQL_INSERT_PLATE = "INSERT INTO plate"  + Plate.SQL_INSERT_FIELDS + Plate.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_PLATE = "UPDATE plate SET "  + Plate.SQL_UPDATE_FIELDS + " WHERE plateId=?";
	private static final String SQL_DELETE_PLATE = "DELETE FROM plate WHERE plateId=?";


	

	public List<Plate> getInventoryPlates() throws DatabaseException{
		List<Plate> plates = new ArrayList<>();
		String sql = SQL_GET_PLATES_BY_FIELD + "status=?";
		sql += " ORDER BY priceList";		
		return getInventoryPlatesByQuery(sql, Plate.SALE_STATUS.LIST.toString(), false);
	}
	
	public Plate getInventoryPlateById(Integer id) throws NotFoundException, DatabaseException{
		Plate plate = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_PLATE_BY_ID);){
			getPS.setInt( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Plate not found for id: " + id);
			}
			else {
				plate = new Plate(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getPlateById was not successful.",e);
		}
		return plate;
	}
	
	
	public List<Plate> getAllPlates() throws DatabaseException{
		List<Plate> plates = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_PLATES);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				plates.add(new Plate(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getPlates was not successful.",e);
		}
		return plates;
	}

	public List<Plate> getSalePlates() throws DatabaseException{
		List<Plate> plates = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_SALE_PLATES);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				plates.add(new Plate(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getPlates was not successful.",e);
		}
		return plates;
	}	
//	//public List<Plate> getPlatesByTags(List<Tag> tags) throws DatabaseException{
//	public List<Plate> getPlatesByTags(Integer tagId) throws DatabaseException{
//		List<Plate> plates = new ArrayList<>();
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_PLATES_BY_TAG);){
//		
//			getPS.setInt( 	1, tagId);
//			ResultSet rs = getPS.executeQuery();
//			while (rs.next()) {
//				plates.add(new Plate(rs));
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getPlates was not successful.",e);
//		}
//		return plates;
//	}

//	public List<PlateAdmin> getPlatesByQueryField(String field, String value) throws DatabaseException{
//		// TODO: support other fields
//		boolean isLike = false;
//		String sql = null;
//		if 		( "year".equals(field))   sql = SQL_GET_PLATES_BY_FIELD + "year=?";
//		else if ( "status".equals(field)) sql = SQL_GET_PLATES_BY_FIELD + "status=?";
//		else if ( "tag".equals(field))  sql = SQL_GET_PLATES_BY_TAG;
//		else if ( "author".equals(field)) { 
//			sql = SQL_GET_PLATES_BY_FIELD + "author LIKE ?";
//			isLike = true;
//		}
//		else {
//			throw new DatabaseException("Query not supported for field: " + field);
//		}
//		sql += " ORDER BY year";
//		return getAdminPlatesByQuery(sql, value, isLike);
//	}

//	private List<PlateAdmin> getAdminPlatesByQuery(String sql, String value, boolean isLike) throws DatabaseException{
//		List<PlateAdmin> plates = new ArrayList<>();
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(sql);){
//		
//			if ( isLike ) getPS.setString( 	1, "%" + value + "%");
//			else 		  getPS.setString( 	1, value);
//			
//			ResultSet rs = getPS.executeQuery();
//			while (rs.next()) {
//				plates.add(new PlateAdmin(rs));
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getPlates was not successful.",e);
//		}
//		return plates;
//	}
	private List<Plate> getInventoryPlatesByQuery(String sql, String value, boolean isLike) throws DatabaseException{
		List<Plate> plates = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(sql);){
		
			if ( isLike ) getPS.setString( 	1, "%" + value + "%");
			else 		  getPS.setString( 	1, value);
			
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				plates.add(new Plate(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getPlates was not successful.",e);
		}
		return plates;
	}
	
	public Plate getPlateById(Integer id) throws NotFoundException, DatabaseException{
		Plate plate = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_PLATE_BY_ID);){
			getPS.setInt( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Plate not found for id: " + id);
			}
			else {
				plate = new Plate(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getPlateById was not successful.",e);
		}
		return plate;
	}
	

	public int insertPlate(Plate p) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_PLATE);){
		
			
			//if ( b.getSubjectId() != null ) insertPS.setLong( 1, b.getSubjectId()); 
			//else 							insertPS.setNull( 1, java.sql.Types.INTEGER);
			if ( p.getSubject() != null ) insertPS.setLong( 1, p.getSubject().getId()); 
			else 						  insertPS.setNull( 1, java.sql.Types.INTEGER);
			if ( p.getSource() != null )  insertPS.setLong( 2, p.getSource().getId()); 
			else 						  insertPS.setNull( 2, java.sql.Types.INTEGER);
			if ( p.getSize() != null ) 	  insertPS.setLong( 3, p.getSize().getId()); 
			else 						  insertPS.setNull( 3, java.sql.Types.INTEGER);

			insertPS.setString(  4, p.getName());
			insertPS.setString(  5, p.getCondition());			
			insertPS.setString(  6, p.getDetails());
			
			if ( p.getPriceList() != null ) 	insertPS.setLong( 7, p.getPriceList()); 
			else 								insertPS.setNull( 7, java.sql.Types.INTEGER);

			insertPS.setInt(	  8, p.getSalePercent());

			insertPS.setString(  9, p.getDateSold());
			insertPS.setString(	10, p.getUrlRelative());
			insertPS.setString(	11, p.getStatus());
			
			int numRows = insertPS.executeUpdate();
			int newId = getNewId(conn);
			return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Plate was not successful.",e);
		}
	}

	public void bulkUpdatePlateField(String field, String status, List<Integer> plateIds) throws DatabaseException{
		switch(field) {				// Prevent SQLi by only allowing known field names
			case "status":
			case "priceList":
			case "salePercent":
				bulkUpdatePlates(field, status, plateIds);
				break;	
			default: throw new DatabaseException("Update unsuccessful. Not configured to bulk update: " + field);
		}
	}
	
	private void bulkUpdatePlates(String field, String status, List<Integer> plateIds) throws DatabaseException{
		final String idSQL = plateIds.toString().replace("[","(").replace("]",")");				// UPDATE plate SET status= 'HOLD' WHERE plateId in (67, 62)
		final String sql = "UPDATE plate SET " + field + "= ? WHERE plateId in " + idSQL;
		//System.out.println("Running bulk update SQL: " + sql);
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement updatePS = conn.prepareStatement(sql);){
			
			for ( Integer id: plateIds ) {
				updatePS.setString( 1, status);
				int success = updatePS.executeUpdate();				
			}

		} catch (SQLException e) {
			throw new DatabaseException("Bulk Update Plate Status was not successful.",e);
		}
	}
	
	public void updatePlate(Plate p) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement updatePS = conn.prepareStatement(SQL_UPDATE_PLATE);){

			if ( p.getSubject() != null ) updatePS.setLong( 1, p.getSubject().getId()); 
			else 						  updatePS.setNull( 1, java.sql.Types.INTEGER);
			if ( p.getSource() != null )  updatePS.setLong( 2, p.getSource().getId()); 
			else 						  updatePS.setNull( 2, java.sql.Types.INTEGER);
			if ( p.getSize() != null ) 	  updatePS.setLong( 3, p.getSize().getId()); 
			else 						  updatePS.setNull( 3, java.sql.Types.INTEGER);

			updatePS.setString(  4, p.getName());
			updatePS.setString(  5, p.getCondition());			
			updatePS.setString(  6, p.getDetails());
			
			if ( p.getPriceList() != null ) 	updatePS.setLong( 7, p.getPriceList()); 
			else 								updatePS.setNull( 7, java.sql.Types.INTEGER);

			updatePS.setInt(	  8, p.getSalePercent());

			updatePS.setString(  9, p.getDateSold());
			updatePS.setString(	10, p.getUrlRelative());
			updatePS.setString(	11, p.getStatus());			
			updatePS.setInt( 	12, p.getId());
			int success = updatePS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Plate was not successful.",e);
		}
	}
	public void deletePlate(Integer id) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_PLATE);){
			
			delPS.setInt( 	1, id);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Plate not deleted for id: " + id);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Plate was not successful.",e);
		}
	}
}
