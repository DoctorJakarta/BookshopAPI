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


import net.jakartaee.bookshop.data.SizeDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Size;



@Path("size")
public class SizeResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSizes() {
 		try {
			List<Size> sizes = new SizeDAO().getSizes();
	        return Response.ok(sizes, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
//
//    @GET
//    @Path("{sizeId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTagById( @PathParam("sizeId") Long id) {				// Book has sizeID as Long to handle SQL  NULL
//		try {
//			Size size = new SizeDAO().getSizeById(id);
//	        return Response.ok(size, MediaType.APPLICATION_JSON).build();
//		} catch (NotFoundException e) {
//			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
//
//    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addTag(Size size) {
//    	try {
//			new SizeDAO().insertSize(size);;
//			return Response.ok(null, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response updateTag(Size size) {
//    	try {
//			new SizeDAO().updateSize(size);
//			return Response.ok(size, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }
//    
//    @DELETE
//    @Path("{sizeId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteTagById( @PathParam("sizeId") Integer id) {
//		try {
//			new SizeDAO().deleteSize(id);
//	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
//    
}
