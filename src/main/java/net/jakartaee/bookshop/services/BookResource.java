package net.jakartaee.bookshop.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.jakartaee.bookshop.data.BookDAO;
import net.jakartaee.bookshop.data.ReferenceDAO;
import net.jakartaee.bookshop.data.SubjectDAO;
import net.jakartaee.bookshop.data.TagDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Book;
import net.jakartaee.bookshop.model.Book.SALE_STATUS;
import net.jakartaee.bookshop.model.Tag;


@Path("book")
public class BookResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
    	ReferenceDAO rdao = new ReferenceDAO();
 		try {
			List<Book> books = new BookDAO().getAllBooks();
			for ( Book book : books ) {
				book.setReferences( rdao.getBookReferences(book.getId()) );
			}
	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @GET
    @Path("{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById( @PathParam("bookId") Integer id) {
		try {
			Book book = new BookDAO().getBookById(id);
			if ( book.getSubjectId() != null ) book.setSubject(new SubjectDAO().getSubjectById(book.getSubjectId()));
			book.setReferences(new ReferenceDAO().getBookReferences(book.getId()) );
			book.setTags( new TagDAO().getBookTags( book.getId()) );
			System.out.println("Got book on SHELF: " + ( book.getStatus().equals(SALE_STATUS.SOLD)));
	        return Response.ok(book, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   
    
//    @GET
//    @Path("tag/{tagKey}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBookById( @PathParam("tagKey") String tagKey) {
//		try {
//			List<Book> books = new BookDAO().getBooksByTags(tagKey);
//	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
//		} catch (NotFoundException e) {
//			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
    	TagDAO tdao = new TagDAO();
    	try {
			int bookId = new BookDAO().insertBook(book);
			book.setId(bookId);
			for ( Tag t: book.getTags() ) {				
				tdao.insertBookTag(book.getId(), t.getId() );
			}			
			return Response.ok(book, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
    	TagDAO tdao = new TagDAO();
    	try {
			new BookDAO().updateBook(book);
			tdao.deleteBookTags(book.getId());
			for ( Tag t: book.getTags() ) {				
				tdao.insertBookTag(book.getId(), t.getId() );
			}
			return Response.ok(book, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
    
    @DELETE
    @Path("{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBookById( @PathParam("bookId") Integer id) {
		try {
			new BookDAO().deleteBook(id);
	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   
    
}
