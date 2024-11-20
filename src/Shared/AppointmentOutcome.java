package Shared;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents the outcome of an appointment, including details of diagnosis,
 * treatment, prescribed medicine, medicine collection status, and consultation
 * notes.
 * 
 * @author Cheng Jun Long Brandon
 */
public class AppointmentOutcome implements Serializable {
	/**
	 * The date of the appointment
	 */
	private Calendar dateOfAppointment;
	/**
	 * The details of diagnosis and treatment
	 */
	private DiagnosisAndTreatment diagnosisAndTreatment;
	/**
	 * The medicine prescribed for the appointment
	 */
	private String medicine;
	/**
	 * The status of the medicine (e.g., pending or dispersed)
	 */
	private String medicineStatus; // pending to dispersed, dispersed.
	/**
	 * Consultation notes for the appointment
	 */
	private String consultationNotes;
	// change to diagnosis

	/**
	 * Appointment Outcome Constructor
	 * 
	 * @param dateOfAppointment The date of the appointment.
	 */
	public AppointmentOutcome(Calendar dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
//        this.services = "NIL";  //also treatment
//        this.diagnosis = "NIL";
		this.medicine = "NIL";
		this.medicineStatus = "NIL";
		this.consultationNotes = "NIL";
		String temp = "NIL";
		this.diagnosisAndTreatment = new DiagnosisAndTreatment(temp, temp);
	}

	/**
	 * getDatOfAppointment: return date of appointment
	 * 
	 * @return date of appointment The date of the appointment.
	 */
	public Calendar getDateOfAppointment() {
		return dateOfAppointment;
	}

	/**
	 * Str_getDateofAppointment: return formatted date of appointment
	 * 
	 * @return formatted date of appointment
	 */
	public String Str_getDateofAppointment() {
		Date date = dateOfAppointment.getTime();
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat.format(date);
	}

	/**
	 * getMedicine: return medicine
	 * 
	 * @return medicine The medicine to be prescribed
	 */
	public String getMedicine() {
		return medicine;
	}

	/**
	 * getMedicineStatus: return collection status of medicine
	 * 
	 * @return collection status of medicine
	 */
	public String getMedicineStatus() {
		return medicineStatus;
	}

	/**
	 * getConsultationNotes: returns the appointment consultation notes
	 * 
	 * @return appointment consultation notes
	 */
	public String getConsultationNotes() {
		return consultationNotes;
	}

	/**
	 * DiagnosisandTreatment: returns diagnosis and treatment
	 * 
	 * @return diagnosis and treatment
	 */
	public DiagnosisAndTreatment getDiagnosisandTreatment() {
		return diagnosisAndTreatment;
	}

	/**
	 * setConsultationNotes: sets the appointment notes
	 * 
	 * @param consultationNotes consultation notes to be set
	 */
	protected void setConsultationNotes(String consultationNotes) {
		this.consultationNotes = consultationNotes;
	}

	/**
	 * setMedicine: sets the appointment medicine
	 * 
	 * @param medicine medicine to be prescribed
	 */
	protected void setMedicine(String medicine) {
		this.medicine = medicine;
	}

	/**
	 * setMedicineStatus: sets the collection status of the medicine
	 * 
	 * @param medicineStatus Status of medicine
	 */
    public void setMedicineStatus(String medicineStatus) {
		this.medicineStatus = medicineStatus;
	}

	/**
	 * setDiagnosisandTreatment: sets the diagnosis and treatment
	 * 
	 * @param diagnosisandTreatment1 sets diagnosis and treatment
	 */
	protected void setDiagnosisandTreatment(DiagnosisAndTreatment diagnosisandTreatment1) {
		this.diagnosisAndTreatment = diagnosisandTreatment1;
	}

	/**
	 * Returns a string of the appointment outcome, including diagnosis, treatment,
	 * medicine, medicine status, and consultation notes.
	 * 
	 * @return the appointment outcome.
	 */
	@Override
	public String toString() {
		return "The appointment outcome are as follows \n" + getDiagnosisandTreatment().toString()
				+ "\nMedicines to be administered:  " + getMedicine() + "\nThe status of medicine administration: "
				+ getMedicineStatus() + "\nThe consultation notes: " + getConsultationNotes()
				+ "\n--------------------------------------";
	}

}
