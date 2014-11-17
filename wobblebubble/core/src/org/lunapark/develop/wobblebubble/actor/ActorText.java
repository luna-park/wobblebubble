package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.lunapark.develop.wobblebubble.assets.Assets;

public class ActorText extends Actor {

	private String textValue = "";
	private BitmapFont font;

	public ActorText(CharSequence charSequence) {
		textValue = String.valueOf(charSequence);
		font = Assets.fontFoo;

		// font.setColor(0.9f, 0.9f, 0.5f, 1);
		//font.setColor(1, 1, 1, 1);
		// font.scale(1);

	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		// super.draw(batch, parentAlpha);
		font.draw(batch, textValue, getX(), getY());
	}


}
