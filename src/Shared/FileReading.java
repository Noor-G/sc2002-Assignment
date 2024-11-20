package Shared;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Administrator.Administrator;
import Doctor.Doctor;
import Patient.Patient;
import Pharmacist.Pharmacist;

/**
 * FileReading:handles serialization and deserialization of hospital data, such
 * as staff, patients, appointments.
 * 
 * @author Cheng Jun Long Brandon
 */
public class FileReading {
	/**
	 * Initialize FileReading
	 */
	public FileReading() {
	}

	/**
	 * getStaffList: returns listOfStaff from ListOfStaff.ser
	 * 
	 * @return list of staff
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Staff> getStaffList() throws IOException, ClassNotFoundException {
		ArrayList<Staff> listOfStaff = new ArrayList<>();
		listOfStaff = deserializing("ListOfStaff.ser");
		return listOfStaff;
	}

	/**
	 * getDocList: returns a list of doctors from listOfStaff
	 * 
	 * @return list of doctors
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Doctor> getDocList() throws IOException, ClassNotFoundException {
		ArrayList<Doctor> listOfDoctor = new ArrayList<>();
		ArrayList<Staff> listOfStaff = new ArrayList<>();
		listOfStaff = getStaffList();

		for (Staff staff : listOfStaff) {
			if (staff instanceof Doctor) {
				listOfDoctor.add((Doctor) staff); // originally a doctor class
			}
		}

		return listOfDoctor;
	}

	/**
	 * getPharList: returns a list of Pharmacists from listOfStaff
	 * 
	 * @return list of pharmacists
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Pharmacist> getPharList() throws IOException, ClassNotFoundException {
		ArrayList<Pharmacist> listOfPhar = new ArrayList<>();
		ArrayList<Staff> listOfStaff = new ArrayList<>();
		listOfStaff = getStaffList();

		for (Staff staff : listOfStaff) {
			if (staff instanceof Pharmacist) {
				listOfPhar.add((Pharmacist) staff); // originally a phar class
			}
		}

		return listOfPhar;
	}

	/**
	 * getAdminList: returns a list of Administrators from listOfStaff
	 * 
	 * @return list of Administrators
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Administrator> getAdminList() throws IOException, ClassNotFoundException {
		ArrayList<Administrator> listOfAdmin = new ArrayList<>();
		ArrayList<Staff> listOfStaff = new ArrayList<>();
		listOfStaff = getStaffList();

		for (Staff staff : listOfStaff) {
			if (staff instanceof Administrator) {
				listOfAdmin.add((Administrator) staff); // originally a admin class
			}
		}

		return listOfAdmin;
	}

	/**
	 * getPatientList: returns a list of Patients from listOfStaff
	 * 
	 * @return list of Patients
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Patient> getPatientList() throws IOException, ClassNotFoundException {
		ArrayList<Patient> listOfPatient = new ArrayList<>();
		listOfPatient = deserializing("ListOfPatient.ser");

		return listOfPatient;
	}

	/**
	 * getAppointmentList: returns a list of appointments from ListOfAppointment.ser
	 * 
	 * @return list of appointments
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static ArrayList<Appointment> getAppointmentList() throws IOException, ClassNotFoundException {
		ArrayList<Appointment> listOfAppointment = new ArrayList<>();
		listOfAppointment = deserializing("ListOfAppointment.ser");

		return listOfAppointment;
	}

	/**
	 * Serializing: Serializes an array of object and creates the file based on fileName
	 * 
	 * @param <T>      type of the object to be serialized
	 * @param list     list of objects to serialize
	 * @param fileName file to save the serialized object
	 * @throws IOException when there is an error while reading or writing files.
	 */
	public static <T> void serializing(ArrayList<T> list, String fileName) throws IOException {
		FileOutputStream fileout = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(fileout);
		out.writeObject(list);
		out.close();
		fileout.close();
	}

	/**
	 * Deserializing: reads a serializer file and returns the list of Objects
	 * 
	 * @param <T>      type of the object to be serialized
	 * @param fileName file to save the serialized object
	 * @return list of Objects
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static <T> ArrayList<T> deserializing(String fileName) throws IOException, ClassNotFoundException {
		ArrayList<T> placement = null;
		FileInputStream filein = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(filein);

		placement = (ArrayList<T>) in.readObject(); // casting error that wont matter, but can find ways to make cleaner

		in.close();
		filein.close();
		return placement;
	}

	/**
	 * getNumber: returns formatted hospitalID and appointmentID
	 * 
	 * @param ID ID to be format
	 * @return formatted ID
	 */
	public static String getNumber(int ID) {

		String adding = "0";
		String newID = String.valueOf(ID + 1);
		while (newID.length() < 3) {
			newID = adding.concat(newID);
		}
		return newID;
	}

}
