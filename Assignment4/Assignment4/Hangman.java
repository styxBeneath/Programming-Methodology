
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Hangman extends ConsoleProgram {

	/* instance variables and objects */
	private static final int CHANCES_NUM = 8;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String word = ""; // The word that a user has to guess.
	private String presentChars = ""; // guessed characters during the game.
	private String currentString = ""; // word that appears during the game.
	private String FinalWord; // full form of the word.
	private int chancesLeft = CHANCES_NUM;
	private boolean letterExists; // tells if whether inputed letter exists in the word or is already guessed.
	private HangmanCanvas canvas;
	private boolean userWantsToContinue = true;
	private HangmanLexicon lexicon = new HangmanLexicon();

	// setting-up the canvas part.
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		while (userWantsToContinue) {

			canvas.reset();

			setUpConsole();

			while (true) {

				printCurrentCondition();
				// user prints a character.
				char letter = readChar("Your guess: ");
				// tells whether inputed character is a letter.
				boolean letterCheck = Character.isLetter(letter);

				// notifies an user to input a proper symbol,if the inputed symbol isn't a
				// character.
				if (!letterCheck) {
					println("Please enter a letter");
					continue;
				}

				/*
				 * this method sets the boolean variable letterExists as true if inputed
				 * character exists in the word or is already guessed.otherwise it sets the
				 * boolean variable as false.
				 */
				findLetterInWord(letter);

				if (letterExists) {
					println("That guess is correct.");
				} else {
					println("There are no " + letter + "'s in the word.");
				}

				// game ends after a user guesses the word or misses all the chances.
				if (chancesLeft == 0 || currentString.equals(FinalWord)) {
					break;
				}

			}
			if (currentString.equals(FinalWord)) {
				printYouWin();
			} else {
				printYouLose();
			}
			askToContinue();
		}
	}

	private void setUpConsole() {
		presentChars = "";
		currentString = "";
		chancesLeft = CHANCES_NUM;
		word = readWord();
		FinalWord = word; // saves full version of word,because we change "word" variable during the game.

		/*
		 * sets up the string which appears during the game at first,it consists of only
		 * '-' s.
		 */
		for (int i = 0; i < word.length(); i++) {
			currentString += '-';
		}
		println("Welcome to Hangman!");
		// draws "locked" word on the canvas;
		canvas.displayWord(currentString);
	}

	private String readWord() {
		int numOfString = rgen.nextInt(0, lexicon.getWordCount()-1);
		return lexicon.getWord(numOfString);
	}

	// inputting of a character.
	private char readChar(String s) {
		String letter;
		while (true) {
			letter = readLine(s);
			if (letter.length() > 0) {
				break;
			}

		}
		// if user inputs more than one character,we choose the first one.
		char c = letter.charAt(0);

		/*
		 * if the character is a small letter, we make it a big one and return it as a
		 * inputed letter.
		 */
		if (c >= 'a' && c <= 'z') {
			c -= 'a' - 'A';
		}
		return c;
	}

	// prints following lines during the game
	private void printCurrentCondition() {
		println("The word now looks like this: " + currentString);
		println("You have " + chancesLeft + " guesses left.");
	}

	// finds an inputed letter in the word and marks the result in a boolean
	// variable.
	private void findLetterInWord(char letter) {
		int indexInWord = indexOfLetter(letter, word); // index of the letter in the word.
		// index of the letter in the string made up with guessed words
		int indexInpresentChars = indexOfLetter(letter, presentChars);

		// the case when the letter doesn't exist in the word.
		if (indexInWord == -1 && indexInpresentChars == -1) {
			chancesLeft--;
			canvas.noteIncorrectGuess(letter);
			letterExists = false;
		}

		// the case when the letter exists in the word.
		if (indexInWord != -1) {
			letterExists = true;

			// we switch this particular letter with '-' in the word.
			word = word.substring(0, indexInWord) + '-' + word.substring(indexInWord + 1);
			currentString = currentString.substring(0, indexInWord) + letter + currentString.substring(indexInWord + 1);
			canvas.displayWord(currentString);
			presentChars += letter;
		}
		// the case when there are no such letters left in the word,but the letter is
		// already guessed
		if (indexInWord == -1 && indexInpresentChars != -1) {
			letterExists = true;
		}

	}

	// returns the index of certain character in a certain string.
	private int indexOfLetter(char letter, String word) {
		for (int index = 0; index < word.length(); index++) {
			char c = word.charAt(index);
			int abs = letter - c;
			if (abs == 0) {
				return index;
			}
		}
		return -1;
	}

	// win notification.
	private void printYouWin() {
		println("You guessed the word: " + FinalWord);
		println("You win");
	}

	// lose notification
	private void printYouLose() {
		println("You're completely hung.");
		println("The word was: " + FinalWord);
		println("You lose.");
	}

	// checks if user wants to continue the game.
	private void askToContinue() {
		println("End the game: print '0';" + '\n' + "Continue the game: print any other symbol;");
		char c = readChar("State your choice:");
		if (c == '0') {
			userWantsToContinue = false;
			println("You've finished the game;");
		}
	}
}
