package Shared;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Appointment class represents a medical appointment made by a patient with
 * a doctor. It includes details such as the appointment ID, doctor and patient
 * information, the appointment date, its status, and its outcome.
 * 
 * @author Cheng Jun Long Brandon
 */

public class Appointment implements Serializable {
	/**
	 * ID of appointment
	 */
	private String appointmentID; // A001, A002 etc
	/**
	 * ID of the doctor.
	 */
	private String doctorID;
	/**
	 * Name of the doctor handling the appointment.
	 */
	private String doctorName;
	/**
	 * ID of the patient attending the appointment.
	 */
	private String patientID;
	/**
	 * Date and time of the appointment.
	 */
	private Calendar dateOfAppointment;
	/**
	 * Status of the appointment
	 */
	private String appointmentStatus;
	// prescriptions, and whether it is administered yet or not
	/**
	 * Outcome of the appointment
	 */
	private AppointmentOutcome appointmentOutcome;

	/**
	 * Appointment constructor
	 * 
	 * @param appointmentID      ID of the appointment
	 * @param doctorID           Id of the Doctor
	 * @param doctorName         Name of the doctor handling the appointment
	 * @param dateOfAppointment  date and time of the appointment
	 * @param appointmentOutcome Outcome of the appointment
	 */
	public Appointment(String appointmentID, String doctorID, String doctorName, Calendar dateOfAppointment,
			AppointmentOutcome appointmentOutcome) { // constructor for doctor to make "blank" appointments
		this.appointmentID = appointmentID;
		this.doctorID = doctorID;
		this.doctorName = doctorName;
		this.dateOfAppointment = dateOfAppointment;
		this.appointmentStatus = "Available";
		this.appointmentOutcome = appointmentOutcome;
		this.patientID = "NIL";
	}

	/**
	 * getAppointmentID: returns ID of Appointment
	 * 
	 * @return ID of Appointment
	 */
	public String getAppointmentID() {
		return appointmentID;
	}

	/**
	 * getDoctorID: returns ID of the Appointment Doctor
	 * 
	 * @return ID of the Appointment Doctor
	 */
	public String getDoctorID() {
		return doctorID;
	}

	/**
	 * getDoctorName: returns Name of the Appointment Doctor
	 * 
	 * @return Name of the Appointment Doctor
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * getDoctorID: returns ID of the Appointment Patient
	 * 
	 * @return ID of the Appointment Patient
	 */
	public String getPatientID() {
		return patientID;
	}

	/**
	 * getDoctorID: returns date of the Appointment
	 * 
	 * @return date of the Appointment
	 */
	public Calendar getDateOfAppointment() {
		return dateOfAppointment;
	}

	/**
	 * getDoctorID: returns status of the Appointment
	 * 
	 * @return status of the Appointment
	 */
	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	/**
	 * getDoctorID: returns formatted date of the Appointment
	 * 
	 * @return formatted date of the Appointment
	 */
	public String str_GetDateofAppointment() {
		Date date = dateOfAppointment.getTime();
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return dateformat.format(date);
	}

	/**
	 * setPatientID: set the appointment patient's ID
	 * 
	 * @param patientID ID of the patient attending the appointment
	 */
	public void setPatientID(String patientID) { // for patient to set their Id to available slot, use the patient login
													// id
		this.patientID = patientID;
	}

	/**
	 * setDateOfAppointment: sets the appointment date
	 * 
	 * @param dateOfAppointment date and time to set for the appointment
	 */
	public void setDateOfAppointment(Calendar dateOfAppointment) {
		this.dateOfAppointment = dateOfAppointment;
	}

	/**
	 * setAppointmentStatus: sets the appointment status
	 * 
	 * @param appointmentStatus the status to set for the appointment
	 */
	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	/**
	 * getAppointmentOutcome: returns appointment outcome
	 * 
	 * @return appointment outcome
	 */
	public AppointmentOutcome getAppointmentOutcome() {
		return appointmentOutcome;
	}

	/**
	 * updateMedicine: set medicine of appointment
	 * 
	 * @param med sets the medicine of the appointment
	 */
	public void updateMedicine(String med) {
		appointmentOutcome.setMedicine(med);
	}

	/**
	 * updateMedStatus: update status of appointment medicine
	 * 
	 * @param medStatus status of the medicine
	 */
	public void updateMedStatus(String medStatus) {
		appointmentOutcome.setMedicineStatus(medStatus);
	}

	/**
	 * updateConsultationNotes: set consultation notes of appointment
	 * 
	 * @param consultation notes from the appointment
	 */
	public void updateConsultationNote(String consultation) {

		appointmentOutcome.setConsultationNotes(consultation);
	}

	/**
	 * updateDiagnosis: sets the diagnosis and treatment
	 * 
	 * @param diagnosis sets the diagnosis and treatment
	 */
	public void updateDiagnosis(DiagnosisAndTreatment diagnosis) {
		appointmentOutcome.setDiagnosisandTreatment(diagnosis);
	}

	/**
	 * doctorUpdate: method for doctor to update appointment
	 * 
	 * @param services     diagnosis and treatment details
	 * @param med          prescribed medicine
	 * @param medStatus    status of the prescribed medicine
	 * @param consultation consultation notes for the appointment
	 */
	public void doctorUpdate(DiagnosisAndTreatment services, String med, String medStatus, String consultation) {
		updateDiagnosis(services);
		updateMedicine(med);
		updateMedStatus(medStatus);
		updateConsultationNote(consultation);
	}

	/**
	 * PatientView: method for Patient
	 * 
	 * @return display of appointment
	 */
	public String patientView() {
		return "Name of Doctor in charged: " + getDoctorName() + "\nAppointment ID: " + getAppointmentID()
				+ "\nDate and Time of appointment:" + str_GetDateofAppointment() + "\nStatus:" + getAppointmentStatus()
				+ "\n--------------------------------------";
	}

	@Override
	public String toString() {
		return "Appointment ID: " + getAppointmentID() + "\nID of Doctor in charged: " + getDoctorID()
				+ "\nName of Doctor in charged: " + getDoctorName() + "\nID of Patient: " + getPatientID()
				+ "\nDate and Time of appointment:" + str_GetDateofAppointment() + "\nAppointment status: "
				+ getAppointmentStatus() + "\n" + appointmentOutcome.toString();
	}

}
