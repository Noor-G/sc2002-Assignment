package Patient;

import static Shared.SanityCheck.nullCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import Shared.*;
import Shared.FileReading;

/**
 * The Patient class represents a patient with their personal details.
 * 
 * @author Grover EKhnoor Kaur
 * @author Cheng Jun Long Brandon
 */
public class Patient extends Users {
	/**
	 * Blood type of the patient.
	 */
	private String bloodType;
	/** List of past appointment outcomes for the patient. */
	private String role = "Patient";
	/**
	 * List of past diagnosis and treatments received by the patient.
	 */
	private ArrayList<AppointmentOutcome> pastAppointmentOutcomeList = new ArrayList<>();
	/**
	 * List of past diagnosis and treatments received by the patient.
	 */
	private ArrayList<DiagnosisAndTreatment> pastDiagnosisAndTreatment = new ArrayList<>();

	// Lists to store past appointments and treatments

	/**
	 * Patient constructor
	 * 
	 * @param ID          ID of the patient
	 * @param name        name of the patient
	 * @param password    password of the patient
	 * @param email       email of the patient
	 * @param number      contact number of the patient
	 * @param gender      gender of the patient
	 * @param dateOfBirth Date of birth of the patient
	 * @param bloodType   Blood type of the patient
	 */
	public Patient(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth, String bloodType) {
		super(ID, name, password, email, number, gender, dateOfBirth);
		this.bloodType = bloodType;
	}

	/**
	 * getBloodType: returns bloodType of patient
	 * 
	 * @return bloodType of patient
	 */
	public String getBloodType() {
		return bloodType;
	}

	/**
	 * getRole: returns the string "Patient"
	 * 
	 * @return role of the patient
	 */
	public String getRole() {
		return role;
	}

	/**
	 * updateContactInfo: updates the contact info of the Patient
	 * 
	 * @param newContactNumber new contact number of the patient
	 * @param newEmail         new email address of the patient
	 */
	protected void updateContactInfo(int newContactNumber, String newEmail) {
		setNumber(newContactNumber);
		setEmail(newEmail);
		System.out.println("Contact information updated successfully.");
	}

	/**
	 * getPastAppointmentOutcomeList: returns list of past appointments
	 * 
	 * @return list of past Appointments
	 */
	public ArrayList<AppointmentOutcome> getPastAppointmentOutcomeList() {
		return pastAppointmentOutcomeList;
	}

	/**
	 * addAppointmentOutcome: adds outcome to appointment
	 * 
	 * @param outcome outcome of the appointment
	 */
	public void addAppointmentOutcome(AppointmentOutcome outcome) {
		pastAppointmentOutcomeList.add(outcome);
	}

	// add treatment diagnosis

	/**
	 * getPastDiagnosisAndTreatment: returns history of diagnosis and treatments
	 * 
	 * @return list of past diagnosis and treatments
	 */
	public ArrayList<DiagnosisAndTreatment> getPastDiagnosisAndTreatment() {
		return pastDiagnosisAndTreatment;
	}

	/**
	 * addPastDiagnosisAndTreatment: adds diagnosis and treatment to Patient's
	 * history
	 * 
	 * @param diagnosisAndTreatment diagnosis and treatment to be added
	 */
	public void addPastDiagnosisAndTreatment(DiagnosisAndTreatment diagnosisAndTreatment) {
		pastDiagnosisAndTreatment.add(diagnosisAndTreatment);
	}

