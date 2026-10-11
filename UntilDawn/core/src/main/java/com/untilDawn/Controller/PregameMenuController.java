package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.Player;
import com.untilDawn.Module.User;
import com.untilDawn.Module.Weapon;
import com.untilDawn.View.GameView;
import com.untilDawn.View.PregameView;

public class PregameMenuController {
    private PregameView pregameView;

    public void setPregameView(PregameView pregameView) {
        this.pregameView = pregameView;
    }

    public void selectHero(String hero) {
        App app = App.getInstance();
        User user = app.getCurrentUser();
        Player player = user.getPlayer();
        player.changeHeroName(hero);
    }

    public void selectGun(int id){
        App app = App.getInstance();
        User user = app.getCurrentUser();
        Player player = user.getPlayer();
        switch (id){}
    }

    public void selectTime(int time){
        App app = App.getInstance();
        User user = app.getCurrentUser();
        Player player = user.getPlayer();
        player.setGameSpan(time);
    }

    public void startGame(){
        App.getInstance().getCurrentUser().resetPlayer();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new GameView(new GameController()));
    }

    public void changeGun(String name){
        App app = App.getInstance();
        User user = app.getCurrentUser();
        Player player = user.getPlayer();
        switch (name){
            case "Revolver":
                player.changeWeapon(new Weapon(1),25,6);
                break;
            case "Shot Gun":
                player.changeWeapon(new Weapon(4),15,2);
                break;
            case "SMGs Dual":
                player.changeWeapon(new Weapon(1),10,24);
                break;
        }
    }


}
