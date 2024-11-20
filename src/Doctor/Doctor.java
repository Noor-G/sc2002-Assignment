package Doctor;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Shared.Appointment;
import Shared.AppointmentOutcome;
import Shared.FileReading;
import Shared.Staff;

/**
 * Doctor entity class represents doctors within the HMS system. It extends from
 * the user class and inherits common attributes and behaviors from all users as
 * has additional attributes specific to doctors. This class provides methods to
 * retrieve,edit the role and view doctor details.
 * 
 * @author Cheng Jun Long Brandon
 * 
 */
public class Doctor extends Staff {

	/**
	 * Doctor constructor
	 * 
	 * @param ID          ID of doctor
	 * @param name        Name of doctor
	 * @param password    password of doctor
	 * @param email       email address of doctor
	 * @param number      phone number of doctor
	 * @param gender      gender of doctor
	 * @param dateOfBirth date of birth of the doctor
	 */

	public Doctor(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth) {
		super(ID, name, password, email, number, gender, dateOfBirth);
		this.role = "Doctor";
	}

	/**
	 * viewAppointment: Prints out all appointments with matching DoctorID from the
	 * ListOfAppointment.ser
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void viewAppointment() throws IOException, ClassNotFoundException { // view all appointments, including
																					// confirmed
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		boolean empty = true;
		String doctorID = getID();
		for (Appointment appointment : appointmentList) {
			if (appointment.getDoctorID().equalsIgnoreCase(doctorID)) {
				empty = false;
				System.out.println(appointment.patientView());
			}
		}
		if (empty)
			System.out.println("There are currently no appointments");

	}

	/**
	 * viewAppointment: Prints out all appointments that has been accepted by the
	 * Doctor from the ListOfAppointment.ser
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void viewAcceptedAppointment() throws IOException, ClassNotFoundException { // view all appointments,
																							// including confirmed
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		String DoctorID = getID();
		boolean empty = true;
		for (Appointment appointment : appointmentList) {
			if (appointment.getDoctorID().equalsIgnoreCase(DoctorID)) {
				if (appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
					empty = false;
					System.out.println(appointment.patientView());
				}
			}
		}
		if (empty)
			System.out.println("There are currently no accepted appointment.");
	}

	/**
	 * newAppointment: Creates a new available appointment and adds it to
	 * ListOfAppointment.ser");
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void newAppointment() throws IOException, ClassNotFoundException {
		// need user input for services, date of apptment,
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		System.out.println("Enter Appointment ID");
		String appointmentID = nullCheck();

		System.out.println("Enter year, month, day, hour, minute for appointment. \n"
				+ "For example, for 23 May 2024, at 1.30pm, year:2024, month: 5, day:23, hour: 13, minute: 30");

		System.out.println("Enter year for appointment: ");
		int year = numCheck();
		System.out.println("Enter month for appointment: ");
		int month = numCheck() - 1;
		System.out.println("Enter day for appointment: ");
		int day = numCheck();

		System.out.println("Enter hour for appointment: ");
		int hour = numCheck();

		System.out.println("Enter minute: ");
		int minute = numCheck();


		Calendar dateOfApptment = new GregorianCalendar(year, month, day, hour, minute);
		String doctorID = getID();
		String docName = getName();

		AppointmentOutcome appointmentOutcome = new AppointmentOutcome(dateOfApptment);
		Appointment appointment = new Appointment(appointmentID, doctorID, docName, dateOfApptment, appointmentOutcome);
		System.out.println("Doctor, this is the appointment which you have created.\n" + appointment.patientView());
		appointmentList.add(appointment);
		FileReading.serializing(appointmentList, "ListOfAppointment.ser");

	}

	/**
	 * acceptAppointment: accept or deny available appointments
	 * ListOfAppointment.ser");
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void acceptAppointment() throws IOException, ClassNotFoundException {
		String doctorID = getID();
		ArrayList<Appointment> list = new ArrayList<Appointment>();
		list = FileReading.deserializing("ListOfAppointment.ser");
		boolean request = false;
		for (Appointment appointment : list) {
			if (appointment.getDoctorID().equals(doctorID)
					&& appointment.getAppointmentStatus().equalsIgnoreCase("Pending")) {
				request = true;
				System.out.println(appointment);
				System.out.println(
						"Would you like to accept the appointment?\nIf yes: enter 1.\nIf no, press any other digits:");
				int choice = numCheck();
				if (choice == 1) {
					appointment.setAppointmentStatus("Confirmed");
					System.out.println("you have confirmed the appointment");
				} else {
					appointment.setAppointmentStatus("Available");
					appointment.setPatientID("NIL");
				}
				FileReading.serializing(list, "ListOfAppointment.ser");
			}
		}
		if (!request) {
			System.out.println("There are no appointment requests at this moment");
		}

	}
}