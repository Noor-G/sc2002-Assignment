package Doctor;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;

import java.io.IOException;
import java.util.ArrayList;

import Patient.Patient;
import Shared.Appointment;
import Shared.DiagnosisAndTreatment;
import Shared.FileReading;
import menus.Main;

/**
 * docLogin class is the core for managing patients and appointments within the
 * HMS system. It has methods for viewing patient records, updating patient
 * records, viewing schedule, setting availability for appointments, accepting
 * or denying appointment requests, viewing upcoming appointments, and recording
 * appointment outcome.
 * 
 * @author Cheng Jun Long Brandon
 * @author Nadathur Ammal Shreya Sudharshan
 */
public class DocLogin {

	/**
	 * Constructor for the docLogin class.
	 */
	private DocLogin() {
	}

	/**
	 * doctorMenu: displays menu for doctors. it allows them to select various
	 * options to manage patient records, appointments, and personal details.
	 * 
	 * @param doctor1 menu to be displayed and managed.
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static void doctorMenu(Doctor doctor1) throws IOException, ClassNotFoundException {
		int choice;
		do {
			System.out.println("------------------------------------------------");
			System.out.println("1. View Patient Records");
			System.out.println("2. Update Patient Records");
			System.out.println("3. View Personal Schedule");
			System.out.println("4. Set Availability for Appointments");
			System.out.println("5. Accept or Decline Appointment Requests");
			System.out.println("6. View Upcoming Appointments");
			System.out.println("7. Record Appointment Outcome");
			System.out.println("8. Change Password");
			System.out.println("9. Logout");
			System.out.println("Enter your choice: ");

			choice = numCheck();
			System.out.println();

			switch (choice) {
			case 1:
				viewPatient(doctor1.getID());
				break;
			case 2:
				updatePatient();
				break;
			case 3:
				doctor1.viewAppointment();
				break;
			case 4:
				doctor1.newAppointment();
				break;
			case 5:

				doctor1.acceptAppointment();
				break;
			case 6:
				doctor1.viewAcceptedAppointment();
				break;
			case 7:
				doctor1.viewAcceptedAppointment();
				System.out.println("Enter appointment ID to update");
				String appointmentID = nullCheck();
				updateRecord(appointmentID);
				break;
			case 8:
				doctor1.changePassword();
				break;
			case 9:
				System.out.println("You have logged out.");
				Main.login();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 9);
	}

	/**
	 * viewPatient: for viewing patients under care
	 * 
	 * @param DoctorID The ID of the doctor
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void viewPatient(String DoctorID) throws IOException, ClassNotFoundException {
		ArrayList<Patient> listOfPatient = FileReading.getPatientList();

		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		boolean empty = true;

		for (Appointment appointment : appointmentList) {
			if (appointment.getDoctorID().equalsIgnoreCase(DoctorID)) {
				for (Patient patient : listOfPatient) {
					if (appointment.getPatientID().equalsIgnoreCase(patient.getID())) {
						patient.viewMedicalRecord();
						empty = false;
					}
				}
			}
		}
		if (empty)
			System.out.println("There are currently no patients recorded.");
	}

	/**
	 * updatePatient: update patients' details
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void updatePatient() throws IOException, ClassNotFoundException {
		// for loop to search, and edit patient
		ArrayList<Patient> listOfPatient = new ArrayList<>();
		listOfPatient = FileReading.getPatientList();

		System.out.println("Enter patient ID for updating");
		String patientID = nullCheck();
		for (Patient patient : listOfPatient) {
			if (patient.getID().equalsIgnoreCase(patientID)) {
				patient.viewMedicalRecord();
				System.out.println("Enter new diagnosis");
				String diagnosis = nullCheck();
				System.out.println("Enter new treatment"); // serialise it back
				String treatment = nullCheck();

				DiagnosisAndTreatment diag = new DiagnosisAndTreatment(diagnosis, treatment);
				patient.addPastDiagnosisAndTreatment(diag);
				System.out.println("Added: " + diag);
				patient.viewMedicalRecord();
				FileReading.serializing(listOfPatient, "ListofPatient.ser");
				return;
			}
		}
		System.out.println("Patient with ID " + patientID + " not found.");

	}

	/**
	 * updateRecord: update the appointment outcome after appointment
	 * 
	 * @param appointmentID The ID of the appointment being updated
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void updateRecord(String appointmentID) throws IOException, ClassNotFoundException {
		ArrayList<Appointment> list = FileReading.deserializing("ListOfAppointment.ser");

		for (Appointment appointment : list) {
			if (appointment.getAppointmentID().equals(appointmentID)) {
				// user input

				// make new diag class
				System.out.println("Enter diagnosis provided: ");
				String diagnosis = nullCheck();
				System.out.println("Enter services provided: ");
				String services = nullCheck();
				DiagnosisAndTreatment diagAndTreatment = new DiagnosisAndTreatment(diagnosis, services);

				// make new apptmentoutcome class
				System.out.println("Enter medicine prescribed (Enter NIL if there is no medicine)"); // make it
				String med = nullCheck();
				System.out.println("Enter status of medicine (Nil, pending or dispensed)");
				String medStatus = nullCheck();
				System.out.println("Enter consultation notes");
				String consultation = nullCheck();
				appointment.doctorUpdate(diagAndTreatment, med, medStatus, consultation);

				// set appointment to completed
				appointment.setAppointmentStatus("Completed");
				System.out.println(
						"The following is the updated appointment: \n" + appointment + "\n-------------------");
				FileReading.serializing(list, "ListOfAppointment.ser");

				// update the patient arraylist
				ArrayList<Patient> listOfPatient = FileReading.deserializing("ListofPatient.ser");
				for (Patient patient : listOfPatient) {
					if (patient.getID().equalsIgnoreCase(appointment.getPatientID())) {
						patient.addAppointmentOutcome(appointment.getAppointmentOutcome());
						patient.addPastDiagnosisAndTreatment(diagAndTreatment);
					}
				}
				FileReading.serializing(listOfPatient, "ListofPatient.ser");
				return;
			}
		}
		System.out.println("There are no appointments with ID: " + appointmentID);
	}
}
