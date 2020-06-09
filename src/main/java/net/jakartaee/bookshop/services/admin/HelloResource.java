package net.jakartaee.bookshop.services.admin;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getText() {
        return Response.ok("Hello", MediaType.TEXT_HTML).build();
    }

    
}