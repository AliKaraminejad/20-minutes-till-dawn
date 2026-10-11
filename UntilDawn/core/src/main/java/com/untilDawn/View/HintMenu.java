package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untilDawn.Controller.MainMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;

public class HintMenu implements Screen {
    private TextButton hero1, hero2, hero3, keys, cheatCode, abilities, backButton;
    private Stage stage;
    private Table table;
    private Skin skin;

    public HintMenu(Skin skin) {
        this.skin = skin;
        stage = new Stage();
        table = new Table();

        // Buttons with localized text from enum
        hero1 = new TextButton(Language.HINT_HERO_SHANA.getText(), skin);
        hero2 = new TextButton(Language.HINT_HERO_DIAMOND.getText(), skin);
        hero3 = new TextButton(Language.HINT_HERO_SCARLET.getText(), skin);
        keys = new TextButton(Language.HINT_KEYS.getText(), skin);
        cheatCode = new TextButton(Language.HINT_CHEAT.getText(), skin);
        abilities = new TextButton(Language.HINT_ABILITIES.getText(), skin);
        backButton = new TextButton(Language.HINT_BACK.getText(), skin);
    }

    @Override
    public void show() {
        table.setFillParent(true);
        table.add(hero1).pad(10).row();
        table.add(hero2).pad(10).row();
        table.add(hero3).pad(10).row();
        table.add(keys).pad(10).row();
        table.add(cheatCode).pad(10).row();
        table.add(abilities).pad(10).row();
        table.add(backButton).pad(10).row();

        // Back button logic
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });

        // Dialogs for each hint
        addDialogOnClick(hero1, Language.HINT_DIALOG_SHANA.getText());
        addDialogOnClick(hero2, Language.HINT_DIALOG_DIAMOND.getText());
        addDialogOnClick(hero3, Language.HINT_DIALOG_SCARLET.getText());
        addDialogOnClick(keys, Language.HINT_DIALOG_KEYS.getText());
        addDialogOnClick(cheatCode, Language.HINT_DIALOG_CHEAT.getText());
        addDialogOnClick(abilities, Language.HINT_DIALOG_ABILITIES.getText());

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void addDialogOnClick(TextButton button, String message) {
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog dialog = new Dialog("", skin) {
                    protected void result(Object object) {
                        this.hide();
                    }
                };
                dialog.text(message);
                dialog.button(Language.HINT_OK.getText());
                dialog.show(stage);
            }
        });
    }

    @Override public void render(float v) {
        ScreenUtils.clear(0, 0, 0.1f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override public void resize(int i, int i1) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}

