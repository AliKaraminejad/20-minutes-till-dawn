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
import com.untilDawn.Module.Enums.Language;


public class ProfileMenu implements Screen {
    private TextField profileMenu;
    private Stage stage;
    private Table table;
    private ProfileMenuController controller;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton deleteAccount;
    private TextButton back;
    private Label successLabel;
    private TextButton changeAvatar;

    public ProfileMenu(ProfileMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        stage = new Stage();
        profileMenu = new TextField(Language.PROFILE_MENU.getText(), skin);
        changeUsername = new TextButton(Language.CHANGE_USERNAME.getText(), skin);
        changePassword = new TextButton(Language.CHANGE_PASSWORD.getText(), skin);
        deleteAccount = new TextButton(Language.DELETE_ACCOUNT.getText(), skin);
        changeAvatar = new TextButton(Language.CHANGE_AVATAR.getText(), skin);
        back = new TextButton(Language.BACK.getText(), skin);
        successLabel = new Label("", skin);

        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setChangeUsernameMenu();
            }
        });

        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setChangePasswordMenu();
            }
        });

        deleteAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Dialog confirmDialog = new Dialog("", skin) {
                    @Override
                    protected void result(Object object) {
                        if ((boolean) object) {
                            controller.deleteAccount();
                        }
                    }
                };
                confirmDialog.text(Language.CONFIRM_DELETE.getText());
                confirmDialog.button(Language.YES.getText(), true);
                confirmDialog.button(Language.NO.getText(), false);
                confirmDialog.show(stage);
            }
        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

        changeAvatar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeAvatar();
            }
        });

        table = new Table();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        table.center().pad(40);
        table.add(profileMenu).colspan(2).center().padBottom(30).width(500).height(90);
        profileMenu.setDisabled(true);
        table.row();
        table.add(changeUsername).colspan(2).center().padBottom(30).width(500).height(90);
        table.row();
        table.add(changePassword).colspan(2).center().padBottom(30).width(500).height(90);
        table.row();
        table.add(deleteAccount).colspan(2).center().padBottom(30).width(500).height(90);
        table.row();
        table.add(changeAvatar).colspan(2).center().padBottom(30).width(500).height(90);
        table.row();
        table.add(back).colspan(2).center().padBottom(30).width(500).height(90);
        table.row();
        table.add(successLabel).colspan(2);
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
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    public void showError(String message) {
        successLabel.setText(message);
        successLabel.setColor(Color.RED);
        successLabel.getColor().a = 0f;
        successLabel.clearActions();

        successLabel.addAction(Actions.sequence(
            Actions.alpha(1f),
            Actions.delay(6f),
            Actions.fadeOut(0.5f),
            Actions.run(() -> {
                successLabel.setText("");
                successLabel.getColor().a = 1f;
            })
        ));
    }

    public void showSuccess(String message) {
        successLabel.setText(message);
        successLabel.setColor(Color.GREEN);
        successLabel.getColor().a = 0f;
        successLabel.clearActions();

        successLabel.addAction(Actions.sequence(
            Actions.alpha(1f),
            Actions.delay(6f),
            Actions.fadeOut(0.5f),
            Actions.run(() -> {
                successLabel.setText("");
                successLabel.getColor().a = 1f;
            })
        ));
    }

    public Stage getStage() {
        return stage;
    }
}
