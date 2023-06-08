
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {

	/*
	 * this method receives the values of a right triangle's adjacents, calculates
	 * the value of hypotenuse and returns it.
	 */
	private double hypotenuseCounter(int adjacent1, int adjacent2) {

		// variable "hypotenuse" initially takes the value equal to the sum of adjacent's squares.
		double hypotenuse = (double) (adjacent1 * adjacent1 + adjacent2 * adjacent2);

		// variable "hypotenuse" takes the value equal to its square root.
		hypotenuse = Math.sqrt(hypotenuse);

		return hypotenuse;
	}

	public void run() {
		println("Enter values to compute Pythagorean theorem.");

		// a consumer inputs two integers: values of right triangle's adjacents.
		int adjacent1 = readInt("a: ");
		int adjacent2 = readInt("b: ");

		// this program prints the value of hypotenuse by using the "hypotenuseCounter" method.
		println("c = " + hypotenuseCounter(adjacent1, adjacent2));

	}
}
