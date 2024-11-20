package menus;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Administrator.AdminLogin;
import Administrator.Administrator;
import Doctor.Doctor;
import Doctor.DocLogin;
import Patient.Patient;
import Patient.PatientLogin;
import Pharmacist.PharLogin;
import Pharmacist.Pharmacist;
import Shared.*;
import Shared.FileReading;

/**
 * Main: main class of the HMS system. displays menu for the primary logging in
 * and registering new patients
 * 
 * @author Cheng Jun Long Brandon
 * @author Nadathur Ammal Shreya Sudharshan
 */
public class Main {
	/**
	 * Initializes main class
	 */
	public Main() {
	}

	/**
	 * main: the main method
	 * 
	 * @param args command line arguments
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		login();
	}

	/**
	 * login: displays login menu
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static void login() throws IOException, ClassNotFoundException {
		updateAppointment();
		int choice;
		ArrayList<Patient> listOfPatient = FileReading.getPatientList();
		ArrayList<Staff> listOfStaff = FileReading.getStaffList();
		do {
			System.out.println("\n------Login Menu------");
			System.out.println("1. Login as Patient");
			System.out.println("2. Register new account as patient");
			System.out.println("3. Login as Staff");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			choice = numCheck();

			switch (choice) {
			case 1:
				System.out.println("Enter ID");
				String ID = nullCheck();
				System.out.println("Enter Password");
				String pw = nullCheck();
				boolean login = false;
				listOfPatient = FileReading.getPatientList();
				for (Patient patient : listOfPatient) {
					if (patient.getID().equalsIgnoreCase(ID)) {
						if (patient.getPassword().equals(pw)) {
							PatientLogin.patientMenu(patient);
							login = true;
						}
					}
				}
				if (login) {
					choice = 4;
				} else {
					System.out.println("You have entered an invalid option. Please try again.");
				}
				break;

			case 2:

				PatientLogin.newPatient();
				break;

			case 3:
				System.out.println("Enter ID");
				ID = nullCheck();
				System.out.println("Enter Password");
				pw = nullCheck();
				login = false;
				listOfStaff = FileReading.getStaffList();
				for (Staff staff : listOfStaff) {
					if (staff.getID().equalsIgnoreCase(ID) && staff.getPassword().equals(pw)) {
						login = true;
						String role = staff.getRole();
						switch (role) {
						case "Doctor":
							DocLogin.doctorMenu((Doctor) staff);
							break;
						case "Pharmacist":
							PharLogin.displayMenu((Pharmacist) staff);
							break;
						case "Administrator":
							AdminLogin.displayMenu((Administrator) staff);

						}
					}
				}
				if (login) {
					choice = 4;
				} else {
					System.out.println("you have entered an invalid option. please try again.");
				}
				break;
			case 4:
				System.out.println("You have logged out.");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 4);
	}

	/**
	 * Test login for doctors.
	 * 
	 * @throws IOException            if an I/O error occurs while reading or
	 *                                writing files.
	 * @throws ClassNotFoundException if a class definition cannot be found during
	 *                                deserialization.
	 */
	private static void docTestLogin() throws IOException, ClassNotFoundException {
		ArrayList<Doctor> listOfDoc = FileReading.getDocList();
		Doctor doctor = listOfDoc.getFirst();
		DocLogin.doctorMenu(doctor); // make schedule for all apptment under name, wifout
	}

	/**
	 * Test login for patients.
	 * 
	 * @throws IOException            if an I/O error occurs while reading or
	 *                                writing files.
	 * @throws ClassNotFoundException if a class definition cannot be found during
	 *                                deserialization.
	 */
	private static void patientTestLogin() throws IOException, ClassNotFoundException {
		ArrayList<Patient> listOfPatient = FileReading.getPatientList();
		Patient patient = listOfPatient.getFirst();
		PatientLogin.patientMenu(patient);

	}

	/**
	 * Test login for administrators.
	 * 
	 * @throws IOException            if an I/O error occurs while reading or
	 *                                writing files.
	 * @throws ClassNotFoundException if a class definition cannot be found during
	 *                                deserialization.
	 */
	private static void adminTestLogin() throws IOException, ClassNotFoundException {
		ArrayList<Administrator> listOfAdmin = FileReading.getAdminList();
		Administrator admin = listOfAdmin.getFirst();
		AdminLogin.displayMenu(admin); //
	}

