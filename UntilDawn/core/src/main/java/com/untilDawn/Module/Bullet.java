package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private int x;
    private int y;
    private Texture bulletTexture = new Texture("bullet (1).png");
    private Sprite sprite = new Sprite(bulletTexture);
    private int damage = 5;
    private CollisionRect rect;
    private Vector2 direction ;
    private int originX;
    private int originY;

    public Bullet(int x , int y, int originX, int originY) {
        sprite.setSize(40 , 40);
        this.x = x;
        this.y = y;
        this.originX = originX;
        this.originY = originY;
        sprite.setX((float)originX);
        sprite.setY((float) originY);
        direction = new Vector2(
            originX - x,
            originY - y
        ).nor();

        rect = new CollisionRect(x,y,sprite.getWidth(),sprite.getHeight());
    }

    public Texture getTexture() {
        return bulletTexture;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public int getDamage() {
        return damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }
}
