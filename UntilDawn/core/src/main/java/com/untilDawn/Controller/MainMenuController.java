package com.untilDawn.Controller;

import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.View.*;

public class MainMenuController {
    private MainMenu view;

    public void setController(MainMenu view) {
        this.view = view;
    }

    public void exit(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void setting(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new Setting(new SettingController(), GameAssetManager.getInstance().getSkin()));
    }

    public void logout(){
        App app = App.getInstance();
        app.setCurrentUser(null);
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void goToProfile(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenu(new ProfileMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void openPregame(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new PregameView(new PregameMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
