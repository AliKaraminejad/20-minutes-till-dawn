package com.untilDawn.Controller;

import com.badlogic.gdx.utils.Timer;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.User;
import com.untilDawn.View.LoginMenu;
import com.untilDawn.View.MainMenu;
import com.untilDawn.View.SignUpMenu;

public class LoginMenuController {
    private LoginMenu view;

    public void setView(LoginMenu view) {
        this.view = view;
    }

    public void handleGoToSignUp() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignUpMenu(new SignUpMenuController(), GameAssetManager.getInstance().getSkin()));
    }

    public void handleLogin() {
        App app = App.getInstance();
        String username = view.getUsername().getText();
        String password = view.getPassword().getText();
        User user = app.getUserByUsername(username);
        if (user == null) {
            view.showError("incorrect username");
        }
        else if(!user.getPassword().equals(password)) {
            view.showError("incorrect password");
        }
        else {
            app.setCurrentUser(user);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
        }
    }

    public void handleForgotPassword() {
        String username = view.getUsername().getText();
        User user = App.getInstance().getUserByUsername(username);
        if (user == null) {
            view.showError("incorrect username");
        }
        else {
            view.showSecurityQuestion(user.getQuestion());
        }
    }

    public void validateSecurityAnswer(String answer) {
        String username = view.getUsername().getText();
        User user = App.getInstance().getUserByUsername(username);
        if (!answer.trim().equalsIgnoreCase(user.getAnswer())) {
            view.showError("Incorrect answer.");
        } else {
            view.showError("This is your password: " + user.getPassword());

            // Wait a few seconds, then go back to login screen
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
                }
            }, 7); // Delay in seconds
        }

    }

    public void cancel() {
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(), GameAssetManager.getInstance().getSkin()));
    }
}
