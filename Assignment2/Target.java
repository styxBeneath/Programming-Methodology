
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

public class Target extends GraphicsProgram {
	// radius of the biggest circle in cm;
	private static final double RADIUS1 = 2.54;
	// radius of the middle circle in cm;
	private static final double RADIUS2 = 1.65;
	// radius of the smallest circle in cm;
	private static final double RADIUS3 = 0.76;
	// value of 1 cm in pixels;
	private static final double CM_TO_PIXELS = 72.0 / 2.54;

	public void run() {
		// radius of the biggest circle in pixels;
		int radius1 = (int) (RADIUS1 * CM_TO_PIXELS);
		// radius of the middle circle in pixels;
		int radius2 = (int) (RADIUS2 * CM_TO_PIXELS);
		// radius of the smallest circle in pixels;
		int radius3 = (int) (RADIUS3 * CM_TO_PIXELS);

		// creation of the biggest circle;
		GOval circle1 = new GOval(getWidth() / 2 - radius1, getHeight() / 2 - radius1, 2 * radius1, 2 * radius1);
		circle1.setFilled(true);
		circle1.setFillColor(Color.RED);

		// creation of the middle circle;
		GOval circle2 = new GOval(getWidth() / 2 - radius2, getHeight() / 2 - radius2, 2 * radius2, 2 * radius2);
		circle2.setFilled(true);
		circle2.setFillColor(Color.WHITE);

		// creation of the smallest circle;
		GOval circle3 = new GOval(getWidth() / 2 - radius3, getHeight() / 2 - radius3, 2 * radius3, 2 * radius3);
		circle3.setFilled(true);
		circle3.setFillColor(Color.RED);

		// drawing these 3 cicles on canvas;
		add(circle1);
		add(circle2);
		add(circle3);
	}
}
