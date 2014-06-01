package org.lunapark.develop.wobblebubble.screen;

import java.util.Random;

import org.lunapark.develop.wobblebubble.actor.ActorBonusBigBoom;
import org.lunapark.develop.wobblebubble.actor.ActorBonusDroid;
import org.lunapark.develop.wobblebubble.actor.ActorBubble;
import org.lunapark.develop.wobblebubble.actor.ActorFx;
import org.lunapark.develop.wobblebubble.actor.ActorTable;
import org.lunapark.develop.wobblebubble.actor.ActorText;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.assets.GameConstants.bonusType;
import org.lunapark.develop.wobblebubble.base.ScreenBase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
	private boolean allowCheck = true, allowFling = true, startTheGame = false;
	private ActorTable droidTarget = new ActorTable(0, 0); //
	private FlingVector droidVector;

	// Effects
	private ActorFx actorFx;

	// Actors
	private ActorBonusBigBoom actorBonusBigBoom;
	private ActorBonusDroid actorBonusDroid;

	// Tasks
	private Timer.Task allowTask, droidTask;

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
		Gdx.input.setCatchBackKey(true);

		InputListener inputListener = new InputListener() {
			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				if ((keycode == Keys.BACK) || (keycode == Keys.ESCAPE)) {
					game.setScreen(new ScreenMainMenu(game));
				}
				return true;
			}
		};

		gestureListener = new ActorGestureListener() {
			@Override
			public void tap(InputEvent event, float x, float y, int count,
					int button) {
				// XXX Tap on bubble
				// ActorBubble a = (ActorBubble) event.getListenerActor();
				// bonusBomb(a.getActorTable().i, a.getActorTable().j);
			}

			@Override
			public boolean longPress(Actor actor, float x, float y) {
				// XXX Long tap on bubble
				bonusDroid();
				return true;
			}

			@Override
			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {
				// super.fling(event, velocityX, velocityY, button);
				startTheGame = true;
				ActorBubble a = (ActorBubble) event.getListenerActor();

				if (!actorBonusDroid.isActivated()) {
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
			}
		};

		// Create bg
		Image backgroudImage = new Image(Assets.txGameBackground);

		//
		stage.addListener(inputListener);

		// Create text labels
		scoreText = new ActorText(String.valueOf(score));
		scoreText.setPosition(
				GameConstants.SCREEN_SIZE_X - Assets.fontFoo.getXHeight() * 8,
				GameConstants.SCREEN_SIZE_Y - Assets.fontFoo.getXHeight() * 2);

		// Create fx pool actor
		actorFx = new ActorFx();

		// Create bonus actors
		actorBonusBigBoom = new ActorBonusBigBoom();
		actorBonusDroid = new ActorBonusDroid();

		// Add bg & actors to stage
		stage.addActor(backgroudImage);
		stage.addActor(scoreText);

		// Create field
		createField();
		checkForProfit();

		// Add bonus
		stage.addActor(actorBonusBigBoom);
		stage.addActor(actorBonusDroid);

		// Add fx actor
		stage.addActor(actorFx);

		// Tasks
		allowTask = new Task() {
			@Override
			public void run() {

				allowCheck = true;
				allowFling = true;
				checkForProfit();
			}
		};
		
		droidTask = new Task() {
			
			@Override
			public void run() {
				if (actorBonusDroid.isActivated())
					actorBonusDroid.fadeOut();				
			}
		};
	}

	/**
	 * Tasks queue
	 * 
	 * @param delayTime
	 */
	private void taskQueue(Timer.Task task, float delayTime) {
		if (task.isScheduled()) {
			task.cancel();
		}

		Timer.schedule(task, delayTime);
	}

	/**
	 * Boom all bubbles
	 */
	private void bigBoom() {

		long id = Assets.sfxHarp.play();
		Assets.sfxHarp.setPriority(id, 0);
		actorBonusBigBoom.showBonus();

		allowFling = false;
		allowCheck = false;

		scoreAdd(GameConstants.SCORE_BONUS);

		for (int i = 0; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j < FIELD_SIZE_Y; j++) {
				hitBubble(i, j);
			}
		}

		Assets.sfxImpact.stop();
		Assets.sfxImpact.play();

		taskQueue(allowTask, GameConstants.MOVE_DURATION
				* GameConstants.DELAY_CHECK);
	}

	/**
	 * Create game field
	 */
	private void createField() {

		for (int i = 0; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j < FIELD_SIZE_Y; j++) {
				// Create random bubble
				ActorBubble aBubble = new ActorBubble(
						random.nextInt(GameConstants.BUBBLE_TYPES));
				aBubble.setActorTable(i, j);
				// Add bubble to gamefield
				gameField[i][j] = aBubble;
				aBubble.setPosition(getXbyI(i), getYbyJ(j));
				aBubble.addListener(gestureListener);
				stage.addActor(aBubble);
			}
		}

	}

	/**
	 * XXX Activate droid
	 */
	private void bonusDroid() {
		if (!actorBonusDroid.isActivated())
			actorBonusDroid.fadeIn();

		taskQueue(droidTask, GameConstants.DROID_DURATION);
		
	}

	private void bonusDroidWork() {
		if (allowFling && allowCheck && actorBonusDroid.isActivated()) {
			startTheGame = true;
			moveBubble(gameField[droidTarget.i][droidTarget.j], droidVector);
		}
	}

	/**
	 * Hit bubble in bubble-table
	 * 
	 * @param i
	 * @param j
	 */
	private void hitBubble(int i, int j) {
		gameField[i][j].setFired();
		actorFx.addFX(getXbyI(i) + STEP / 2, getYbyJ(j) + STEP / 2);
		scoreAdd(GameConstants.SCORE_INCREMENT + GameConstants.SCORE_INCREMENT2);
	}

	/**
	 * Check for blank spaces
	 */
	private void checkSpaces() {

		// Check for blank spaces
		if (allowCheck) {

			for (int i = 0; i < FIELD_SIZE_X; i++) {

				int[] bubblesIndexes = new int[FIELD_SIZE_Y];
				int a = 0;

				// Add fired bubbles
				for (int j = 0; j < FIELD_SIZE_Y; j++) {
					if (gameField[i][j].isFired()) {
						bubblesIndexes[a] = j;
						gameField[i][j].setPosition(gameField[i][j].getX(),
								gameField[i][j].getY() - STEP * FIELD_SIZE_Y);
						a++;
					}
				}

				// Have fired bubbles in a column
				if (a > 0) {
					allowCheck = false;
					allowFling = false;

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
					// Move graphics
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

					// XXX Delay before next check
					// Timer.schedule(new Task() {
					// @Override
					// public void run() {
					// checkForProfit();
					// allowCheck = true;
					// allowFling = true;
					// }
					// }, GameConstants.MOVE_DURATION *
					// GameConstants.DELAY_CHECK);

					taskQueue(allowTask, GameConstants.MOVE_DURATION
							* GameConstants.DELAY_CHECK);

					a = 0;

				}

			}
		}

		// Check available moves
		if (!checkMotion() && allowCheck && allowFling) {
			System.out.println("Нет ходов");
			allowFling = false;
			allowCheck = false;

			// XXX Delay before big boom
			Timer.schedule(new Task() {
				@Override
				public void run() {
					bigBoom();
				}
			}, GameConstants.MOVE_DURATION * GameConstants.DELAY_BOOM);

		}

		// XXX Droid
		if (actorBonusDroid.isActivated())
			bonusDroidWork();

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
	 * Check for profit
	 */
	private boolean checkForProfit() {

		boolean profit = false;

		ActorTable[] firedTable = new ActorTable[200];
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
			Assets.sfxImpact.stop();
			Assets.sfxImpact.play();
			for (int k = 0; k < firedIndex; k++) {
				int ii = firedTable[k].i;
				int jj = firedTable[k].j;
				if (!gameField[ii][jj].isFired()) {

					if (gameField[ii][jj].isBonus()) {

						// XXX Bonuses
						bonusType type = gameField[ii][jj].getBonusType();
						System.out
								.println("i: " + ii + " j:" + jj + " " + type);
						// Bomb bonus
						if (type == bonusType.BOMB) {
							int a, b, c, d;

							if (ii > 0)
								a = ii - 1;
							else
								a = ii;

							if (ii == FIELD_SIZE_X - 1)
								b = ii;
							else
								b = ii + 1;

							if (jj > 0)
								c = jj - 1;
							else
								c = jj;

							if (jj == FIELD_SIZE_Y - 1)
								d = jj;
							else
								d = jj + 1;

							for (int x = a; x <= b; x++) {
								for (int y = c; y <= d; y++) {
									firedTable[firedIndex] = new ActorTable(0,
											0);
									firedTable[firedIndex].i = x;
									firedTable[firedIndex].j = y;
									firedIndex++;
								}
							}
							Assets.sfxBomb.stop();
							Assets.sfxBomb.play(0.5f);
						}

					}
					hitBubble(ii, jj);
				}
			}

		}
		return profit;
	}

	/**
	 * Add score and display
	 * 
	 * @param increment
	 */
	public void scoreAdd(int increment) {
		// Game started, score added
		if (startTheGame)
			score += increment;
		scoreText.setTextValue(String.valueOf(score));
	}

	/**
	 * XXX Movement available?
	 * 
	 * @return
	 */
	private boolean checkMotion() {

		int bubble1;
		int bubble2;
		int bubble3;
		// Situation 1
		// *.*
		// .*.
		droidVector = FlingVector.DOWN;
		for (int i = 0; i < FIELD_SIZE_X - 2; i++) {
			for (int j = 1; j < FIELD_SIZE_Y; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j - 1].getBubbleType();
				bubble3 = gameField[i + 2][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation A1 detected");
					droidTarget.i = i + 1;
					droidTarget.j = j - 1;
					return true;
				}

				bubble1 = gameField[i][j - 1].getBubbleType();
				bubble2 = gameField[i + 1][j].getBubbleType();
				bubble3 = gameField[i + 2][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation B1 detected");
					droidTarget.i = i;
					droidTarget.j = j - 1;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j].getBubbleType();
				bubble3 = gameField[i + 2][j - 1].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation C1 detected");
					droidTarget.i = i + 2;
					droidTarget.j = j - 1;
					return true;
				}
			}
		}

		// Situation A2
		// .*.
		// *.*
		droidVector = FlingVector.UP;
		for (int i = 0; i < FIELD_SIZE_X - 2; i++) {
			for (int j = 0; j < FIELD_SIZE_Y - 1; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j + 1].getBubbleType();
				bubble3 = gameField[i + 2][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation A2 detected");
					droidTarget.i = i + 1;
					droidTarget.j = j + 1;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j].getBubbleType();
				bubble3 = gameField[i + 2][j + 1].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation B2 detected");
					droidTarget.i = i + 2;
					droidTarget.j = j + 1;
					return true;
				}

				bubble1 = gameField[i][j + 1].getBubbleType();
				bubble2 = gameField[i + 1][j].getBubbleType();
				bubble3 = gameField[i + 2][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation C2 detected");
					droidTarget.i = i;
					droidTarget.j = j + 1;
					return true;
				}
			}
		}

		// Situation A3
		// .*
		// *.
		// .*
		droidVector = FlingVector.RIGHT;
		for (int i = 1; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j < FIELD_SIZE_Y - 2; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i - 1][j + 1].getBubbleType();
				bubble3 = gameField[i][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation A3 detected");
					droidTarget.i = i - 1;
					droidTarget.j = j + 1;
					return true;
				}

				bubble1 = gameField[i - 1][j].getBubbleType();
				bubble2 = gameField[i][j + 1].getBubbleType();
				bubble3 = gameField[i][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation B3 detected");
					droidTarget.i = i - 1;
					droidTarget.j = j;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i][j + 1].getBubbleType();
				bubble3 = gameField[i - 1][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation C3 detected");
					droidTarget.i = i - 1;
					droidTarget.j = j + 2;
					return true;
				}
			}
		}

		// Situation A4
		// .*
		// *.
		// .*
		droidVector = FlingVector.LEFT;
		for (int i = 0; i < FIELD_SIZE_X - 1; i++) {
			for (int j = 0; j < FIELD_SIZE_Y - 2; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j + 1].getBubbleType();
				bubble3 = gameField[i][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation A4 detected");
					droidTarget.i = i + 1;
					droidTarget.j = j + 1;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i][j + 1].getBubbleType();
				bubble3 = gameField[i + 1][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation B4 detected");
					droidTarget.i = i + 1;
					droidTarget.j = j + 2;
					return true;
				}

				bubble1 = gameField[i + 1][j].getBubbleType();
				bubble2 = gameField[i][j + 1].getBubbleType();
				bubble3 = gameField[i][j + 2].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation C4 detected");
					droidTarget.i = i + 1;
					droidTarget.j = j;
					return true;
				}
			}
		}

		// Situation D1, D3
		// * *
		// . *
		// * .
		// * *
		for (int i = 0; i < FIELD_SIZE_X; i++) {
			for (int j = 0; j < FIELD_SIZE_Y - 3; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i][j + 1].getBubbleType();
				bubble3 = gameField[i][j + 3].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation D1 detected");
					droidVector = FlingVector.UP;
					droidTarget.i = i;
					droidTarget.j = j + 3;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i][j + 2].getBubbleType();
				bubble3 = gameField[i][j + 3].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation D3 detected");
					droidVector = FlingVector.DOWN;
					droidTarget.i = i;
					droidTarget.j = j;
					return true;
				}
			}
		}

		// Situation D2, D4
		// *.**
		// **.*
		for (int i = 0; i < FIELD_SIZE_X - 3; i++) {
			for (int j = 0; j < FIELD_SIZE_Y; j++) {
				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 2][j].getBubbleType();
				bubble3 = gameField[i + 3][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation D2 detected");
					droidVector = FlingVector.RIGHT;
					droidTarget.i = i;
					droidTarget.j = j;
					return true;
				}

				bubble1 = gameField[i][j].getBubbleType();
				bubble2 = gameField[i + 1][j].getBubbleType();
				bubble3 = gameField[i + 3][j].getBubbleType();

				if ((bubble1 == bubble2) && (bubble2 == bubble3)) {
					// System.out.println("Situation D4 detected");
					droidVector = FlingVector.LEFT;
					droidTarget.i = i + 3;
					droidTarget.j = j;
					return true;
				}
			}
		}

		return false;
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

			// XXX Delay before swap back bubbles
			Timer.schedule(new Task() {

				@Override
				public void run() {
					if (!checkForProfit()) {
						swapBubbles(gameField[fi][fj], fk, fl);
					}

					allowFling = true;
				}
			}, GameConstants.MOVE_DURATION * GameConstants.DELAY_FLING);

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

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}
}
