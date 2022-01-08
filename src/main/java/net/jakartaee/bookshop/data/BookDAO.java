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
import net.jakartaee.bookshop.model.Book.SALE_STATUS;
import net.jakartaee.bookshop.model.BookAdmin;


public class BookDAO extends SQLiteDAO{
	private static final String SQL_GET_ALL_BOOKS = "SELECT * FROM book ORDER BY year";
	private static final String SQL_GET_BOOKS_BY_FIELD = "SELECT * FROM book WHERE ";
	
	//SELECT * FROM book JOIN xref_book_tag USING (bookId) JOIN tag USING (tagId) WHERE tag.name = "Science";
			
	//private static final String SQL_GET_BOOKS = "SELECT * FROM book JOIN xref_book_tag USING (bookId) JOIN tag USING (tagKey) ";
	private static final String SQL_GET_BOOKS_BY_TAG = "SELECT * FROM book JOIN xref_book_tag USING (bookId) JOIN tag USING (tagId) WHERE tag.name=?" ;

	//private static final String SQL_GET_BOOK_BY_ID = "SELECT * FROM book LEFT JOIN subject USING (subjectId) WHERE bookId=?";
	private static final String SQL_GET_BOOK_BY_ID = "SELECT * FROM book WHERE bookId=?";
	private static final String SQL_INSERT_BOOK = "INSERT INTO book"  + Book.SQL_INSERT_FIELDS + Book.SQL_INSERT_VALUES;
	private static final String SQL_UPDATE_BOOK = "UPDATE book SET "  + Book.SQL_UPDATE_FIELDS + " WHERE bookId=?";
	private static final String SQL_DELETE_BOOK = "DELETE FROM book WHERE bookId=?";


	

	public List<Book> getInventoryBooks() throws DatabaseException{
		List<Book> books = new ArrayList<>();
		String sql = SQL_GET_BOOKS_BY_FIELD + "status=?";
		sql += " ORDER BY year";		
		return getInventoryBooksByQuery(sql, Book.SALE_STATUS.LIST.toString(), false);
	}
	
