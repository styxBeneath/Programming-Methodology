
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {
		/*
		 * "distance" variable is equal to the distance between the left edge of canvas
		 and the first brick in base
		 */
		int distance = (getWidth() - BRICK_WIDTH * BRICKS_IN_BASE) / 2;

		/** variable "i" is equal to the number of bricks that should be added on */
		for (int i = BRICKS_IN_BASE; i >= 1; i--) {
			
			/** variable "j" helps us to add "i" single brick on a certain line */
			for (int j = 0; j < i; j++) {
				
				/*
				 * distance + (BRICKS_IN_BASE - i)*BRICK_WIDTH/2 is the x coordinate of the
				 first brick in a certain line, at that moment, "+ j*BRICK_WIDTH" is 0;
				  
				 * distance + (BRICKS_IN_BASE-i)*BRICK_WIDTH/2 + j*BRICK_WIDTH is the x
				 coordinate of all other bricks in a certain line;
				 
				 * (BRICKS_IN_BASE+1-i)*BRICK_HEIGHT is equal to the number of the current line;
				 
				 *getHeight()-(BRICKS_IN_BASE+1-i)*BRICK_HEIGHT is equal to the y coordinate of
				 every single brick in a current line;
				 */
				add(new GRect(distance + (BRICKS_IN_BASE - i) * BRICK_WIDTH / 2 + j * BRICK_WIDTH,getHeight() - (BRICKS_IN_BASE + 1 - i) * BRICK_HEIGHT, BRICK_WIDTH, BRICK_HEIGHT));
			
			}
		}
	}
}
