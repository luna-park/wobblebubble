package org.lunapark.develop.wobblebubble.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class ActorFx extends Actor {

	private ParticleEffectPool bombEffectPool;
	Array<PooledEffect> effects = new Array<PooledEffect>();

	public ActorFx() {
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("fx/explosion.p"),
				Gdx.files.internal("data"));
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);

		//addFX(0, 0);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		for (int i = effects.size - 1; i >= 0; i--) {
			PooledEffect effect = effects.get(i);
			effect.draw(batch, Gdx.graphics.getDeltaTime());
			if (effect.isComplete()) {
				effect.free();
				effects.removeIndex(i);
			}
		}
	}

	public void addFX(float x, float y) {
		// Create effect:
		PooledEffect effect = bombEffectPool.obtain();
		effect.setPosition(x, y);
		effects.add(effect);
	}

}
