package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;

public class ActorBonusBigBoom extends Actor {

	private TextureAtlas.AtlasRegion texture;
	private float x0, y0, x1 = -30, y1 = -40;
	private float actorWidth, actorHeight;
	
	public ActorBonusBigBoom() {
		texture = Assets.txBonusBigBoom;
		actorWidth = texture.getRegionWidth();
		actorHeight = texture.getRegionHeight();
		setBounds(0, 0, actorWidth, actorHeight);
		x0 = -actorWidth;
		y0 = -actorHeight;
		setPosition(x0, y0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), actorWidth, actorHeight);
	}
	
	public void showBonus() {
		MoveToAction bonusFadeIn = new MoveToAction();
		bonusFadeIn.setDuration(GameConstants.BONUS_FADE_IN);
		bonusFadeIn.setPosition(x1, y1);
		
		MoveToAction bonusIdle = new MoveToAction();
		bonusIdle.setDuration(GameConstants.BONUS_IDLE);
		bonusIdle.setPosition(x1 + 2, y1 - 1);
		
		MoveToAction bonusFadeOut = new MoveToAction();
		bonusFadeOut.setDuration(GameConstants.BONUS_FADE_OUT);
		bonusFadeOut.setPosition(x0, y0);
		
		SequenceAction sequenceAction = new SequenceAction(bonusFadeIn, bonusIdle, bonusFadeOut);
		addAction(sequenceAction);
	}

}
