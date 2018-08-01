package com.tutorialpoint.rest.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tutorialpoint.Patient;
import com.tutorialpoint.User;

public class RestApiClient {
	
	public static void main(String[] args) {
	Client client = ClientBuilder.newClient();
	String baseURI = "http://localhost:9090/UserManagment/rest/PatientService";
	getRequest(client,baseURI);
	Patient patient = new Patient();
	patient.setId(23);
	patient.setName("P1");
	postRequest(client,patient,baseURI);
	
	
	
	
	}

	private static void postRequest(Client client,Patient patient,String baseURI) {
		
		Patient createdPatient = client.target(baseURI)
				.path("/patients").request()
				.post(Entity.entity(patient, MediaType.APPLICATION_JSON),Patient.class);
	
		System.out.println("created Patient :"+createdPatient.getName());
		
				
		
	}

	private static void getRequest(Client client,String baseURI) {
		Response response = client.target(baseURI)
				.path("/patients").path("/{id}")
				.resolveTemplate("id", 123).request(MediaType.APPLICATION_JSON)
				.get();
		Patient patient = response.readEntity(Patient.class); //unwrap the wrapper object - User is model class 
		System.out.println("Name of patient response :"+patient.getName());
		//System.out.println("response :"+user.getName());
		System.out.println("status "+response.getStatus());
		
		String res = client.target("http://localhost:9090/UserManagment/rest/UserService/users/2")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		//User user = response.readEntity(User.class); //unwrap the wrapper object - User is model class 
		System.out.println("User response :"+res);
		
	}

}
