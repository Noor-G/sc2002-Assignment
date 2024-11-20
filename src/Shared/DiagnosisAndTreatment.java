package Shared;

import java.io.Serializable;

/**
 * Represents the diagnosis made by the doctor and the corresponding treatment
 * prescribed. This class implements serialization.
 * 
 * @author Cheng Jun Long Brandon
 */
public class DiagnosisAndTreatment implements Serializable {

	/**
	 * The diagnosis made by the doctor
	 */
	private String diagnosis;
	/**
	 * The treatment prescribed for the diagnosis
	 */
	private String treatment;

	/**
	 * DiagnosisAndTreatment constructor
	 * 
	 * @param diagnosis diagnosis made by the doctor
	 * @param treatment treatemnet prescribed
	 */
	public DiagnosisAndTreatment(String diagnosis, String treatment) {
		this.diagnosis = diagnosis;
		this.treatment = treatment;
	}

	/**
	 * getDiagnosis: returns diagnosis
	 * 
	 * @return diagnosis
	 */
	public String getDiagnosis() {
		return diagnosis;
	}

	/**
	 * getTreatment: returns treatment
	 * 
	 * @return treatment
	 */
	public String getTreatment() {
		return treatment;
	}

	/**
	 * setDiagnosis: sets diagnosis
	 * 
	 * @param diagnosis diagnosis to be set
	 */
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	/**
	 * setTreatment: sets treatment
	 * 
	 * @param treatment treatment to be set
	 */
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	/**
	 * Returns a string of the diagnosis and treatment.
	 * 
	 * @return A string representing the diagnosis and treatment.
	 */

	@Override
	public String toString() {
		return "Diagnosis: " + getDiagnosis() + ", Treatment: " + getTreatment();
	}
}
