package org.lunapark.develop.wobblebubble.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.lunapark.develop.wobblebubble.WobbleBubble;
import org.lunapark.develop.wobblebubble.assets.GameConstants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConstants.SCREEN_SIZE_X;
		config.height = GameConstants.SCREEN_SIZE_Y;
		new LwjglApplication(new WobbleBubble(), config);
	}
}