	public Book getInventoryBookById(Integer id) throws NotFoundException, DatabaseException{
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
	
	
	public List<BookAdmin> getAllBooks() throws DatabaseException{
		List<BookAdmin> books = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_ALL_BOOKS);){
		
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				books.add(new BookAdmin(rs));
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

	public List<BookAdmin> getBooksByQueryField(String field, String value) throws DatabaseException{
		// TODO: support other fields
		boolean isLike = false;
		String sql = null;
		if 		( "year".equals(field))   sql = SQL_GET_BOOKS_BY_FIELD + "year=?";
		else if ( "status".equals(field)) sql = SQL_GET_BOOKS_BY_FIELD + "status=?";
		else if ( "tag".equals(field))  sql = SQL_GET_BOOKS_BY_TAG;
		else if ( "author".equals(field)) { 
			sql = SQL_GET_BOOKS_BY_FIELD + "author LIKE ?";
			isLike = true;
		}
		else {
			throw new DatabaseException("Query not supported for field: " + field);
		}
		sql += " ORDER BY year";
		return getAdminBooksByQuery(sql, value, isLike);
	}

	private List<BookAdmin> getAdminBooksByQuery(String sql, String value, boolean isLike) throws DatabaseException{
		List<BookAdmin> books = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(sql);){
		
			if ( isLike ) getPS.setString( 	1, "%" + value + "%");
			else 		  getPS.setString( 	1, value);
			
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				books.add(new BookAdmin(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBooks was not successful.",e);
		}
		return books;
	}
	private List<Book> getInventoryBooksByQuery(String sql, String value, boolean isLike) throws DatabaseException{
		List<Book> books = new ArrayList<>();
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(sql);){
		
			if ( isLike ) getPS.setString( 	1, "%" + value + "%");
			else 		  getPS.setString( 	1, value);
			
			ResultSet rs = getPS.executeQuery();
			while (rs.next()) {
				books.add(new Book(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBooks was not successful.",e);
		}
		return books;
	}
	
	public BookAdmin getBookAdminById(Integer id) throws NotFoundException, DatabaseException{
		BookAdmin book = null;
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement getPS = conn.prepareStatement(SQL_GET_BOOK_BY_ID);){
			getPS.setInt( 	1, id);
		
			ResultSet rs = getPS.executeQuery();
			if (!rs.next()) {
				throw new NotFoundException("Book not found for id: " + id);
			}
			else {
				book = new BookAdmin(rs);		
			}
		} catch (SQLException e) {
			throw new DatabaseException("getBookById was not successful.",e);
		}
		return book;
	}
	

	public int insertBook(BookAdmin b) throws DatabaseException{
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
			insertPS.setString( 9, b.getVolume());
			insertPS.setString( 10, b.getSize());
			insertPS.setString( 11, b.getPages());
//			if ( b.getPages() != null ) 		insertPS.setLong( 9, b.getPages()); 
//			else 								insertPS.setNull( 9, java.sql.Types.INTEGER);
			
			// Atributes
			insertPS.setString( 12, b.getBinding());
			insertPS.setString( 13, b.getCondition());			
			insertPS.setString( 14, b.getDetails());
			insertPS.setString( 15, b.getContents());			
			insertPS.setString( 16, b.getNotes());
			insertPS.setString( 17, b.getRarity());
			insertPS.setString( 18, b.getReprints());
			
			if ( b.getPriceBought() != null ) 	insertPS.setLong( 19, b.getPriceBought()); 
			else 								insertPS.setNull( 19, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		insertPS.setLong( 20, b.getPriceMin()); 
			else 								insertPS.setNull( 20, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		insertPS.setLong( 21, b.getPriceMax()); 
			else 								insertPS.setNull( 21, java.sql.Types.INTEGER);
			if ( b.getPriceList() != null ) 	insertPS.setLong( 22, b.getPriceList()); 
			else 								insertPS.setNull( 22, java.sql.Types.INTEGER);

			insertPS.setInt(	23, b.getSalePercent());

			insertPS.setString( 24, b.getDateBought());
			insertPS.setString( 25, b.getDateSold());
			insertPS.setString(	26, b.getUrlRelative());
			insertPS.setString(	27, b.getStatus());
			
			int numRows = insertPS.executeUpdate();
			int newId = getNewId(conn);
			return newId;

		} catch (SQLException e) {
			throw new DatabaseException("Insert Book was not successful.",e);
		}
	}

	public void bulkUpdateBookField(String field, String status, List<Integer> bookIds) throws DatabaseException{
		switch(field) {				// Prevent SQLi by only allowing known field names
			case "status":
			case "salePercent":
				bulkUpdateBooks(field, status, bookIds);
				break;		
		}
	}
	
	private void bulkUpdateBooks(String field, String status, List<Integer> bookIds) throws DatabaseException{
		final String idSQL = bookIds.toString().replace("[","(").replace("]",")");				// UPDATE book SET status= 'HOLD' WHERE bookId in (67, 62)
		final String sql = "UPDATE book SET " + field + "= ? WHERE bookId in " + idSQL;
		System.out.println("Running bulk update SQL: " + sql);
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement updatePS = conn.prepareStatement(sql);){
			
			for ( Integer id: bookIds ) {
				updatePS.setString( 1, status);
				int success = updatePS.executeUpdate();				
			}

		} catch (SQLException e) {
			throw new DatabaseException("Bulk Update Book Status was not successful.",e);
		}
	}
	
	public void updateBook(BookAdmin b) throws DatabaseException{
		try(
				Connection conn = SQLiteDatabase.getConnection();
				PreparedStatement updatePS = conn.prepareStatement(SQL_UPDATE_BOOK);){
		
			//if ( b.getSubjectId() != null ) insertPS.setLong( 1, b.getSubjectId()); 
			//else 							insertPS.setNull( 1, java.sql.Types.INTEGER);
			if ( b.getSubject() != null ) updatePS.setLong( 1, b.getSubject().getId()); 
			else 						  updatePS.setNull( 1, java.sql.Types.INTEGER);
			updatePS.setString( 2, b.getTitle());
			updatePS.setString( 3, b.getAuthor());
			updatePS.setString( 4, b.getPublisher());
			updatePS.setString( 5, b.getPublisherPlace());

			updatePS.setInt( 	6, b.getYear());
			updatePS.setString( 7, b.getEdition());
			updatePS.setString( 8, b.getPrinting());
			updatePS.setString( 9, b.getVolume());
			updatePS.setString( 10, b.getSize());
			updatePS.setString( 11, b.getPages());
//			if ( b.getPages() != null ) 		insertPS.setLong( 9, b.getPages()); 
//			else 								insertPS.setNull( 9, java.sql.Types.INTEGER);
			updatePS.setString( 12, b.getBinding());
			updatePS.setString( 13, b.getCondition());
			
			updatePS.setString( 14, b.getDetails());
			updatePS.setString( 15, b.getContents());			
			updatePS.setString( 16, b.getNotes());
			
			updatePS.setString( 17, b.getRarity());
			updatePS.setString( 18, b.getReprints());
			
			if ( b.getPriceBought() != null ) 	updatePS.setLong( 19, b.getPriceBought()); 
			else 								updatePS.setNull( 19, java.sql.Types.INTEGER);
			if ( b.getPriceMin() != null ) 		updatePS.setLong( 20, b.getPriceMin()); 
			else 								updatePS.setNull( 20, java.sql.Types.INTEGER);
			if ( b.getPriceMax() != null ) 		updatePS.setLong( 21, b.getPriceMax()); 
			else 								updatePS.setNull( 21, java.sql.Types.INTEGER);
			if ( b.getPriceList() != null ) 	updatePS.setLong( 22, b.getPriceList()); 
			else 								updatePS.setNull( 22, java.sql.Types.INTEGER);

			updatePS.setInt(	23, b.getSalePercent());

			updatePS.setString( 24, b.getDateBought());
			updatePS.setString( 25, b.getDateSold());
			updatePS.setString(	26, b.getUrlRelative());
			updatePS.setString(	27, b.getStatus());
			
			updatePS.setInt( 	28, b.getId());
			int success = updatePS.executeUpdate();

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
