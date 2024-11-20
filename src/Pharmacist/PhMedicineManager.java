package Pharmacist;

import java.io.IOException;
import java.util.ArrayList;

import Shared.Medicine;
import Shared.MedicineMethods;

/**
 * PhMedicineManager manages the medicine inventory in the pharmacy. It extends
 * the functionality of the MedicineMethods class to load, view, and check the
 * stock levels of medicines. The class provides methods for viewing the
 * medicine inventory and checking for low stock levels.
 * 
 * @author Maestro
 */

public class PhMedicineManager extends MedicineMethods {
	/**
	 * Default constructor for PhMedicineManager.
	 */
	public PhMedicineManager() {
		// You can add any initialization code here if necessary
	}

	/**
	 * viewMedicineInventory: prints MedicineList.csv and calls checkLowInStocks
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected void viewMedicineInventory() throws IOException {
		MedicineList = new ArrayList<>();
		loadMedicineInventoryFromCSV();
		boolean empty = true;
		System.out.println("--- Medicine Inventory ---");
		System.out.printf("%-20s %-15s %-20s%n", "Medicine", "Stock Level", "Low Stock Alert Level");
		System.out.println("-----------------------------------------------------");

		for (Medicine med : MedicineList) {
			System.out.printf("%-20s %-15d %-20d%n", med.getName(), med.getInStock(), med.getStockAlertLevel());
			empty = false;
		}
		if (empty) {
			System.out.println("There are currently no medicine in Medicine_List.csv");
			return;
		}
		checkLowInStock();
	}

	/**
	 * checkLowInStock: prints warning if any Medicine's stock is under Alert Level
	 */
	private void checkLowInStock() {
		boolean first = true;
		for (int i = 0; i < MedicineList.size(); i++) {
			Medicine med = MedicineList.get(i);

			if (med.getInStock() < med.getStockAlertLevel()) {
				if (first == true) // the below sysout is only printed once
				{
					System.out.println();
					System.out.println(
							"Warning! Low Medicine Stock. Please create replenishment request(s) for the following Medicine:");
					first = false;
				}
				System.out.println(med.getName() + ": " + med.getInStock() + " units in stock (Low Alert Level: "
						+ med.getStockAlertLevel() + ")");
			}
		}
		System.out.println();
	}
}