	/**
	 * Test login for pharmacists.
	 * 
	 * @throws IOException            if an I/O error occurs while reading or
	 *                                writing files.
	 * @throws ClassNotFoundException if a class definition cannot be found during
	 *                                deserialization.
	 */
	private static void pharTestLogin() throws IOException, ClassNotFoundException {
		ArrayList<Pharmacist> listOfPhar = FileReading.getPharList();
		Pharmacist phar = listOfPhar.getFirst();
		PharLogin.displayMenu(phar); //
	}

	/**
	 * renewall: renew appointment, patient, staff list in the
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void renewAll() throws IOException, ClassNotFoundException {
		renewStaffList();
		renewAppointment();
	}

	/**
	 * renewPatientlist: renews patient list
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	private static void renewPatientList() throws IOException { // temporary function, just to renew the list
		ArrayList<Patient> listOfPatient = new ArrayList<Patient>();
		Calendar bday1 = new GregorianCalendar(1980, 4, 13);
		Calendar bday2 = new GregorianCalendar(1975, 10, 21);
		Calendar bday3 = new GregorianCalendar(1990, 6, 7);

		Patient doctor1 = new Patient("PA001", "Alice Brown", "password1", "alice.brown@gmail.com", 91234567, "female",
				bday1, "A+");
		Patient doctor2 = new Patient("PA002", "Bob Stone", "password2", "bob.stone@gmail.com", 91235437, "male", bday2,
				"B+");
		Patient doctor3 = new Patient("PA003", "Charlie White", "password4", "charlie.white@gmail.com", 95454267,
				"male", bday3, "O-");

		listOfPatient.add(doctor1);
		listOfPatient.add(doctor2);
		listOfPatient.add(doctor3);

		for (int i = 0; i < listOfPatient.size(); i++) {
			System.out.println(listOfPatient.get(i));
		}
		FileReading.serializing(listOfPatient, "ListofPatient.ser");
	}

	/**
	 * renewAppointment: renews appointment list
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void renewAppointment() throws IOException, ClassNotFoundException {
		renewPatientList();
		Calendar dateOfApptment1 = new GregorianCalendar(2024, 9, 5, 12, 30);
		Calendar dateOfApptment2 = new GregorianCalendar(2024, 11, 21, 15, 30);
		Calendar dateOfApptment3 = new GregorianCalendar(2024, 11, 25, 29, 30);
		Calendar dateOfApptment4 = new GregorianCalendar(2024, 11, 20, 15, 30);
		Calendar dateOfApptment5 = new GregorianCalendar(2024, 11, 16, 15, 30);
		Calendar dateOfApptment6 = new GregorianCalendar(2024, 10, 17, 13, 30);
		Calendar dateOfApptment7 = new GregorianCalendar(2024, 10, 30, 13, 30);

		AppointmentOutcome AppointmentOutcome1 = new AppointmentOutcome(dateOfApptment1);
		AppointmentOutcome AppointmentOutcome2 = new AppointmentOutcome(dateOfApptment2);
		AppointmentOutcome AppointmentOutcome3 = new AppointmentOutcome(dateOfApptment3);
		AppointmentOutcome AppointmentOutcome4 = new AppointmentOutcome(dateOfApptment4);
		AppointmentOutcome AppointmentOutcome5 = new AppointmentOutcome(dateOfApptment5);
		AppointmentOutcome AppointmentOutcome6 = new AppointmentOutcome(dateOfApptment6);
		AppointmentOutcome AppointmentOutcome7 = new AppointmentOutcome(dateOfApptment7);

		Appointment appointment1 = new Appointment("A001", "D001", "John Smith", dateOfApptment1, AppointmentOutcome1);
		Appointment appointment2 = new Appointment("A002", "D002", "Emily Clark", dateOfApptment2, AppointmentOutcome2);
		Appointment appointment3 = new Appointment("A003", "D002", "Emily Clark", dateOfApptment3, AppointmentOutcome3);
		Appointment appointment4 = new Appointment("A004", "D001", "John Smith", dateOfApptment4, AppointmentOutcome4);
		Appointment appointment5 = new Appointment("A005", "D001", "John Smith", dateOfApptment5, AppointmentOutcome5);
		Appointment appointment6 = new Appointment("A006", "D001", "John Smith", dateOfApptment6, AppointmentOutcome6);
		Appointment appointment7 = new Appointment("A007", "D001", "John Smith", dateOfApptment7, AppointmentOutcome7);

		// making apptment4 pending
		appointment4.setPatientID("PA001");
		appointment4.setAppointmentStatus("Pending");

		// making apptment5 confirmed
		appointment5.setPatientID("PA001");
		appointment5.setAppointmentStatus("Confirmed");

		// make appt6 done
		appointment6.setPatientID("PA001");
		appointment6.setAppointmentStatus("Completed");
		DiagnosisAndTreatment diagandtreatment = new DiagnosisAndTreatment("sick with fever",
				"gave paracetemol, cooling pad");
		appointment6.doctorUpdate(diagandtreatment, "Paracetamol", "pending", "need rest");
		ArrayList<Patient> listOfPatient = FileReading.deserializing("ListofPatient.ser");
		for (Patient patient : listOfPatient) {
			if (patient.getID().equalsIgnoreCase(appointment6.getPatientID())) {
				patient.addAppointmentOutcome(appointment6.getAppointmentOutcome());
				patient.addPastDiagnosisAndTreatment(diagandtreatment);
			}
		}

		// make appointment 7
		appointment7.setPatientID("PA002");
		appointment7.setAppointmentStatus("Pending");
		FileReading.serializing(listOfPatient, "ListofPatient.ser");

		ArrayList<Appointment> listOfAppointment = new ArrayList<Appointment>();
		listOfAppointment.add(appointment1);
		listOfAppointment.add(appointment2);
		listOfAppointment.add(appointment3);
		listOfAppointment.add(appointment4); // make this one patient booked, pending
		listOfAppointment.add(appointment5); // make this one confirmed
		listOfAppointment.add(appointment6); // confirmed
		listOfAppointment.add(appointment7); // different patient

		FileReading.serializing(listOfAppointment, "ListOfAppointment.ser");

		for (Appointment appointment : listOfAppointment) {
			System.out.println(appointment);
		}
	}

	/**
	 * renewStaffList: renews staff list
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	private static void renewStaffList() throws IOException { // temporary function, just to renew the list
		ArrayList<Staff> listOfDoctor = new ArrayList<>();
		Calendar bday1 = new GregorianCalendar(1979, 11, 5); // 6 is July, to make more
		Calendar bday2 = new GregorianCalendar(1986, 11, 5); // 6 is July, to make more
		Calendar bday3 = new GregorianCalendar(1995, 11, 5); // 6 is July, to make more
		Calendar bday4 = new GregorianCalendar(1984, 11, 5); // 6 is July, to make more

		Doctor doctor1 = new Doctor("D001", "John Smith", "password1", "johnny@gmail.com", 91234567, "male", bday1);
		Doctor doctor2 = new Doctor("D002", "Emily Clark", "password2", "emily@gmail.com", 91234267, "female", bday2);
		Pharmacist phar1 = new Pharmacist("PH001", "Mark Lee", "password3", "mark@gmail.com", 91342467, "male", bday3);
		Administrator admin1 = new Administrator("A001", "Sarah Lee", "password6", "emily@gmail.com", 89375693,
				"female", bday4);
		listOfDoctor.add(doctor1);
		listOfDoctor.add(doctor2);
		listOfDoctor.add(phar1);
		listOfDoctor.add(admin1);
		for (int i = 0; i < listOfDoctor.size(); i++) {
			System.out.println(listOfDoctor.get(i));
		}
		FileReading.serializing(listOfDoctor, "ListofStaff.ser");
	}

	/**
	 * updateAppointment: update the appointment list to cancel outstanding appointments past current time
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static void updateAppointment() throws IOException, ClassNotFoundException {
		ArrayList<Appointment> appointmentList = FileReading.getAppointmentList();
		Calendar timeNow = Calendar.getInstance();
		for (Appointment apptment : appointmentList) {
			if (apptment.getDateOfAppointment().before(timeNow)
					&& !apptment.getAppointmentStatus().equalsIgnoreCase("completed")) {
				apptment.setAppointmentStatus("Cancelled");
			}
		}
		FileReading.serializing(appointmentList, "ListOfAppointment.ser");
	}

}