	@Override
	/**
	 * viewAppointment: prints out available appointments for booking
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void viewAppointment() throws IOException, ClassNotFoundException {
		String patientID = getID();
		ArrayList<Appointment> Appointmentlist = new ArrayList<>();
		Appointmentlist = FileReading.getAppointmentList();
		boolean empty = true;
		System.out.println("Available Appointments:");
		for (Appointment appointment : Appointmentlist) {
			if (appointment.getAppointmentStatus().equalsIgnoreCase("Available")) {
				empty = false;
				System.out.println(appointment.patientView());
			}
		}
		if (empty)
			System.out.println("There is no available apppointment at the moment.");
	}

	/**
	 * booking: book an available appointment
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void booking() throws IOException, ClassNotFoundException {
		String patientID = getID();
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		Boolean contain = false;

		// avail appointment
		viewAppointment();
		System.out.println("Please enter appointment ID to be booked");
		String bookingID = nullCheck();

		for (Appointment appointment : appointmentList) {
			if (appointment.getAppointmentID().equalsIgnoreCase(bookingID)) {
				appointment.setPatientID(patientID);
				appointment.setAppointmentStatus("Pending");
				System.out.println("You have successfully booked appointment " + appointment.getAppointmentID()
						+ "\nThe date of appointment will be on " + appointment.str_GetDateofAppointment()
						+ "----------------------------------------------------------");
				FileReading.serializing(appointmentList, "ListOfAppointment.ser");
				contain = true;
			}
		}
		if (!contain) {
			System.out.println("You have entered an invalid appointment ID.");
		}

	}

	/**
	 * viewScheduledAppointments: view booked appointment
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void viewScheduledAppointments() throws IOException, ClassNotFoundException {
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		String patientID = getID();
		System.out.println("Scheduled Appointments:");
		for (Appointment appointment : appointmentList) {
			if (appointment.getPatientID().equalsIgnoreCase(patientID)
					&& !appointment.getAppointmentStatus().equalsIgnoreCase("Available")) {
				System.out.println(appointment.patientView());
			}
		}
	}

	/**
	 * viewMedicalRecord: prints Patient's medical record
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void viewMedicalRecord() throws IOException, ClassNotFoundException {

		// Find the patient by ID
		System.out.println("\n--- Medical Record for Patient ID: " + getID() + " ---");
		System.out.println("Patient ID: " + getID());
		System.out.println("Name: " + getName());
		System.out.println("Date of Birth: " + Str_getDateofBirth());
		System.out.println("Gender: " + getGender());
		System.out.println("Contact Number: " + getNumber());
		System.out.println("Email: " + getEmail());
		System.out.println("Blood Type: " + getBloodType());
		System.out.println("Past diagnosis and treatment are as follow:");
		for (DiagnosisAndTreatment diag : pastDiagnosisAndTreatment) {
			System.out.println(diag);
		}
		System.out.println("--------------------------------------------");
	}

	/**
	 * viewPastApptmentOutcome: prints patient's past appointments
	 */
	public void viewPastApptmentOutcome() {
		boolean empty = true;
		for (AppointmentOutcome outcome : pastAppointmentOutcomeList) {
			System.out.println("Date: " + outcome.Str_getDateofAppointment() + "\n" + outcome); // make a patientview
																								// under outcome class
			empty = false;
		}
		if (empty)
			System.out.println("You have no past appointments recorded in the hospital.");
	}

	/**
	 * rescheduleAppointment: reschedule booked appointment
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void rescheduleAppointment() throws IOException, ClassNotFoundException {
		try {
			String patientID = getID();
			System.out.println("Would you like to rebook an appointment? (y/n): ");
			String response = nullCheck().trim().toLowerCase();

			if (response.equalsIgnoreCase("y")) {
				// If the user confirms, proceed with re-booking
				cancelAppointment();
				booking();
				System.out.println("Appointment rescheduled successfully for patient ID: " + patientID);
			} else if (response.equalsIgnoreCase("n")) {
				System.out.println("Rebooking canceled. Appointment was not rescheduled.");
			} else {
				System.out.println("Invalid input! Rebooking canceled. Appointment was not rescheduled.");
			}

		} catch (Exception e) {
			System.err.println("An unexpected error occurred while rescheduling the appointment. ");
			e.printStackTrace();
		}

	}

	/**
	 * cancelAppointment: cancel booked appointment
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void cancelAppointment() throws IOException, ClassNotFoundException {
		ArrayList<Appointment> list = FileReading.getAppointmentList();
		String patientID = getID();

		System.out.println("Scheduled Appointments:");
		boolean appointmentFound = false;

		// Display scheduled appointments
		for (Appointment appointment : list) {
			if (appointment.getPatientID().equalsIgnoreCase(patientID)
					&& !appointment.getAppointmentStatus().equalsIgnoreCase("Available")) {
				System.out.println(appointment);
				appointmentFound = true;
			}
		}

		if (!appointmentFound) {
			System.out.println("No scheduled appointments to cancel.");
			return;
		}

		boolean appointmentCancelled = false;
		int attempts = 0;

		// Allow up to 3 attempts to cancel an appointment
		while (!appointmentCancelled && attempts < 3) {
			System.out.print("Enter the appointment ID of the appointment you would like to cancel: ");
			String appointmentID = nullCheck();

			for (Appointment appointment : list) {
				if (appointment.getAppointmentID().equalsIgnoreCase(appointmentID)
						&& appointment.getPatientID().equalsIgnoreCase(patientID)) {

					// Update appointment status and reset patient ID
					appointment.setAppointmentStatus("Available");
					appointment.setPatientID("NIL");
					System.out.println(
							"Appointment " + appointment.getAppointmentID() + " has been successfully canceled.");

					// Serialize updated list back to the file
					FileReading.serializing(list, "ListOfAppointment.ser");
					appointmentCancelled = true;
					break;
				}
			}

			if (!appointmentCancelled) {
				System.out.println("Invalid appointment ID or the appointment does not belong to you.");
				attempts++;

				if (attempts < 3) {
					System.out.println("Please try again. Attempts remaining: " + (3 - attempts));
				} else {
					System.out.println("Maximum attempts reached. Returning to main menu.");
				}
			}
		}
	}

	@Override
	public String toString() {
		return super.toString() + ", role is " + getRole() + ", blood type is " + getBloodType();
	}

}
