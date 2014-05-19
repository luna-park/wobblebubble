package org.lunapark.develop.wobblebubble.screen;

import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.base.ScreenBase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenu extends ScreenBase {

	private Stage stage;

	public MainMenu(Game game) {
		super(game);
	}

	@Override
	public void render(float delta) {
		// Clear screen
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stage
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void show() {
		// Create stage and stretch it to full screen
		stage = new Stage(new StretchViewport(GameConstants.SCREEN_SIZE_X,
				GameConstants.SCREEN_SIZE_Y));

		Gdx.input.setInputProcessor(stage);
		// Create bg
		Image backgroudImage = new Image(Assets.txMainMenuBackground);

		InputListener listener = new InputListener() {

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				game.setScreen(new ScreenGame(game));
				return true;
			}

		};

		backgroudImage.addListener(listener);

		stage.addActor(backgroudImage);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		super.dispose();
	}

}
