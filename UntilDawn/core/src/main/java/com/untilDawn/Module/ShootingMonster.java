package com.untilDawn.Module;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ShootingMonster {
    private CollisionRect collisionRect;
    private Texture texture = new Texture(GameAssetManager.getInstance().getEyeBat0());
    private Sprite sprite = new Sprite(texture);
    private Animation<Texture> animation;
    private float x;
    private float y;
    private float time = 0;
    private int health;

    public ShootingMonster(int x ,int y) {
        this.x = x;
        this.y = y;
        collisionRect = new CollisionRect(x,y,sprite.getWidth(),sprite.getHeight());
        sprite.setPosition(x, y);
        sprite.setSize(sprite.getWidth()*2f, sprite.getHeight()*2f);
        health = 200;
    }

    public void move(int dx, int dy) {
        x -= dx;
        y -= dy;
        sprite.setPosition(x, y);
        collisionRect.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }

    public void decreaseHealth(int health) {
        this.health -= health;
    }

    public int getHealth() {
        return health;
    }
}
