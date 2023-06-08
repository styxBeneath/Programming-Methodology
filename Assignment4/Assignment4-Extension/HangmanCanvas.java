
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.applet.AudioClip;
import java.awt.Font;

import acm.graphics.*;
import acm.util.MediaTools;

public class HangmanCanvas extends GCanvas {
	private int stage = 1;
	private GLabel wordLabel;
	private GLabel lettersLabel;
	private GLabel definitionLabel;
	private GImage loseImg = new GImage("loseImg.jpg");
	private GImage winImg = new GImage("winImg.jpg");
	private GImage EndOfGameImg = new GImage("EndOfGame.png");
	private AudioClip wrongLetterAudio = MediaTools.loadAudioClip("wrongLetterAudio.au");
	String wrongLetters = "";

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		this.removeAll();

		drawScaffold();
		wordLabel = new GLabel("", 10, 30);
		add(wordLabel);
		lettersLabel = new GLabel("", 10, 50);
		add(lettersLabel);
		stage = 1;
		wrongLetters = "";
	}

	// adds scaffold on canvas.
	private void drawScaffold() {
		GLine scaffold = new GLine(getWidth() / 2 - BEAM_LENGTH, SCAFFOLD_OFFSET, getWidth() / 2 - BEAM_LENGTH,
				SCAFFOLD_HEIGHT + SCAFFOLD_OFFSET);
		add(scaffold);
		GLine beam = new GLine(getWidth() / 2 - BEAM_LENGTH, SCAFFOLD_OFFSET, getWidth() / 2, SCAFFOLD_OFFSET);
		add(beam);
		GLine rope = new GLine(getWidth() / 2, SCAFFOLD_OFFSET, getWidth() / 2, SCAFFOLD_OFFSET + ROPE_LENGTH);
		add(rope);
	}

	// adds the head on canvas.
	private void drawHead() {
		GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head, getWidth() / 2 - HEAD_RADIUS, ROPE_LENGTH + SCAFFOLD_OFFSET);
	}

	// adds the body on canvas.
	private void drawBody() {
		double bodyOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS;
		GLine body = new GLine(getWidth() / 2, bodyOffSet, getWidth() / 2, bodyOffSet + BODY_LENGTH);
		add(body);
	}

	// adds the left hand on canvas.
	private void drawLeftHand() {
		double handOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine upperHand = new GLine(getWidth() / 2, handOffSet, getWidth() / 2 - UPPER_ARM_LENGTH, handOffSet);
		GLine lowerHand = new GLine(getWidth() / 2 - UPPER_ARM_LENGTH, handOffSet, getWidth() / 2 - UPPER_ARM_LENGTH,
				handOffSet + LOWER_ARM_LENGTH);
		add(upperHand);
		add(lowerHand);
	}

	// adds the right hand on canvas.
	private void drawRightHand() {
		double handOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		GLine upperHand = new GLine(getWidth() / 2, handOffSet, getWidth() / 2 + UPPER_ARM_LENGTH, handOffSet);
		GLine lowerHand = new GLine(getWidth() / 2 + UPPER_ARM_LENGTH, handOffSet, getWidth() / 2 + UPPER_ARM_LENGTH,
				handOffSet + LOWER_ARM_LENGTH);
		add(upperHand);
		add(lowerHand);
	}

	// adds the left leg on canvas.
	private void drawLeftLeg() {
		double legOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		GLine leftHip = new GLine(getWidth() / 2, legOffSet, getWidth() / 2 - HIP_WIDTH, legOffSet);
		GLine leftLeg = new GLine(getWidth() / 2 - HIP_WIDTH, legOffSet, getWidth() / 2 - HIP_WIDTH,
				legOffSet + LEG_LENGTH);
		add(leftHip);
		add(leftLeg);
	}

	// adds the right leg on canvas.
	private void drawRightLeg() {
		double legOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		GLine leftHip = new GLine(getWidth() / 2, legOffSet, getWidth() / 2 + HIP_WIDTH, legOffSet);
		GLine leftLeg = new GLine(getWidth() / 2 + HIP_WIDTH, legOffSet, getWidth() / 2 + HIP_WIDTH,
				legOffSet + LEG_LENGTH);
		add(leftHip);
		add(leftLeg);
	}

	// adds the left foot on the canvas.
	private void drawLeftFoot() {
		double footOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine leftFoot = new GLine(getWidth() / 2 - HIP_WIDTH, footOffSet, getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
				footOffSet);
		add(leftFoot);
	}

	// adds the right foot on the canvas.
	private void drawRightFoot() {
		double footOffSet = SCAFFOLD_OFFSET + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine rightFoot = new GLine(getWidth() / 2 + HIP_WIDTH, footOffSet, getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
				footOffSet);
		add(rightFoot);
	}

	public void displayDefinition(String definition) {

		definitionLabel = new GLabel(definition);
		double definitionLabelOffSet = SCAFFOLD_OFFSET / 2 + definitionLabel.getHeight() / 2;
		add(definitionLabel, getWidth() / 2 - definitionLabel.getWidth() / 2, definitionLabelOffSet);

	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		double wordLabelOffSet = SCAFFOLD_OFFSET + SCAFFOLD_HEIGHT + WORD_OFFSET_FROM_SCAFFOLD;
		remove(wordLabel);
		wordLabel = new GLabel(word);
		wordLabel.setFont(new Font("Serif", Font.BOLD, 25));
		add(wordLabel, getWidth() / 2 - wordLabel.getWidth() / 2, wordLabelOffSet);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		double lettersLabelOffSet = SCAFFOLD_OFFSET + SCAFFOLD_HEIGHT + WORD_OFFSET_FROM_SCAFFOLD
				+ wordLabel.getHeight() + LETTERS_OFFSET_FROM_WORD;
		if (wrongLetters.indexOf(letter) == -1) {
			wrongLetters = wrongLetters + letter;
		}
		remove(lettersLabel);
		lettersLabel = new GLabel(wrongLetters);
		lettersLabel.setFont(new Font("Serif", Font.BOLD, 15));
		add(lettersLabel, getWidth() / 2 - lettersLabel.getWidth() / 2, lettersLabelOffSet);
		wrongLetterAudio.play();
		if (stage == 1)
			drawHead();
		if (stage == 2)
			drawBody();
		if (stage == 3)
			drawLeftHand();
		if (stage == 4)
			drawRightHand();
		if (stage == 5)
			drawLeftLeg();
		if (stage == 6)
			drawRightLeg();
		if (stage == 7)
			drawLeftFoot();
		if (stage == 8) {
			drawRightFoot();
			
		}
		stage++;
	}
	
	public void addLoseImg() {
		this.removeAll();
		add(loseImg,0,0);
	}
	public void addWinImg() {
		this.removeAll();
		add(winImg,0,0);
	}
	public void addEndOfGameImg() {
		this.removeAll();
		add(EndOfGameImg, 0, 0);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_OFFSET = 70;
	private static final int WORD_OFFSET_FROM_SCAFFOLD = 30;
	private static final int LETTERS_OFFSET_FROM_WORD = 10;
	private static final int SCAFFOLD_HEIGHT = 300;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 15;
	private static final int HEAD_RADIUS = 30;
	private static final int BODY_LENGTH = 120;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 95;
	private static final int FOOT_LENGTH = 28;

}
