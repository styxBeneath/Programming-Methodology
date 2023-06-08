
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class TargetExtension extends GraphicsProgram {
	private static final int NUM_OF_CIRCLES = 5;
	private static final int NUM_OF_STEPS = 150; // number of steps occuring on canvas;
	private static final double SPEED = 3.0;
	private static final double GROWTH_OF_RADIUS = 0.89; // difference between the following circles;
	private static final double RADIUS1 = 0.76; // radius of the smallest circe in cm;
	private static final double CM_TO_PIXELS = 72.0 / 2.54;

	public void run() {

		int radius1 = (int) (RADIUS1 * CM_TO_PIXELS); // radius of the smallest circle in pixel;
		int t = 0; // numper of the steps that already happened;

		while (t <= NUM_OF_STEPS) {
			for (int i = NUM_OF_CIRCLES - 1; i >= 0; i--) {
				pause(SPEED);

				// pixel radius of the current circle, which is going to be drawn;
				int radius = radius1 + (int) ((double) (i) * GROWTH_OF_RADIUS * CM_TO_PIXELS);

				GOval circle = new GOval(getWidth() / 2 - radius, getHeight() / 2 - radius, 2 * radius, 2 * radius);
				
				/*
				 * which one is "t" - even of odd, determines the progression of the circle colors on the target -
				 * (black,red,black,red...) or (red,black,red,black...)
				 */
				if (t % 2 == 0) { 
					if (i % 2 == 1) {
						circle.setFilled(true);
						circle.setFillColor(Color.black);
					} else {
						circle.setFilled(true);
						circle.setFillColor(Color.red);
					}

				} else {
					if (i % 2 == 1) {
						circle.setFilled(true);
						circle.setFillColor(Color.red);
					} else {
						circle.setFilled(true);
						circle.setFillColor(Color.black);
					}

				}
				add(circle);

			}
			t++;
		}
	}
}
