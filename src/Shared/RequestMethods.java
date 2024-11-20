package Shared;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * The RequestMethods class manages requests and medicines, including reading
 * and writing data from/to files.nd age)
 * 
 * @author Maestro
 */
public class RequestMethods implements Serializable {
	/**
	 * Initialize RequestMethods
	 */
	public RequestMethods() {
	}

	/**
	 * A list of medicines available in the inventory.
	 */
	protected List<Medicine> medicineList = new ArrayList<>();
	/**
	 * A static list of requests.
	 */
	protected static List<Request> requestList = new ArrayList<>();
	/**
	 * The file path to the medicine inventory CSV file.
	 */
	protected static final String CSV_FILE_PATH = "Medicine_List.csv";
	/**
	 * The file path to the serialized requests file.
	 */
	private static final String SER_FILE_PATH = "ListOfRequests.ser";

	/**
	 * loadMedicineInventoryFromCSV: loads the MedicineList from the CSV to
	 * MedicineList
	 * 
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected void loadMedicineInventoryFromCSV() throws IOException {
		medicineList = new ArrayList<>(); // remove old elements
		try {
			BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH));
			String line;
			br.readLine(); // skip header in csv
			while ((line = br.readLine()) != null) {
				StringTokenizer star = new StringTokenizer(line, ",");
				String name = star.nextToken().trim(); // first token
				int inStock = Integer.parseInt(star.nextToken().trim()); // second token
				int stockAlertLevel = Integer.parseInt(star.nextToken().trim()); // third token
				medicineList.add(new Medicine(name, inStock, stockAlertLevel));
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * readRequestSer: reads ListOfRequests.ser and puts it into RequestList
	 * 
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected static void readRequestSer() throws IOException, ClassNotFoundException {
		try (FileInputStream fileIn = new FileInputStream(SER_FILE_PATH);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			requestList = new ArrayList<>();
			while (true) {
				try {
					requestList.add((Request) in.readObject());
				} catch (EOFException e) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// in case where file doesn't exist (file should already exist but just in case)
			return;
		}
	}

	/**
	 * saveRequestToSer: save the request list to ListOfRequests.ser
	 * 
	 * @param RequestList List of request is saved
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected static void saveRequestToSer(List<Request> RequestList) throws IOException {
		FileOutputStream fileOut = new FileOutputStream(SER_FILE_PATH);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		for (Request req : RequestList) {
			out.writeObject(req);
		}
		out.close();
	}

	/**
	 * getRequestList: returns RequestList
	 * 
	 * @return request list
	 * @throws ClassNotFoundException when a class definition cannot be found during
	 *                                deserialization.
	 * @throws IOException            when there is an error while reading or
	 *                                writing files.
	 */
	protected static List<Request> getRequestList() throws ClassNotFoundException, IOException {
		readRequestSer();
		return requestList;
	}

	/**
	 * setRequestList: update request list according to the argument
	 * 
	 * @param newRequestList updates the request list
	 * @throws IOException when there is an error while reading or writing files.
	 */
	protected static void setRequestList(List<Request> newRequestList) throws IOException {
		requestList = newRequestList;
		saveRequestToSer(requestList);
	}

}
