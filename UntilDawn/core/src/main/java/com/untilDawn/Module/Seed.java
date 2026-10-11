package com.untilDawn.Module;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Seed {
    private Texture texture;
    private Sprite sprite;
    private CollisionRect collisionRect;
    private int x;
    private int y;

    public Seed(int x , int y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture("Soft.png");
        this.sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(15, 15);
        collisionRect = new CollisionRect(x , y , 15, 15);
    }

    public void move(int x , int y){
        this.x -= x;
        this.y -= y;
        sprite.setPosition(this.x, this.y);
        collisionRect.setPosition(this.x, this.y);
    }

    public boolean collides(CollisionRect collisionRect){
        if(this.collisionRect.collides(collisionRect)){
            return true;
        }
        return false;
    }

    public Sprite getSprite() {
        return sprite;
    }



}
