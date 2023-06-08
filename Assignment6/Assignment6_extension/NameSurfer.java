
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
	private JLabel nameLabel2;
	private JTextField nameField2;
	private JLabel decadeLabel;
	private JTextField decadeField;
	private JLabel rankLabel;
	private JTextField rankField;
	private JButton search;
	private JButton update;
	private JButton clear;
	private JButton remove;
	private JRadioButton graphMode;
	private JRadioButton chartMode;
	private ButtonGroup bg;
	private NameSurferDataBase base;
	private NameSurferEntry entry;
	private NameSurferGraph Graph;
	private boolean mode = true;

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		addNorthComponents();
		addSouthComponents();

		Graph = new NameSurferGraph();
		add(Graph, CENTER);

		base = new NameSurferDataBase(NAMES_DATA_FILE);
		addActionListeners();
	}

	private void addNorthComponents() {
		nameLabel2 = new JLabel("Name");
		add(nameLabel2, NORTH);
		nameField2 = new JTextField(10);
		add(nameField2, NORTH);
		decadeLabel = new JLabel("Decade");
		add(decadeLabel, NORTH);
		decadeField = new JTextField(10);
		add(decadeField, NORTH);
		search = new JButton("Search");
		add(search, NORTH);
		rankLabel = new JLabel("    Rank");
		add(rankLabel, NORTH);
		rankField = new JTextField(10);
		add(rankField, NORTH);
	}

	private void addSouthComponents() {
		nameLabel = new JLabel("Name");
		add(nameLabel, SOUTH);
		nameField = new JTextField(10);
		add(nameField, SOUTH);
		nameField.addActionListener(this);
		update = new JButton("Update");
		add(update, SOUTH);
		clear = new JButton("Clear");
		add(clear, SOUTH);
		remove = new JButton("Remove");
		add(remove, SOUTH);
		graphMode = new JRadioButton("Graph");
		chartMode = new JRadioButton("Chart");
		bg = new ButtonGroup();
		bg.add(graphMode);
		bg.add(chartMode);
		add(graphMode, SOUTH);
		add(chartMode, SOUTH);
		graphMode.setSelected(true);
		graphMode.addActionListener(this);
		chartMode.addActionListener(this);
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == graphMode) {
			mode = true;
			Graph.selectMode(mode);
			Graph.update();
		}
		if (e.getSource() == chartMode) {
			mode = false;
			Graph.selectMode(mode);
			Graph.update();
		}

		if (e.getSource() == nameField || e.getSource() == update) {
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
		if (e.getSource() == remove) {
			String name = nameField.getText();
			nameField.setText("");
			Graph.remove(name);

		}
		if (e.getSource() == search) {
			String name = nameField2.getText();
			entry = base.findEntry(name);
			Integer decade = null;
			try {
				decade = Integer.parseInt(decadeField.getText());
			} catch (Exception e1) {
				rankField.setText("Invalid input");

			}
			if (decade != null && (int) decade <= 2000 && (int) decade >= 1900 && (int) (decade % 10) == 0
					&& entry != null) {
				int dec = (decade - 1900) / 10 + 1;
				int rank = entry.getRank(dec);
				rankField.setText(Integer.toString(rank));

			} else {
				rankField.setText("Invalid input");

			}

		}

	}
}
