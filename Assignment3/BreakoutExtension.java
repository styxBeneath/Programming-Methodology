
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */
import static java.lang.Math.abs;
import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BreakoutExtension extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int PAUSE_TIME = 10;

	/** number of hits needed to remove single brick from canvas */
	private static final int brickLife = 3;

	/*
	 * during a single try, "CHANGE_PERIOD" is the number of brick hits we need to
	 * increase vy,decrease the width of paddle and increase the increment of score
	 */
	private static final int CHANGE_PERIOD = 10;

	/** total number of hits */
	private static final int HITS_NEEDED = NBRICKS_PER_ROW * NBRICK_ROWS * brickLife;

	/*
	 * increment of vy by single period. vy must be less than bick's width,in order
	 * to avoid the ball skipping nearest brick.
	 */
	private static final double acceleration = (double) (BRICK_HEIGHT - 4) / HITS_NEEDED * CHANGE_PERIOD;

	/*
	 * decrement of paddle by single period. paddle's width must be more than ball's
	 * diameter
	 */
	private static final double PADDLE_DECREMENT = (double) (PADDLE_WIDTH - 2 * BALL_RADIUS) / HITS_NEEDED
			* CHANGE_PERIOD;

	/*
	 * minimum increment of score by single hit
	 */
	private static final int SINGLE_HIT_SCORE = 10;

	/*
	 * after "CHANGE_PERIOD" hits, score increment will increase by
	 * "SCORE_INCREASE_COEFFICIENT" after 2*"CHANGE_PERIOD" hits, score increment
	 * will increase by 2*"SCORE_INCREASE_COEFFICIENT"...
	 */
	private static final int SCORE_INCREASE_COEFFICIENT = 2;

	// instance variables and objects

	private int bricksNumber = HITS_NEEDED;// indicates the number of bricks left.
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private GLabel clickToStart;
	private GLabel score;// label which indicates the current score.
	private int hitsCounter;// current number of hits.at the beginning of each try,this variable is 0.
	private int scoreNum = 0;// number which indicates the current score.
	private int numOfTriesLeft = NTURNS;// indicates the number of tries the user has left.
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx, vy;
	private GImage img = new GImage("retroWave.jpg");
	private GHeart heart;// heart shaped object which is created in attached class
	private AudioClip failClip = MediaTools.loadAudioClip("Fail.au");
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	private AudioClip winnerClip = MediaTools.loadAudioClip("Winner.au");
	private AudioClip gameOverClip = MediaTools.loadAudioClip("GameOver.au");

	public void run() {
		setUpCanvas();// adds bricks and background image
		for (int i = 0; i < NTURNS; i++) {
			setUpComponents(); // adds objects that need to be removed and added again during the game.
			gameProcess(); // this method works until the ball goes out the window

			/*
			 * after the "gameProcess" method,if there are 0 bricks left on the canvas,
			 * "for" loop ends working and program prints"winner".
			 */
			if (bricksNumber == 0) {
				ball.setVisible(false);
				printWinner();
				winnerClip.play();
				break;
			}

		}

		/*
		 * after the end of "for" loop,if there are still some bricks left, it means
		 * that user has lost a game,so program prints "game over"
		 */
		if (bricksNumber > 0) {
			printGameOver();
			gameOverClip.play();
		}
	}

	private void setUpCanvas() {
		add(img);
		img.setBounds(0, 0, getWidth(), getHeight());
		img.sendToBack();
		drawBricks();
	}

	private void setUpComponents() {
		drawScore();
		drawClickToStart();
		drawHeart();
		drawPaddle();
		drawBall();
		hitsCounter = 0;
	}

	// draws the "score:current score"
	private void drawScore() {
		score = new GLabel("score:" + scoreNum);
		score.setColor(Color.RED);
		score.setFont(new Font("Serif", Font.BOLD, 20));
		add(score, getWidth() - score.getWidth() - 5, score.getHeight());
	}

	// draws the "click to start" label
	private void drawClickToStart() {
		clickToStart = new GLabel("Click To Start");
		clickToStart.setFont(new Font("Serif", Font.BOLD, 30));
		clickToStart.setColor(Color.cyan);
		add(clickToStart, getWidth() / 2 - clickToStart.getWidth() / 2,3 * getHeight() / 4 - clickToStart.getHeight() / 2);
	}

	// draws the heart shaped object which indicates the number of tries left
	private void drawHeart() {
		heart = new GHeart(numOfTriesLeft);
		add(heart, 0, 15);
	}

	/*
	 * due to the fact that there are some problems on setting the size of canvas,
	 * we need to determine the width of single brick using a default width of the
	 * canvas,separation between bricks and the number of bricks.
	 */
	private void drawBricks() {
		double brickWidth = (getWidth() - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
		for (int i = 0; i < NBRICK_ROWS; i++) {

			for (int j = 0; j < NBRICKS_PER_ROW; j++) {

				double x = j * (brickWidth + BRICK_SEP);

				double y = BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP);

				brick = new GRect(x, y, brickWidth, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);

				int brickLifeNum = brickLife;

				// we have to determine how many lives does the brick have
				// bricks with 3 lives, have red color
				// bricks with 2 lives, have orange color
				// bricks with 1 life, have green color
				if (brickLifeNum == 3) {
					brick.setColor(Color.RED);
				}
				if (brickLifeNum == 2) {
					brick.setColor(Color.ORANGE);
				}
				if (brickLifeNum == 1) {
					brick.setColor(Color.green);
				}

			}
		}
	}

	// paddle creation
	private void drawPaddle() {
		// at the beginning,paddle stands at the middle of X axis.
		double x = getWidth() / 2 - PADDLE_WIDTH / 2;

		// the Y coordinate is consistent during the game
		double y = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;

		paddle = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);

		paddle.setFilled(true);
		paddle.setFillColor(Color.cyan);
		add(paddle);
		addMouseListeners();
	}

	public void mouseMoved(MouseEvent e) {

		/*
		 * mouse tracks the middle point of the paddle. the paddle changes its location
		 * only if the middle point of the paddle is more than half paddle width and
		 * less than getHeight() - half paddle width.
		 */
		if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
			
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}

	}

	// ball creation
	private void drawBall() {

		// ball starts at the centre of canvas
		double x = getWidth() / 2 - BALL_RADIUS;
		double y = getHeight() / 2 - BALL_RADIUS;

		ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setFillColor(Color.cyan);
		add(ball);
	}

	private void gameProcess() {

		/*
		 * after user clicks the mouse, "click to start" label disappears and the game
		 * starts
		 */
		waitForClick();
		remove(clickToStart);

		getBallVelocity();// determines the values of vx and vy at the beginning

		while (true) {
			
			ballMovement();// single movement of ball

			/*
			 * when ball goes out the bottom border,we remove ball from canvas also we
			 * remove paddle,because it may be decreased in width during the game and at the
			 * new try,we have to set its default size also we remove heart object and score
			 * label to reset them
			 */
			if (ball.getY() >= getHeight()) {
				remove(ball);
				remove(paddle);
				remove(heart);
				remove(score);
				failClip.play();
				numOfTriesLeft--;
				break;
			}

			// if there are 0 bricks left,program ends the "while" loop
			if (bricksNumber == 0) {
				break;
			}
		}
	}

	// determines the speed of ball by X and Y axis
	private void getBallVelocity() {
		vy = 4.0;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}

	}

	// single movement of the ball
	private void ballMovement() {

		ball.move(vx, vy);

		/*
		 * check for the left and right walls,especially, we need to check whether vx is
		 * more or less than 0 at the right and left borders, in order to avoid the
		 * risks of sticking the ball outside the borders.
		 */
		if ((ball.getX() <= 0 && vx < 0) || (ball.getX() >= (getWidth() - BALL_RADIUS * 2) && vx > 0)) {
			vx = -vx;
			bounceClip.play();
		}

		/*
		 * We only have to check for the top border, because we already check for the
		 * bottom border in "gameProcess" method
		 */
		if ((ball.getY() + vy <= 0 && vy < 0)) {
			vy = -vy;
			bounceClip.play();
		}

		// presenting objects at the ball's edges
		GObject collider = getCollidingObject();

		if (collider == paddle) {

			/*
			 * in order to avoid the sticking of the ball beneath the paddle we do next: the
			 * ball changes its Y coordinate by |vy| during each movement, so it doesn't
			 * reflect exactly from the top of the paddle. therefore, we can allow the ball
			 * to cover the paddle only by |vy| pixels to avoid the sticking.
			 */

			if (ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2
					&& ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 + abs(vy)) {
				vy = -vy;
				bounceClip.play();
			}

			/*
			 * if ball goes from left to right and bounces off the left half of paddle, or
			 * if ball goes from right to left and bounces off the right half of paddle, we
			 * also change vx
			 */
			if (vx > 0 && ball.getX() >= paddle.getX() - BALL_RADIUS
					&& ball.getX() < paddle.getX() + PADDLE_WIDTH / 2 - BALL_RADIUS) {
				vx = -vx;
			}
			if (vx < 0 && ball.getX() >= paddle.getX() + PADDLE_WIDTH / 2 - BALL_RADIUS
					&& ball.getX() <= paddle.getX() + PADDLE_WIDTH - BALL_RADIUS) {
				vx = -vx;
			}
		}

		/*
		 * the only colliding object besides the paddle,heart and score is a brick, so
		 * if collider is not equal to null,heart and score,it certainly is the brick.
		 * we remove this brick from canvas if its color is green,change it to green if
		 * it is orange,change it to orange if it is red and decrease the number of the
		 * bricks left by 1,because technically a brick with n lives is equal to n
		 * bricks. also we change vy,so the ball does reflect.
		 */
		else if (collider != null && collider != heart && collider != score) {
			if (collider.getColor() == Color.green) {
				remove(collider);
			}
			if (collider.getColor() == Color.ORANGE) {
				collider.setColor(Color.green);
			}
			if (collider.getColor() == Color.RED) {
				collider.setColor(Color.ORANGE);
			}
			bricksNumber--;
			hitsCounter++;

			// by each period,we increase vy,decrease the paddle and increase the score
			// increment
			if (hitsCounter % CHANGE_PERIOD == 0) {
				if (vy > 0)
					vy += acceleration;
				else
					vy -= acceleration;
				paddle.setSize(paddle.getWidth() - PADDLE_DECREMENT, paddle.getHeight());
			}

			scoreNum += SINGLE_HIT_SCORE + SCORE_INCREASE_COEFFICIENT * (hitsCounter / CHANGE_PERIOD);
			remove(score);
			drawScore();
			vy = -vy;
			bounceClip.play();
		}
		pause(PAUSE_TIME);
	}

	private GObject getCollidingObject() {

		/*
		 * since the image covers the whole canvas,it won't be efficient to return the
		 * image object when there are no other objects on the current points.
		 */
		if ((getElementAt(ball.getX(), ball.getY())) != img) {
			return getElementAt(ball.getX(), ball.getY());
		}
		if (getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY()) != img) {
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
		}
		if (getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != img) {
			return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		}
		if (getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != img) {
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
		}

		// we return null if there are no objects besides image
		return null;

	}

	// we print winner and the final score
	private void printWinner() {
		GLabel Winner = new GLabel("Winner!!!", getWidth() / 2, getHeight() / 2);
		Winner.setFont(new Font("Serif", Font.BOLD, 50));
		removeAll();
		setBackground(Color.green);
		add(Winner);
		Winner.move(-Winner.getWidth() / 2, -Winner.getHeight());
		drawFinalScore();

	}

	// we print game over and the final score
	private void printGameOver() {
		GLabel gameOver = new GLabel("Game Over", getWidth() / 2, getHeight() / 2);
		gameOver.setFont(new Font("Serif", Font.BOLD, 50));
		removeAll();
		setBackground(Color.RED);
		add(gameOver);
		gameOver.move(-gameOver.getWidth() / 2, -gameOver.getHeight());
		drawFinalScore();

	}

	// draws the final score
	private void drawFinalScore() {
		score = new GLabel("Final Score:" + scoreNum);
		score.setColor(Color.BLACK);
		score.setFont(new Font("Serif", Font.BOLD, 20));
		add(score, getWidth() / 2 - score.getWidth() / 2, getHeight() / 2 - score.getHeight() / 2);
	}
}
