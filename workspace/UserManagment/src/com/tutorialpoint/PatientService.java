package com.tutorialpoint;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface PatientService {

	//http://localhost:9090/UserManagment/rest/PatientService/patients/123
	@GET
	@Path("/patients/{id}/")
	@Produces(MediaType.APPLICATION_JSON)
	Patient getPatient(@PathParam("id") String id);

	/*
	 * http://localhost:9090/UserManagment/rest/PatientService/patients
	 * body :
	 * 	<Patient>
    	<id>123</id>
    	<name>JohnPsd</name>
		</Patient>
		
		For, XML (application/xml)
	 */
	@PUT
	@Path("/patients/")
	@Consumes(MediaType.APPLICATION_JSON)
	Response updatePatient(Patient patient);

	/*
	 *  http://localhost:9090/UserManagment/rest/PatientService/patients
	 *  body :
	 * 	<Patient>
    	<name>Ravi</name>
		</Patient>
		For, XML (application/xml)
		
		Response:
		<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		<Patient>
    		<id>124</id>
    	<name>Ravi1</name>
		</Patient>
	 */
	
	@POST
	@Path("/patients/")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addPatient(Patient patient);
	
	/*
	 * http://localhost:9090/UserManagment/rest/PatientService/patients/123
	 */

	@DELETE
	@Path("/patients/{id}/")
	Response deletePatients(@PathParam("id") String id);

	
	//http://localhost:9090/UserManagment/rest/PatientService/prescriptions/223
	@GET
	@Path("/prescriptions/{id}")
	Prescription getPrescription(@PathParam("id") String prescriptionId);

}
