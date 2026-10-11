package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.MainMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.Player;
import com.untilDawn.Module.User;

public class EndingGame implements Screen {
    App app = App.getInstance();
    User user = app.getCurrentUser();
    private Stage stage;
    private ImageTextButton username ;
    private TextButton survivalTime;
    private TextButton kills;
    private TextButton score;
    private TextButton win;
    private Texture avatarTexture;
    private Table table;
    private TextButton back;

    public EndingGame(Skin skin) {
        stage = new Stage();

        survivalTime = new TextButton("Survival Time: "+user.getSurvivalTime()+" (s)", skin);
        kills = new TextButton("Kills: "+user.getPlayer().getKill(),skin);
        score = new TextButton("Score: "+user.getScore(),skin);
        if(user.isHasWonLastGame()){
            win = new TextButton("Win", skin);
        }
        else{
            win = new TextButton("Dead", skin);
        }

        avatarTexture = new Texture(Gdx.files.internal(App.getInstance().getCurrentUser().getAvatarPath()));
        Image avatar = new Image(avatarTexture);
        avatar.setSize(64, 64);

        Stack avatarStack = new Stack();
        avatarStack.add(avatar);

        username = new ImageTextButton( App.getInstance().getCurrentUser().getUsername(), skin);
        username.setColor(Color.MAGENTA);

        username.clearChildren(); // Remove default image & label

        username.add(avatarStack).size(64, 64).padRight(10);

        username.add(username.getLabel()).expandX().left();
        username.setTouchable(Touchable.disabled);

        back = new TextButton("Back To Main Menu", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
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

        table.add(username).colspan(2).center().padTop(20);
        table.row();
        table.add(survivalTime).colspan(2).center().padTop(20);
        table.row();
        table.add(kills).colspan(2).center().padTop(20);
        table.row();
        table.add(score).colspan(2).center().padTop(20);
        table.row();
        table.add(win).colspan(2).center().padTop(20);
        table.row();
        table.add(back).colspan(2).center().padTop(20);
        stage.addActor(table);
        username.setTouchable(Touchable.disabled);
        survivalTime.setTouchable(Touchable.disabled);
        kills.setTouchable(Touchable.disabled);
        score.setTouchable(Touchable.disabled);
        win.setTouchable(Touchable.disabled);
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
}
