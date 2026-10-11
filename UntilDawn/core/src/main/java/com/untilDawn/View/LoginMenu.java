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
import com.untilDawn.Controller.LoginMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;

public class LoginMenu implements Screen {
    private LoginMenuController controller;
    private Stage stage;
    private Table table;
    private Label label;
    private TextField username;
    private TextField password;
    private TextButton login;
    private TextButton goToSignUp;
    private TextButton forgotPassword;
    private Label errorLabel;
    private Label successLabel;
    private Skin skin;
    private Label questionLabel;
    private TextField answerField;
    private TextButton submitAnswerButton;
    private TextButton cancelButton;



    public LoginMenu(LoginMenuController controller, Skin skin){
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage();
        this.label = new Label(Language.LOGIN_MENU.getText(), this.skin);
        this.login = new TextButton(Language.LOGIN.getText(), this.skin);
        this.password = new TextField(Language.PASSWORD.getText(), this.skin);
        this.username = new TextField(Language.USERNAME.getText(), this.skin);
        this.goToSignUp = new TextButton(Language.GO_TO_SIGN_UP.getText(), this.skin);
        this.forgotPassword = new TextButton(Language.FORGOT_PASSWORD.getText(), this.skin);
        this.errorLabel = new Label("", skin);
        this.successLabel = new Label("", skin);
        login.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleLogin();
            }
        });

        goToSignUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleGoToSignUp(); // Optional: create this method
            }
        });
        forgotPassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.handleForgotPassword(); // Optional: create this method
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

        table.center().pad(40); // Global padding for all content

        table.add(errorLabel).colspan(2).center().padTop(20);
        table.row();
        table.add(errorLabel).colspan(2).center().padTop(20);
        table.row();
        // Title Label
        table.add(label).colspan(2).center().padBottom(40);
        table.row();

        // Username Field
        table.add(username).colspan(2).center().width(600).padBottom(20);
        table.row();

        // Password Field
        table.add(password).colspan(2).center().width(600).padBottom(20);
        table.row();

        // Login Button
        table.add(login).colspan(2).center().width(320).height(100).padBottom(15);
        table.row();

        // Sign Up Redirect
        table.add(goToSignUp).colspan(2).center().width(320).height(100).padBottom(15);
        table.row();
        table.add(forgotPassword).colspan(2).center().width(320).height(100).padBottom(15);

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



    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }


    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setColor(Color.RED);
        errorLabel.getColor().a = 0f; // Make sure alpha is fully visible
        errorLabel.clearActions();   // Cancel previous animations

        // Optional: fade in first to make sure it's visible
        errorLabel.addAction(Actions.sequence(
            Actions.alpha(1f),               // Reset alpha in case it's faded
            Actions.delay(6f),               // Wait 6 seconds
            Actions.fadeOut(0.5f),           // Fade out over 0.5 seconds
            Actions.run(() -> {
                errorLabel.setText("");      // Clear the text after fading
                errorLabel.getColor().a = 1f; // Reset alpha for next time
            })
        ));
    }


    public void showSecurityQuestion(String question) {
        username.setVisible(false);
        password.setVisible(false);
        login.setVisible(false);
        goToSignUp.setVisible(false);
        forgotPassword.setVisible(false);

        questionLabel = new Label(question, GameAssetManager.getInstance().getSkin());
        answerField = new TextField("", GameAssetManager.getInstance().getSkin());
        submitAnswerButton = new TextButton(Language.SUBMIT.getText(), GameAssetManager.getInstance().getSkin());
        cancelButton = new TextButton(Language.CANCEL.getText(), GameAssetManager.getInstance().getSkin());

        submitAnswerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.validateSecurityAnswer(answerField.getText());
            }
        });
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.cancel();
            }
        });

        table.row();
        table.add(questionLabel).colspan(2).padTop(20);
        table.row();
        table.add(answerField).colspan(2).width(500).padTop(10);
        table.row();
        // Submit button
        table.add(submitAnswerButton)
            .width(280)
            .height(80)
            .padRight(20)
            .padBottom(10);

// Cancel button
        table.add(cancelButton)
            .width(280)
            .height(80)
            .padBottom(10);

    }

}
