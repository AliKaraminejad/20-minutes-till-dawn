package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.badlogic.gdx.math.MathUtils.random;

public class Enemy {
    private Texture enemyTexture = new Texture(GameAssetManager.getInstance().getMonster00());
    private Sprite enemySprite = new Sprite(enemyTexture);
    private float enemyX;
    private float enemyY;
    private float time =0;
    private CollisionRect rect ;
    private int health;

    public Enemy(int x , int y) {
        this.enemyX = x;
        this.enemyY = y;
        enemySprite.setSize(enemyTexture.getWidth() * 2f, enemyTexture.getHeight() * 2f);
        enemySprite.setPosition(enemyX, enemyY);
        rect = new CollisionRect(enemyX,enemyY,enemySprite.getWidth(),enemySprite.getHeight());
        this.health = 50;
    }

    public void move(int dx,int dy){
        enemyX -= dx;
        enemyY -= dy;
        enemySprite.setPosition(enemyX,enemyY);
        rect.setPosition(enemyX,enemyY);
    }

    public float getTime() {
        return time;
    }

    public void resetTime() {
        this.time = 0;
    }

    public Sprite getEnemySprite() {
        return enemySprite;
    }

    public CollisionRect getRect() {
        return rect;
    }

    public float getEnemyY() {
        return enemyY;
    }

    public float getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(float enemyX) {
        this.enemyX = enemyX;
    }

    public void setEnemyY(float enemyY) {
        this.enemyY = enemyY;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void decreaseHealth(int health) {
        this.health -= health;
    }

    public int getHealth() {
        return health;
    }

}
