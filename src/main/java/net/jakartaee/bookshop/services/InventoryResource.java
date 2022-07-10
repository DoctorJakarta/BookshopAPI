package net.jakartaee.bookshop.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.jakartaee.bookshop.data.BookDAO;
import net.jakartaee.bookshop.data.PlateDAO;
import net.jakartaee.bookshop.data.ReferenceDAO;
import net.jakartaee.bookshop.data.SubjectDAO;
import net.jakartaee.bookshop.data.TagDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Book;
import net.jakartaee.bookshop.model.BookAdmin;
import net.jakartaee.bookshop.model.Plate;
import net.jakartaee.bookshop.model.Book.SALE_STATUS;

@Path("inventory")
public class InventoryResource {
    @GET
    @Path("book")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
    	//ReferenceDAO rdao = new ReferenceDAO();
    	TagDAO tdao = new TagDAO();
 		try {
			List<Book> books = new BookDAO().getInventoryBooks();
			for ( Book book : books ) {
				book.setTags( tdao.getBookTags( book.getId()) );
			}
	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("plate")
    public Response getPlates() {
  		try {
			List<Plate> plates = new PlateDAO().getInventoryPlates();
	        return Response.ok(plates, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
//    @GET
//    @Path("{queryField}/{queryValue}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBookBySearch( @PathParam("queryField") String queryField,  @PathParam("queryValue") String queryValue) {
//		try {
//			List<BookAdmin> books = new BookDAO().getInventoryBooksByQueryField(queryField, queryValue);
//	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
//		} catch (NotFoundException e) {
//			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   

    @GET
    @Path("{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById( @PathParam("bookId") Integer id) {
		try {
			Book book = new BookDAO().getInventoryBookById(id);
			if ( book.getSubjectId() != null ) book.setSubject(new SubjectDAO().getSubjectById(book.getSubjectId()));
			//book.setReferences(new ReferenceDAO().getBookReferences(book.getId()) );
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
}
