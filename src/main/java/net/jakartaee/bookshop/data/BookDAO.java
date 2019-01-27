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
import net.jakartaee.bookshop.model.Book;


public class BookDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_BOOKS = "SELECT * FROM book";
	//private static final String SQL_GET_BOOKS = "SELECT * FROM book JOIN xref_book_tag USING (bookId) JOIN tag USING (tagKey) ";
	//private static final String SQL_GET_BOOKS_BY_TAG = "SELECT * FROM book LEFT JOIN subject USING (subjectId) JOIN xref_book_tag USING (bookId) WHERE tagId=?" ;

	//private static final String SQL_GET_BOOK_BY_ID = "SELECT * FROM book LEFT JOIN subject USING (subjectId) WHERE bookId=?";
	private static final String SQL_GET_BOOK_BY_ID = "SELECT * FROM book WHERE bookId=?";
	private static final String SQL_INSERT_BOOK = "INSERT INTO book"  + Book.SQL_INSERT_FIELDS + Book.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_BOOK = "UPDATE book SET "  + Book.SQL_UPDATE_FIELDS + " WHERE bookId=?";
	private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE bookId=?";


	
	public List<Book> getAllBooks() throws DatabaseException{
		List<Book> books = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_BOOKS);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBooks was not successful.",e);
		}
		return books;
	}
	
//	//public List<Book> getBooksByTags(List<Tag> tags) throws DatabaseException{
//	public List<Book> getBooksByTags(Integer tagId) throws DatabaseException{
//		List<Book> books = new ArrayList<>();
//		try(
//				Connection conn = SQLiteDatabase.getConnection();
//				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOKS_BY_TAG);){
//		
//			getPS.setInt( 	1, tagId);
//			ResultSet rs = getPS.executeQuery();
//			while (rs.next()) {
//				books.add(new Book(rs));
//			}
//		} catch (SQLException e) {
//			throw new DatabaseException("getBooks was not successful.",e);
//		}
//		return books;
//	}
	
	public Book getBookById(Integer id) throws NotFoundException, DatabaseException{
		Book book = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOK_BY_ID);){
			getPS.setInt( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Book not found for id: " + id);
			}
			else {
				book = new Book(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBookById was not successful.",e);
		}
		return book;
	}
	public int insertBook(Book b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_INSERT_BOOK);){
		
			
			//if ( b.getSubjectId() != null ) insertPS.setLong( 1, b.getSubjectId()); 
			//else 							insertPS.setNull( 1, java.sql.Types.INTEGER);
			if ( b.getSubject() != null ) insertPS.setLong( 1, b.getSubject().getId()); 
			else 						  insertPS.setNull( 1, java.sql.Types.INTEGER);
			insertPS.setString( 2, b.getTitle());
			insertPS.setString( 3, b.getAuthor());
			insertPS.setString( 4, b.getPublisher());
			insertPS.setString( 5, b.getPublisherPlace());

			insertPS.setInt( 	6, b.getYear());
			insertPS.setString( 7, b.getEdition());
			insertPS.setString( 8, b.getPrinting());

			insertPS.setString( 9, b.getDesc());
			insertPS.setString( 10, b.getNotes());
			if ( b.getPrice() != null ) 		insertPS.setLong( 11, b.getPrice()); 
			else 								insertPS.setNull( 11, java.sql.Types.INTEGER);
			if ( b.getPriceBought() != null ) 	insertPS.setLong( 12, b.getPriceBought()); 
			else 								insertPS.setNull( 12, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		insertPS.setLong( 13, b.getPriceMin()); 
			else 								insertPS.setNull( 13, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		insertPS.setLong( 14, b.getPriceMax()); 
			else 								insertPS.setNull( 14, java.sql.Types.INTEGER);

			insertPS.setString( 15, b.getDateBought());
			insertPS.setString( 16, b.getDateSold());
			insertPS.setString(	17, b.getStatus());
			insertPS.setString( 18, b.getCondition());
			
			int numRows = insertPS.executeUpdate();
			int newId = getNewId(conn);
			return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Book was not successful.",e);
		}
	}
	
	public void updateBook(Book b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement insertPS = conn.prepareStatement(SQL_UPDATE_BOOK);){
		
			//if ( b.getSubjectId() != null ) insertPS.setLong( 1, b.getSubjectId()); 
			//else 							insertPS.setNull( 1, java.sql.Types.INTEGER);
			if ( b.getSubject() != null ) insertPS.setLong( 1, b.getSubject().getId()); 
			else 						  insertPS.setNull( 1, java.sql.Types.INTEGER);
			insertPS.setString( 2, b.getTitle());
			insertPS.setString( 3, b.getAuthor());
			insertPS.setString( 4, b.getPublisher());
			insertPS.setString( 5, b.getPublisherPlace());

			insertPS.setInt( 	6, b.getYear());
			insertPS.setString( 7, b.getEdition());
			insertPS.setString( 8, b.getPrinting());

			insertPS.setString( 9, b.getDesc());
			insertPS.setString( 10, b.getNotes());
			if ( b.getPrice() != null ) 		insertPS.setLong( 11, b.getPrice()); 
			else 								insertPS.setNull( 11, java.sql.Types.INTEGER);
			if ( b.getPriceBought() != null ) 	insertPS.setLong( 12, b.getPriceBought()); 
			else 								insertPS.setNull( 12, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		insertPS.setLong( 13, b.getPriceMin()); 
			else 								insertPS.setNull( 13, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		insertPS.setLong( 14, b.getPriceMax()); 
			else 								insertPS.setNull( 14, java.sql.Types.INTEGER);

			insertPS.setString( 15, b.getDateBought());
			insertPS.setString( 16, b.getDateSold());
			insertPS.setString(	17, b.getStatus());
			insertPS.setString( 18, b.getCondition());
			
			insertPS.setInt( 	19, b.getId());
			int success = insertPS.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Update Book was not successful.",e);
		}
	}
	public void deleteBook(Integer id) throws NotDeletedException, DatabaseException {
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement delPS = conn.prepareStatement(SQL_DELETE_BOOK);){
			
			delPS.setInt( 	1, id);
		
			if ( delPS.executeUpdate() == 0 ) {
				throw new NotDeletedException("Book not deleted for id: " + id);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Delete Book was not successful.",e);
		}
	}
}
