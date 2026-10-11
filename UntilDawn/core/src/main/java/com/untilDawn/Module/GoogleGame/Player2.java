package com.untilDawn.Module.GoogleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.untilDawn.Module.CollisionRect;
import com.untilDawn.Module.GameAssetManager;

public class Player2 {
    private int score;
    private CollisionRect collisionRect;
    private float playerX ;
    private float playerY ;
    private Texture playerTexture;
    private Sprite playerSprite;
    private boolean goingUp = false;
    private int bottom;
    private int top;

    public Player2() {
        playerTexture = new Texture(GameAssetManager.getInstance().getcharacter2_idle0());
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition(480, 500);
        playerX = 480;
        playerY = 500;
        playerSprite.setSize(playerTexture.getWidth() * 2 , playerTexture.getHeight() * 2);
        collisionRect = new CollisionRect(playerX, playerY , playerSprite.getWidth(), playerSprite.getHeight());
        bottom = 500;
        top = 680;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void jump(int jumpSpeed){
        isInTop();
        if(goingUp){
            playerY -= 1.3 * jumpSpeed;
            collisionRect.move(0 , - (float) (1.3 *jumpSpeed));
        }
        else {
            playerY += jumpSpeed;
            collisionRect.move(0 , +jumpSpeed);
        }
        playerSprite.setPosition(playerX, playerY);

        if(isInBottom()){
            goingUp = false;
        }
    }

    private void isInTop(){
        if(playerY >= top){
            goingUp = true;
        }
    }

    public boolean isInBottom(){
        if(playerY <= bottom){
            playerY = bottom;
            return true;
        }
        return false;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }
}
