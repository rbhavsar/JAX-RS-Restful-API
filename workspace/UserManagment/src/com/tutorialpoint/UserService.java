package com.tutorialpoint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.message.internal.MediaTypes;
import org.glassfish.jersey.server.ContainerRequest;

import sun.misc.IOUtils;

@Path("/UserService")
public class UserService {
	
   UserDao userDao = new UserDao();
   private static final String SUCCESS_RESULT="{success}";
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
	
	JSON Response :
	
	[
    {
        "id": 1,
        "name": "Mahesh",
        "profession": "Teacher"
    },
    {
        "id": 3,
        "name": "bhavsar1",
        "profession": "maths1"
    }
	]
	
    */
   
   @GET
   @Path("/users")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getUsers(@Context UriInfo uriInfo) throws IOException{
	   List<User> list= userDao.getAllUsers();
	  URI path =  uriInfo.getAbsolutePathBuilder().path("").build();
	  System.out.println("path :"+path);
	 return Response.ok(path).entity(list).build();
	 //return userDao.getAllUsers();
   }
   
   
   //Get Insert user form
   @GET
   public void doGet(@Context HttpServletResponse servletResponse,@Context HttpServletRequest servletRequest) throws ServletException, IOException{
	  System.out.println("invoking get");
	   RequestDispatcher view= servletRequest.getRequestDispatcher("//WEB-INF//views//add_user.jsp");
	  view.forward(servletRequest, servletResponse); 
   }
   
   /*
    * GET
    * http://localhost:9090/UserManagment/rest/UserService/u?userid=1&profession=Teacher1
    * Response:
    * {
    "id": 1,
    "name": "Mahesh1",
    "profession": "Teacher1"
	}
    */
   @GET
   @Path("/u")
   @Produces(MediaType.APPLICATION_JSON)
   public User getUserInfo(@QueryParam("userid") int userid,@QueryParam("profession") String profession) {
	   System.out.println(userid+":"+profession);
	   User user = userDao.getUserBy(userid,profession);
		  if(user==null) {
			  //throw new NotFoundException();
			  throw new SomeBusinessException("user not found for given userId "+userid);
		  }
	      return user;
   }
   
   
   /*GET
    * http://localhost:9090/UserManagment/rest/UserService/us?start=1&size=2
    * Response:
    * [
    {
        "id": 2,
        "name": "Naresh",
        "profession": "Dev"
    },
    {
        "id": 9,
        "name": "Kumaran",
        "profession": "CEO"
    }
	]
    */
   
   @GET
   @Path("/us")
   @Produces(MediaType.APPLICATION_JSON)
   public List<User> getUsersDetails(@QueryParam("start") int start,
		   					   @QueryParam("size") int size) {
	   System.out.println("Inside Pagination!!!");
	   
		 if(start >= 0 && size>=0) {
			 System.out.println("in if condition start size..");
			 return userDao.getUsersPaginated(start, size);
		 } 
	      return userDao.getAllUsers();
   	}
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/annotations
    * Add Headers - customHeaderValue - Basic dXNlcjpwYXNzd29yZDE=
    * Print in console - headers : Basic dXNlcjpwYXNzd29yZDE=
    */
   
   @GET
   @Path("/annotations")
   @Produces(MediaType.APPLICATION_JSON)
   public List<User> getUsersDetails(@HeaderParam("customHeaderValue") String header,
		   							 @CookieParam("name") String cookie	) {
	   System.out.println("header :"+header);
	   System.out.println("cookie :"+cookie);
	   return null;
   }
   
   @GET
   @Path("/getUsersFile")
   @Produces("text/plain") // if you want pdf output then applications/pdf
   public Response getUsersFile() {
	   File file=new File("Users.txt");
	   ResponseBuilder response=Response.ok((Object)file); //Wrap the file object into ResponseBuilder Object and send response object to client
	   //Sometime you want to send extra information like status code 200 code 
	   response.header("Content-Disposition", "attachment;filename=UsersData.txt");//set headers - instruct to browser to show popup when received response from server
	   return response.build();
   }
   
   /*
    * add_user.jsp file contains code to select file and upload
    * Response:
    * File Uploaded Sucessfully!!
    * 
    * In case of postman
    * ==============================
    * POST : http://localhost:9090/UserManagment/rest/UserService/upload
    * Body - > form-data
    * file - choose file - User.txt
    * For more information : https://stackoverflow.com/questions/39037049/how-to-upload-a-file-and-json-data-in-postman
    * For upload and download check - https://www.youtube.com/watch?v=yr01OEk6FfM
    */
   
 
   @POST
   @Path("/upload")
   @Consumes(MediaType.MULTIPART_FORM_DATA) //This function - can accept uploaded file from client
   public String uploadFile(@FormDataParam("file") InputStream uploadInputStream,@FormDataParam("file") FormDataContentDisposition fileDetail) {
	   //InputStream holds the file object , fieDetail hold information about fileName.
	   saveToDisk(uploadInputStream,fileDetail);
	   return "File Uploaded Sucessfully!!";
   }
   
