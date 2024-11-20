package Administrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import Shared.Appointment;
import Shared.Staff;
import Shared.FileReading;

/**
 * Administrator class are for users who have administrative privileges in the
 * HMS system. Administrator class extends the Staff class and inherits
 * properties from the Staff class(password,email,phone number,gender,date of
 * birth and age)
 * 
 * @author Maestro
 * @author Johnson Chow
 * @author Cheng Jun Long Brandon
 */

public class Administrator extends Staff {

	/**
	 * Administrator constructor
	 * 
	 * @param ID          ID of the user
	 * @param name        Full name of the user
	 * @param password    password of the user for account authentication
	 * @param email       email address of the user for contact and notifications
	 * @param number      Contact number of the user
	 * @param gender      Gender of the user
	 * @param dateOfBirth date of birth in the format "DDMMYYYY" or as a string of
	 *                    the user
	 */
	public Administrator(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth) {
		super(ID, name, password, email, number, gender, dateOfBirth);
		this.role = "Administrator";
	}

	/**
	 * viewAppointment: Prints out all appointments from the ListOfAppointment.ser
	 * 
	 * @throws ClassNotFoundException If the class of a serialized object cannot be
	 *                                found.
	 * @throws IOException            when there is an error while reading the file.
	 */
	@Override
	protected void viewAppointment() throws ClassNotFoundException, IOException {
		ArrayList<Appointment> listOfAppointment = new ArrayList<Appointment>();
		listOfAppointment = FileReading.deserializing("ListOfAppointment.ser");

		for (Appointment appointment : listOfAppointment) {
			System.out.println(appointment);
		}

	}

}