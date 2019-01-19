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
import net.jakartaee.bookshop.model.Tag;


public class TagDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_TAGS = "SELECT * FROM tag ";
	private static final String SQL_GET_BOOK_TAGS = "SELECT * FROM tag JOIN xref_tag_book USING ( tagKey ) WHERE bookId=?";

	private static final String SQL_GET_TAG_BY_KEY = "SELECT * FROM tag WHERE tagKey=?";
	private static final String SQL_INSERT_TAG = "INSERT INTO tag"  + Tag.SQL_INSERT_FIELDS + Tag.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_TAG = "UPDATE tag SET "  + Tag.SQL_UPDATE_FIELDS + " WHERE tagKey=?";
	private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE tagKey=?";

	private static final String SQL_INSERT_BOOK_TAG = "INSERT INTO xref_tag_book ( tagKey, bookId ) VALUES (?,?)";
	private static final String SQL_DELETE_BOOK_TAGS = "DELETE FROM xref_tag_book WHERE bookId=?";
	
	public List<Tag> getAllTags() throws DatabaseException{
		List<Tag> tags = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_TAGS);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				tags.add(new Tag(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getTags was not successful.",e);
		}
		return tags;
	}
	public List<Tag> getBookTags(Integer bookId) throws DatabaseException{
		List<Tag> tags = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOK_TAGS);){
		
			getPS.setInt( 	1, bookId);
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				tags.add(new Tag(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getTags was not successful.",e);
		}
		return tags;
	}

	
	public Tag getTagByKey(String key) throws NotFoundException, DatabaseException{
		Tag tag = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_TAG_BY_KEY);){
			getPS.setString( 	1, key);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Tag not found for id: " + key);
			}
			else {
				tag = new Tag(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getTagById was not successful.",e);
		}
		return tag;
	}
	
	public void insertTag(Tag b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_TAG);){
		
			insertPS.setString( 1, b.getKey());
			insertPS.setString( 2, b.getName());

			
			int numRows = insertPS.executeUpdate();
			//int newId = getNewId(conn);
			//return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Tag was not successful.",e);
		}
	}
	
	public void updateTag(Tag b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_TAG);){
		
			insertPS.setString( 1, b.getName());
			
			insertPS.setString( 2, b.getKey());
			int success = insertPS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Tag was not successful.",e);
		}
	}
	public void deleteTag(String key) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_TAG);){
			
			delPS.setString( 	1, key);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Tag not deleted for key: " + key);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Tag was not successful.",e);
		}
	}
	
	
	//
	// XREF Methods
	//
	public int deleteBookTags(Integer bookId) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_BOOK_TAGS);){
			
			delPS.setInt( 	1, bookId);
			return delPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Delete BookTags was not successful.",e);
		}
	}
	public int insertBookTag(Integer id, String key) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_BOOK_TAG);){
		
			insertPS.setString( 1, key);
			insertPS.setInt( 	2, id);
			
			return insertPS.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Insert BookTag was not successful.",e);
		}
	}
}
