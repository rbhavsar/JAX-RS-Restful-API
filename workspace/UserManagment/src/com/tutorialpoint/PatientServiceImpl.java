package com.tutorialpoint;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
@Path("/PatientService")
public class PatientServiceImpl implements PatientService {
	private long currentId = 123;
	Map<Long, Patient> patients = new HashMap<Long, Patient>();
	Map<Long, Prescription> prescriptions = new HashMap<Long, Prescription>();

	public PatientServiceImpl() {
		init();
	}

	final void init() {
		Patient patient = new Patient();
		patient.setName("John");
		patient.setId(currentId);
		patients.put(patient.getId(), patient);

		Prescription prescription = new Prescription();
		prescription.setDescription("prescription 223");
		prescription.setId(223);
		prescriptions.put(prescription.getId(), prescription);
	}

	public Response addPatient(Patient patient) {
		System.out.println("...invoking addPatient, Patient Name is... " + patient.getName());
		patient.setId(++currentId);
		patients.put(patient.getId(), patient);
		return Response.ok(patient).build();
	}

	public Patient getPatient(String id) {
		System.out.println("...invoking getPatient, Patient Id is... " + id);
		Long patientId = Long.parseLong(id);
		Patient patient = patients.get(patientId);
		if(patient == null)
		{
			System.out.println("Check :"+Response.Status.NOT_FOUND);
			throw new WebApplicationException(Response.Status.NOT_FOUND);
			//throw new SomeBusinessException("User Not Found ");
		}
		return patient;
	}

	public Response updatePatient(Patient updatedPatient) {
		System.out.println("...invoking updatePatient, Patient Name is... " + updatedPatient.getName());

		Patient currentPatient = patients.get(updatedPatient.getId());

		Response response = null;
		if (currentPatient != null) {
			patients.put(updatedPatient.getId(), updatedPatient);
			response = Response.ok().build();
		} else {
			//response = Response.notModified().build();
			throw new NotFoundException();
		}

		return response;
	}

	public Response deletePatients(String id) {
		System.out.println("...invoking deletePatients, Patient Id is... " + id);
		Long patientId = Long.parseLong(id);
		Patient patient = patients.get(patientId);
		Response response = null;
		if (patient != null) {
			patients.remove(patientId);
			response = Response.ok().build();
		} else {
			//response = Response.notModified().build();
			throw new SomeBusinessException("Businesss Exception Message");
		}

		return response;
	}

	public Prescription getPrescription(String prescriptionId) {
		long id = Long.parseLong(prescriptionId);
		Prescription prescription = prescriptions.get(id);
		return prescription;
	}

}
