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
import net.jakartaee.bookshop.model.Tag;


public class BookDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_BOOKS = "SELECT * FROM book ";
	//private static final String SQL_GET_BOOKS = "SELECT * FROM book JOIN xref_tag_book USING (bookId) JOIN tag USING (tagKey) ";
	private static final String SQL_GET_BOOKS_BY_TAG = "SELECT * FROM book JOIN xref_tag_book USING (bookId) WHERE tagKey=?" ;

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
	
	//public List<Book> getBooksByTags(List<Tag> tags) throws DatabaseException{
	public List<Book> getBooksByTags(String tagKey) throws DatabaseException{
		List<Book> books = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOKS_BY_TAG);){
		
			getPS.setString( 	1, tagKey);
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBooks was not successful.",e);
		}
		return books;
	}
	
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
		
			insertPS.setString( 1, b.getAuthor());
			insertPS.setString( 2, b.getTitle());
			insertPS.setInt( 	3, b.getYear());
			insertPS.setString( 4, b.getDesc());
			insertPS.setString( 5, b.getComment());
			if ( b.getPrice() != null ) 		insertPS.setLong( 6, b.getPrice()); 
			else 								insertPS.setNull( 6, java.sql.Types.INTEGER);
			if ( b.getPriceBought() != null ) 	insertPS.setLong( 7, b.getPriceBought()); 
			else 								insertPS.setNull( 7, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		insertPS.setLong( 8, b.getPriceMin()); 
			else 								insertPS.setNull( 8, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		insertPS.setLong( 9, b.getPriceMax()); 
			else 								insertPS.setNull( 9, java.sql.Types.INTEGER);

			insertPS.setString( 10, b.getDateBought());
			insertPS.setString( 11, b.getDateSold());
			insertPS.setBoolean(12, b.isSold());
			
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
		
			insertPS.setString( 1, b.getAuthor());
			insertPS.setString( 2, b.getTitle());
			insertPS.setInt( 	3, b.getYear());
			insertPS.setString( 4, b.getDesc());
			insertPS.setString( 5, b.getComment());
			if ( b.getPrice() != null ) 		insertPS.setLong( 6, b.getPrice()); 
			else 								insertPS.setNull( 6, java.sql.Types.INTEGER);
			if ( b.getPriceBought() != null ) 	insertPS.setLong( 7, b.getPriceBought()); 
			else 								insertPS.setNull( 7, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		insertPS.setLong( 8, b.getPriceMin()); 
			else 								insertPS.setNull( 8, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		insertPS.setLong( 9, b.getPriceMax()); 
			else 								insertPS.setNull( 9, java.sql.Types.INTEGER);

			insertPS.setString( 10, b.getDateBought());
			insertPS.setString( 11, b.getDateSold());
			insertPS.setBoolean(12, b.isSold());
			
			insertPS.setInt( 	13, b.getId());
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
