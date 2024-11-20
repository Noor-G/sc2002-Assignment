package Shared;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The SanityCheck class provides methods for validating user input. This
 * ensures user input is of the expected type and that it meets certain
 * conditions, such as being an integer, a positive integer, or a non-empty
 * string.)
 * 
 * @author Maestro
 */
public class SanityCheck {

	static Scanner sc = new Scanner(System.in);

	/**
	 * Initializes the Scanner object to read user input.
	 */
	public SanityCheck() {
	}

	/**
	 * numCheck: checks to see if the input is integer
	 * 
	 * @return integer
	 */
	public static int numCheck() {
		while (true) {
			try {
				int num = sc.nextInt();
				sc.nextLine();
				return num; // If input is valid, return num
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number!");
				sc.nextLine(); // Clear the invalid input from the sc buffer
			}
		}
	}

	/**
	 * numCheckPos: checks to see if the input is a positive integer
	 * 
	 * @return positive integer
	 */
	public static int numCheckPos() {
		while (true) {
			try {
				int num = sc.nextInt();
				sc.nextLine(); // Clear the invalid input from the sc buffer
				if (num < 0) {
					System.out.println("Input cannot be less than 0!");
					continue;
				}
				return num; // If input is valid, return num
			} catch (InputMismatchException e) {
				System.out.println("Please enter a number!");
				sc.nextLine(); // Clear the invalid input from the sc buffer
			}
		}
	}

	/**
	 * nullCheck: checks to see if input is null or empty space
	 * 
	 * @return String
	 */
	public static String nullCheck() {
		while (true) {
			try {
				String str = sc.nextLine();
				if (str == null || str.trim() == "") {
					System.out.println("Input cannot be empty space.");
					continue;
				}
				return str; // If input is valid, return num
			} catch (InputMismatchException e) {
				System.out.println("Error detected. Try again.");
			}
		}

	}
}
