package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.MainMenuController;
import com.untilDawn.Main;
import com.untilDawn.Module.App;
import com.untilDawn.Module.Enums.Language;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.Player;
import com.untilDawn.Module.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreBoard implements Screen {

    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table dataRowsTable;
    private SelectBox<String> sortBox;

    private List<User> users;
    private User currentUser;
    private TextButton backButton;

    private static final int MAX_ROWS = 10;

    public ScoreBoard(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        backButton = new TextButton(Language.BACK.getText(), skin);
        this.users = new ArrayList<>(App.getInstance().getUsers()); // Safe copy
        this.currentUser = App.getInstance().getCurrentUser();

        createUI();
    }

    private enum SortCriteria {
        SCORE(Language.SCORE.getText()),
        USERNAME(Language.USERNAME.getText()),
        KILL(Language.KILL.getText()),
        SURVIVAL_TIME(Language.SURVIVAL_TIME.getText());

        private final String label;

        SortCriteria(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public static SortCriteria fromLabel(String label) {
            for (SortCriteria criteria : values()) {
                if (criteria.label.equals(label)) return criteria;
            }
            return SCORE;
        }
    }

    private void createUI() {
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        Label title = new Label(Language.SCOREBOARD_TITLE.getText(), skin, "title");
        mainTable.add(title).colspan(4).padBottom(20).row();

        sortBox = new SelectBox<>(skin);
        sortBox.setItems(
            SortCriteria.SCORE.toString(),
            SortCriteria.USERNAME.toString(),
            SortCriteria.KILL.toString(),
            SortCriteria.SURVIVAL_TIME.toString()
        );

        sortBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sortAndRefresh();
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), GameAssetManager.getInstance().getSkin()));
            }
        });


        mainTable.add(new Label(Language.SORT_BY.getText(), skin)).pad(5);
        mainTable.add(sortBox).colspan(3).left().row();

        // Table headers
        mainTable.add(new Label(Language.USERNAME.getText(), skin)).pad(5);
        mainTable.add(new Label(Language.SCORE.getText(), skin)).pad(5);
        mainTable.add(new Label(Language.KILL.getText(), skin)).pad(5);
        mainTable.add(new Label(Language.SURVIVAL_TIME.getText(), skin)).pad(5);
        mainTable.row();

        // Data rows table
        dataRowsTable = new Table();
        mainTable.add(dataRowsTable).colspan(4).top().row();

        sortAndRefresh(); // Initial sort and load
    }

    private void sortAndRefresh() {
        String selectedLabel = sortBox.getSelected();
        SortCriteria criteria = SortCriteria.fromLabel(selectedLabel);

        switch (criteria) {
            case USERNAME:
                users.sort(Comparator.comparing(User::getUsername));
                break;
            case SCORE:
                users.sort(Comparator.comparingInt(User::getScore).reversed());
                break;
            case KILL:
                users.sort(Comparator.comparingInt(User::getKills).reversed());
                break;
            case SURVIVAL_TIME:
                users.sort(Comparator.comparingInt(User::getSurvivalTime).reversed());
                break;
        }
        mainTable.add(backButton).colspan(4).padTop(30).row();

        refreshTable();
    }

    private void refreshTable() {
        dataRowsTable.clearChildren();

        int count = 0;
        for (User user : users) {
            if (count >= MAX_ROWS) break;

            boolean isCurrentUser = user.equals(currentUser);

            // Labels
            Label usernameLabel = new Label(user.getUsername() + (isCurrentUser ? " (You)" : ""), skin);
            Label scoreLabel = new Label(String.valueOf(user.getScore()), skin);
            Label killLabel = new Label(String.valueOf(user.getKills()), skin);
            Label timeLabel = new Label(user.getSurvivalTime() + "s", skin);

            Color rowColor = getRowColor(user, count);
            usernameLabel.setColor(rowColor);
            scoreLabel.setColor(rowColor);
            killLabel.setColor(rowColor);
            timeLabel.setColor(rowColor);

            // Top 3 icon


            dataRowsTable.add().width(32);

            // Current user icon (optional)
            if (isCurrentUser) {
                Image userIcon = new Image(new Texture(Gdx.files.internal(App.getInstance().getCurrentUser().getAvatarPath()))); // <-- Your icon file
                dataRowsTable.add(userIcon).size(24).pad(5);
            } else {
                dataRowsTable.add().width(24); // maintain spacing
            }

            // Add the rest of the columns
            dataRowsTable.add(usernameLabel).pad(10);
            dataRowsTable.add(scoreLabel).pad(10);
            dataRowsTable.add(killLabel).pad(10);
            dataRowsTable.add(timeLabel).pad(10);
            dataRowsTable.row();

            count++;
        }
    }



    private Color getRowColor(User user, int index) {
        if (index < 3) return Color.GOLD;
        if (user.equals(currentUser)) return Color.SKY;
        return Color.WHITE;
    }

    // Screen interface implementations
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void show() {

    }
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
