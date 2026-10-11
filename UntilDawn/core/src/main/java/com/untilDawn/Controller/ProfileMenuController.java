package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.User;
import com.untilDawn.View.*;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileMenuController {
    private ProfileMenu view;
    private ChangeUsername changeUsername;
    private ChangePassword changePassword;

    public void setView(ProfileMenu view) {
        this.view = view;
    }

    public void setChangeUsername(ChangeUsername changeUsername) {
        this.changeUsername = changeUsername;
    }

    public void setChangePassword(ChangePassword changePassword) {
        this.changePassword = changePassword;
    }

    public void back(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new MainMenu(new MainMenuController(),GameAssetManager.getInstance().getSkin()));
    }

    public void setChangeUsernameMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ChangeUsername(this, GameAssetManager.getInstance().getSkin()));
    }

    public void changeUsername() {
        String oldUsername = changeUsername.getOldUsername().getText();
        String newUsername = changeUsername.getNewUsername().getText();
        String confirmNewUsername = changeUsername.getConfirmNewUsername().getText();
        if(oldUsername == null || newUsername == null || confirmNewUsername == null) {
            changeUsername.showError("all fields must be filled");
        }
        else if(!oldUsername.equals(App.getInstance().getCurrentUser().getUsername())) {
            changeUsername.showError("wrong username");
        }
        else if(oldUsername.equals(newUsername)) {
            changeUsername.showError("new username must be different");
        }
        else if(!newUsername.equals(confirmNewUsername)) {
            changeUsername.showError("the username and confirmation are different");
        }
        else {
            User user = App.getInstance().getCurrentUser();
            user.setUsername(newUsername);
            changeUsername.showSuccess("username changed successfully");
        }
    }

    public void backFromChangeUsername() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ProfileMenu(this, GameAssetManager.getInstance().getSkin()));
    }

    public void setChangePasswordMenu() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new ChangePassword(this, GameAssetManager.getInstance().getSkin()));
    }

    public void changePassword() {
        String oldPassword = changePassword.getoldPassword().getText();
        String newPassword = changePassword.getnewPassword().getText();
        String confirm = changePassword.getConfirmnewPassword().getText();
        if(oldPassword == null || newPassword == null || confirm == null) {
            changePassword.showError("all fields must be filled");
        }
        else if(!oldPassword.equals(App.getInstance().getCurrentUser().getPassword())) {
            changePassword.showError("wrong password");
        }
        else if(oldPassword.equals(newPassword)) {
            changePassword.showError("new password must be different");
        }
        else if(!newPassword.equals(confirm)) {
            changePassword.showError("the password and confirmation are different");
        }
        else if(!isPasswordValid(newPassword)) {
            changePassword.showError("the password format is incorrect");
        }

        else {
            User user = App.getInstance().getCurrentUser();
            user.setPassword(newPassword);
            changePassword.showSuccess("password changed successfully");
        }
    }

    private boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@%$#&*()_])[A-Za-z\\d@%$#&*()_]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }



    public void deleteAccount() {
        App app = App.getInstance();
        User currentUser = app.getCurrentUser();
        if(currentUser == null) {
            view.showError("you are not logged in");
        }
        if(currentUser.isGuest()){
            view.showError("you are a guest");
        }
        else {
            app.removeUser(currentUser);
            app.setCurrentUser(null);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(),GameAssetManager.getInstance().getSkin()));
        }
    }

    public void changeAvatar() {
        Stage stage = view.getStage();
        Dialog dialog = new Dialog(Language.CHOOSING_AVATAR.getText(), GameAssetManager.getInstance().getSkin());
        dialog.pad(20);

        Table mainTable = new Table();
        Table avatarTable = new Table();

        // Predefined avatars
        String[] avatarPaths = GameAssetManager.getInstance().getAvatars().toArray(new String[0]);

        for (int i = 0; i < avatarPaths.length; i++) {
            final String path = avatarPaths[i];
            Texture avatarTex = new Texture(Gdx.files.internal(path));
            Image avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(avatarTex)));

            TextButton avatarButton = new TextButton("", GameAssetManager.getInstance().getSkin());
            avatarButton.add(avatarImage).size(64, 64).pad(5);

            avatarButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    App.getInstance().getCurrentUser().setAvatarPath(path);
                    dialog.hide();
                }
            });

            avatarTable.add(avatarButton).pad(10);
            if ((i + 1) % 3 == 0) avatarTable.row(); // 3 avatars per row
        }

        // 📌 Add custom avatar picker
        TextButton chooseCustom = new TextButton(Language.CHOOSE_FROM_COMPUTER.getText(), GameAssetManager.getInstance().getSkin());
        chooseCustom.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getFileChooserBridge().pickFileForAvatar(); // Opens file dialog

                // Delay avatar loading until AFTER the file is chosen
                Gdx.app.postRunnable(() -> {
                    String avatarPath = App.getInstance().getCurrentUser().getAvatarPath();
                    Texture avatarTexture;

// Use the path to load the texture if it exists
                    if (avatarPath != null && Gdx.files.absolute(avatarPath).exists()) {
                        avatarTexture = new Texture(Gdx.files.absolute(avatarPath));
                    } else {
                        avatarTexture = new Texture(Gdx.files.internal("avatars/default.png"));
                    }

// Create the image for display
                    Image avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(avatarTexture)));

// ✅ Correct: Ensure the path is stored as a String
                    App.getInstance().getCurrentUser().setAvatarPath(avatarPath);
                    // You'll need to implement this method in your view

                    dialog.hide();
                });
            }
        });


        mainTable.add(chooseCustom).colspan(3).padBottom(15);
        mainTable.row();
        mainTable.add(avatarTable).colspan(3);

        dialog.getContentTable().add(mainTable);
        dialog.button(Language.CANCEL.getText());
        dialog.show(stage);
    }

}
