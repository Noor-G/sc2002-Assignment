package Administrator;

import static Shared.SanityCheck.numCheck;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Shared.Medicine;
import Shared.Request;
import Shared.RequestMethods;

/**
 * Request manager extends RequestMethods to access the database.
 * 
 * @author Maestro
 * @author Johnson Chow
 */

public class AdRequestManager extends RequestMethods {
	/**
	 * Scanner to gain input from the user.
	 */
	Scanner scanner = new Scanner(System.in);

	/**
	 * Default constructor for AdRequestManager. uses the default constructor of the
	 * parent class RequestMethods to initialize handling request and inventory
	 * management.
	 */
	public AdRequestManager() {
	}

	/**
	 * checkRequest: checks to see if request has been made by pharmacist.Request is
	 * not removed after decision and is only removed when the pharmacist is
	 * notified. If approved stock levels will have to be increased.
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void checkRequest() throws ClassNotFoundException, IOException {
		requestList = getRequestList();
		if (requestList.size() < 1) {
			System.out.println("There are no replenishment requests. ");
			System.out.println("Returning to Main Menu...");
			return;
		}
		loadMedicineInventoryFromCSV();
		System.out.println("--- Medicine Inventory ---");
		System.out.printf("%-20s %-15s %-20s%n", "Medicine", "Stock Level", "Low Stock Alert Level");
		System.out.println("-----------------------------------------------------");

		for (Medicine med : medicineList) {
			System.out.printf("%-20s %-15d %-20d%n", med.getName(), med.getInStock(), med.getStockAlertLevel());
		}
		System.out.println();

		int entry = 0;
		for (int i = 0; i < requestList.size(); i++) {
			Request req = requestList.get(i);
			if (req.getStatus().equalsIgnoreCase("pending")) {
				System.out.println(++entry + ")");
				System.out.println("Medicine Name: " + req.getMedName());
				System.out.print("Requested by: ");
				System.out.print(req.getRequestBy().get(0));
				for (int j = 1; j < req.getRequestBy().size(); j++) {
					System.out.print(", " + req.getRequestBy().get(j));
				}
				System.out.println();
				System.out.println("Status: " + req.getStatus());
				System.out.println();
			}
		}
		if (entry == 0) { // happens when all requests have been answered, but not all pharmacists has
							// seen it yet (not removed from requestlist yet)
			System.out.println(
					"All requests have been answered, but at least one of the Pharmacists that requested haven't seen it yet.");
			return;
		}

		System.out.println("Which entry would you like to update?");
		int choice;
		while (true) {
			choice = numCheck();
			if (choice > entry || choice < 1) {
				System.out.println("That entry does not exist");
				continue;
			}
			break;
		}

		System.out.println("Select option");
		System.out.println("(1) Set Status to 'Approved'");
		System.out.println("(2) Set Status to 'Denied'");
		System.out.println("(3) Return to Main Menu");
		int updateChoice = 0;
		while (true) {
			updateChoice = numCheck();
			if (updateChoice > 3 || updateChoice < 1) {
				System.out.println("That entry does not exist");
				continue;
			}
			break;
		}
		if (updateChoice == 3) {
			System.out.println("Returning to Main Menu...");
			return;
		}

		if (updateChoice == 1) {
			if (replenishInStock(requestList.get(choice - 1).getMedName()) == -1) {
				System.out.println("Replenishment Failed");
			} else {
				((Request) requestList.get(choice - 1)).setStatus("Approved");
				requestList.add(((Request) requestList.get(choice - 1))); // add the updated request into the list
				requestList.remove(choice - 1); // remove the old one (the one updated cannot be updated anymore because
												// choice <= entry)
				System.out.println("Request Approved");
			}
		} else if (updateChoice == 2) {
			((Request) requestList.get(choice - 1)).setStatus("Denied");
			requestList.add(((Request) requestList.get(choice - 1))); // add the updated request into the list
			requestList.remove(choice - 1);// remove the old one (the one updated cannot be updated anymore because
											// choice <= entry)
			System.out.println("Request Denied");
		}

		// load RequestList into serializer
		setRequestList(requestList);
	}

	/**
	 * replenishInStock: Updates amount of stock of a medicine. Cannot be lower than
	 * already in stock.
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	private int replenishInStock(String name) throws IOException {
		loadMedicineInventoryFromCSV();
		for (Medicine med : medicineList) {
			if (med.getName().equalsIgnoreCase(name)) {
				System.out.print("Enter new Stock Level: ");
				int newInStock = 0;
				while (true) {
					try {
						newInStock = scanner.nextInt();
						scanner.nextLine();
						if (newInStock < med.getInStock()) { // replenish stock cannot result in less stocks
							System.out.println("Input cannot be less than current stock!");
							continue;
						}
						break;
					} catch (InputMismatchException e) {
						System.out.println("Please enter a number!");
						scanner.nextLine(); // scanner buffer
					}
				}
				med.setInStock(newInStock);
				System.out.println("Stock level updated successfully.");
				// Saves updated inventory to CSV
				saveMedicineInventoryToCSV();
				return 1;
			}
		}
		System.out.println("Request failed.");
		return -1;
	}

	/**
	 * saveMedicineInventoryToCSV updates the medicine CSV.
	 */
	private void saveMedicineInventoryToCSV() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
			// Write header
			bw.write("Medicine,Stock Level,Low Stock Alert Level");
			bw.newLine();

			// Writes Medicine data
			for (Medicine med : medicineList) {
				bw.write(med.getName() + "," + med.getInStock() + "," + med.getStockAlertLevel());
				bw.newLine();
			}
			System.out.println("Medicine inventory saved to CSV.");
		} catch (IOException e) {
			System.out.println("Error saving to CSV file: " + e.getMessage());
		}
	}

}
