package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.untilDawn.Main;
import com.untilDawn.Module.*;
import com.untilDawn.View.LoginMenu;
import com.untilDawn.View.MainMenu;
import com.untilDawn.View.PickQuestion;
import com.untilDawn.View.SignUpMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.badlogic.gdx.math.MathUtils.random;

public class SignUpMenuController {
    private SignUpMenu view;
    private PickQuestion question;

    public void setQuestion(PickQuestion question) {
        this.question = question;
    }

    public void setView(SignUpMenu view) {
        this.view = view;
    }

    public void handleSignUpButton() {
        if(view != null) {
            App app = App.getInstance();
            String username = view.getUsernameTextField().getText();
            String password = view.getPasswordTextField().getText();
            if (app.isUsernameTaken(username)) {
                view.showError("Username is already taken.");
            } else if (password == null || !isPasswordValid(password)) {
                view.showError("invalid password.");
            } else {
                int rand = random.nextInt(GameAssetManager.getInstance().getAvatars().size());
                app.addUser(new User(username, password,GameAssetManager.getInstance().getAvatars().get(rand)));
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PickQuestion(this,GameAssetManager.getInstance().getSkin()));//new LoginMenu(new LoginMenuController(),GameAssetManager.getInstance().getSkin()));
            }
        }
    }

    public void handleGuestButton() {
        if(view != null) {
            int rand = random.nextInt(GameAssetManager.getInstance().getAvatars().size());
            App app = App.getInstance();
            User user = new User(GameAssetManager.getInstance().getAvatars().get(rand));
            App.getInstance().setGuestUser(user);
            App.getInstance().setCurrentUser(user);
//            view.showSuccess("You are now in Main Menu!");
//            Timer.schedule(new Timer.Task() {
//                @Override
//                public void run() {
                    Main.getMain().getScreen().dispose();
                    Main.getMain().setScreen(new MainMenu(new MainMenuController(),GameAssetManager.getInstance().getSkin()));
//                }
//            }, 4);
        }
    }

    public void handleBackFromQuestioning(){
        App app = App.getInstance();
        app.removeUserWithNoQuestion();
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new SignUpMenu(this,GameAssetManager.getInstance().getSkin()));
    }

    public void handleQuestioning(){
        String question = this.question.getSelectedQuestion().getText().toString();
        String answer = this.question.getAnswer().getText().toString();
        App app = App.getInstance();
        app.addQuestionForUser(question, answer);
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(),GameAssetManager.getInstance().getSkin()));
    }

    private boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@%$#&*()_])[A-Za-z\\d@%$#&*()_]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void exit(){
        JsonUserSaver.saveUsersToJson(App.getInstance().getUsers(), "user_stats.json");
//        User currentUser = App.getInstance().getCurrentUser();
//        UserDataBase.saveUser(currentUser);

        Gdx.app.exit();
    }

    public void goToLogin(){
        Main.getMain().getScreen().dispose();
        Main.getMain().setScreen(new LoginMenu(new LoginMenuController(),GameAssetManager.getInstance().getSkin()));
    }


}
