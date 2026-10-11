package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.GameController;
import com.untilDawn.Controller.MainMenuController;
import com.untilDawn.Controller.PregameMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

public class MainMenu implements Screen {
    private MainMenuController controller;
    private Stage stage;
    private Table table;
    private Label label;
    private TextButton Setting;
    private TextButton Exit;
    private TextButton Profile;
    private TextButton Pregame;
    private TextButton ScoreBoard;
    private TextButton Hint;
    private Texture backgroundTexture;
    private TextButton logout;
    private TextButton save;
    private Texture avatarTexture;
    private ImageTextButton username;

    public MainMenu(MainMenuController controller, Skin skin) {
        this.controller = controller;
        stage = new Stage();
        table = new Table();
        label = new Label(Language.MAIN_MENU.getText(), skin);
        Setting = new TextButton(Language.SETTINGS.getText(), skin);
        Exit = new TextButton(Language.EXIT.getText(), skin);
        Profile = new TextButton(Language.PROFILE.getText(), skin);
        Pregame = new TextButton(Language.PREGAME.getText(), skin);
        ScoreBoard = new TextButton(Language.SCOREBOARD.getText(), skin);
        Hint = new TextButton(Language.HINT.getText(), skin);
        backgroundTexture = new Texture(Gdx.files.internal("20-minutes-till-dawn-full-1.jpg"));
        logout = new TextButton(Language.LOGOUT.getText(), skin);
        save = new TextButton(Language.SAVED_GAME.getText(), skin);
        // Create avatar texture
        avatarTexture = new Texture(Gdx.files.internal(App.getInstance().getCurrentUser().getAvatarPath()));
        Image avatar = new Image(avatarTexture);
        avatar.setSize(64, 64);

// Stack (in case you later add a circular mask)
        Stack avatarStack = new Stack();
        avatarStack.add(avatar);

// Create button
        if(App.getInstance().getCurrentUser().isGuest()){
            username = new ImageTextButton( Language.GUEST.getText()+" "+Language.SCORE.getText() +": "+ App.getInstance().getCurrentUser().getScore(), skin);
        }
        else{
            username = new ImageTextButton( App.getInstance().getCurrentUser().getUsername()+" "+Language.SCORE.getText() +": "+ App.getInstance().getCurrentUser().getScore(), skin);
        }
        username.setColor(Color.MAGENTA);

// Clear default layout and rebuild it manually
        username.clearChildren(); // Remove default image & label

// Add avatar to the left
        username.add(avatarStack).size(64, 64).padRight(10);

// Add the label text
        username.add(username.getLabel()).expandX().left();
        username.setTouchable(Touchable.disabled);


        controller.setController(this);

        Exit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.exit();
            }
        });

        Profile.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToProfile();
            }
        });
        Pregame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.openPregame();
            }
        });
        ScoreBoard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new ScoreBoard(GameAssetManager.getInstance().getSkin()));
            }
        });
        Hint.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new HintMenu(GameAssetManager.getInstance().getSkin()));
            }
        });
        Setting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.setting();
            }
        });
        logout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.logout();
            }
        });
        save.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);

        table.center().pad(40);
        table.add(username).colspan(2).padBottom(20).width(500).height(120).padRight(10);
        username.setDisabled(true);
        table.row();
        table.add(Setting).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(Profile).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(Pregame).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(ScoreBoard).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(Hint).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(save).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(Exit).colspan(2).center().padTop(20).width(500).height(80);
        table.row();
        table.add(logout).colspan(2).center().padTop(20).width(500).height(80);
        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0,1);
        Main.getBatch().begin();
        Main.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
