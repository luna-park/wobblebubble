package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;

public class ActorBonusDroid extends Actor {

	private TextureAtlas.AtlasRegion texture;
	private float actorWidth, actorHeight;
	private float x0, y0, x1 = 405, y1 = -65;
	private boolean activated = false;

	public ActorBonusDroid() {
		texture = Assets.txBonusDroid;
		actorWidth = texture.getRegionWidth();
		actorHeight = texture.getRegionHeight();
		setBounds(0, 0, actorWidth, actorHeight);
		x1 = (GameConstants.SCREEN_SIZE_X - actorWidth) / 1;
		x0 = x1;
		y0 = -actorHeight;		
		setPosition(x0, y0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), actorWidth, actorHeight);
	}

	public void fadeIn() {
		activated = true;
		Assets.sfxDroidActivated.play();
		MoveToAction bonusFadeIn = new MoveToAction();
		bonusFadeIn.setDuration(GameConstants.BONUS_FADE_IN);
		bonusFadeIn.setPosition(x1, y1);
		addAction(bonusFadeIn);
	}
	
	public void fadeOut() {
		activated = false;
		Assets.sfxDroidDeactivated.play();
		MoveToAction bonusFadeOut = new MoveToAction();
		bonusFadeOut.setDuration(GameConstants.BONUS_FADE_IN);
		bonusFadeOut.setPosition(x0, y0);
		addAction(bonusFadeOut);
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
