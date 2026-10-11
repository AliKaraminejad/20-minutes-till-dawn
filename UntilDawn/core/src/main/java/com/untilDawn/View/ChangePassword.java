package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.ProfileMenuController;
import com.untilDawn.Main;

public class ChangePassword implements Screen {
    private Stage stage;
    private TextField oldPassword;
    private TextField newPassword;
    private TextField confirmnewPassword;
    private TextButton submit;
    private TextButton back;
    private ProfileMenuController controller;
    private Table table;
    private Label successLabel;

    public ChangePassword(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        stage = new Stage();
        controller.setChangePassword(this);
        oldPassword = new TextField("Current Password", skin);
        newPassword = new TextField("New Password", skin);
        confirmnewPassword = new TextField("Confirm New Password", skin);
        submit = new TextButton("Submit", skin);
        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword();
            }
        });
        back = new TextButton("Back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.backFromChangeUsername();
            }
        });
        successLabel = new Label("", skin);
        table = new Table();

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center().pad(40);
        table.add(oldPassword).padBottom(20).width(500).height(80);
        table.row();
        table.add(newPassword).padBottom(20).width(500).height(80);
        table.row();
        table.add(confirmnewPassword).padBottom(20).width(500).height(80);
        table.row();
        Table buttonRow = new Table();
        buttonRow.add(submit).width(200).height(80).padRight(20);
        buttonRow.add(back).width(200).height(80);
        table.add(buttonRow);
        table.row();
        table.add(successLabel).padBottom(20).width(500).height(80);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getBatch().begin();
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

    }

    public TextField getoldPassword() {
        return oldPassword;
    }

    public TextField getnewPassword() {
        return newPassword;
    }

    public TextField getConfirmnewPassword() {
        return confirmnewPassword;
    }

    public void showError(String message) {
        successLabel.setText(message);
        successLabel.setColor(Color.RED);
        successLabel.getColor().a = 0f; // Make sure alpha is fully visible
        successLabel.clearActions();   // Cancel previous animations

        // Optional: fade in first to make sure it's visible
        successLabel.addAction(Actions.sequence(
            Actions.alpha(1f),               // Reset alpha in case it's faded
            Actions.delay(6f),               // Wait 6 seconds
            Actions.fadeOut(0.5f),           // Fade out over 0.5 seconds
            Actions.run(() -> {
                successLabel.setText("");      // Clear the text after fading
                successLabel.getColor().a = 1f; // Reset alpha for next time
            })
        ));
    }

    public void showSuccess(String message) {
        successLabel.setText(message);
        successLabel.setColor(Color.GREEN);
        successLabel.getColor().a = 0f; // Make sure alpha is fully visible
        successLabel.clearActions();   // Cancel previous animations

        // Optional: fade in first to make sure it's visible
        successLabel.addAction(Actions.sequence(
            Actions.alpha(1f),               // Reset alpha in case it's faded
            Actions.delay(6f),               // Wait 6 seconds
            Actions.fadeOut(0.5f),           // Fade out over 0.5 seconds
            Actions.run(() -> {
                successLabel.setText("");      // Clear the text after fading
                successLabel.getColor().a = 1f; // Reset alpha for next time
            })
        ));
    }
}
