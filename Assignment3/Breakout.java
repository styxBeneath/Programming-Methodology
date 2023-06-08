
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

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

	/** instance variables */
	private int bricksNumber = NBRICKS_PER_ROW * NBRICK_ROWS;
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private double vx, vy;

	public void run() {
		prepareForGame(); // adds bricks and paddle on the canvas
		for (int i = 0; i < NTURNS; i++) {
			drawBall();
			gameProcess(); // this method works until the ball goes out the window

			/*
			 * after the "gameProcess" method,if there are 0 bricks left on the canvas,
			 * "for" loop ends working and program prints"winner".
			 */
			if (bricksNumber == 0) {
				ball.setVisible(false);
				printWinner();
				break;
			}

		}

		/*
		 * after the end of "for" loop,if there are still some bricks left, it means
		 * that user has lost a game,so program prints "game over"
		 */
		if (bricksNumber > 0) {
			printGameOver();
		}
	}

	private void prepareForGame() {
		drawBricks();
		drawPaddle();
	}

	// drawing all the bricks on canvas
	private void drawBricks() {

		/*
		 * due to the fact that there are some problems on setting the size of canvas,
		 * we need to determine the width of single brick using a default width of the
		 * canvas,separation between bricks and the number of bricks.
		 */
		double brickWidth = (getWidth() - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;
		for (int i = 0; i < NBRICK_ROWS; i++) {

			for (int j = 0; j < NBRICKS_PER_ROW; j++) {

				double x = j * (brickWidth + BRICK_SEP);

				double y = BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP);

				brick = new GRect(x, y, brickWidth, BRICK_HEIGHT);
				add(brick);
				brick.setFilled(true);

				if (i < 2) {
					brick.setColor(Color.RED);
				}
				if (i == 2 || i == 3) {
					brick.setColor(Color.ORANGE);
				}
				if (i == 4 || i == 5) {
					brick.setColor(Color.YELLOW);
				}
				if (i == 6 || i == 7) {
					brick.setColor(Color.GREEN);
				}
				if (i == 8 || i == 9) {
					brick.setColor(Color.CYAN);
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
		add(paddle);
		addMouseListeners();
	}

	public void mouseMoved(MouseEvent e) {
		/*
		 * mouse tracks the middle point of the paddle. the paddle changes its location
		 * only if the middle point of the paddle is more than half paddle width and
		 * less than getHeight() - half a paddle width.
		 */
		if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}

	}

	// ball creation
	private void drawBall() {
		double x = getWidth() / 2 -  BALL_RADIUS;
		double y = getHeight() / 2 -  BALL_RADIUS;
		ball = new GOval(x, y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball);
	}

	private void gameProcess() {
		// game starts after the user clicks the mouse.
		waitForClick();
		getBallVelocity();
		while (true) {
			ballMovement();

			/*
			 * when the ball passes the bottom border,program removes it from canvas and
			 * ends the "while" loop
			 */
			if (ball.getY() >= getHeight()) {
				remove(ball);
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
		 * check for the left and right walls,especially we need to check whether vx is
		 * more or less than 0 at the right and left borders, in order to avoid the
		 * risks of sticking the ball outside the borders.
		 */
		if ((ball.getX() <= 0 && vx < 0) || (ball.getX() >= (getWidth() - BALL_RADIUS * 2) && vx > 0)) {
			vx = -vx;
		}
		/*
		 * We only have to check for the top border, because we already check for the
		 * bottom border in "gameProcess" method
		 */
		if ((ball.getY() + vy <= 0 && vy < 0)) {
			vy = -vy;
		}

		// presenting objects at the ball's edges
		GObject collider = getCollidingObject();
		if (collider == paddle) {
			/*
			 * in order to avoid the sticking of the ball beneath the
			 * paddle we do next: the ball changes its Y coordinate by 4 during each movement, so it
			 * doesn't reflect exactly from the top of the paddle. therefore, we can allow
			 * the ball to cover the paddle only by 4 pixels to avoid the sticking.
			 */
			if (ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2
					&& ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 + 4) {
				vy = -vy;
			}
		}
		/*
		 * the only colliding object besides the paddle is a brick, so if collider is
		 * not equal to null,it certainly is the brick. we remove this brick from canvas
		 * and decrease the number of the bricks left by 1; also we change vy,so the
		 * ball does reflect.
		 */
		else if (collider != null) {
			remove(collider);
			bricksNumber--;
			vy = -vy;
		}
		pause(PAUSE_TIME);
	}

	private GObject getCollidingObject() {
		/*
		 * we check for the edges of ball's square. if some object presents at one of
		 * the points, we return that object.
		 */
		if ((getElementAt(ball.getX(), ball.getY())) != null) {
			return getElementAt(ball.getX(), ball.getY());
		}
		if (getElementAt((ball.getX() + BALL_RADIUS * 2), ball.getY()) != null) {
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
		}
		if (getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
		}
		if (getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null) {
			return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
		}

		// we return null if there are no objects present
		return null;

	}

	/*
	 * the method creates "winner" label,adds its leftmost and topmost point at the
	 * center of canvas and then changes its location so that label is at the center
	 * at the end.
	 */
	private void printWinner() {
		GLabel Winner = new GLabel("Winner!!", getWidth() / 2, getHeight() / 2);
		Winner.setColor(Color.RED);
		add(Winner);
		Winner.move(-Winner.getWidth() / 2, -Winner.getHeight());

	}

	/*
	 * the method creates "game over" label,adds its leftmost and topmost point at
	 * the center of canvas and then changes its location so that label is at the
	 * center at the end.
	 */
	private void printGameOver() {
		GLabel gameOver = new GLabel("Game Over", getWidth() / 2, getHeight() / 2);
		gameOver.setColor(Color.RED);
		add(gameOver);
		gameOver.move(-gameOver.getWidth() / 2, -gameOver.getHeight());

	}

}
