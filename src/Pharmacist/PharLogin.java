package Pharmacist;

import static Shared.SanityCheck.numCheck;

import java.io.IOException;

import menus.Main;

/**
 * PharLogin displays the menu and handles the actions that a pharmacist can
 * perform It provides options to view the medicine list, make replenishment
 * requests, view appointment outcomes, and change passwords.
 * 
 * @author Nadathur Ammal Shreya Sudharshan
 */

public class PharLogin {
	/**
	 * Initializes Pharlogin
	 */
	public PharLogin() {
	}

	/**
	 * displayMenu: displays menu for pharmacists
	 * 
	 * @param pharma object shows the currently logged-in pharmacist.
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	public static void displayMenu(Pharmacist pharma) throws IOException, ClassNotFoundException {
		PhMedicineManager medMan = new PhMedicineManager();
		PhRequestManager reqMan = new PhRequestManager();
		PhPrescriptionManager preman = new PhPrescriptionManager();
		int choice = -1;
		if (pharma.getGender().equalsIgnoreCase("male")) {
			System.out.println("Hello " + "Mr. " + pharma.getName());
		} else if (pharma.getGender().equalsIgnoreCase("female")) {
			System.out.println("Hello " + "Ms. " + pharma.getName());
		} else {
			System.out.println("Hello " + pharma.getName());
		}
		reqMan.checkRequest(pharma); // check to see if request made by this pharmacist has been answered by the
										// admin

		do {
			System.out.println("----------------------------------------------------------");
			System.out.println("What would you like to do?");
			System.out.println("(1) View Medicine List");
			System.out.println("(2) Make Replenishment Request");
			System.out.println("(3) View Appointment Outcome Record List");
			System.out.println("(4) Change Password");
			System.out.println("(5) Exit");

			choice = numCheck();

			System.out.println();
			switch (choice) {
			case 1:
				medMan.viewMedicineInventory();
				break;
			case 2:
				reqMan.createRequest(pharma);
				break;
			case 3:
				preman.prescriptMeds();
				break;
			case 4:
				pharma.changePassword();
				break;
			case 5:
				System.out.println("You've logged out.");
				Main.login();
				break;
			}
		} while (choice != 5);

	}
}
