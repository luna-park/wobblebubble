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

	// Droid
	public static final float DROID_DURATION = 20.0f; // 15
	public static final int DROID_PARTS = 7; // activation bonus
	public static final int BUBBLE_TYPES = 6; // Default 6
	public static final float DELAY_FLING = 1.2f;
	public static final float DELAY_CHECK = 2.0f;
	public static final float DELAY_BOOM = 3.0f;
	// Bonuses
	public static final int BONUS_TYPES = 3;
	// Game settings
	public static int GOAL = 3; // 3 in a row/column
	public static int SCORE_INCREMENT = 50;
	public static int SCORE_INCREMENT2 = 10;
	public static int SCORE_BIG_BOOM_BONUS = 10000;

	public static float LEVEL_BASE = 1.5f;
	public static int LEVEL_INCREMENT = 20000;

	public static int BONUS_RANDOM = 25;
	public static int BONUS_SCORE_INC = 5000;
	public static enum bonusType {
		DROID, BOMB, SCORE
	}

}
