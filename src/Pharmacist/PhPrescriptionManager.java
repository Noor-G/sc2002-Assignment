package Pharmacist;

import static Shared.SanityCheck.nullCheck;
import static Shared.SanityCheck.numCheck;

import java.io.IOException;
import java.util.ArrayList;

import Shared.Appointment;
import Shared.FileReading;
import Shared.Medicine;
import Shared.MedicineMethods;

/**
 * The PhPrescriptionManager class manages prescriptions in the pharmacy. It
 * allows pharmacists to update the status of prescriptions and handle medicine
 * stock updates.
 * 
 * @author Maestro
 */
public class PhPrescriptionManager extends MedicineMethods {
	static String AppointmentDB = "ListOfAppointment.ser";

	/**
	 * Default constructor for the PhPrescriptionManager class.
	 */
	public PhPrescriptionManager() {
	}

	/**
	 * checkLowInStocks: add Medicine prescription to AppointmentOutcome and updates
	 * the MedicineStatus
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void prescriptMeds() throws ClassNotFoundException, IOException {
		ArrayList<Appointment> listOfAppointment = new ArrayList<Appointment>();
		listOfAppointment = FileReading.deserializing(AppointmentDB);
		boolean empty = true;
		ArrayList<Appointment> pendingAppointments = new ArrayList<Appointment>(); // list of appointments that needs
		// medicine collection
		for (Appointment appoi : listOfAppointment) {
			if (!appoi.getAppointmentOutcome().getMedicineStatus().equalsIgnoreCase("dispensed") // only shows
																									// appointments
																									// where the
																									// medicine status
																									// is not dispensed
																									// and the medicine
																									// isn't NIL
					&& !appoi.getAppointmentOutcome().getMedicine().equalsIgnoreCase("NIL")) {
				empty = false;
				pendingAppointments.add(appoi);
				System.out.println(appoi);
			}
		}
		if (empty) {
			System.out.println("There are currently no patients to collect medicine");
			return;
		}
		System.out.println("Enter the ID of the Appointment you would like to update?");
		String editID = ""; // ID of Appointment to be edited
		boolean found = false; // track if appointment is found
		while (!found) {
			editID = nullCheck();
			for (Appointment ap : pendingAppointments) {
				if (ap.getAppointmentID().equalsIgnoreCase(editID)) {
					found = true;
				}
			}
			if (!found)
				System.out.println("That appointment does not exist");
			// continues while loop if for loop is exhausted
		}

		System.out.println("Select option");
		System.out.println("(1) Set Status to 'Dispensed'");
		System.out.println("(2) Return to Main Menu");
		int updateChoice = 0;
		while (true) {
			updateChoice = numCheck();
			if (updateChoice > 2 || updateChoice < 1) {
				System.out.println("That option does not exist");
				continue;
			}
			break;
		}

		if (updateChoice == 2) {
			System.out.println("Returning to Main Menu...");
			return;
		}
		found = false; // track if medicine is found or not
		if (updateChoice == 1) {
			MedicineList = new ArrayList<>();
			loadMedicineInventoryFromCSV();
			Appointment appoi = null;
			int a = 0;
			for (int i = 0; i < listOfAppointment.size(); i++) {
				appoi = listOfAppointment.get(i);
				if (appoi.getAppointmentID().equalsIgnoreCase(editID)) {
					for (Medicine med : MedicineList) {
						if (appoi.getAppointmentOutcome().getMedicine().equalsIgnoreCase(med.getName())) {
							found = true;
							a = i;
							if (med.getInStock() == 0) {
								System.out.println(med.getName()
										+ " is currently out of stock. Please make a replenishment request to the Administrator.");
								return;
							}
							med.setInStock(med.getInStock() - 1);

						}
					}

					if (!found) { // medicine not found
						System.out.println(
								"That medicine does not exist. Please consult the Administrator to add it to the Medicine Inventory.");
						return;
					}
				}
			}
			appoi = listOfAppointment.get(a); // assign appoi to the edited appointment
			listOfAppointment.remove(a); // temporarily remove it
			appoi.getAppointmentOutcome().setMedicineStatus("Dispensed");
			listOfAppointment.add(appoi); // add the updated version back into the list
			System.out.println("Medicine Status changed to 'Dispensed'");
			saveMedicineInventoryToCSV();
		}
		FileReading.serializing(listOfAppointment, AppointmentDB);

	}

}
