package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by znak on 17.11.2014.
 */
public class ActorButton extends Actor {

    private Texture texture;
    private int actorWidth, actorHeight;

    public ActorButton(Texture texture) {
        this.texture = texture;
        actorWidth = texture.getWidth();
        actorHeight = texture.getHeight();
        setBounds(0, 0, actorWidth, actorHeight);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), actorWidth, actorHeight);
    }
}
