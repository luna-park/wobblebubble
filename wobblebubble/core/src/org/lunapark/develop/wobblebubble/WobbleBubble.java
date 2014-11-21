package org.lunapark.develop.wobblebubble;

import com.badlogic.gdx.Game;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.screen.ScreenMainMenu;

public class WobbleBubble extends Game {

	@Override
	public void create() {
		// Resources loading
		Assets.load();

		// Start 1st screen
		setScreen(new ScreenMainMenu(this));

	}

	@Override
	public void dispose() {
		Assets.dispose();
		super.dispose();
	}
}
