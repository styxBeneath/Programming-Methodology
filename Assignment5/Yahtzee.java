
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private int[][] scoreMatrix = new int[MAX_PLAYERS + 1][N_CATEGORIES + 1];
	private boolean[][] categoryMatrix = new boolean[MAX_PLAYERS + 1][N_CATEGORIES + 1];
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		for (int round = 1; round <= YahtzeeConstants.N_SCORING_CATEGORIES; round++) {
			for (int player = 1; player <= nPlayers; player++) {
				int[] dice = new int[N_DICE];

				firstRoll(dice, player);
				reRoll(dice);
				reRoll(dice);

				checkCategory(dice, player);

			}
		}
		lastUpdateOfScorecard();
		printWinners();
	}

	private void firstRoll(int[] dice, int player) {
		display.printMessage(playerNames[player - 1] + "'s turn! Click 'Roll Dice' button to roll the dice.");
		display.waitForPlayerToClickRoll(player);

//		dice[0] = 4;
//		dice[1] = 4;
//		dice[2] = 3;
//		dice[3] = 2;
//		dice[4] = 1;

		for (int i = 0; i < N_DICE; i++) {

			dice[i] = rgen.nextInt(1, 6);
		}

		display.displayDice(dice);

	}

	private void reRoll(int[] dice) {
		display.printMessage("Select the dice you wish to re-roll and click 'Roll Again'.");
		display.waitForPlayerToSelectDice();

		for (int i = 0; i < N_DICE; i++) {
			if (display.isDieSelected(i)) {
				dice[i] = rgen.nextInt(1, 6);
			}
		}

		display.displayDice(dice);

	}

	private void checkCategory(int[] dice, int player) {
		display.printMessage("Select a category for this roll.");

		while (true) {
			int category = display.waitForPlayerToSelectCategory();

			// A case where the category isn't already chosen
			if (!categoryMatrix[player][category]) {
				int score = scoreCounter(dice, category);

				// saves the score for certain players certain category
				scoreMatrix[player][category] = score;

				// states that the category for certain player is already chosen
				categoryMatrix[player][category] = true;

				// counts and updates total score for current player by each round
				updateTotalScore(player);
				display.updateScorecard(category, player, score);
				break;

			} else {
				display.printMessage("Please, select an available category.");
			}

		}

	}

	// counts a relevant score for chosen category for certain combination of dices
	private int scoreCounter(int[] dice, int category) {
		int score = 0;
		if (category == ONES || category == TWOS || category == THREES || category == FOURS || category == FIVES
				|| category == SIXES) {
			score = checkOneToSix(dice, category);
		}
		if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
			score = checkKinds(dice, category);
		}
		if (category == FULL_HOUSE) {
			score = checkFullHouse(dice);
		}
		if (category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) {
			score = checkStraights(dice, category);
		}
		if (category == YAHTZEE) {
			score = checkYahtzee(dice);
		}
		if (category == CHANCE) {
			score = takeChance(dice);
		}
		return score;
	}

	// checks first six categories
	private int checkOneToSix(int[] dice, int category) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (dice[i] == category) {
				point++;
			}
		}

		return category * point;

	}

	// checks three of a kind and four of a kind
	private int checkKinds(int[] dice, int category) {
		int point = 0;
		int kind = 0;
		if (category == THREE_OF_A_KIND) {
			kind = 3;
		} else {
			kind = 4;
		}
		int[] values = new int[7];

		// counts how many dices are presented for 1-6 integers
		for (int i = 0; i < N_DICE; i++) {
			values[dice[i]]++;
			point += dice[i]; // sums up each dies number
		}

		for (int i = 1; i <= 6; i++)
			if (values[i] >= kind)
				return point;

		return 0;

	}

	private int checkFullHouse(int[] dice) {
		int value1 = dice[0];
		int value2 = 0;
		int nValue1 = 1;
		int nValue2 = 0;

		// saves first present die with different value than the first die
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] != value1) {
				value2 = dice[i];
				break;
			}
		}
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] == value1) {
				nValue1++;
			}
			if (dice[i] == value2) {
				nValue2++;
			}
		}

		if ((nValue1 == 2 && nValue2 == 3) || (nValue1 == 3 && nValue2 == 2)) {
			return 25;
		}

		return 0;

	}

	// checks small straight and large straight
	private int checkStraights(int[] dice, int category) {

		// case of small straight
		if (category == SMALL_STRAIGHT) {
			boolean[] arr = new boolean[7];

			for (int i = 0; i < N_DICE; i++) {
				arr[dice[i]] = true;
			}
			if (arr[1] && arr[2] && arr[3] && arr[4]) {
				return 30;
			}

			if (arr[2] && arr[3] && arr[4] && arr[5]) {
				return 30;
			}
			if (arr[3] && arr[4] && arr[5] && arr[6]) {
				return 30;
			}
			return 0;
		}

		// case of large straight
		if (category == LARGE_STRAIGHT) {
			boolean[] arr = new boolean[7];
			for (int i = 0; i < N_DICE; i++) {
				arr[dice[i]] = true;
			}
			if (arr[1] && arr[2] && arr[3] && arr[4] && arr[5]) {
				return 40;
			}
			if (arr[2] && arr[3] && arr[4] && arr[5] && arr[6]) {
				return 40;
			}
		}
		return 0;
	}

	private int checkYahtzee(int[] dice) {
		int die1 = dice[0];
		for (int i = 1; i < N_DICE; i++) {
			if (dice[i] != die1) {
				return 0;
			}
		}
		return 50;

	}

	private int takeChance(int[] dice) {
		int point = 0;
		for (int i = 0; i < N_DICE; i++) {
			point += dice[i];
		}

		return point;
	}

	private void updateTotalScore(int player) {
		int point = 0;
		for (int category = ONES; category <= CHANCE; category++) {
			if (category != UPPER_SCORE) {
				point += scoreMatrix[player][category];
			}
		}

		scoreMatrix[player][TOTAL] = point;
		display.updateScorecard(TOTAL, player, point);

	}

	private void lastUpdateOfScorecard() {
		updateUpperAndBonusScores();
		updateLowerScore();
		for (int player = 1; player <= nPlayers; player++) {
			updateTotalScore(player);
		}
	}

	private void updateUpperAndBonusScores() {
		for (int player = 1; player <= nPlayers; player++) {
			int point = 0;
			for (int category = ONES; category <= SIXES; category++) {
				point += scoreMatrix[player][category];
			}

			if (point >= 63) {
				scoreMatrix[player][UPPER_BONUS] = 35;
				display.updateScorecard(UPPER_BONUS, player, scoreMatrix[player][UPPER_BONUS]);
			}
			display.updateScorecard(UPPER_SCORE, player, point);
		}

	}

	private void updateLowerScore() {
		for (int player = 1; player <= nPlayers; player++) {
			int point = 0;
			for (int category = THREE_OF_A_KIND; category <= CHANCE; category++) {
				point += scoreMatrix[player][category];
			}

			display.updateScorecard(LOWER_SCORE, player, point);
		}

	}

	private void printWinners() {
		int max = 0;

		// saves the maximum total score
		for (int player = 1; player <= nPlayers; player++) {
			if (max < scoreMatrix[player][TOTAL]) {
				max = scoreMatrix[player][TOTAL];
			}
		}

		String winners = "";

		// saves the names of winners,even if there are more than one
		for (int player = 1; player <= nPlayers; player++) {
			if (scoreMatrix[player][TOTAL] == max) {
				winners += playerNames[player - 1] + ", ";
			}
		}

		display.printMessage("Congratulations, " + winners + "you're the winner with a total score of " + max + "!");

	}

}
