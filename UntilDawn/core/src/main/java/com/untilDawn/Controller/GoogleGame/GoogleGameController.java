package com.untilDawn.Controller.GoogleGame;

import com.untilDawn.Main;
import com.untilDawn.Module.GoogleGame.Obstacle;

public class GoogleGameController {
    private static Obstacle obstacle = new Obstacle();


    public static void update(float deltaTime) {
        ObstacleController.update(deltaTime);
        Player2Controller.update(deltaTime);
    }
}
