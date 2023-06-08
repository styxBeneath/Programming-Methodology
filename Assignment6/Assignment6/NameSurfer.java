
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	private JLabel nameLabel;
	private JTextField nameField;
	private JButton graph;
	private JButton clear;
	private NameSurferDataBase base;
	private NameSurferEntry entry;
	private NameSurferGraph Graph;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		nameLabel = new JLabel("Name");
		add(nameLabel, SOUTH);
		nameField = new JTextField(20);
		add(nameField, SOUTH);
		nameField.addActionListener(this);
		graph = new JButton("Graph");
		add(graph, SOUTH);
		clear = new JButton("Clear");
		add(clear, SOUTH);
		Graph = new NameSurferGraph();
		add(Graph, CENTER);
		base = new NameSurferDataBase(NAMES_DATA_FILE);
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == nameField || e.getSource() == graph) {
			String name = nameField.getText();
			nameField.setText("");
			entry = base.findEntry(name);
			if (entry != null) {
				Graph.addEntry(entry);
				Graph.update();
			}

		}
		if (e.getSource() == clear) {
			Graph.clear();
		}

	}
}
