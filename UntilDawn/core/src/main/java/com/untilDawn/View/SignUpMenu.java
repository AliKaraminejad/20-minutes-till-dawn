package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.SignUpMenuController;
import com.untilDawn.Main;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.untilDawn.Module.Enums.Language;


import java.awt.*;

public class SignUpMenu implements Screen {
    private Stage stage;
    private TextButton signUpButton;
    private TextButton guestButton;
    private Label signUpLabel;
    private TextField passwordTextField;
    private TextField usernameTextField;
    private Table table;
    private SignUpMenuController controller;
    private Label errorLabel;
    private Label successLabel;
    private TextButton exitButton;
    private TextButton goToLoginButton;


    public SignUpMenu(SignUpMenuController controller, Skin skin) {
        this.controller = controller;
        stage = new Stage();
        table = new Table();

        signUpButton = new TextButton(Language.SIGN_UP.getText(), skin);
        guestButton = new TextButton(Language.GUEST.getText(), skin);
        signUpLabel = new Label(Language.SIGN_UP.getText(), skin);
        usernameTextField = new TextField(Language.USERNAME.getText(), skin);
        passwordTextField = new TextField(Language.PASSWORD.getText(), skin);
        errorLabel = new Label("", skin);
        successLabel = new Label("", skin);
        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleSignUpButton();
            }
        });


        guestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGuestButton(); // Optional: create this method
            }
        });
        exitButton = new TextButton(Language.EXIT.getText(), skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit(); // Optional: create this method
            }
        });
        goToLoginButton = new TextButton(Language.LOGIN.getText(), skin);
        goToLoginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToLogin(); // Optional: create this method
            }
        });

        this.table = new Table();

        controller.setView(this);

    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.center().pad(20);

        // Title label
        table.add(signUpLabel).colspan(2).center().padBottom(30);
        table.row();

        // Username field
        table.add(new Label(Language.SIGN_UP.getText(), signUpLabel.getStyle())).right().padRight(10);
        table.add(usernameTextField).width(300).left();
        table.row().padTop(10);

        // Password field
        table.add(new Label(Language.PASSWORD.getText(), signUpLabel.getStyle())).right().padRight(10);
        table.add(passwordTextField).width(300).left();
        table.row().padTop(10);

        // Sign up and guest buttons on same row
        table.add(signUpButton).width(200).height(80).padTop(20).padRight(20);
        table.add(guestButton).width(200).height(80).padTop(20).padRight(13);
        table.add(exitButton).width(200).height(80).padTop(20).padRight(3);
        table.add(goToLoginButton).width(200).height(80).padTop(20).padLeft(10);

        // Error label at bottom
        table.add(errorLabel).colspan(2).center().padTop(20);
        table.row();
        table.add(successLabel).colspan(2).center().padTop(20);

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
        stage.dispose();
    }

    public TextButton getSignUpButton() {
        return signUpButton;
    }

    public Label getSignUpLabel() {
        return signUpLabel;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public TextField getUsernameTextField() {
        return usernameTextField;
    }

    public TextButton getGuestButton() {
        return guestButton;
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

}
