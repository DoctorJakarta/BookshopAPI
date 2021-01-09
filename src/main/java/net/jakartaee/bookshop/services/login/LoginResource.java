package net.jakartaee.bookshop.services.login;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.jakartaee.bookshop.auth.JwtHandler;
import net.jakartaee.bookshop.auth.PasswordHandler;
import net.jakartaee.bookshop.data.UserDAO;
import net.jakartaee.bookshop.model.ErrorResponse;
import net.jakartaee.bookshop.model.User;
import net.jakartaee.bookshop.model.UserDB;



@Path("/auth/")
public class LoginResource {

	@Context
    private ServletContext servletContext; 		// Necessary to set Request Scope Attr
	
    
	@POST
	@Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDB user) {

     	try {
    		JwtHandler jwth = new JwtHandler();
     		UserDB dbUser = new UserDAO().getUserByUsername(user.getUsername());
     		PasswordHandler pwh = new PasswordHandler(dbUser.getPwdsalt());			// Create PasswordHandler with the saved (dbUser)  SALT
     		
     		//pwh.checkPassword(dbUser.getPwdhash(), user.getPassword());
     		
     	   	String jwtAccess = jwth.getInitialAccessToken(dbUser.getUsername(), dbUser.getRole());
			User returnUser = new User(dbUser, jwtAccess);
			return Response.ok(returnUser, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse("Access denied.", e.getMessage(), 409);
			return Response.status(Response.Status.CONFLICT).type(MediaType.APPLICATION_JSON).entity(er).build();
		}

    }


}
