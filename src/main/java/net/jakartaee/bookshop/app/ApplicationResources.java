package net.jakartaee.bookshop.app;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ResourceConfig;

import net.jakartaee.bookshop.auth.JwtHandler;


@ApplicationPath("/api/v1.0/")
public class ApplicationResources extends ResourceConfig {

    public static final String JWT_HANDLER_ATTR = "JwtHandler";

	public ApplicationResources(@Context ServletContext servletContext) {
		System.out.println("Application init.");
		
		try {
			JwtHandler jwth = new JwtHandler();
			servletContext.setAttribute(JWT_HANDLER_ATTR, jwth);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	
}
