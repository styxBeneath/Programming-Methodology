
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	// when consumer inputs ENDER_NUMBER, the program finishes its work.
	private static final int ENDER_NUMBER = 0;

	public void run() {

		// variable "min" initially has the largest value that int can have,so that
		// primarly inputed number is always smaller than it.
		int min = Integer.MAX_VALUE;

		// variable "max" initially has the smallest value that int can have, so that
		// primarly inputed number is always larger than it.
		int max = Integer.MIN_VALUE;

		// variable "number" describes the number of inputed integers
		int number = 0;

		// this loop works until consumer inputs the ENDER_NUMBER.
		while (true) {
			int integer = readInt(); // consumer inputs an integer at every step of the loop.

			// this operator checks if consumer has input the ENDER_NUMBER
			if (integer == ENDER_NUMBER) {
				break;
			} else {
				if (integer > max) {
					max = integer; // we compare current "integer" to the current maximum number.
				}
				if (integer < min) {
					min = integer; // we compare current "integer" to the current minimum number.
				}
				number++; // after every itteration of the loop, "number" increases by 1,so that we know
							// how many numbers we have after loop.
			}
		}

		// we check if consumer hasn't input any numbers besides the ENDER_NUMBER.
		if (number == 0) {
			println("you haven't input a valid number");
		} else {
			println("smallest: " + min); // program prints the largest that was inputed.
			println("largest: " + max); // program prints the smallest that was inputed.
		}
	}
}
