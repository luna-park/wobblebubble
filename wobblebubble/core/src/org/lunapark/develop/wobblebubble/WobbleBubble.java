package org.lunapark.develop.wobblebubble;

import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.screen.ScreenMainMenu;

import com.badlogic.gdx.Game;

public class WobbleBubble extends Game {

	@Override
	public void create() {
		// Resources loading
		Assets.load();

		// Start 1st screen
		setScreen(new ScreenMainMenu(this));

	}

}
