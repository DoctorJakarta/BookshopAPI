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


import net.jakartaee.bookshop.data.SourceDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Source;



@Path("source")
public class SourceResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSources() {
 		try {
			List<Source> sources = new SourceDAO().getSources();
	        return Response.ok(sources, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
//
//    @GET
//    @Path("{sourceId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getTagById( @PathParam("sourceId") Long id) {				// Book has sourceID as Long to handle SQL  NULL
//		try {
//			Source source = new SourceDAO().getSourceById(id);
//	        return Response.ok(source, MediaType.APPLICATION_JSON).build();
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
//    public Response addTag(Source source) {
//    	try {
//			new SourceDAO().insertSource(source);;
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
//    public Response updateTag(Source source) {
//    	try {
//			new SourceDAO().updateSource(source);
//			return Response.ok(source, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }
//    
//    @DELETE
//    @Path("{sourceId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteTagById( @PathParam("sourceId") Integer id) {
//		try {
//			new SourceDAO().deleteSource(id);
//	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
//    
}
