package net.jakartaee.bookshop.auth;

import static net.jakartaee.bookshop.auth.JwtHandler.AUTHZ_HEADER;
import static net.jakartaee.bookshop.auth.JwtHandler.USERNAME;
import static net.jakartaee.bookshop.auth.JwtHandler.jstStart;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import net.jakartaee.bookshop.exceptions.AuthzException;


//
// By default ALL JaxRS requests are filtered
// Selective filtering can be done with NAME or DYNAMIC binding (https://stackoverflow.com/questions/23641345/jersey-request-filter-only-on-certain-uri)
// But it is simpler for single purpose use to add custom code IN the filter, as shown below
// This places assumptions on the URL PATH syntax for the application 
//

@Provider
//@PreMatching		// This is necessary because instantiation depends on stuff that must be set up in the request filter
					// ApplicationResources adds JwtHandler to the ServletContext
					// https://stackoverflow.com/questions/21024688/request-filter-not-working-in-jersey-2-on-embedded-jetty

public class AuthzFilter implements ContainerRequestFilter {
	
    @Context
    private HttpServletRequest req; 		// Necessary to set Request Scope Attr
   
    @Context
    private ServletContext servletContext; 		// Necessary to set Request Scope Attr
 
	// This is the INCOMING Request Filter
	@Override
	public void filter(ContainerRequestContext creq) throws WebApplicationException {
		//System.out.println("Filtering: " + creq);
		
		boolean isLoginLogout = creq.getUriInfo().getPath().contains("login");
		if (isLoginLogout) return; // No further authZ is necessary if login

			// "inventory" is the only public page
		boolean isPublic = creq.getUriInfo().getPath().contains("inventory");
		//System.out.println("Got Public: " + isPublic);

		if (isPublic) return; 														// No further authZ is necessary unless path includes authz/

		try {
			JwtHandler jwth = new JwtHandler();
			String bearer = getAuthzBearer(creq, jwth);

			if (bearer == null) { 						// Angular sends in "null" for bearer after it has been deleted
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
			String uid = jwth.getClaimString(bearer, USERNAME);

		} catch (AuthzException e) {
			creq.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated.").build());
		} catch (IOException e) {
			e.printStackTrace();
			creq.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("User not authenticated.").build());
		}

	}


    
    private String getAuthzBearer(ContainerRequestContext creq, JwtHandler jwth) {
		String authHeader = creq.getHeaderString(AUTHZ_HEADER);
		if (authHeader == null ) return null;
		String bearer = authHeader.substring(jstStart);
	   	//System.out.println("Got bearer: " + bearer );
		//System.out.println("In getAuthzBearer with NULL ("+ ("null".equals(bearer))+") authHeader?: " + bearer);
		if ("null".equals(bearer)) return null;						// Angular may send "null" AuthZ Bearer 
		return bearer;
    }

}