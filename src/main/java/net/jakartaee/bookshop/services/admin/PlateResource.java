package net.jakartaee.bookshop.services.admin;

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

import net.jakartaee.bookshop.data.PlateDAO;
import net.jakartaee.bookshop.data.SubjectDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Book.SALE_STATUS;
import net.jakartaee.bookshop.model.Plate;


@Path("plate")
public class PlateResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlates() {
  		try {
			List<Plate> plates = new PlateDAO().getAllPlates();
	        return Response.ok(plates, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @GET
    @Path("{plateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById( @PathParam("plateId") Integer id) {
		try {
			Plate plate = new PlateDAO().getPlateById(id);
			if ( plate.getSubjectId() != null ) plate.setSubject(new SubjectDAO().getSubjectById(plate.getSubjectId()));
			System.out.println("Got plate on SHELF: " + ( plate.getStatus().equals(SALE_STATUS.SOLD)));
	        return Response.ok(plate, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   
//    
//    @GET
//    @Path("{queryField}/{queryValue}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getBookBySearch( @PathParam("queryField") String queryField,  @PathParam("queryValue") String queryValue) {
//		try {
//			List<BookAdmin> books = new BookDAO().getBooksByQueryField(queryField, queryValue);
//	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
//		} catch (NotFoundException e) {
//			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
//   
//    @GET
//    @Path("sale")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getSaleBooks() {
//    	// ReferenceDAO rdao = new ReferenceDAO();
//    	TagDAO tdao = new TagDAO();
// 		try {
//			List<BookAdmin> books = new BookDAO().getSaleBooks();
//			for ( BookAdmin book : books ) {
//				//book.setReferences( rdao.getBookReferences(book.getId()) );
//				book.setTags( tdao.getBookTags( book.getId()) );
//			}
//	        return Response.ok(books, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlate(Plate plate) {
     	try {
			int plateId = new PlateDAO().insertPlate(plate);
			return Response.ok(plate, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(Plate plate) {
    	try {
			new PlateDAO().updatePlate(plate);
			return Response.ok(plate, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
 
    @PUT
    @Path("{updateField}/{updateValue}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bulkUpdateBooks(@PathParam("updateField") String field, @PathParam("updateValue") String value, List<Integer> plateIds) {
   	try {
    	System.out.println("Bulk Updating ("+field+") field for plateIds: " + plateIds);
    		new PlateDAO().bulkUpdatePlateField(field, value, plateIds);
			return Response.ok(null, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
    
    @DELETE
    @Path("{plateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBookById( @PathParam("plateId") Integer id) {
		try {
			new PlateDAO().deletePlate(id);
	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   
    
}
