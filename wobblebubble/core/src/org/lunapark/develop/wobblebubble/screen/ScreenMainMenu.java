package org.lunapark.develop.wobblebubble.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import org.lunapark.develop.wobblebubble.actor.ActorButton;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.base.ScreenBase;

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
		Image backgroundImage = new Image(Assets.txMainMenuBackground);
		stage.addActor(backgroundImage);

		ActorButton btnPlay = new ActorButton(Assets.txBtnPlay);
		ActorButton btnQuit = new ActorButton(Assets.txBtnQuit);
		btnPlay.setPosition(107, 76);
		btnQuit.setPosition(538, 76);
		btnPlay.addListener(new InputListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Assets.sfxBonusScore.play();
				Assets.bgmMain.stop();
				game.setScreen(new ScreenGame(game));
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
									 int pointer, int button) {
				return true;
			}
		});

		btnQuit.addListener(new InputListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.exit();
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
									 int pointer, int button) {
				return true;
			}
		});

		stage.addActor(btnPlay);
		stage.addActor(btnQuit);

		Assets.bgmMain.play();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		super.dispose();
	}

}
