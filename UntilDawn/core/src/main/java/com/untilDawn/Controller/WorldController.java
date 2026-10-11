package com.untilDawn.Controller;

import com.badlogic.gdx.graphics.Texture;
import com.untilDawn.Main;

public class WorldController {
    private PlayerController playerController;
    private Texture background;
    private float backGroundX;
    private float backGroundY;

    public WorldController(PlayerController playerController) {
        background = new Texture("map (1).png");
        this.playerController = playerController;
    }

    public void update() {
        backGroundX = playerController.getPlayer().getPlayerX();
        backGroundY = playerController.getPlayer().getPlayerY();
        Main.getBatch().draw(background, backGroundX, backGroundY);
    }
}
