package org.lunapark.develop.wobblebubble.screen;

import java.util.Random;

import org.lunapark.develop.wobblebubble.actor.ActorBubble;
import org.lunapark.develop.wobblebubble.actor.ActorFx;
import org.lunapark.develop.wobblebubble.actor.ActorTable;
import org.lunapark.develop.wobblebubble.actor.ActorText;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.base.ScreenBase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ScreenGame extends ScreenBase {

	// Constants

	private int FIELD_SIZE_X = GameConstants.FIELD_SIZE_X;
	private int FIELD_SIZE_Y = GameConstants.FIELD_SIZE_Y;

	private int DELTA_X = (GameConstants.SCREEN_SIZE_X - GameConstants.CELL_SIZE
			* GameConstants.FIELD_SIZE_X) / 2; // Lower left side of board
	private int DELTA_Y = (GameConstants.SCREEN_SIZE_Y - GameConstants.CELL_SIZE
			* GameConstants.FIELD_SIZE_Y) / 2;

	private static final int STEP = 64;

	private int score = 0;

	// Great random
	Random random = new Random();

	// Stage
	private Stage stage;

	// Input listener
	private ActorGestureListener gestureListener;

	// Text
	private ActorText scoreText;

	// Game field
	private ActorBubble[][] gameField = new ActorBubble[FIELD_SIZE_X][FIELD_SIZE_Y];

	// Fling direction
	private enum FlingVector {
		RIGHT, LEFT, UP, DOWN

	}

	// Game variables
	private boolean allowCheck = true, allowFling = true;

	// Effects
	ActorFx actorFx;

	/**
	 * Constructor
	 * 
	 * @param game
	 */
	public ScreenGame(Game game) {
		super(game);
	}

	@Override
	public void show() {

		// Create stage and stretch it to full screen
		stage = new Stage(new StretchViewport(GameConstants.SCREEN_SIZE_X,
				GameConstants.SCREEN_SIZE_Y));

		Gdx.input.setInputProcessor(stage);

		gestureListener = new ActorGestureListener() {

			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				// XXX Delete bubble on tap
				// ActorBubble aBubble = (ActorBubble) event.getListenerActor();
				// System.out.println("i:" + aBubble.getActorTable().i + " j:"
				// + aBubble.getActorTable().j + " type:"
				// + aBubble.getBubbleType());
				// aBubble.setFired();
				// deleteBubble((ActorBubble) event.getListenerActor());

			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
				// super.fling(event, velocityX, velocityY, button);
				ActorBubble a = (ActorBubble) event.getListenerActor();

				if (Math.abs(velocityX) > Math.abs(velocityY)) {
					if (velocityX > 0) {
						moveBubble(a, FlingVector.RIGHT);
					} else {
						moveBubble(a, FlingVector.LEFT);
					}
				} else {
					if (velocityY > 0) {
						moveBubble(a, FlingVector.DOWN);
					} else {
						moveBubble(a, FlingVector.UP);
					}
				}

			}

		};

		Gdx.input.setInputProcessor(stage);
		// Create bg
		Image backgroudImage = new Image(Assets.txGameBackground);

		// Create text labels
		scoreText = new ActorText("Score: " + score);
		scoreText.setPosition(624, 400);

		// Create fx pool actor
		actorFx = new ActorFx();

		// Add bg & actors to stage
		stage.addActor(backgroudImage);
		stage.addActor(scoreText);

		// Create field
		createField();
		checkForProfit();

		// Add fx actor
		stage.addActor(actorFx);
	}

	/**
	 * Create game field
	 */
	private void createField() {

		for (int i = 0; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j < FIELD_SIZE_Y; j++) {
				// Create random bubble
				ActorBubble aBubble = new ActorBubble(
						random.nextInt(Assets.BUBBLE_TYPES));
				aBubble.setActorTable(i, j);
				// Add bubble to gamefield
				gameField[i][j] = aBubble;
				aBubble.setPosition(getXbyI(i), getYbyJ(j));
				aBubble.addListener(gestureListener);
				stage.addActor(aBubble);
			}
		}

		scoreText.setTextValue("Score: " + score);

	}

	/**
	 * Check for blank space
	 */
	private void checkSpaces() {
		if (allowCheck) {

			for (int i = 0; i < FIELD_SIZE_X; i++) {

				int[] bubblesIndexes = new int[FIELD_SIZE_Y];
				int a = 0;

				// Add fired bubbles
				for (int j = 0; j < FIELD_SIZE_Y; j++) {
					if (gameField[i][j].isFired()) {
						// XXX Add fx
						actorFx.addFX(getXbyI(i) + STEP / 2, getYbyJ(j) + STEP
								/ 2);

						bubblesIndexes[a] = j;
						gameField[i][j].setPosition(gameField[i][j].getX(),
								gameField[i][j].getY() - STEP * FIELD_SIZE_Y);
						a++;
					}
				}

				// Have fired bubbles in a column
				if (a > 0) {
					allowCheck = false;
					// Add unfired bubbles
					for (int j = 0; j < FIELD_SIZE_Y; j++) {
						if (!gameField[i][j].isFired()) {
							bubblesIndexes[a] = j;
							a++;
						}
					}

					/**
					 * Construct new column
					 */
					// Duplicate links
					final ActorBubble[] duplicate = new ActorBubble[FIELD_SIZE_Y];
					// FIXME Move graphics
					// for (int j = FIELD_SIZE_Y - 1; j >= 0; j--) {
					for (int j = 0; j < FIELD_SIZE_Y; j++) {
						int jj = bubblesIndexes[j];
						gameField[i][jj].moveIt(getXbyI(i), getYbyJ(j));

						// Fill duplicate array
						duplicate[j] = gameField[i][jj];
						duplicate[j].setActorTable(i, j);
						if (duplicate[j].isFired())
							duplicate[j].clearFired();

					}

					// Move values from duplicate array column
					for (int j = 0; j < FIELD_SIZE_Y; j++) {
						gameField[i][j] = duplicate[j];
					}

					// Delay before next check
					Timer.schedule(new Task() {
						@Override
						public void run() {
							checkForProfit();
							allowCheck = true;
						}
					}, GameConstants.MOVE_DURATION * 2);

					a = 0;

				}

			}
		}
	}

	/**
	 * Convert index i to X
	 * 
	 * @param i
	 * @return
	 */
	private float getXbyI(int i) {
		return i * STEP + DELTA_X;
	}

	/**
	 * Convert index j to Y
	 * 
	 * @param j
	 * @return
	 */
	private float getYbyJ(int j) {
		return j * STEP + DELTA_Y;
	}

	/**
	 * TODO Check for profit
	 */
	private boolean checkForProfit() {

		boolean profit = false;

		ActorTable[] firedTable = new ActorTable[FIELD_SIZE_X * FIELD_SIZE_Y];
		int firedIndex = 0;
		
		// Find 3 in a row
		for (int i = 0; i <= FIELD_SIZE_X - GameConstants.GOAL; i++) {
			for (int j = 0; j < FIELD_SIZE_Y; j++) {
				if (gameField[i][j].getBubbleType() == gameField[i + 1][j]
						.getBubbleType()) {
					if (gameField[i + 1][j].getBubbleType() == gameField[i + 2][j]
							.getBubbleType()) {

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i;
						firedTable[firedIndex].j = j;
						firedIndex++;

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i + 1;
						firedTable[firedIndex].j = j;
						firedIndex++;

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i + 2;
						firedTable[firedIndex].j = j;
						firedIndex++;

						profit = true;
					}
				}
			}
		}

		for (int i = 0; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j <= FIELD_SIZE_Y - GameConstants.GOAL; j++) {

				if (gameField[i][j].getBubbleType() == gameField[i][j + 1]
						.getBubbleType()) {
					if (gameField[i][j + 1].getBubbleType() == gameField[i][j + 2]
							.getBubbleType()) {

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i;
						firedTable[firedIndex].j = j;
						firedIndex++;

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i;
						firedTable[firedIndex].j = j + 1;
						firedIndex++;

						firedTable[firedIndex] = new ActorTable(0, 0);
						firedTable[firedIndex].i = i;
						firedTable[firedIndex].j = j + 2;
						firedIndex++;

						profit = true;
					}
				}
			}
		}

		if (profit) {
			for (int k = 0; k < firedIndex; k++) {
				int ii = firedTable[k].i;
				int jj = firedTable[k].j;
				if (!gameField[ii][jj].isFired())
					gameField[ii][jj].setFired();
				score += 10;
			}
			Assets.sfxImpact.play();
			scoreText.setTextValue("Score: " + score);
		}

		return profit;
	}

	/**
	 * Move bubble
	 * 
	 * @param aBubble
	 */
	private void moveBubble(ActorBubble aBubble, FlingVector flingVector) {

		if (allowFling && allowCheck) {
			allowFling = false;

			int i = aBubble.getActorTable().i;
			int j = aBubble.getActorTable().j;
			int k = i;
			int l = j;

			switch (flingVector) {
			case RIGHT:
				if (i < FIELD_SIZE_X - 1) {
					k = i + 1;
					l = j;
				}
				break;

			case LEFT:
				if (i > 0) {
					k = i - 1;
					l = j;
				}
				break;
			case UP:
				if (j > 0) {
					k = i;
					l = j - 1;
				}
				break;
			case DOWN:
				if (j < FIELD_SIZE_Y - 1) {
					k = i;
					l = j + 1;
				}
				break;

			default:
				break;
			}

			if ((k != i) || (l != j)) {
				swapBubbles(aBubble, k, l);
				Assets.sfxTwang.play();
			}

			// Check profit after animation
			final int fi = i;
			final int fj = j;
			final int fk = k;
			final int fl = l;
			Timer.schedule(new Task() {

				@Override
				public void run() {
					if (!checkForProfit()) {
						swapBubbles(gameField[fi][fj], fk, fl);
					}

					allowFling = true;
				}
			}, GameConstants.MOVE_DURATION * 1.1f);

		}
	}

	/**
	 * Swap bubbles positions
	 * 
	 * @param aBubble
	 *            - target bubble
	 * @param k
	 *            - new position row
	 * @param l
	 *            - new position column
	 */
	private void swapBubbles(ActorBubble aBubble, int k, int l) {
		int i = aBubble.getActorTable().i;
		int j = aBubble.getActorTable().j;

		// Move graphic
		gameField[i][j].moveIt(getXbyI(k), getYbyJ(l));
		gameField[k][l].moveIt(getXbyI(i), getYbyJ(j));

		// Move in array
		gameField[i][j].setActorTable(k, l);
		gameField[k][l].setActorTable(i, j);

		gameField[i][j] = gameField[k][l];
		gameField[k][l] = aBubble;

	}

	/**
	 * Render screen
	 */
	@Override
	public void render(float delta) {
		// Clear screen
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stage
		stage.act(delta);
		stage.draw();

		// Check state
		checkSpaces();
	}

	/**
	 * TODO Dispose
	 * 
	 */
	@Override
	public void dispose() {
		stage.dispose();
		super.dispose();
	}

}
