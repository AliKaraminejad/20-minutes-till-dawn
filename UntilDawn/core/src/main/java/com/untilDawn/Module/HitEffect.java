package com.untilDawn.Module;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HitEffect {
    private Animation<Texture> animation;
    private float x, y;
    private float stateTime = 0;
    private boolean finished = false;

    public HitEffect(float x, float y) {
        this.x = x;
        this.y = y;
//        this.animation = GameAssetManager.getInstance().getHitEffectAnimation();
    }

    public void update(float delta) {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch) {
        if (!finished) {
            Texture frame = animation.getKeyFrame(stateTime, false);
            batch.draw(frame, x, y);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
