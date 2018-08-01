package com.tutorialpoint;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter{
	
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);
		System.out.println("authHeader "+authHeader);
		if(authHeader != null && authHeader.size()>0) {
			String authToken = authHeader.get(0);
			System.out.println("authToken: "+authToken);
			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
			System.out.println("authToken after replaceFirst "+authToken);
			String decodedString = Base64.decodeAsString(authToken);
			System.out.println("decoedString "+decodedString);
			StringTokenizer tokenizer = new StringTokenizer(decodedString,":");
			String username = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			
			if("user".equals(username) && "password".equals(password)) {
				return;
			}
			
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED)
											.entity("User canot access the resources.")
											.build();
			requestContext.abortWith(unauthorizedStatus); // it will abort from filter method with Response which is contructed above this line.
			
		}
		
	}

}
