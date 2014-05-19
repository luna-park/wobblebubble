package org.lunapark.develop.wobblebubble.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Assets {
	// Constants
	public static final int BUBBLE_TYPES = 5;

	// Textures
	public static Texture txGameBackground, txMainMenuBackground;
	public static Texture txBubble01, txBubble02, txBubble03, txBubble04,
			txBubble05, txBubble06;

	// SFX
	public static Sound sfxNewGame;
	public static Sound sfxTwang;
	public static Sound sfxHiscore;
	public static Sound sfxImpact;

	// Arrays
	public static Texture[] txBubbles;

	// Particles
	public static ParticleEffect fxBoom;

	public static void load() {

		// Load textures
		txGameBackground = new Texture(Gdx.files.internal("data/game_bg.png"));
		txMainMenuBackground = new Texture(
				Gdx.files.internal("data/main_menu_bg.png"));

		// Load sounds
		sfxNewGame = Gdx.audio.newSound(Gdx.files.internal("sfx/newgame.wav"));
		sfxTwang = Gdx.audio.newSound(Gdx.files.internal("sfx/TWANG1.WAV"));
		sfxHiscore = Gdx.audio.newSound(Gdx.files.internal("sfx/hiscore.wav"));
		sfxImpact = Gdx.audio.newSound(Gdx.files.internal("sfx/impact.wav"));

		// Texture arrays
		txBubbles = new Texture[BUBBLE_TYPES];
		
		for (int i = 0; i < BUBBLE_TYPES; i++) {
			String bubbleFileName = "data/bubble_0" + i +".png";
			txBubbles[i] = new Texture(Gdx.files.internal(bubbleFileName));
		}
//		txBubbles[0] = new Texture(Gdx.files.internal("data/bubble_00.png"));
//		txBubbles[1] = new Texture(Gdx.files.internal("data/bubble_01.png"));
//		txBubbles[2] = new Texture(Gdx.files.internal("data/bubble_02.png"));
//		txBubbles[3] = new Texture(Gdx.files.internal("data/bubble_03.png"));
//		txBubbles[4] = new Texture(Gdx.files.internal("data/bubble_04.png"));
//		txBubbles[5] = new Texture(Gdx.files.internal("data/bubble_05.png"));

		
	}

}
