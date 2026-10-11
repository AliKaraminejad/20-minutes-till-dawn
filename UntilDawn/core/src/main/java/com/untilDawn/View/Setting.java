package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.SettingController;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;

public class Setting implements Screen {
    private Stage stage;
    private Table table;
    private Slider volumeSlider;
    private TextButton Music1;
    private TextButton Music2;
    private TextButton Music3;
    private TextButton back;
    private Texture backgroundTexture;
    private CheckBox muteSfx;
    private CheckBox autoReload;
    private CheckBox blackAndWhite;
    private CheckBox french;


    public Setting(SettingController controller, Skin skin) {
        stage = new Stage();
        controller.setSetting(this);
        backgroundTexture = new Texture("settings-25-512.png");
        this.volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        volumeSlider.setValue(Main.getMain().getBackgroundMusic().getVolume()); // set current volume
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                float newVolume = volumeSlider.getValue();
                Main.getMain().getBackgroundMusic().setVolume(newVolume);
            }
        });

        Music1 = new TextButton(Language.MUSIC1.getText(), skin);
        Music2 = new TextButton(Language.MUSIC2.getText(), skin);
        Music3 = new TextButton(Language.MUSIC3.getText(), skin);
        back = new TextButton(Language.BACK.getText(), skin);

        Music1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setMusic1();
            }
        });
        Music2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setMusic2();
            }
        });
        Music3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setMusic3();
            }
        });
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });
        muteSfx = new CheckBox(Language.MUTE_SFX.getText(), skin);
        muteSfx.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                App.getInstance().getCurrentUser().getPlayer().setMuteSFX(muteSfx.isChecked());
            }
        });
        muteSfx.setChecked(App.getInstance().getCurrentUser().getPlayer().isMuteSFX());
        autoReload = new CheckBox(Language.AUTO_RELOAD.getText(), skin);
        autoReload.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                App.getInstance().getCurrentUser().getPlayer().setAutoReload(autoReload.isChecked());
            }
        });
        autoReload.setChecked(App.getInstance().getCurrentUser().getPlayer().isAutoReload());
        autoReload.setDisabled(false);

        blackAndWhite = new CheckBox(Language.BLACK_AND_WHITE.getText(), skin);
        blackAndWhite.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                App.getInstance().getCurrentUser().getPlayer().setBlackAndWhite(blackAndWhite.isChecked());
            }
        });
        blackAndWhite.setChecked(App.getInstance().getCurrentUser().getPlayer().isBlackAndWhite());

        french = new CheckBox("French", skin);
        french.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Language.setIsEnglish(!french.isChecked());
            }
        });
        french.setChecked(!Language.isIsEnglish());
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table(); // Use the class-level variable
        table.setFillParent(true);
        table.center();

        table.add(new Label(Language.MUSIC_VOLUME.getText(), GameAssetManager.getInstance().getSkin())).padBottom(10);
        table.row();
        table.add(volumeSlider).width(300).padBottom(20);
        table.row();
        table.add(Music1).width(300).padBottom(20);
        table.row();
        table.add(Music2).width(300).padBottom(20);
        table.row();
        table.add(Music3).width(300).padBottom(20);
        table.row();
        table.add(muteSfx).width(300).padBottom(20);
        table.row();
        table.add(autoReload).width(300).padBottom(20); // Now this will be shown correctly
        table.row();
        table.add(blackAndWhite).width(300).padBottom(20);
        table.row();
        table.add(french).width(300).padBottom(20);
        table.row();
        table.add(back).width(300).padBottom(20);

        stage.addActor(table);
    }


    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getBatch().begin();
        float width = 512;
        float height = 512;

        float x = (Gdx.graphics.getWidth() - width) / 1f;
        float y = (Gdx.graphics.getHeight() - height) / 1f;

        Main.getBatch().draw(backgroundTexture, x, y, width, height);
        Main.getBatch().end();
        stage.act(Math.min( Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        stage.dispose();
    }
}
