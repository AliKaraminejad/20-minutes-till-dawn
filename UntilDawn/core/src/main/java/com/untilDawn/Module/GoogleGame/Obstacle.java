package com.untilDawn.Module.GoogleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.untilDawn.Module.CollisionRect;
import com.untilDawn.Module.GameAssetManager;

public class Obstacle {
    private CollisionRect collisionRect;
    private float X = 0;
    private float Y = 0;
    private Texture texture;
    private Sprite sprite;

    public Obstacle() {
        texture = new Texture(GameAssetManager.getInstance().getObstacle());
        sprite = new Sprite(texture);
        sprite.setPosition(1200,500 );
        X = 1200;
        Y = 500;
        sprite.setSize(texture.getWidth() / 2 , texture.getHeight() / 2 );
        collisionRect = new CollisionRect(X, Y , sprite.getWidth(), sprite.getHeight());
    }


    public void move(float delta) {
        this.X -= delta;
        this.sprite.setPosition(X,Y );
        this.collisionRect.move(-delta,0);
    }

    public Sprite getsprite() {
        return sprite;
    }

    public boolean isOut(){
        if(X < 400){
            return true;
        }
        return false;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }
}
