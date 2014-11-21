package org.lunapark.develop.wobblebubble.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;


public class Assets {
	public static TextureAtlas.AtlasRegion txGameBackground, txMainMenuBackground,
			txBtnPlay, txBtnQuit,
			txBonusBigBoom, txBonusDroid,
			txBonusIconDroid, txBonusIconBomb, txBonusIconScore, txBonusIconHorizontal,
			txBonusIconVertical;
	// Arrays
	public static TextureAtlas.AtlasRegion[] txBubbles;
	// SFX
	public static Sound sfxMove, sfxImpact, sfxBomb, sfxBonusScore;
	public static Sound sfxHarp, sfxDroidActivated, sfxDroidDeactivated, sfxDroidPart;
	public static Sound sfxLaser1, sfxLaser2;
	// Music
	public static Music bgmMain;
	// Fonts
	public static BitmapFont fontFoo;
	// Textures
	private static TextureAtlas gameTextureAtlas = new TextureAtlas(Gdx.files.internal("texture/texture.pack"));

	// //
	public static void load() {

		// Load texture atlas

		txMainMenuBackground = gameTextureAtlas.findRegion("main_menu_bg");
		txGameBackground = gameTextureAtlas.findRegion("game_bg");
		txBtnPlay = gameTextureAtlas.findRegion("btn_play");
		txBtnQuit = gameTextureAtlas.findRegion("btn_quit");

		txBonusBigBoom = gameTextureAtlas.findRegion("fedya");
		txBonusDroid = gameTextureAtlas.findRegion("droid");
		txBonusIconBomb = gameTextureAtlas.findRegion("bonus_bomb");
		txBonusIconDroid = gameTextureAtlas.findRegion("bonus_droid");
		txBonusIconScore = gameTextureAtlas.findRegion("bonus_score");
		txBonusIconHorizontal = gameTextureAtlas.findRegion("bonus_horizontal");
		txBonusIconVertical = gameTextureAtlas.findRegion("bonus_vertical");

		// Texture arrays
		txBubbles = new TextureAtlas.AtlasRegion[GameConstants.BUBBLE_TYPES];
		for (int i = 0; i < GameConstants.BUBBLE_TYPES; i++) {
			txBubbles[i] = gameTextureAtlas.findRegion("bubble", i);
		}

		// Load textures
//		txGameBackground = new Texture(Gdx.files.internal("data/game_bg.png"));
//		txMainMenuBackground = new Texture(
//				Gdx.files.internal("data/main_menu_bg.png"));
//		txBtnPlay = new Texture(Gdx.files.internal("data/btn_play.png"));
//		txBtnQuit = new Texture(Gdx.files.internal("data/btn_quit.png"));
//
//		txBonusBigBoom = new Texture(Gdx.files.internal("data/fedya.png"));
//		txBonusDroid = new Texture(Gdx.files.internal("data/droid.png"));
//		txBonusIconDroid = new Texture(
//				Gdx.files.internal("data/bonus_droid.png"));
//		txBonusIconBomb = new Texture(Gdx.files.internal("data/bonus_bomb.png"));
//		txBonusIconScore = new Texture(Gdx.files.internal("data/bonus_score.png"));


		// Load sounds
		sfxMove = Gdx.audio.newSound(Gdx.files.internal("sfx/move.wav"));
		sfxImpact = Gdx.audio.newSound(Gdx.files.internal("sfx/bubblepop.wav"));
		sfxBomb = Gdx.audio.newSound(Gdx.files.internal("sfx/bomb.wav"));
		sfxHarp = Gdx.audio.newSound(Gdx.files.internal("sfx/harp.ogg"));

		sfxBonusScore = Gdx.audio.newSound(Gdx.files.internal("sfx/gem.wav"));
		
		sfxDroidActivated = Gdx.audio.newSound(Gdx.files
				.internal("sfx/droid_activation.ogg"));
		sfxDroidDeactivated = Gdx.audio.newSound(Gdx.files
				.internal("sfx/droid_deactivation.ogg"));
		sfxDroidPart = Gdx.audio.newSound(Gdx.files.internal("sfx/droid_part.wav"));

		sfxLaser1 = Gdx.audio.newSound(Gdx.files.internal("sfx/laser1.wav"));
		sfxLaser2 = Gdx.audio.newSound(Gdx.files.internal("sfx/laser2.wav"));

		// Load music
		bgmMain = Gdx.audio.newMusic(Gdx.files.internal("music/bgm01.mp3"));



		// Fonts
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("font/foo.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 32;
		fontFoo = generator.generateFont(parameter);		
		generator.dispose();
	}

	public static void dispose() {
		/**
		 * Dispose all textures, sounds, music
		 */
		gameTextureAtlas.dispose();
		bgmMain.dispose();

		sfxBonusScore.dispose();
		sfxMove.dispose();
		sfxBomb.dispose();
		sfxDroidActivated.dispose();
		sfxDroidDeactivated.dispose();
		sfxHarp.dispose();
		sfxImpact.dispose();
		sfxLaser1.dispose();
		sfxLaser2.dispose();
	}

}
