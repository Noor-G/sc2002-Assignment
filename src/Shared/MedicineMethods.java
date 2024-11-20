package Shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The MedicineMethods class manages the medicine inventory. It allows viewing
 * the inventory, loading it from a CSV file, and saving changes back to the
 * CSV.
 * 
 * @author Maestro
 */
public class MedicineMethods {
	/**
	 * Initializes MedicineMethods
	 */
	public MedicineMethods() {
	}

	/**
	 * A list that holds all the medicines in the inventory.
	 */
	protected List<Medicine> MedicineList = new ArrayList<>();
	/**
	 * The file path for the CSV file that contains the medicine inventory data.
	 */
	private static final String CSV_FILE_PATH = "./Medicine_List.csv";
	// need to update path based on where the file is on your own computer

	/**
	 * viewMedicineInventory: prints the Medicine in Medicine_List.csv
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected void viewMedicineInventory() throws IOException {
		MedicineList = new ArrayList<>();
		loadMedicineInventoryFromCSV();
		System.out.println("--- Medicine Inventory ---");
		System.out.printf("%-20s %-15s %-20s%n", "Medicine", "Stock Level", "Low Stock Alert Level");
		System.out.println("-----------------------------------------------------");
		for (Medicine med : MedicineList) {
			System.out.printf("%-20s %-15d %-20d%n", med.getName(), med.getInStock(), med.getStockAlertLevel());
		}
		System.out.println();
	}

	/**
	 * loadMedicineInventoryFromCSV: loads the MedicineList from the CSV to
	 * MedicineList
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected void loadMedicineInventoryFromCSV() throws IOException {
		MedicineList = new ArrayList<>(); // remove old elements
		try {
			BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH));
			String line;
			br.readLine(); // skip header in csv
			while ((line = br.readLine()) != null) {
				StringTokenizer star = new StringTokenizer(line, ",");
				String name = star.nextToken().trim(); // first token
				int inStock = Integer.parseInt(star.nextToken().trim()); // second token
				int stockAlertLevel = Integer.parseInt(star.nextToken().trim()); // third token
				MedicineList.add(new Medicine(name, inStock, stockAlertLevel));
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * saveMedicineInventoryToCSV: saves the MedicineList to the CSV
	 */
	protected void saveMedicineInventoryToCSV() {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
			// Write header
			bw.write("Medicine,Stock Level,Low Stock Alert Level");
			bw.newLine();

			// Writes Medicine data
			for (Medicine med : MedicineList) {
				bw.write(med.getName() + "," + med.getInStock() + "," + med.getStockAlertLevel());
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("Error saving to CSV file: " + e.getMessage());
		}
	}
}
