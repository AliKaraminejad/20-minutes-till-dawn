package com.untilDawn.Controller.GoogleGame;

import com.untilDawn.Main;
import com.untilDawn.Module.GoogleGame.App2;
import com.untilDawn.Module.GoogleGame.Obstacle;

public class ObstacleController {
    private static Obstacle obstacle = new Obstacle();
    private static float speed = 5;

    public static void update(float deltaTime) {
        check();
        draw(deltaTime);
    }

    private static void draw(float deltaTime) {
        obstacle.move(deltaTime * 10 * speed);
        obstacle.getsprite().draw(Main.getBatch());
        speed += deltaTime ;
    }

    private static void check() {
        if(obstacle.isOut()){
            obstacle = new Obstacle();
        }
    }

    public static Obstacle getObstacle() {
        return obstacle;
    }

}
