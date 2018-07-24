package com.tutorialpoint;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/UserService")
public class UserService {
	
   UserDao userDao = new UserDao();
   private static final String SUCCESS_RESULT="<result>success</result>";
   private static final String FAILURE_RESULT="<result>failure</result>";


   /*
    * http://localhost:9090/UserManagment/rest/UserService/users
    * Response :
    * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	<users>
    <user>
        <id>1</id>
        <name>Mahesh</name>
        <profession>Teacher</profession>
    </user>
    <user>
        <id>2</id>
        <name>ravi1</name>
        <profession>sci1</profession>
    </user>
	</users>
    */
   
   @GET
   @Path("/users")
   @Produces(MediaType.APPLICATION_XML)
   public List<User> getUsers(){
      return userDao.getAllUsers();
   }
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/users/2
    * Response:
    * <user>
    <id>2</id>
    <name>ravi1</name>
    <profession>sci1</profession>
	</user>
    */

   @GET
   @Path("/users/{userid}")
   @Produces(MediaType.APPLICATION_XML)
   public User getUser(@PathParam("userid") int userid){
	   
	  User user = userDao.getUser(userid);
	  if(user==null) {
		  throw new NotFoundException();
	  }
      return userDao.getUser(userid);
   }
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/users
    * In Postman ->
    * Body - > x-www-form-urlencoded screen
    * Configure below key value pair
    * id - 3
    * name - Shah
    * profession - Teacher
    * Click on Send
    * Response :<result>success</result>
    */

   @POST
   @Path("/users")
   @Produces(MediaType.APPLICATION_XML)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String createUser(@FormParam("id") int id,
      @FormParam("name") String name,
      @FormParam("profession") String profession,
      @Context HttpServletResponse servletResponse) throws IOException{
      User user = new User(id, name, profession);
      int result = userDao.addUser(user);
      if(result == 1){
         return SUCCESS_RESULT;
      }
      return FAILURE_RESULT;
   }
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/users
    * In Postman ->
    * Body - > x-www-form-urlencoded screen
    * Configure below key value pair
    * id - 3
    * name - Sharma
    * profession - Developer
    * Click on Send
    * Response :<result>success</result>
    * 
    */

   @PUT
   @Path("/users")
   @Produces(MediaType.APPLICATION_XML)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String updateUser(@FormParam("id") int id,
      @FormParam("name") String name,
      @FormParam("profession") String profession,
      @Context HttpServletResponse servletResponse) throws IOException{
      User user = new User(id, name, profession);
      int result = userDao.updateUser(user);
      if(result == 1){
         return SUCCESS_RESULT;
      }
      return FAILURE_RESULT;
   }
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/users/3
    * <result>success</result>
    */

   @DELETE
   @Path("/users/{userid}")
   @Produces(MediaType.APPLICATION_XML)
   public String deleteUser(@PathParam("userid") int userid){
      int result = userDao.deleteUser(userid);
      if(result == 1){
         return SUCCESS_RESULT;
      }
      else {
    	  return FAILURE_RESULT;
      }
   }

   @OPTIONS
   @Path("/users")
   @Produces(MediaType.APPLICATION_XML)
   public String getSupportedOperations(){
      return "<operations>GET, PUT, POST, DELETE</operations>";
   }
}