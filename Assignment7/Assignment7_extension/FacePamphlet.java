
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	private JTextField statusField;
	private JTextField friendField;
	private JTextField removeFriendField;
	private JTextField imageField;
	private JTextField nameField;
	private JButton add;
	private JButton delete;
	private JButton lookup;
	private JButton changeStatus;
	private JButton changePic;
	private JButton addFriend;
	private JButton removeFriend;
	private JLabel nameLabel;
	private JLabel emptyLabel1;
	private JLabel emptyLabel2;
	private JLabel emptyLabel3;
	private FacePamphletDatabase dataBase;
	private FacePamphletProfile profile;
	private FacePamphletCanvas canvas;
	private String currentName = ""; // saves the name of current profile

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		createWestField();
		createNorthField();
		canvas = new FacePamphletCanvas();
		add(canvas, CENTER);
		dataBase = new FacePamphletDatabase();
		addActionListeners();
	}

	// adds interactors to the west field
	private void createWestField() {
		statusField = new JTextField(TEXT_FIELD_SIZE);
		add(statusField, WEST);
		statusField.addActionListener(this);
		statusField.setActionCommand("Change Status");
		changeStatus = new JButton("Change Status");
		add(changeStatus, WEST);
		emptyLabel1 = new JLabel(EMPTY_LABEL_TEXT);
		add(emptyLabel1, WEST);
		imageField = new JTextField(TEXT_FIELD_SIZE);
		add(imageField, WEST);
		imageField.addActionListener(this);
		imageField.setActionCommand("Change Picture");
		changePic = new JButton("Change Picture");
		add(changePic, WEST);
		emptyLabel2 = new JLabel(EMPTY_LABEL_TEXT);
		add(emptyLabel2, WEST);
		friendField = new JTextField(TEXT_FIELD_SIZE);
		add(friendField, WEST);
		friendField.addActionListener(this);
		friendField.setActionCommand("Add Friend");
		addFriend = new JButton("Add Friend");
		add(addFriend, WEST);
		emptyLabel3 = new JLabel(EMPTY_LABEL_TEXT);
		add(emptyLabel3, WEST);
		removeFriendField = new JTextField(TEXT_FIELD_SIZE);
		add(removeFriendField, WEST);
		removeFriend = new JButton("Remove Friend");
		add(removeFriend, WEST);
		removeFriendField.setActionCommand("Remove Friend");
	}

	// adds interactors to the north field
	private void createNorthField() {
		nameLabel = new JLabel("Name");
		add(nameLabel, NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		add = new JButton("Add");
		add(add, NORTH);
		delete = new JButton("Delete");
		add(delete, NORTH);
		lookup = new JButton("Lookup");
		add(lookup, NORTH);
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getActionCommand());
		if (e.getSource() == add) {
			String name = nameField.getText();
			if (name.equals("")) {
				currentName = "";
				// when displayProfile method receives null object,it deletes everything from
				// canvas
				canvas.displayProfile(null);
				canvas.showMessage("Please,select name");
			} else {
				if (dataBase.containsProfile(name)) {

					canvas.displayProfile(dataBase.getProfile(name));
					canvas.showMessage("profile with name " + name + " already exists");
					currentName = name;
				} else {
					profile = new FacePamphletProfile(name);
					dataBase.addProfile(profile);
					dataBase.updateFile();
					canvas.displayProfile(profile);
					canvas.showMessage("New profile created");
					currentName = name;
				}
			}
		}
		if (e.getSource() == delete) {
			String name = nameField.getText();
			if (dataBase.containsProfile(name)) {
				dataBase.deleteProfile(name);
				dataBase.removeFromFriendlists(name);
				dataBase.updateFile();
				currentName = "";
				canvas.displayProfile(null);
				canvas.showMessage("profile of " + name + " deleted");
			} else {
				currentName = "";
				canvas.displayProfile(null);
				canvas.showMessage("A profile with the name " + name + " does not exist");
			}
		}
		if (e.getSource() == lookup) {
			String name = nameField.getText();
			if (dataBase.containsProfile(name)) {
				currentName = name;
				canvas.displayProfile(dataBase.getProfile(name));
				canvas.showMessage("Displaying " + name);
				// System.out.println(dataBase.getProfile(name).getImageName()+" === ");

			} else {
				currentName = "";
				canvas.displayProfile(null);
				canvas.showMessage("A profile with the name " + name + " does not exist");

			}
		}

		if (e.getActionCommand() == "Change Status") {
			if (currentName.equals("")) {
				canvas.showMessage("Please select a profile to change status");
			} else {
				// we consider that is user inputs empty string,it means to delete current
				// status
				if (statusField.getText().equals("")) {
					dataBase.getProfile(currentName).setStatus("");
					canvas.displayProfile(dataBase.getProfile(currentName));
					canvas.showMessage("Status deleted");
					dataBase.updateFile();
				} else {
					dataBase.getProfile(currentName).setStatus(statusField.getText());
					canvas.displayProfile(dataBase.getProfile(currentName));
					canvas.showMessage("Status updated to " + statusField.getText());
					dataBase.updateFile();
				}
			}
		}

		if (e.getActionCommand() == "Add Friend") {
			if (currentName.equals("")) {
				canvas.showMessage("Please select a profile to add friend");
			} else {
				if (friendField.getText().equals("")) {
					canvas.showMessage("'Add Friend' field is empty,please type the name of friend");
				} else {
					if (currentName.equals(friendField.getText())) {
						canvas.showMessage(currentName + " cannot be friends with themselves");
					} else if (dataBase.containsProfile(friendField.getText())) {
						if (dataBase.getProfile(currentName).isFriendsWith(friendField.getText())) {
							canvas.showMessage(currentName + " is already friends with " + friendField.getText());
						} else {
							dataBase.getProfile(currentName).addFriend(friendField.getText());
							dataBase.getProfile(friendField.getText()).addFriend(currentName);
							canvas.displayProfile(dataBase.getProfile(currentName));
							canvas.showMessage(friendField.getText() + " added as a friend");
							dataBase.updateFile();
						}
					} else {
						canvas.showMessage("A profile with the name " + friendField.getText() + " does not exist");
					}
				}
			}
		}

		if (e.getActionCommand() == "Change Picture") {
			if (currentName.equals("")) {
				canvas.showMessage("Please select a profile to change picture");
			} else {
				// we consider that is user inputs empty string,it means to delete current
				// profile picture
				if (imageField.getText().equals("")) {
					dataBase.getProfile(currentName).setImage(null);
					// if profile doesn't have an image,we save this information in the file of
					// profiles
					dataBase.getProfile(currentName).setImageName("emptyImage");
					canvas.displayProfile(dataBase.getProfile(currentName));
					canvas.showMessage("Picture deleted");
					dataBase.updateFile();
				} else {
					try {
						dataBase.getProfile(currentName).setImage(new GImage(imageField.getText()));
						dataBase.getProfile(currentName).setImageName(imageField.getText());
						canvas.displayProfile(dataBase.getProfile(currentName));
						canvas.showMessage("Picture updated");
						dataBase.updateFile();
					} catch (Exception e1) {
						canvas.showMessage("Picture with name " + imageField.getText() + " does not exist");
					}
				}
			}
		}

		if (e.getActionCommand() == "Remove Friend") {
			if (currentName.equals("")) {
				canvas.showMessage("Please select a profile to remove friend");
			} else {
				if (removeFriendField.getText().equals("")) {
					canvas.showMessage("'Remove Friend' field is empty,please type the name of friend");
				} else {
					if (dataBase.containsProfile(removeFriendField.getText())) {
						if (dataBase.getProfile(currentName).isFriendsWith(removeFriendField.getText())) {
							dataBase.getProfile(currentName).removeFriend(removeFriendField.getText());
							dataBase.getProfile(removeFriendField.getText()).removeFriend(currentName);
							canvas.displayProfile(dataBase.getProfile(currentName));
							canvas.showMessage(removeFriendField.getText() + " removed from friendlist");
							dataBase.updateFile();
						} else {

							canvas.showMessage(currentName + " is not friends with " + removeFriendField.getText());
						}
					} else {
						canvas.showMessage(
								"A profile with the name " + removeFriendField.getText() + " does not exist");
					}
				}
			}
		}

	}

}
