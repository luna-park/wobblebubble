package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TouchableAction;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;
import org.lunapark.develop.wobblebubble.assets.GameConstants.bonusType;

import java.util.Random;

public class ActorBubble extends Actor {
	private TextureAtlas.AtlasRegion texture;
	private int actorWidth, actorHeight;
	private boolean fired = false;
	private ActorTable actorTable;
	private int bubbleType;

	private boolean bonus = false;
	private Random random;
	private bonusType bubbleBonusType;

	public ActorBubble(int bubbleType) {

		random = new Random();
		this.setBubbleType(bubbleType);

		texture = Assets.txBubbles[bubbleType];
		actorWidth = texture.getRegionWidth();
		actorHeight = texture.getRegionHeight();
		setBounds(0, 0, actorWidth, actorHeight);

		actorTable = new ActorTable(255, 255);
		createBonus();
	}

	private void createBonus() {
		Random random = new Random();
		int range = random.nextInt(GameConstants.BONUS_RANDOM);
		if (range < 2) {
			bonus = true;
			int typeRange = random.nextInt(GameConstants.BONUS_TYPES);
			switch (typeRange) {
			case 0:
				bubbleBonusType = bonusType.BOMB;
				break;
				
			case 1:
				bubbleBonusType = bonusType.DROID;
				break;

			default:
				bubbleBonusType = bonusType.SCORE;
				break;
			}

		} else
			bonus = false;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (bonus) {
			TextureAtlas.AtlasRegion bonusTexture;

			switch (bubbleBonusType) {
			case DROID:
				bonusTexture = Assets.txBonusIconDroid;
				break;
				
			case SCORE:
				bonusTexture = Assets.txBonusIconScore;
				break;
				
			case BOMB:
				bonusTexture = Assets.txBonusIconBomb;
				break;

			default:
				bonusTexture = Assets.txBonusIconScore;
				break;
			}
			batch.draw(bonusTexture, getX(), getY(), actorWidth, actorHeight);
		}
		texture = Assets.txBubbles[bubbleType];
		batch.draw(texture, getX(), getY(), actorWidth, actorHeight);

	}

	public boolean isFired() {
		return fired;
	}

	public void setFired() {
		fired = true;
		setVisible(false);
		bonus = false;
		bubbleType = random.nextInt(GameConstants.BUBBLE_TYPES);
	}

	public void clearFired() {
		fired = false;
		setVisible(true);
		createBonus();
	}

	public void moveIt(float toX, float toY) {

		// Move graphics
		MoveToAction moveToAction = new MoveToAction();
		moveToAction.setDuration(GameConstants.MOVE_DURATION);
		moveToAction.setPosition(toX, toY);

		// Set touch
		TouchableAction touchableAction = new TouchableAction();
		touchableAction.setTouchable(Touchable.enabled);

		// Set untouch
		TouchableAction untouchableAction = new TouchableAction();
		untouchableAction.setTouchable(Touchable.disabled);

		// Create sequence - untouch, move, touch
		SequenceAction sequenceAction = new SequenceAction(untouchableAction,
				moveToAction, touchableAction);

		// Add sequence
		addAction(sequenceAction);
	}

	/**
	 * Get position in array
	 * 
	 * @return
	 */
	public ActorTable getActorTable() {
		return actorTable;
	}

	/**
	 * Set position in 2D-array
	 * 
	 * @param i
	 *            row
	 * @param j
	 *            column
	 */
	public void setActorTable(int i, int j) {
		actorTable.i = i;
		actorTable.j = j;
	}

	public int getBubbleType() {
		return bubbleType;
	}

	public void setBubbleType(int bubbleType) {
		this.bubbleType = bubbleType;
	}

	//
	public boolean isBonus() {
		return bonus;
	}

	public bonusType getBonusType() {
		return bubbleBonusType;
	}

}
