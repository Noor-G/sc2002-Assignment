package Administrator;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;

import Doctor.Doctor;
import Pharmacist.Pharmacist;
import Shared.FileReading;
import Shared.Staff;
import Shared.Users;

/**
 * StaffManager: manages the staff by being able to add staff,view all staff in
 * the system and remove staff.
 * 
 * @author Maestro
 * @author Johnson Chow
 * @author Cheng Jun Long Brandon
 */
public class StaffManager {

	/**
	 * Initializes the StaffManager
	 */
	public StaffManager() {
	}

	/**
	 * staffMenu: displays menu for the staff management methods
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void staffMenu() throws IOException, ClassNotFoundException {
		int choice;
		do {
			System.out.println("\n--- Manage Hospital Staff ---");
			System.out.println("1. Add staff");
			System.out.println("2. View all staff");
			System.out.println("3. Remove staff");
			System.out.println("4. Back to Administrator menu");
			System.out.print("Enter your choice: ");

			choice = numCheck();

			switch (choice) {
			case 1:
				newStaff();
				break;
			case 2:
				viewStaff();
				break;
			case 3:
				removeStaff();
				break;
			case 4:
				System.out.println("Returning to main menu...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 4);
	}

	/**
	 * newStaff: adds new staff to the CSV
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void newStaff() throws IOException, ClassNotFoundException {
		ArrayList<Staff> listOfStaff = new ArrayList<Staff>();
		listOfStaff = FileReading.getStaffList();

		System.out.println("\nPlease enter Name");
		String name = nullCheck();
		System.out.println("\nPlease enter Password");
		String password = nullCheck();
		System.out.println("\nPlease enter email");
		String email = nullCheck();
		System.out.println("\nPlease enter gender");
		String gender = nullCheck();

		System.out.println("\nPlease enter year of birth");
		int year = numCheck();
		System.out.println("\nPlease enter month of birth");
		int month = numCheck() - 1;
		System.out.println("\nPlease enter day of birth");
		int day = numCheck();
		Calendar dateOfBirth = new GregorianCalendar(year, month, day);

		System.out.println("\nPlease enter contact number");
		int number = numCheck();

		System.out.println("""
				Please enter choose role.\

				1. Doctor\

				2. Pharmacist\

				3. Administrator""");

		int choice = numCheck();
		switch (choice) {
		case 1:
			ArrayList<Doctor> listOfDoc = FileReading.getDocList();
			String docID = "D".concat(FileReading.getNumber(listOfDoc.size()));
			Doctor newDoctor = new Doctor(docID, name, password, email, number, gender, dateOfBirth);
			listOfStaff.add(newDoctor);
			System.out.println("You have added a new doctor. The following are the details.\n" + newDoctor);
			break;
		case 2:
			ArrayList<Pharmacist> listOfPh = FileReading.getPharList();
			String phID = "PH".concat(FileReading.getNumber(listOfPh.size()));

			Pharmacist phar = new Pharmacist(phID, name, password, email, number, gender, dateOfBirth);
			listOfStaff.add(phar);
			System.out.println("You have added a new pharmacist. The following are the details.\n" + phar);
			break;
		case 3:
			ArrayList<Administrator> listOfAd = FileReading.getAdminList();
			String aID = "A".concat(FileReading.getNumber(listOfAd.size()));

			Administrator newAdmin = new Administrator(aID, name, password, email, number, gender, dateOfBirth);
			listOfStaff.add(newAdmin);
			System.out.println("You have added a new administrator. The following are the details.\n" + newAdmin);
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
		saveStaffData(listOfStaff);
	}

	/**
	 * viewStaff: prints every staff from the CSV. Can be filtered based on some
	 * attributes
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void viewStaff() throws IOException, ClassNotFoundException {

		ArrayList<Staff> listOfStaff = FileReading.getStaffList();
		for (Staff staff : listOfStaff) {
			System.out.println(staff);
		}
		int choice;
		do {
			System.out.println("------Filter Menu------");
			System.out.println("Would like to filter the list?");
			System.out.println("1. Filter based on role");
			System.out.println("2. Filter based on gender");
			System.out.println("3. Filter based on age");
			System.out.println("4. Return to Staff Menu");
			System.out.print("Enter your choice: ");

			choice = numCheck();
			System.out.println();

			switch (choice) {
			case 1:
				int roleChoice;
				do {
					System.out.println("------Role Menu------");
					System.out.println("How would like to filter the list?");
					System.out.println("1. Doctor");
					System.out.println("2. Pharmacist");
					System.out.println("3. Administrator");
					System.out.println("4. Cancel");
					System.out.print("Enter your choice: ");

					roleChoice = numCheck();
					System.out.println();

					switch (roleChoice) {
					case 1:
						ArrayList<Doctor> doctorArrayList = FileReading.getDocList();
						System.out.println("Here is the list of doctors");
						for (Doctor doc : doctorArrayList) {
							System.out.println(doc);
						}
						break;
					case 2:
						ArrayList<Pharmacist> pharArrayList = FileReading.getPharList();
						System.out.println("Here is the list of pharmacists");
						for (Pharmacist phar : pharArrayList) {
							System.out.println(phar);
						}
						break;
					case 3:
						ArrayList<Administrator> adminArrayList = FileReading.getAdminList();
						System.out.println("Here is the list of administrators");
						for (Administrator admin : adminArrayList) {
							System.out.println(admin);
						}
						break;
					case 4:
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
						break;
					}
				} while (roleChoice != 4);
				break;

			case 2:

				int genderChoice;

				do {

					System.out.println("------Gender Menu------");
					System.out.println("How would like to filter the list?");
					System.out.println("1. Male");
					System.out.println("2. Female");
					System.out.println("3. Cancel");
					System.out.print("Enter your choice: ");

					genderChoice = numCheck();
					System.out.println();

					switch (genderChoice) {
					case 1:

						System.out.println();
						for (Staff staff : listOfStaff) {
							if (staff.getGender().equalsIgnoreCase("male")) {
								System.out.println(staff);
							}
						}
						break;
					case 2:
						System.out.println();
						for (Staff staff : listOfStaff) {
							if (staff.getGender().equalsIgnoreCase("female")) {
								System.out.println(staff);
							}
						}
						break;
					case 3:
						break;
					default:
						System.out.println("Invalid choice. Please try again.");
					}
				} while (genderChoice != 3);
				break;

			case 3:

				System.out.println("Filter by increasing age");
				listOfStaff.sort(Comparator.comparingInt(Users::getAge));
				for (Staff staff : listOfStaff) {
					System.out.println(staff);
				}
				break;

			case 4:
				System.out.println("Returning to Staff Menu...");
			}
		} while (choice != 4);
	}

	/**
	 * removeStaff removes staff from the CSV
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	private static void removeStaff() throws IOException, ClassNotFoundException {
		ArrayList<Staff> listOfStaff = new ArrayList<>();
		listOfStaff = FileReading.getStaffList();

		Iterator<Staff> iterator = listOfStaff.iterator();

		System.out.println("Please enter ID of staff to be removed:");
		String staffID = nullCheck();

		while (iterator.hasNext()) {
			Staff staff = iterator.next();

			if (staff.getID().equalsIgnoreCase(staffID)) {
				System.out.println(staff + "\n");
				System.out.println("Confirmation to remove staff " + staff.getID()
						+ " ? \nYes: enter 1. \nNo:Enter any other number");
				int choice = numCheck();
				if (choice == 1) {
					iterator.remove();
					System.out.println("Staff " + staff.getID() + " has been removed");
				}
				break;
			}
		}
		saveStaffData(listOfStaff);
	}

	private static void saveStaffData(ArrayList<Staff> list) throws IOException {
		FileReading.serializing(list, "ListofStaff.ser");
	}

}
