package org.lunapark.develop.wobblebubble.actor;

import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorText extends Actor {

	private String textValue = "";

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	private BitmapFont font;

	public ActorText(CharSequence charSequence) {
		textValue = String.valueOf(charSequence);
		font = Assets.fontFoo;

		// font.setColor(0.9f, 0.9f, 0.5f, 1);
		//font.setColor(1, 1, 1, 1);
		// font.scale(1);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		// super.draw(batch, parentAlpha);
		font.draw(batch, textValue, getX(), getY());
	}

}
