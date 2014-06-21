package org.lunapark.develop.wobblebubble.screen;

import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.base.ScreenBase;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class ScreenMainMenu extends ScreenBase {

	private Stage stage;

	public ScreenMainMenu(Game game) {
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
		Gdx.input.setCatchBackKey(true);
		// Create bg
		Image backgroudImage = new Image(Assets.txMainMenuBackground);

		InputListener listener = new InputListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				game.setScreen(new ScreenGame(game));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				
				return true;
			}

			@Override
			public boolean keyUp(InputEvent event, int keycode) {
				// TODO Auto-generated method stub
				if ((keycode == Keys.BACK) || (keycode == Keys.ESCAPE)) {
					Gdx.app.exit();
				}
				return false;
			}

		};

		//stage.addListener(listener);

		stage.addActor(backgroudImage);
		
		
		// Skin
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		TextButton button = new TextButton("Start game", skin, "default");
		button.setPosition(100, 100);
		button.addListener(listener);
		stage.addActor(button);
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		super.dispose();
	}

}
