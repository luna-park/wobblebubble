package org.lunapark.develop.wobblebubble.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	// Textures
	public static Texture txGameBackground, txMainMenuBackground;
	public static Texture txBubble01, txBubble02, txBubble03, txBubble04,
			txBubble05, txBubble06;
	public static Texture txBonusBigBoom, txBonusDroid;
	public static Texture txBonusIconDroid, txBonusIconBomb, txBonusIconScore;

	// SFX
	public static Sound sfxMove, sfxImpact, sfxBomb, sfxBonusScore;
	public static Sound sfxHarp, sfxDroidActivated, sfxDroidDeactivated;

	// Arrays
	public static Texture[] txBubbles;

	// Particles
	public static ParticleEffect fxBoom;

	// Fonts
	public static BitmapFont fontFoo;

	// //
	public static void load() {

		// Load textures
		txGameBackground = new Texture(Gdx.files.internal("data/game_bg.png"));
		txMainMenuBackground = new Texture(
				Gdx.files.internal("data/main_menu_bg.png"));
		txBonusBigBoom = new Texture(Gdx.files.internal("data/fedya.png"));
		txBonusDroid = new Texture(Gdx.files.internal("data/droid.png"));
		txBonusIconDroid = new Texture(
				Gdx.files.internal("data/bonus_droid.png"));
		txBonusIconBomb = new Texture(Gdx.files.internal("data/bonus_bomb.png"));
		txBonusIconScore = new Texture(Gdx.files.internal("data/bonus_score.png"));

		// Load sounds
		sfxMove = Gdx.audio.newSound(Gdx.files.internal("sfx/move.wav"));
		sfxImpact = Gdx.audio.newSound(Gdx.files.internal("sfx/bubblepop.wav"));
		sfxBomb = Gdx.audio.newSound(Gdx.files.internal("sfx/bomb.wav"));
		sfxHarp = Gdx.audio.newSound(Gdx.files.internal("sfx/harp.ogg"));
		
		sfxBonusScore = Gdx.audio.newSound(Gdx.files.internal("sfx/TWANG1.WAV"));
		
		sfxDroidActivated = Gdx.audio.newSound(Gdx.files
				.internal("sfx/droid_activation.ogg"));
		sfxDroidDeactivated = Gdx.audio.newSound(Gdx.files
				.internal("sfx/droid_deactivation.ogg"));

		// Texture arrays
		txBubbles = new Texture[GameConstants.BUBBLE_TYPES];

		for (int i = 0; i < GameConstants.BUBBLE_TYPES; i++) {
			String bubbleFileName = "data/bubble_0" + i + ".png";
			txBubbles[i] = new Texture(Gdx.files.internal(bubbleFileName));
		}
		// txBubbles[0] = new Texture(Gdx.files.internal("data/bubble_00.png"));
		// txBubbles[1] = new Texture(Gdx.files.internal("data/bubble_01.png"));
		// txBubbles[2] = new Texture(Gdx.files.internal("data/bubble_02.png"));
		// txBubbles[3] = new Texture(Gdx.files.internal("data/bubble_03.png"));
		// txBubbles[4] = new Texture(Gdx.files.internal("data/bubble_04.png"));
		// txBubbles[5] = new Texture(Gdx.files.internal("data/bubble_05.png"));

		// Fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/foo.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 32;
		fontFoo = generator.generateFont(parameter);		
		generator.dispose();
		
		//fontFoo = new BitmapFont(Gdx.files.internal("font/fontFoo.fnt"));

	}

}
