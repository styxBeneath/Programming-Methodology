
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {

	public void run() {
		
		int numberOfSteps = 0; // a number of iterations it takes "num" to be 1.
		
		int num = readInt("enter a number: ");
		
		while (num != 1) {

			if (num % 2 == 1) {

				println(num + "is odd,so I make 3n + 1: " + (num * 3 + 1)); // when "num" is an odd number, we multiple it by 3 and increase it by 1.
				num = 3 * num + 1; // after we print the line, we save 3*num+1 as a current value of "num".
			} else {

				println(num + "is even so I take half: " + (num / 2)); // when "num" is an even number, we divide it by 2.
				
				num = num / 2; // after we print the line, wwe save num/2 as a current value of "num".
			}
			
			numberOfSteps++; // in the end of "while" loop, as we've already printed a certain line, we increase "numberOfSteps" by 1;
		}

		/*
		 * after the loop, "numberOfSteps" variable has the equal value to the number of
		 * the iterations occured to make "num" equal to 1.
		 */
		println("the process took " + numberOfSteps + " to reach 1");
	}
}
