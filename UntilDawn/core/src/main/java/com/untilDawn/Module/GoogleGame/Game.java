package com.untilDawn.Module.GoogleGame;

public class Game {
    private static Game game;

    private int score;
    private int level;
    private float difficulty;
    private float time;
    private Player2 player;
    private Obstacle obstacle;

    public Game() {
        score = 0;
        level = 0;
        time = 0;
        player = new Player2();
        obstacle = new Obstacle();
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public Player2 getPlayer() {
        return player;
    }


}
