package Pharmacist;

import static Shared.SanityCheck.nullCheck;

import java.io.IOException;

import Shared.Medicine;
import Shared.Request;
import Shared.RequestMethods;

/**
 * The PhRequestManager class manages the pharmacist's requests to the
 * Administrator for replenishment of medicines. It provides methods for
 * creating new replenishment requests and checking the status of existing
 * requests.
 * 
 * This class extends the RequestMethods class
 * 
 * @author Maestro
 * @author Johnson Chow
 */
public class PhRequestManager extends RequestMethods {
	/**
	 * Initializes PhRequestManager
	 */
	public PhRequestManager() {
	}

	/**
	 * Scanner to gain input from the user.
	 */

	/**
	 * createRequest: create a replenishment request and sends it to Administrator
	 * 
	 * @param pharm pharmacist creating the request.
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void createRequest(Pharmacist pharm) throws IOException, ClassNotFoundException {
		loadMedicineInventoryFromCSV();
		System.out.print("Enter name of Medicine to be replenished: ");
		String name = nullCheck();
		for (Medicine med : medicineList) {
			if (med.getName().equalsIgnoreCase(name)) {
				System.out.println("Send replenishment request for " + name + " to Administrator? (Y/N)");
				String confirmation1 = nullCheck();
				if (!confirmation1.equalsIgnoreCase("Y")) {
					System.out.println("Request cancelled.");
					return;
				}

				if (requestList.size() != 0) { // checks to see if a request for the drug has already been made
					for (Request req : requestList) {
						if (req.getMedName().equalsIgnoreCase(name)) {
							if (req.getStatus().equalsIgnoreCase("approved")
									|| req.getStatus().equalsIgnoreCase("denied")) { // another request was just
																						// approved/denied

								System.out.println("Requests for " + name + " has just been " + req.getStatus()
										+ "recently. Please wait until the pharmacists that made the request has been notified.");

								return;

							}
							for (String pharName : req.getRequestBy()) {
								if (pharm.getName().equalsIgnoreCase(pharName)) { // request already made by the
																					// pharmacist and still pending
									System.out.println("You have already requested for " + name
											+ " and it is still pending approval from the Administrator.");
									return;
								} else {
									req.getRequestBy().add(pharm.getName()); // a different pharmacist request a drug
																				// already requested, adds to the
																				// requested by array
									saveRequestToSer(requestList);
									System.out.println("Request for " + name + " sent to Administrator successfully.");
									return;
								}
							}
						}
					}
				}

				requestList.add(new Request(pharm, name)); // executes into RequestList only in the first iteration or
															// if Serializer is empty (second case does nothing)
				saveRequestToSer(requestList);

				System.out.println("Request for " + name + " sent to Administrator successfully.");
				return;
			}
		}
		System.out.println("Medicine not found.");
	}

	/**
	 * checkRequest: check to see if Administrator has responded
	 * 
	 * @param pharm pharmacist creating the request.
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected void checkRequest(Pharmacist pharm) throws IOException, ClassNotFoundException {
		readRequestSer();
		for (int i = 0; i < requestList.size(); i++) {
			Request req = requestList.get(i);
			for (String pharName : req.getRequestBy()) {
				if (pharm.getName().equalsIgnoreCase(pharName)) {
					if (req.getStatus().equalsIgnoreCase("approved")) {
						System.out
								.println("Your replenishment request for " + req.getMedName() + " has been approved.");
						if (req.getRequestBy().size() == 1)
							requestList.remove(req); // this pharmacist is the last to see the result of the request
						else {
							req.getRequestBy().remove(pharm.getName()); // other pharmacist hasn't seen it
						}
						saveRequestToSer(requestList);
						i--;
					} else if (req.getStatus().equalsIgnoreCase("denied")) {
						System.out.println("Your replenishment request for " + req.getMedName()
								+ " has been denied. Please meet with the Administrator if you disagree with the decision.");
						if (req.getRequestBy().size() == 1)
							requestList.remove(req); // this pharmacist is the last to see the result of the request
						else {
							req.getRequestBy().remove(pharm.getName()); // other pharmacist hasn't seen it
						}
						saveRequestToSer(requestList);
						i--;
					}
				}

			}

		}
	}
}
