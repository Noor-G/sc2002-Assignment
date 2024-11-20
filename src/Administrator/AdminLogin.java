package Administrator;

import static Shared.SanityCheck.numCheck;

import java.io.IOException;

import menus.Main;

/**
 * AdminLogin: class of the administrator menu that users can select from
 * managing hospital staff, view appointment details, manage medicine inventory
 * and check replenishment requests.
 * 
 * @author Nadathur Ammal Shreya Sudharshan
 */
public class AdminLogin {
	/**
	 * AdminLogin : Default constructor for AdminLogin.
	 */
	public AdminLogin() {
	}

	/**
	 * displayMenu: displays menu for Administrators
	 * 
	 * @param admin administrator object that contains the details and functions of
	 *              administrator
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */

	public static void displayMenu(Administrator admin) throws ClassNotFoundException, IOException {
		int choice = 0;
		StaffManager staffManager = new StaffManager();
		AdMedicineManager AdMedicineManager = new AdMedicineManager();
		AdRequestManager reqMan = new AdRequestManager();

		do {
			System.out.println("------------------------------------------------");
			System.out.println("1. Manage Hospital Staff");
			System.out.println("2. View Appointments Details");
			System.out.println("3. Manage Medicine Inventory");
			System.out.println("4. Check Replenishment Requests");
			System.out.println("5. Change Password");
			System.out.println("6. Logout");
			System.out.print("Enter your choice: ");

			choice = numCheck();
			System.out.println();

			switch (choice) {
			case 1:
				staffManager.staffMenu();
				break;
			case 2:
				admin.viewAppointment();
				break;
			case 3:
				AdMedicineManager.manageMedicineInventory();
				break;
			case 4:
				reqMan.checkRequest();
				break;
			case 5:
				admin.changePassword();
				break;
			case 6:
				System.out.println("You have logged out.");
				Main.login();
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		} while (choice != 6);
	}

}