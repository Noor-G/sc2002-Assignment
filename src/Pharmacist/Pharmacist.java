package Pharmacist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import Shared.Appointment;
import Shared.FileReading;
import Shared.Staff;

/**
 * Pharmacist class are for Pharmacist in the HMS system. Pharmacist class
 * extends the Staff class and inherits properties from the Staff
 * class(password,email,phone number,gender,date of birth and age)
 * 
 * @author Maestro
 */
public class Pharmacist extends Staff {

	/**
	 * Pharmacist constructor
	 * 
	 * @param ID          ID of pharmacist
	 * @param name        Name of the pharmacist
	 * @param password    Password of the pharmacist
	 * @param email       Email address of the pharmacist
	 * @param number      Phone number of the pharmacist
	 * @param gender      Gender of the pharmacist
	 * @param dateOfBirth Date of birth of the pharmacist
	 */
	public Pharmacist(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth) {
		super(ID, name, password, email, number, gender, dateOfBirth);
		this.role = "Pharmacist";
	}

	/**
	 * viewAppointment: get appointment list from ListOfAppointment.ser
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void viewAppointment() throws IOException, ClassNotFoundException { // only outcome
		ArrayList<Appointment> Appointmentlist = FileReading.getAppointmentList();
		for (Appointment appointment : Appointmentlist) {
			System.out.println(appointment.getAppointmentOutcome());
		}
	}
}
