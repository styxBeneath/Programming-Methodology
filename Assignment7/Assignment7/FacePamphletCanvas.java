
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		message = new GLabel("");
		message.setFont(MESSAGE_FONT);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		remove(message);
		message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// System.out.println(profile.toString());
		removeAll();
		if (profile != null) {
			addName(profile.getName());
			addPicture(profile.getImage());
			addStatus(profile.getStatus(), profile.getName());
			addFriends(profile.getFriends());
		}

	}

	private void addName(String name) {
		GLabel nameLab = new GLabel(name);
		nameLab.setFont(PROFILE_NAME_FONT);
		nameLab.setColor(Color.BLUE);
		add(nameLab, LEFT_MARGIN, TOP_MARGIN + nameLab.getHeight());
		// this variable saves the Y coordinate of name's bottom point
		endOfName = TOP_MARGIN + nameLab.getHeight();
	}

	private void addPicture(GImage image) {
		if (image == null) {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect, LEFT_MARGIN, endOfName + IMAGE_MARGIN);
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			double labelX = LEFT_MARGIN + IMAGE_WIDTH / 2 - label.getWidth() / 2;
			double labelY = endOfName + IMAGE_MARGIN + IMAGE_HEIGHT / 2 + label.getAscent() / 2;
			add(label, labelX, labelY);
			// this variable saves the Y coordinate of image's bottom point
			endOfImage = endOfName + IMAGE_MARGIN + IMAGE_HEIGHT;
		} else {

			/*
			 * if picture isn't square-shaped,then we set the largest side "IMAGE_HEIGHT"
			 * pixels in length,and we set smallest side's lenght by proportion
			 */
			double side;
			if (image.getWidth() > image.getHeight()) {
				side = image.getWidth();
			} else {
				side = image.getHeight();
			}
			double factor = IMAGE_HEIGHT / side;
			image.scale(factor);
			add(image, LEFT_MARGIN, endOfName + IMAGE_MARGIN);
		}
	}

	private void addStatus(String status, String name) {
		GLabel label;
		if (status.equals("")) {
			label = new GLabel("No current status");

		} else {
			label = new GLabel(name + " is " + status);
		}
		label.setFont(PROFILE_STATUS_FONT);
		add(label, LEFT_MARGIN, endOfImage + STATUS_MARGIN + label.getAscent() / 2);
	}

	private void addFriends(Iterator<String> friends) {
		GLabel friendsLabel = new GLabel("Friends:");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendsLabel, getWidth() / 2, endOfName + IMAGE_MARGIN + friendsLabel.getAscent());

		// this variable saves the Y coordinate of each friend label's top point
		double YDistance = endOfName + IMAGE_MARGIN + friendsLabel.getAscent() + DISTANCE_BETWEEN_FRIENDS;
		while (friends.hasNext()) {
			String nextName = friends.next();
			GLabel friend = new GLabel(nextName);
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, YDistance + friend.getAscent());
			YDistance += friend.getAscent() + DISTANCE_BETWEEN_FRIENDS;

		}
	}

	private double endOfName;
	private double endOfImage;
	private GLabel message;

}