   private void saveToDisk(InputStream uploadInputStream, FormDataContentDisposition fileDetail) {
		//Save uploaded file location
	   	String uploadedFileLocation = "D:\\eclipse\\uploaded\\"+fileDetail.getFileName();
	   	System.out.println("fileLocation:"+uploadedFileLocation);
	   	//Standard code to save data into file using FileOutputStream
	   	try {
	   		OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
	   		int read=0;
	   		byte[] bytes = new byte[1024];
	   		out = new FileOutputStream(new File(uploadedFileLocation));
	   		while((read=uploadInputStream.read(bytes))!=-1) {
	   			out.write(bytes, 0, read);
	   		}
	   		out.flush();
	   		out.close();
	   	}catch(Exception e) {
	   		e.printStackTrace();
	   	}
		
   }
   
   /*
    * Basic Auth with rest api follow - https://www.youtube.com/watch?v=W5jm4E0TTlA
    * In this case created new class SecurityFilter implements ContainerRequestFilter and implement filter method
    * POSTMAN
    * http://localhost:9090/UserManagment/rest/UserService/message
    * Authetication - Enter Username : user and Password : password - click on update request and
    * check headers - It will shows Authorization:Basic dXNlcjpwYXNzd29yZDE=
    * It will decoded in filter method and get username and password and we match it there - Check SecurityFilter.java
    * 
    */
   
   @GET
   @Path("message")
   @Produces(MediaType.TEXT_PLAIN)
   public String securedMethod() {
 	  return "This API is secured";
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
   @Produces(value = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})// To support xml reponse - added into Produces and while using postman - in headers make sure to set Accept - application/xml
   public User getUser(@PathParam("userid") int userid){
	  System.out.println("Invoking getUser for userId :"+userid); 
	  User user = userDao.getUser(userid);
	  if(user==null) {
		  //throw new NotFoundException();
		  throw new SomeBusinessException("user not found for given userId "+userid);
	  }
      return user;
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
    * 
    * Response for failure :
    * {
    "errorCode": 400,
    "errorMessage": "User is already exist :3"
	}
	
	here in this case , set Content-Type - application/x-www-form-urlencoded
	This indicates this method will consume this Content-Type - it can be xml or json
    */

   /*
    * Client can pass two things in header to tell server - Accept(@Produce) , Content-Type (@Consume)
    */
   
   @POST
   @Path("/users")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public String createUser(@FormParam("id") int id,
      @FormParam("name") String name,
      @FormParam("profession") String profession,
      @Context HttpServletResponse servletResponse,@Context HttpServletRequest servletRequest) throws IOException, ServletException{
	  System.out.println("Invoking createUser ,id is "+id);
      User user = new User(id, name, profession);
      int result = userDao.addUser(user);
      if(result == 1){
         return SUCCESS_RESULT;
      }
      else {
    	  throw new SomeBusinessException("User is already exist for id :"+id);
      }
   }
   
   /*
    * Using Response Object
    * http://localhost:9090/UserManagment/rest/UserService/users
    * In Postman ->
    * Body - > x-www-form-urlencoded screen
    * Configure below key value pair
    * id - 3
    * name - Shah
    * profession - Teacher
    * Click on Send
    * Response :
    * {
    	"id": 10,
    	"name": "Sunil",
    	"profession": "Teacher"
	  }
	  
	  
    */
   @POST
   @Path("/cerateUsers")
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
   public Response createUsers(@FormParam("id") int id,
      @FormParam("name") String name,
      @FormParam("profession") String profession,
      @Context HttpServletResponse servletResponse,@Context HttpServletRequest servletRequest,@Context UriInfo uriInfo) throws IOException, ServletException{
	  System.out.println("Invoking createUser ,id is "+id);
      User user = new User(id, name, profession);
      int result = userDao.addUser(user);
      URI uri= uriInfo.getAbsolutePathBuilder().path("").build();
      if(result == 1){
    	 return Response.created(uri).entity(user).build();
         //return SUCCESS_RESULT;
      }
      else {
    	  throw new SomeBusinessException("User is already exist for id :"+id);
      }
   }
   
   /*
    * http://localhost:9090/UserManagment/rest/UserService/users
    * In Postman ->
    * Body - > raw
    {
    "id": 1,
    "name": "Mahesh1",
    "profession": "Teacher1"
	}
    * Click on Send
    * Response :<result>success</result>
    * 
    */

   @PUT
   @Path("/users")
   @Produces(MediaType.APPLICATION_XML)
   @Consumes(MediaType.APPLICATION_JSON)
   public String updateUser(User user) throws IOException{
      //User user = new User(id, name, profession);
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