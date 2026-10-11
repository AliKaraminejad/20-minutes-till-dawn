package com.untilDawn.Controller.GoogleGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.untilDawn.Main;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.GoogleGame.Player2;

public class Player2Controller {
    private static Player2 player2 = new Player2();
    private static boolean isJumping = false;
    private static Animation<Texture> animation = GameAssetManager.getInstance().getcharacter2_idle_frames();
    private static int jumpSpeed = 3;

    public static void update(float deltaTime) {
        check();
        draw();
        handleInput();
        jump();
    }

    private static void check() {
        if(player2.getCollisionRect().collides(ObstacleController.getObstacle().getCollisionRect())) {
            Gdx.app.exit();
        }
    }

    private static void draw() {
        player2.getPlayerSprite().draw(Main.getBatch());

    }

    private static void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            isJumping = true;
        }
    }

    private static void jump() {
        if(isJumping){
            player2.jump(jumpSpeed);
            if(player2.isInBottom())
                isJumping = false;
        }
        jumpSpeed += 30 * Gdx.graphics.getDeltaTime();
    }
}
