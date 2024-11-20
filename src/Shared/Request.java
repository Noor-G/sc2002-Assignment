package Shared;

import java.io.Serializable;
import java.util.ArrayList;

import Pharmacist.Pharmacist;

/**
 * The Request class controls replenishment request for medicines, made by
 * pharmacists.
 * 
 * @author Maestro
 */
public class Request implements Serializable {
	/**
	 * The status of the request
	 */
	private String status;
	/**
	 * A list of names of pharmacists who requested the replenishment.
	 */
	private ArrayList<String> requestBy = new ArrayList<String>();
	/**
	 * The name of the medicine being requested for replenishment.
	 */
	private String medName;

	/**
	 * getStatus: returns status of Request
	 * 
	 * @return stauts of Request
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setStatus: sets the status of the Request
	 * 
	 * @param status Sets the status of the request
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * setStatus: returns the names of the pharmacists who requested the
	 * replenishment
	 * 
	 * @return names of the pharmacists who requested the replenishment
	 */
	public ArrayList<String> getRequestBy() {
		return requestBy;
	}

	/**
	 * setRequestBy: sets the list of people requesting the replenishment
	 * 
	 * @param requestBy List of people requesting replenishment
	 */
	public void setRequestBy(ArrayList<String> requestBy) {
		this.requestBy = requestBy;
	}

	/**
	 * getMedName: returns the name of the Medicine
	 * 
	 * @return medicine name
	 */
	public String getMedName() {
		return medName;
	}

	/**
	 * setMedName: sets the name of the medicine
	 * 
	 * @param medName name of medicine
	 */
	public void setMedName(String medName) {
		this.medName = medName;
	}

	/**
	 * Request constructor
	 * 
	 * @param pharm   pharmacist making the request
	 * @param medName name of the medicine being requested for replenishment
	 */
	public Request(Pharmacist pharm, String medName) {
		this.medName = medName;
		this.status = "pending";
		this.requestBy.add(pharm.getName()); // change to pharm.name eventually
	}

}
