package com.untilDawn.Controller;

import com.untilDawn.Module.Player;

public class CheatCodes {
    private GameController gameController;
    public CheatCodes(GameController gameController) {
        this.gameController = gameController;
    }

    public void decreaseTime(){
        gameController.addGameTime(60);
    }

    public void increaseHealth(){
        Player player = gameController.getPlayerController().getPlayer();
        player.addHealth(player.getInitialHealth() - player.getHealth());
    }

    public void addXp(){
        Player player = gameController.getPlayerController().getPlayer();
        int additional = player.getLevel() * (player.getLevel()+1) * 10;
        player.addXp(additional - player.getXp() + 1);
    }

    public void addKills(){
        Player player = gameController.getPlayerController().getPlayer();
        player.increaseKills();
    }

    public void spawnWingedEnemy(){
        gameController.addWingedMonster();
    }


}
