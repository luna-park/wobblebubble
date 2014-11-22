package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.lunapark.develop.wobblebubble.assets.Assets;
import org.lunapark.develop.wobblebubble.assets.GameConstants;

/**
 * Created by znak on 22.11.2014.
 */
public class ActorLaser extends Actor {

    private TextureAtlas.AtlasRegion texture;
    private int x, y;
    private boolean isVertical = false;
    private long startTime, finishTime;

    public ActorLaser(float x, float y, boolean isVertical) {
        setPosition(x, y);
        this.isVertical = isVertical;
        startTime = System.currentTimeMillis();
        finishTime = startTime + (int) (GameConstants.MOVE_DURATION * 500);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (isVertical)
            texture = Assets.txLaserVertical;
        else
            texture = Assets.txLaserHorizontal;
        batch.draw(texture, getX(), getY());
        if (System.currentTimeMillis() > finishTime) {
            getParent().removeActor(this);
        }
    }


}
