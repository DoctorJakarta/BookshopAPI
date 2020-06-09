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


import net.jakartaee.bookshop.data.AttributeDAO;
import net.jakartaee.bookshop.data.DetailDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Attribute;
import net.jakartaee.bookshop.model.Detail;



@Path("attribute")
public class AttributeResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttributes() {
 		try {
 			System.out.println("Getting Attributes");
			List<Attribute> attributes = new AttributeDAO().getAttributeNames();
			DetailDAO ddao = new DetailDAO();
			for ( Attribute attr : attributes) {
	 			System.out.println("Getting Details");
				List<Detail> details = ddao.getDetailsByAttributeId(attr.getId());
	 			System.out.println("Got Details: " + details);				
				attr.setDetails(details);				
			}
	        return Response.ok(attributes, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @GET
    @Path("{attributeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTagById( @PathParam("attributeId") Integer id) {
//		try {
			//Attribute attribute = new AttributeDAO().getAttributeById(id);
			Attribute attribute = new Attribute();
	        return Response.ok(attribute, MediaType.APPLICATION_JSON).build();
//		} catch (NotFoundException e) {
//			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
    }   
//
//    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addTag(Attribute attribute) {
//    	try {
//			new AttributeDAO().insertAttribute(attribute);;
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
//    public Response updateTag(Attribute attribute) {
//    	try {
//			new AttributeDAO().updateAttribute(attribute);
//			return Response.ok(attribute, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }
//    
//    @DELETE
//    @Path("{attributeId}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteTagById( @PathParam("attributeId") Integer id) {
//		try {
//			new AttributeDAO().deleteAttribute(id);
//	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
//		}
//    }   
    
}
