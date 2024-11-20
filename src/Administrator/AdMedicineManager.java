package Administrator;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;
import static Shared.SanityCheck.numCheckPos;

import java.io.IOException;

import Shared.Medicine;
import Shared.MedicineMethods;

/**
 * Medicine manager adds,removes,view medicine stocks and updates stock of
 * medicine. Medicine manager extends Medicine
 * 
 * @author Maestro
 * @author Johnson Chow
 * @author Cheng Jun Long Brandon
 */
public class AdMedicineManager extends MedicineMethods {

	/**
	 * Constructor for AdMedicineManager.
	 */
	public AdMedicineManager() {
	}

	/**
	 * manageMedicineInventory: displays menu for medicine methods
	 * 
	 * @throws IOException if there is an error reading from or writing to the CSV
	 *                     file
	 */
	protected void manageMedicineInventory() throws IOException {
		loadMedicineInventoryFromCSV();
		int choice = 0;
		do {
			System.out.println("\n--- Manage Medicine Inventory ---");
			System.out.println("1. Add Medicine");
			System.out.println("2. Remove Medicine");
			System.out.println("3. Edit Medicine");
			System.out.println("4. View Medicine Inventory");
			System.out.println("5. Update Stock Level");
			System.out.println("6. Return to Main Menu");
			System.out.print("Enter your choice: ");

			choice = numCheck();

			switch (choice) {
			case 1:
				addMedicine();
				break;
			case 2:
				removeMedicine();
				break;
			case 3:
				editMedicine();
				break;
			case 4:
				viewMedicineInventory();
				break;
			case 5:
				updateInStock();
				break;
			case 6:
				System.out.println("Returning to Main Menu...");
				break;
			default:
				System.out.println("Invalid choice.");
			}
		} while (choice != 6);
	}

	/**
	 * CheckLowinStock checks if the medicine is below the low alert level of stock
	 * and outputs a warning requesting for the stock to by replenished.
	 */
	private void checkLowInStock() {
		boolean first = true;
		for (int i = 0; i < MedicineList.size(); i++) {
			Medicine med = MedicineList.get(i);

			if (med.getInStock() < med.getStockAlertLevel()) {
				if (first == true) // the below block is only printed once
				{
					System.out.println();
					System.out.println(
							"Warning! Low Medicine Stock. Please update stock level for the following Medicine:");
					first = false;
				}
				System.out.println(med.getName() + ": " + med.getInStock() + " units in stock (Low Alert Level: "
						+ med.getStockAlertLevel() + ")");
			}
		}
	}

	/**
	 * addMedicine adds a new medicine into the CSV file.
	 */
	private void addMedicine() {
		System.out.print("Enter name of Medicine: ");
		String name = nullCheck(); // it is very important to check that entries in the csv write methods are not
									// null or else everything breaks
		System.out.print("Enter Stock Level: ");

		int inStock = numCheckPos();

		System.out.print("Enter Low Stock Alert Level: ");
		int stockAlertLevel = numCheckPos();
		MedicineList.add(new Medicine(name, inStock, stockAlertLevel));
		System.out.println("Medicine added successfully.");
		// Saves the updated inventory to the CSV
		saveMedicineInventoryToCSV();
	}

	/**
	 * removeMedicine removes medicine from the CSV file.
	 */
	private void removeMedicine() {
		System.out.print("Enter name of Medicine to be removed: ");
		String name = nullCheck();

		for (int i = 0; i < MedicineList.size(); i++) {
			if (MedicineList.get(i).getName().equalsIgnoreCase(name)) {
				// confirm deletion
				System.out.print("Are you sure you want to delete '" + name + "'? (Y/N): ");
				String confirmation1 = nullCheck();
				if (!confirmation1.equalsIgnoreCase("Y")) {
					System.out.println("Deletion cancelled.");
					return;
				}
				MedicineList.remove(i);
				System.out.println("Medicine '" + name + "' removed successfully.");
				// Saves updated inventory to CSV
				saveMedicineInventoryToCSV();
				return;
			}
		}
		System.out.println("Medicine '" + name + "' not found.");
	}

	/**
	 * editMedicine: edit attributes of existing medicine
	 */
	private void editMedicine() {
		System.out.print("Enter name of Medicine to be edited: ");
		String name = nullCheck();

		for (int i = 0; i < MedicineList.size(); i++) {
			if (MedicineList.get(i).getName().equalsIgnoreCase(name)) {
				// confirm deletion
				System.out.println("What would you like to edit?");
				System.out.println("1) Name");
				System.out.println("2) Stock Level");
				System.out.println("3) Low Stock Level Alert");
				int choice;
				while (true) {
					choice = numCheck();
					if (choice < 1 || choice > 3) {
						System.out.println("Please enter 1, 2, or 3");
						continue;
					}
					break;
				}
				if (choice == 1) {
					System.out.print("Enter new name");
					String newName = nullCheck();
					MedicineList.get(i).setName(newName);
				} else if (choice == 2) {
					System.out.print("Enter new Stock Level");
					int newInStock = numCheck();
					MedicineList.get(i).setInStock(newInStock);
				} else if (choice == 3) {
					System.out.print("Enter new Low Stock Alert Level");
					int newStockAlertLevel = numCheck();
					MedicineList.get(i).setStockAlertLevel(newStockAlertLevel);
				}
				System.out.println("Medicine '" + name + "' edited successfully.");
				// Saves updated inventory to CSV
				saveMedicineInventoryToCSV();
				return;
			}
		}
		System.out.println("Medicine '" + name + "' not found.");
	}

	/**
	 * viewMedicineInventory views medicine from the CSV file.
	 */
	@Override
	protected void viewMedicineInventory() {
		System.out.println("--- Medicine Inventory ---");
		System.out.printf("%-20s %-15s %-20s%n", "Medicine", "Stock Level", "Low Stock Alert Level");
		System.out.println("-----------------------------------------------------");

		for (Medicine med : MedicineList) {
			System.out.printf("%-20s %-15d %-20d%n", med.getName(), med.getInStock(), med.getStockAlertLevel());
		}
		System.out.println();
		checkLowInStock();
	}

	/**
	 * updateInStock Increases level of stock
	 * 
	 * @throws IOException if there is an error reading from or writing to the CSV
	 *                     file
	 */
	private void updateInStock() throws IOException {
		loadMedicineInventoryFromCSV();
		System.out.print("Enter name of Medicine to be updated: ");
		String name = nullCheck();

		for (Medicine med : MedicineList) {
			if (med.getName().equalsIgnoreCase(name)) {
				System.out.print("Enter new Stock Level: ");
				int newInStock = numCheckPos();
				med.setInStock(newInStock);
				System.out.println("Stock level updated successfully.");
				// Saves updated inventory to CSV
				saveMedicineInventoryToCSV();
				return;
			}
		}
		System.out.println("Medicine not found.");
		return;
	}
}
