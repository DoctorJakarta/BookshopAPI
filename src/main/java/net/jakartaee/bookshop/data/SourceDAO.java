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
import net.jakartaee.bookshop.model.Tag;


public class SourceDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_SUBJECT = "SELECT * FROM source ORDER BY parentName, subjectName";

	private static final String SQL_GET_SUBJECT_BY_ID = "SELECT * FROM subject WHERE subjectId=?";
	private static final String SQL_INSERT_SUBJECT = "INSERT INTO subject"  + Subject.SQL_INSERT_FIELDS + Subject.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_SUBJECT = "UPDATE subject SET "  + Subject.SQL_UPDATE_FIELDS + " WHERE subjectId=?";
	private static final String SQL_DELETE_SUBJECT = "DELETE FROM subject WHERE subjectId=?";
	
	public List<Subject> getSubjects() throws DatabaseException{
		List<Subject> subjects = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_SUBJECT);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				subjects.add(new Subject(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getSubjects was not successful.",e);
		}
		return subjects;
	}
	
	public Subject getSubjectById(Long id) throws NotFoundException, DatabaseException{
		Subject subject = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_SUBJECT_BY_ID);){
			getPS.setLong( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Subject not found for id: " + id);
			}
			else {
				subject = new Subject(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getTagById was not successful.",e);
		}
		return subject;
	}
	
	public void insertSubject(Subject s) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_SUBJECT);){
		
			insertPS.setString( 1, s.getParentName());
			insertPS.setString( 2, s.getSubjectName());
			insertPS.setString( 3, s.getCode());
		
			int numRows = insertPS.executeUpdate();
			//int newId = getNewId(conn);
			//return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Subject was not successful.",e);
		}
	}
	
	public void updateSubject(Subject b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_SUBJECT);){
		
			insertPS.setString( 1, b.getParentName());
			insertPS.setString( 2, b.getSubjectName());
			insertPS.setString( 3, b.getCode());
			
			insertPS.setInt( 	4, b.getId());
			int success = insertPS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Subject was not successful.",e);
		}
	}
	
	public void deleteSubject(Integer id) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_SUBJECT);){
			
			delPS.setInt( 	1, id);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Subject not deleted for id: " + id);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Tag was not successful.",e);
		}
	}
	

}
