package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.untilDawn.Main;
import com.untilDawn.Module.*;
import com.untilDawn.Module.Enums.AbilitiName;
import com.untilDawn.Module.Enums.Language;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;

public class PlayerController {
    private Player player;

    private ArrayList<ShootingMonsterController> shootingMonsterControllers;
    private WeaponController weaponController;
    private boolean isAutoAiming = false;
    private GameController gameController;

    public PlayerController(Player player, WeaponController weaponController, GameController gameController) {
        this.player = player;
        this.weaponController = weaponController;
        this.gameController = gameController;
    }

    public void update() {
        player.getPlayerSprite().draw(Main.getBatch());
        if(player.isIdle()){
            idleAnimation();
        }
        handlePlayerInput();
        autoAim();
        autoReload();
    }

    private void autoReload() {
        if(!player.isAutoReload()){
            if(player.getWeapon().isEmpty()){
                weaponController.reload();
            }
        }
    }

    private Sound footstepSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Footsteps_Casual_Grass_02.wav"));
    private float footstepTimer = 0f;
    private final float FOOTSTEP_INTERVAL = 0.3f;

    public void handlePlayerInput() {
        int dx = 0;
        int dy = 0;
        boolean movingThisFrame = false;
        if(input.isKeyPressed(Input.Keys.P)){
            if(!gameController.isPaused()){
                gameController.setPaused(true);
            }
        }

        if(isAutoAiming){
            return;
        }
        if (input.isKeyPressed(Input.Keys.W)) {
            player.setPlayerY(player.getPlayerY() - player.getSpeed());
            if(player.checkSpeedy((int)gameController.getGameTime())){
                dy = player.getSuperSpeed();
            }
            else {
                dy = (int)player.getSpeed();
            }
            player.setRealY(player.getRealY() + dy);
            movingThisFrame = true;
        }
        if (input.isKeyPressed(Input.Keys.D)) {
            player.setPlayerX(player.getPlayerX() - player.getSpeed());
            if(player.checkSpeedy((int)gameController.getGameTime())){
                dx = player.getSuperSpeed();
            }
            else {
                dx = (int)player.getSpeed();
            }
            player.setRealX(player.getRealX() + dx);
            movingThisFrame = true;
        }
        if (input.isKeyPressed(Input.Keys.S)) {
            player.setPlayerY(player.getPlayerY() + player.getSpeed());
            if(player.checkSpeedy((int)gameController.getGameTime())){
                dy = -player.getSuperSpeed();
            }
            else {
                dy = -(int)player.getSpeed();
            }
            player.setRealY(player.getRealY() + dy);
            movingThisFrame = true;
        }
        if (input.isKeyPressed(Input.Keys.A)) {
            player.setPlayerX(player.getPlayerX() + player.getSpeed());
            if(player.checkSpeedy((int)gameController.getGameTime())){
                dx = -player.getSuperSpeed();
            }
            else {
                dx = -(int)player.getSpeed();
            }
            player.setRealX(player.getRealX() + dx);
            player.getPlayerSprite().flip(true, false);
            movingThisFrame = true;
        }
        else if(input.isKeyPressed(Input.Keys.R)){
            if(gameController.isAutoReload()){
                weaponController.reload();
            }
        }



        float deltaTime = Gdx.graphics.getDeltaTime();

        if (movingThisFrame) {
            footstepTimer += deltaTime;
            if (footstepTimer >= FOOTSTEP_INTERVAL) {
                footstepSound.play();
                footstepTimer = 0f;
            }
        } else {
            footstepTimer = FOOTSTEP_INTERVAL;
        }

        gameController.moveAll(dx, dy);
    }



    public void idleAnimation(){
        Animation<Texture> animation = null;
        if(player.getHeroName().equals("Shana")){
            animation = GameAssetManager.getInstance().getCharacter1_idle_frames();
        }
        else if(player.getHeroName().equals("Diamond")){
            animation = GameAssetManager.getInstance().getcharacter2_idle_frames();
        }
        else if(player.getHeroName().equals("Scarlet")){
            animation = GameAssetManager.getInstance().getcharacter3_idle_frames();
        }
        else if(player.getHeroName().equals("Lilith")){
            animation = GameAssetManager.getInstance().getcharacter4_idle_frames();
        }
        else{
            animation = GameAssetManager.getInstance().getcharacter5_idle_frames();
        }

        player.getPlayerSprite().setRegion(animation.getKeyFrame(player.getTime()));

        if (!animation.isAnimationFinished(player.getTime())) {
            player.setTime(player.getTime() + Gdx.graphics.getDeltaTime());
        }
        else {
            player.setTime(0);
        }

        animation.setPlayMode(Animation.PlayMode.LOOP);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    private long shootingTime = 0;
    public void autoAim(){
        if((TimeUtils.millis() - shootingTime) >= 500 && isAutoAiming){
            shootingTime = TimeUtils.millis();
            Enemy closestEnemy = gameController.closestEnemy();
            if(closestEnemy != null){
                weaponController.handleWeaponRotation((int)closestEnemy.getEnemySprite().getX(),(int)closestEnemy.getEnemySprite().getY());
                weaponController.handleShooting((int)closestEnemy.getEnemySprite().getX(),Gdx.graphics.getHeight() - (int)closestEnemy.getEnemySprite().getY());
                input.setCursorPosition((int)closestEnemy.getEnemySprite().getX(),Gdx.graphics.getHeight() - (int)closestEnemy.getEnemySprite().getY());
            }
        }
    }



    public boolean isAutoAiming() {
        return isAutoAiming;
    }

    public void setAutoAiming(boolean autoAiming) {
        isAutoAiming = autoAiming;
    }

    private List<AbilitiName> getRandomAbilities() {
        List<AbilitiName> all = new ArrayList<>(Arrays.asList(AbilitiName.values()));
        Collections.shuffle(all);
        return all.subList(0, 3);
    }

    public void upgrading() {
        List<AbilitiName> choices = getRandomAbilities();
        Skin skin = GameAssetManager.getInstance().getSkin();
        Dialog upgradeDialog = new Dialog("Choose an AbilitiName", skin);

        for (AbilitiName ability : choices) {
            TextButton optionButton = new TextButton(ability.getDisplayName(), skin);
            optionButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    applyAbility(ability);
                    player.addAbility(ability);
                    upgradeDialog.hide();
                }
            });
            upgradeDialog.button(optionButton);
        }

        upgradeDialog.show(gameController.getStage()); // Make sure your stage is accessible here
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(gameController.getStage());
        multiplexer.addProcessor(gameController.getGameView()); // or whatever handles shooting
        Gdx.input.setInputProcessor(multiplexer);
    }


    private void applyAbility(AbilitiName ability) {
        switch (ability) {
            case VITALITY:
                applyVitality();
                break;
            case DAMAGER:
                applyDamager();
                break;
            case PROCREASE:
                applyProcrease();
                break;
            case AMOCREASE:
                applyAmocrease();
                break;
            case SPEEDY:
                speedy();
                break;
        }
    }


    private void applyVitality(){
        player.setInitialHealth(player.getInitialHealth() + 20);
        player.setHealth(player.getInitialHealth());
    }

    private void applyDamager(){
        player.getWeapon().increaseDamage((int)gameController.getGameTime());
    }
    private void applyProcrease(){
        player.getWeapon().addProjecTile();
    }
    private void applyAmocrease(){
        weaponController.getWeapon().increaseAmmo();
    }
    private void speedy(){
        player.applySpeedy((int)gameController.getGameTime());
    }


}
