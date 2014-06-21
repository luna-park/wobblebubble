package org.lunapark.develop.wobblebubble.assets;

public class GameConstants {

	// Graphic settings
	public static final int SCREEN_SIZE_X = 800;
	public static final int SCREEN_SIZE_Y = 480;
	public static final int FIELD_SIZE_X = 7;
	public static final int FIELD_SIZE_Y = 7;
	public static final int CELL_SIZE = 64;

	// Move action duration
	public static final float MOVE_DURATION = 0.3f;

	public static final float BONUS_FADE_IN = 0.3f;
	public static final float BONUS_IDLE = 1.0f;
	public static final float BONUS_FADE_OUT = 0.2f;

	public static final float DROID_DURATION = 60.0f;

	// Game settings
	public static int GOAL = 3;
	public static final int BUBBLE_TYPES = 6;
	public static int SCORE_INCREMENT = 50;
	public static int SCORE_INCREMENT2 = 10;
	public static int SCORE_BONUS = 5000;

	public static final float DELAY_FLING = 1.2f;
	public static final float DELAY_CHECK = 2.0f;
	public static final float DELAY_BOOM = 3.0f;

	// Bonuses
	public static final int BONUS_TYPES = 3;

	public static enum bonusType {
		DROID, BOMB, SCORE
	}

	public static int BONUS_RANDOM = 25;
	public static int BONUS_SCORE_INC = 5000;

}
