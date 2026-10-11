package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Select;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.GameController;
import com.untilDawn.Controller.MainMenuController;
import com.untilDawn.Controller.PregameMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;


public class PregameView implements Screen {
    private SelectBox<String> heroSelector;
    private SelectBox<String> guns;
    private SelectBox<String> gameSpan;
    private TextButton startGameButton;
    private PregameMenuController controller;
    private Skin skin;
    private Stage stage;
    private Table table;
    private TextButton Back;

    public PregameView(PregameMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        controller.setPregameView(this);
        stage = new Stage();

        // Setup hero select box with localized names
        heroSelector = new SelectBox<>(skin);
        heroSelector.setItems(
            Language.HERO_SHANA.getText(),
            Language.HERO_DIAMOND.getText(),
            Language.HERO_SCARLET.getText(),
            Language.HERO_LILITH.getText(),
            Language.HERO_DASHER.getText()
        );

        guns = new SelectBox<>(skin); // You can localize this if gun names are language-specific
        guns.setItems(
            "Revolver",
            "Shot Gun",
            "SMGs Dual"
        );



        gameSpan = new SelectBox<>(skin);
        gameSpan.setItems(
            Language.DURATION_2_MIN.getText(),
            Language.DURATION_5_MIN.getText(),
            Language.DURATION_10_MIN.getText(),
            Language.DURATION_20_MIN.getText()
        );

        startGameButton = new TextButton(Language.START_GAME.getText(), skin);

        startGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.startGame();
            }
        });

        heroSelector.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.selectHero(heroSelector.getSelected());
            }
        });

        guns.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.changeGun(guns.getSelected());
            }
        });

        gameSpan.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int durationMinutes = Integer.parseInt(gameSpan.getSelected().split(" ")[0]) * 60;
                controller.selectTime(durationMinutes);
            }
        });

        Back = new TextButton(Language.BACK.getText(), skin);
        Back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });

        table = new Table(skin);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center().pad(40);
        table.add(heroSelector).expandX().fillX().pad(10);
        table.row();
        table.add(guns).expandX().fillX().pad(10);
        table.row();
        table.add(gameSpan).expandX().fillX().pad(10);
        table.row();
        table.add(startGameButton).expandX().fillX().padTop(20);
        table.row();
        table.add(Back).expandX().fillX().padTop(20);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        Main.getBatch().begin();
        Main.getBatch().end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int i, int i1) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}
