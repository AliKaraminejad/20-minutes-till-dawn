package com.untilDawn.Module;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class Tree {
    private int x;
    private int y;
    private Texture texture;
    private Sprite sprite;
    private CollisionRect collisionRect;

    public Tree(int x, int y){
        this.x = x;
        this.y = y;
        texture = new Texture("T_TreeMonster_2.png");
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(texture.getWidth()*2, texture.getHeight()*2);
        collisionRect = new CollisionRect(x, y, texture.getWidth()*2, texture.getHeight()*2);
    }

    public void move(int x, int y){
        this.x -= x;
        this.y -= y;
        sprite.setPosition(this.x, this.y);
        sprite.setSize(texture.getWidth()*2, texture.getHeight()*2);
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
