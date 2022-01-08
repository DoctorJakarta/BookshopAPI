package net.jakartaee.bookshop.services.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import net.jakartaee.bookshop.data.TagDAO;
import net.jakartaee.bookshop.data.ReferenceDAO;
import net.jakartaee.bookshop.exceptions.DatabaseException;
import net.jakartaee.bookshop.exceptions.NotDeletedException;
import net.jakartaee.bookshop.exceptions.NotFoundException;
import net.jakartaee.bookshop.model.Tag;


@Path("tag")
public class TagResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags() {
    	// ReferenceDAO rdao = new ReferenceDAO();
 		try {
			List<Tag> tagList = new TagDAO().getAllTags();
			//Map<String, Tag> tagMap = tagList.stream().collect(Collectors.toMap(Tag::getKey, item -> item));
			//Map<String, String> tagMap = tagList.stream().collect(Collectors.toMap(Tag::getKey, tag -> tag.getName()));
	        return Response.ok(tagList, MediaType.APPLICATION_JSON).build();
	        //return Response.ok(tagMap, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @GET
    @Path("{tagKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTagById( @PathParam("tagKey") Integer id) {
		try {
			Tag tag = new TagDAO().getTagById(id);
	        return Response.ok(tag, MediaType.APPLICATION_JSON).build();
		} catch (NotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTag(Tag tag) {
    	try {
			new TagDAO().insertTag(tag);
			// get new tagKey
			//return Response.ok(tag, MediaType.APPLICATION_JSON).build();
			return Response.ok(null, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTag(Tag tag) {
    	try {
			new TagDAO().updateTag(tag);
			return Response.ok(tag, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }
    
    @DELETE
    @Path("{tagId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTagById( @PathParam("tagId") Integer id) {
		try {
			new TagDAO().deleteTag(id);
	        return Response.ok(null, MediaType.APPLICATION_JSON).build();
		} catch (DatabaseException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(e.getErrorResponse()).build();
		}
    }   
    
}
