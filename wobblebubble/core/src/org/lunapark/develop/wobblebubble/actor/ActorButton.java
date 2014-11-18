package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

/**
 * Created by znak on 17.11.2014.
 */
public class ActorButton extends Actor {

    private TextureAtlas.AtlasRegion region;
    private int actorWidth, actorHeight;
    private long startTime;
    private int deltaDeg;

    public ActorButton(TextureAtlas.AtlasRegion region) {
        this.region = region;
        actorWidth = region.getRegionWidth();
        actorHeight = region.getRegionHeight();
        setBounds(0, 0, actorWidth, actorHeight);
        startTime = System.currentTimeMillis();
        Random random = new Random();
        deltaDeg = random.nextInt(45);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float deltaY = (float) Math.sin((System.currentTimeMillis() - startTime) / 60.606f + deltaDeg) * 1;
        batch.draw(region, getX(), getY() + deltaY, actorWidth, actorHeight);
    }
}
