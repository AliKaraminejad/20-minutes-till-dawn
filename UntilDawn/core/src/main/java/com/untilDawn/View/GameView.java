package com.untilDawn.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilDawn.Controller.GameController;
import com.untilDawn.Main;
import com.untilDawn.Module.App;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController gameController;

    public GameView(GameController gameController) {
        this.gameController = gameController;
        gameController.setGameView(this);
    }
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            gameController.getPlayerController().setAutoAiming(!gameController.getPlayerController().isAutoAiming());
            return true;
        }

        else if (keycode == Input.Keys.T) {
            gameController.getCheatCodes().decreaseTime();
        }

        else if (keycode == Input.Keys.H) {
            gameController.getCheatCodes().increaseHealth();
        }

        else if (keycode == Input.Keys.L) {
            gameController.getCheatCodes().addXp();
        }

        else if (keycode == Input.Keys.K) {
            gameController.getCheatCodes().addKills();
        }

        else if(keycode == Input.Keys.Q){
            gameController.getCheatCodes().spawnWingedEnemy();
        }
        return false;
    }


    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        gameController.getWeaponController().handleShooting(i,i1);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        gameController.getWeaponController().handleWeaponRotation(i, i1);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float v) {
        ShaderProgram.pedantic = false; // Allow more lenient shader compilation

        ShaderProgram grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            Gdx.app.error("Shader", "Compilation failed:\n" + grayscaleShader.getLog());
        }
        ScreenUtils.clear(30f / 255f, 0f, 30f / 255f, 1f);
        Main.getBatch().begin();
        if(App.getInstance().getCurrentUser().getPlayer().isBlackAndWhite()){
            Main.getBatch().setShader(grayscaleShader);
        }
        gameController.update(Gdx.graphics.getDeltaTime());
        Main.getBatch().end();
//        Main.getBatch().setColor(1f, 1f, 1f, 1f);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    public Stage getStage() {
        return stage;
    }
}
