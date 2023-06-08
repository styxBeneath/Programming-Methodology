
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {
	// contains names and data of the graphs that are drawn on canvas
	private Map<String, int[]> data;

	private int colorCount = 1;

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		data = new LinkedHashMap<>();
		addComponentListener(this);

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		data.clear();
		removeAll();
		updateTable(); // draws empty graph table
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		int[] Ranks = new int[NDECADES];
		String name = entry.getName();
		for (int i = 0; i < NDECADES; i++) {
			Ranks[i] = entry.getRank(i + 1);
		}
		data.put(name, Ranks);
		// System.out.println(Arrays.toString(Ranks));
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		colorCount = 1;
		removeAll();
		updateTable();
		updateGraph();
	}

	private void updateTable() {
		updateHorizontalLines();
		updateVerticalLines();
		updateDecades();
	}

	private void updateVerticalLines() {
		/*
		 * the leftmost and the rightmost vertical lines are drawn 2 pixels away from
		 * edges of the window so that they should appear on the canvas. this detail is
		 * also provided in other methods that are responsible to create an empty table
		 * on canvas.
		 */
		double distance = (double) (getWidth() - 4) / NDECADES;
		for (int i = 0; i <= NDECADES; i++) {
			double Xupper = 2 + i * distance;
			double Yupper = 0;
			double Xlower = 2 + i * distance;
			double Ylower = getHeight();

			add(new GLine(Xupper, Yupper, Xlower, Ylower));
		}
	}

	private void updateHorizontalLines() {
		double Xupper1 = 2;
		double Yupper1 = GRAPH_MARGIN_SIZE;
		double Xlower1 = getWidth() - 2;
		double Ylower1 = GRAPH_MARGIN_SIZE;
		double Xupper2 = 2;
		double Yupper2 = getHeight() - GRAPH_MARGIN_SIZE;
		double Xlower2 = getWidth() - 2;
		double Ylower2 = getHeight() - GRAPH_MARGIN_SIZE;
		add(new GLine(Xupper1, Yupper1, Xlower1, Ylower1));
		add(new GLine(Xupper2, Yupper2, Xlower2, Ylower2));
	}

	private void updateDecades() {
		double distance = (double) (getWidth() - 4) / NDECADES;
		for (int i = 0; i < NDECADES; i++) {
			int decade = START_DECADE + i * 10;
			GLabel decLabel = new GLabel(Integer.toString(decade));
			double x = 2 + (i + 0.5) * distance - decLabel.getWidth() / 2;
			double y = getHeight() - GRAPH_MARGIN_SIZE / 2 + decLabel.getHeight() / 2;

			add(decLabel, x, y);
		}
	}

	private void updateGraph() {
		for (String key : data.keySet()) {
			Color color = selectColor(colorCount);
			updateGraphLines(key, color);
			updateGraphNames(key, color);
			colorCount++;
		}

	}

	private Color selectColor(int colorCount) {
		if (colorCount % 4 == 1) {
			return Color.BLACK;
		} else if (colorCount % 4 == 2) {
			return Color.RED;
		} else if (colorCount % 4 == 3) {
			return Color.BLUE;
		} else {
			return Color.YELLOW;
		}

	}

	private void updateGraphLines(String name, Color color) {

		int[] nameRanks = data.get(name);
		double Xdistance = (double) (getWidth() - 4) / NDECADES;
		double Ydistance = (double) (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
		for (int i = 0; i < NDECADES - 1; i++) {
			double x1 = 2 + i * Xdistance;
			double y1 = getHeight() - GRAPH_MARGIN_SIZE;
			double x2 = 2 + (i + 1) * Xdistance;
			double y2 = getHeight() - GRAPH_MARGIN_SIZE;
			if (nameRanks[i] != 0) {
				y1 = GRAPH_MARGIN_SIZE + Ydistance * nameRanks[i];
			}
			if (nameRanks[i + 1] != 0) {
				y2 = GRAPH_MARGIN_SIZE + Ydistance * nameRanks[i + 1];
			}
			GLine line = new GLine(x1, y1, x2, y2);
			line.setColor(color);
			add(line);

		}
	}

	private void updateGraphNames(String name, Color color) {
		int[] nameRanks = data.get(name);
		double Xdistance = (double) (getWidth() - 4) / NDECADES;
		double Ydistance = (double) (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
		for (int i = 0; i < NDECADES; i++) {
			double x = 2 + i * Xdistance;
			double y = getHeight() - GRAPH_MARGIN_SIZE;
			if (nameRanks[i] != 0) {
				y = GRAPH_MARGIN_SIZE + Ydistance * nameRanks[i];
				GLabel label = new GLabel(name + " " + Integer.toString(nameRanks[i]));
				label.setColor(color);
				add(label, x, y);
			} else {
				GLabel label = new GLabel(name + "*");
				label.setColor(color);
				add(label, x, y);

			}

		}

	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
