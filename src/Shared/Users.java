package Shared;

import static Shared.SanityCheck.nullCheck;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Patient.Patient;

/**
 * Users represents a generic user in the system. It provides attributes such as
 * ID, name, email, and methods for managing user data. Specific user types such
 * as Patients and Staff will extend this class to implement additional
 * functionalities.
 * This class is abstract as it cannot be instantiated directly, but must be
 * subclassed by other classes that represent specific types of users.
 * 
 * @author Cheng Jun Long Brandon
 * @author Nadathur Ammal Shreya Sudharshan
 */
public abstract class Users implements Serializable {
	/**
	 * ID of the user.
	 */
	private String ID;
	/**
	 * Name of the user.
	 */
	private String name;
	/**
	 * Password of the user.
	 */
	private String password;
	/**
	 * Email of the user.
	 */
	private String email;
	/**
	 * phone number of the user.
	 */
	private int number;
	/**
	 * Gender of the user.
	 */
	private String gender;
	/**
	 * Date of birth of the user.
	 */
	private Calendar dateOfBirth;
	/**
	 * Age of the user.
	 */
	private int age;

	/**
	 * User constructor
	 * 
	 * @param ID          ID of user
	 * @param name        Name of user
	 * @param password    password of user
	 * @param email       email of user
	 * @param number      phone number of user
	 * @param gender      gender of user
	 * @param dateOfBirth date of birth of the user
	 */
	public Users(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth) {
		this.ID = ID;
		this.name = name;
		this.password = password;
		this.email = email;
		this.number = number;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;

		// age generator
		Calendar today = Calendar.getInstance();
		this.age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR) - 1;
		if (today.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH) > 0) {
			this.age++;
		}
		else if (today.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH) == 0
				&& today.get(Calendar.DATE) - dateOfBirth.get(Calendar.DAY_OF_MONTH) >= 0) {
			this.age++;
		}
	}

	/**
	 * getID: get ID of the user
	 * 
	 * @return user ID
	 */
	public String getID() {
		return ID;
	}

	/**
	 * getName: get name of the user
	 * 
	 * @return user Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * getPassword: get Password of the user
	 * 
	 * @return user Password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * getEmail: get Email of the user
	 * 
	 * @return user Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * getNumber: get contact number of the user
	 * 
	 * @return user Number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * getGender: get Gender of the user
	 * 
	 * @return user Gender
	 */
	public String getGender() {
		return gender; // can be boolean for male or female, or undefined cus we live in 21st century
		// (i hope no one here is offended oops)
	}

	/**
	 * getDateOfBirth: get date of birth of the user
	 * 
	 * @return user date of birth
	 */
	public Calendar getDateOfBirth() { // can be int as well, in form of DDMMYYYY
		return dateOfBirth;
	}

	/**
	 * Str_getDateofBirth: get formatted date of birth of the user
	 * 
	 * @return user formatted date of birth
	 */
	public String Str_getDateofBirth() {
		Date date = getDateOfBirth().getTime();
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}

	/**
	 * getAge: get Age of the user
	 * 
	 * @return user Age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * setNumber: sets contact number of user
	 * 
	 * @param number contact number of the user
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * setEmail: sets contact email of user
	 * 
	 * @param email email of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * setPassword: sets password of user
	 *
	 * @param pas password of the user
	 */



	protected void setPassword(String pas) {
		this.password = pas;
	}

	/**
	 * changePassword: method of changing the password of a user
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public void changePassword() throws ClassNotFoundException, IOException {
		while (true) {

			System.out.println("Please enter old password:");
			String oldPas = nullCheck();
			System.out.println("Please enter new password:");
			String newPas = nullCheck();
			if (oldPas.equals("-1") && newPas.equals("-1")) {
				return;
			} else if (!oldPas.equals(password)) {
				System.out
						.println("Wrong password. (Press -1 for both old and new password to cancel password change)");
				continue;
			} else {
				password = newPas;
				System.out.println("Password changed successfully");
				if (this.ID.contains("PA")) { // if ID contains PA, means user is a patient
					ArrayList<Patient> listofPatient = FileReading.getPatientList();
					for (Patient pa : listofPatient) {
						if (pa.getID().equalsIgnoreCase(this.ID))
							pa.setPassword(newPas);
					}
					FileReading.serializing(listofPatient, "ListOfPatient.ser");
				} else {
					ArrayList<Staff> listOfStaff = new ArrayList<Staff>();
					listOfStaff = FileReading.getStaffList();
					for (Staff st : listOfStaff) {
						if (st.getID().equalsIgnoreCase(this.ID))
							st.setPassword(newPas);
					}
					FileReading.serializing(listOfStaff, "ListofStaff.ser");
				}
				return;
			}
		}
	}

	/**
	 * Abstract method to get the role of the user
	 * 
	 * @return the role of the user as a String
	 */
	public abstract String getRole();


	protected abstract void viewAppointment() throws ClassNotFoundException, IOException;

	/**
	 * Returns a string representation of the user's details.
	 * 
	 * @return a string containing the user's details
	 */

	@Override
	public String toString() { // password is not printed for privacy
		return "ID is " + getID() + ", Name = " + getName() + ", " + "Email is " + getEmail() + ", number = "
				+ getNumber() + ", Date of Birth = " + Str_getDateofBirth() + ", age is " + getAge() + ", gender = "
				+ getGender();
	}

}
