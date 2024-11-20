package Shared;

import java.util.Calendar;

/**
 * The Staff class represents a staff member in the system. It extends the Users
 * class and adds functionality related to staff details.
 * 
 * This class is abstract as it cannot be instantiated directly, but must be
 * subclassed by other classes that represent specific types of users.
 * 
 * It includes staff member's role, as well as the inherited personal details
 * from the Users class.
 * 
 * @author Cheng Jun Long Brandon
 * @author Nadathur Ammal Shreya Sudharshan
 */
public abstract class Staff extends Users {
	/**
	 * The role of the staff member.
	 */
	protected String role;

	/**
	 * Staff constructor
	 * 
	 * @param ID          ID of staff
	 * @param name        name of staff
	 * @param password    password of staff
	 * @param email       email address of staff
	 * @param number      phone number of staff
	 * @param gender      gender of staff
	 * @param dateOfBirth date of birth of staff
	 */
	public Staff(String ID, String name, String password, String email, int number, String gender,
			Calendar dateOfBirth) {
		super(ID, name, password, email, number, gender, dateOfBirth);
	}

	/**
	 * getRole: returns the role of the Staff
	 * 
	 * @return role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * setRole: set role of staff
	 * 
	 * @param role sets the role of the staff
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Returns a string representation of the staff member, including all personal
	 * details inherited from the users class and the staff member's role.
	 * 
	 * @return a string representation of the staff member
	 */

	@Override
	public String toString() {
		return super.toString() + ", role is " + role;
	}
}