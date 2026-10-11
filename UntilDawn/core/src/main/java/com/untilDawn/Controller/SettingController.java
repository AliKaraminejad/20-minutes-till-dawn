package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.untilDawn.Main;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.View.MainMenu;
import com.untilDawn.View.Setting;

public class SettingController {
    private Setting setting;

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public void back(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void setMusic1(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("manke-male-parvaz-nabodam.mp3"));
        Main.getMain().setBackgroundMusic(backgroundMusic);
    }

    public void setMusic2(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sarah_Blasko_-_All_I_Want(320).mp3"));
        Main.getMain().setBackgroundMusic(backgroundMusic);
    }

    public void setMusic3(){
        Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Naser Abdollahi _ Naseria (320).mp3"));
        Main.getMain().setBackgroundMusic(backgroundMusic);
    }

}
