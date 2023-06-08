
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */
import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {

	// width of each rectangle of the diagram;
	private static final int Width = 150;
	
	// height of each rectangle of the diagram;
	private static final int Height = 60;
	
	// length of the midlle line,which connects "program" and "consoleProgram";
	private static final int distanceBetweenBoxes = 70;
	
	//distance between "graphics" and "console", "console" and "dialog";
	private static final int distanceBetweenBellowBoxes = 75;

	public void run() {
		
		addLines();
		addRectsAndLabels();
		
	}
	
	
	private void addLines() {
		addConsoleLine();
		addGraphicsLine();
		addDialogLine();
	}
	
	
	private void addRectsAndLabels() {
		addProgramRect();
		addConsoleRect();
		addGraphicsRect();
		addDialogRect();
	}
	
	
	private void addProgramRect() {
		int x = getWidth() / 2 - Width / 2; // X coordinate of the starting point of "program" rectangle;
		int y = getHeight() / 2 - Height - distanceBetweenBoxes / 2; // Y coordinate of the starting point of "program" rectangle;
		
		GRect Box = new GRect(x, y, Width, Height);
		add(Box); // adds the Program rectangle;
		
		GLabel programLabel = new GLabel("Program", x, y);
		add(programLabel); // adds the "Program" label on canvas, but on the starting point of the rectangle;
		
		// the "program" label moves to the centre of the rectangle;
		programLabel.move((Width / 2 - programLabel.getWidth() / 2), (Height / 2 + programLabel.getAscent() / 2));
	}

	
	private void addConsoleLine() {
		int x1 = getWidth() / 2; // X coordinate of the starting point of the line;
		int y1 = getHeight() / 2 - distanceBetweenBoxes / 2; // Y coordinate of the starting point of the line;
		int x2 = getWidth() / 2; // X coordinate of the ending point of the line;
		int y2 = getHeight() / 2 + distanceBetweenBoxes / 2; // Y coordinate of the ending point of the line;
		
		GLine Line = new GLine(x1, y1, x2, y2);
		add(Line); // adds the "console" line;
	}

	
	private void addConsoleRect() {
		int x = getWidth() / 2 - Width / 2; // X coordinate of the starting point of "console" rectangle;
		int y = getHeight() / 2 + distanceBetweenBoxes / 2; // Y coordinate of the starting point of "console" rectangle;
		
		GRect Box = new GRect(x, y, Width, Height);
		add(Box); // adds the "console" rectanle;
		
		GLabel consoleLabel = new GLabel("ConsoleProgram", x, y);
		add(consoleLabel); // adds the "console" label on canvas, but on the starting point of the rectangle;
		
		// the "console" label moves to the centre of the rectangle;
		consoleLabel.move((Width / 2 - consoleLabel.getWidth() / 2), (Height / 2 + consoleLabel.getAscent() / 2));
	}

	
	private void addGraphicsLine() {
		int x1 = getWidth() / 2; // X coordinate of the starting point of the line;
		int y1 = getHeight() / 2 - distanceBetweenBoxes / 2; // Y coordinate of the starting point of the line;
		int x2 = getWidth() / 2 - Width - distanceBetweenBellowBoxes; // X coordinate of the ending point of the line;
		int y2 = getHeight() / 2 + distanceBetweenBoxes / 2; // Y coordinate of the ending point of the line;
		
		GLine Line = new GLine(x1, y1, x2, y2);
		add(Line); // adds the "console" line;
	}

	
	private void addGraphicsRect() {
		
		// X coordinate of the starting point of "graphics" rectangle;
		int x = getWidth() / 2 - 3 * (Width / 2) - distanceBetweenBellowBoxes;
		// Y coordinate of the starting point of "graphics" rectangle;
		int y = getHeight() / 2 + distanceBetweenBoxes / 2;
		
		GRect Box = new GRect(x, y, Width, Height);
		add(Box); // adds the "graphics" rectanle;
		
		GLabel graphicsLabel = new GLabel("GraphicsProgram", x, y);
		add(graphicsLabel); // adds the "graphics" label on canvas, but on the starting point of the rectangle;
		
		// the "graphics" label moves to the centre of the rectangle;
		graphicsLabel.move((Width / 2 - graphicsLabel.getWidth() / 2), (Height / 2 + graphicsLabel.getAscent() / 2));
	}

	
	private void addDialogLine() {
		int x1 = getWidth() / 2; // X coordinate of the starting point of the line;
		int y1 = getHeight() / 2 - distanceBetweenBoxes / 2; // Y coordinate of the starting point of the line;
		int x2 = getWidth() / 2 + Width + distanceBetweenBellowBoxes; // X coordinate of the ending point of the line;
		int y2 = getHeight() / 2 + distanceBetweenBoxes / 2; // Y coordinate of the ending point of the line;
		
		GLine Line = new GLine(x1, y1, x2, y2);
		add(Line); // adds the "dialog" line;
	}

	
	private void addDialogRect() {
		// X coordinate of the starting point of "dialog" rectangle;
		int x = getWidth() / 2 + Width / 2 + distanceBetweenBellowBoxes;
		// Y coordinate of the starting point of "dialog" rectangle;
		int y = getHeight() / 2 + distanceBetweenBoxes / 2;
		
		GRect Box = new GRect(x, y, Width, Height);
		add(Box); // adds the "dialog" rectanle;
		
		GLabel dialogLabel = new GLabel("DialogProgram", x, y);
		add(dialogLabel); // adds the "dialog" label on canvas, but on the starting point of the rectangle;
		
		// the "graphics" label moves to the centre of the rectangle;
		dialogLabel.move((Width / 2 - dialogLabel.getWidth() / 2), (Height / 2 + dialogLabel.getAscent() / 2));
	}
}
