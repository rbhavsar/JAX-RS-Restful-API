package com.tutorialpoint.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.tutorialpoint.SomeBusinessException;

@Provider
public class SomeBusinessExceptionMapper implements ExceptionMapper<SomeBusinessException> {
	//public class BusinessExceptionMapper implements ExceptionMapper<SomeBusinessException> {

	/*@Override
	public Response toResponse(SomeBusinessException e) {
		//public Response toResponse(SomeBusinessException e) {
		StringBuilder response = new StringBuilder("<response>");
		response.append("<status>ERROR</status>");
		response.append("<message>"+e.getMessage()+"</message>");
		response.append("</response>");
		
		StringBuilder response = new StringBuilder("{\"status\": \"ERROR\"");
		response.append("message:"+e.getMessage()+"}");
		
		return Response.serverError().entity(response.toString()).type(MediaType.APPLICATION_JSON).build();
	}*/
	
	//For Reponse in Json Format
	public Response toResponse(SomeBusinessException ex) { 
        FaultInfo info = new FaultInfo(); 
        System.out.println("Message in ExceptionMapper :-"+ex.getMessage());
        info.setErrorMessage(ex.getMessage()); 
        info.setErrorCode(400);         
        return Response.status(400).entity(info).type(MediaType.APPLICATION_JSON).build(); 
    } 

}
