package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.untilDawn.Controller.PlayerController;
import com.untilDawn.Module.Enums.AbilitiName;

import java.util.ArrayList;

public class Player {
    private Texture playerTexture;
    private Sprite playerSprite;
    private float playerX = 0;
    private float playerY = 0;
    private float playerWidth = 100;
    private float time = 0;
    private float speed ;
    private CollisionRect collisionRect;
    private boolean isIdle = true;
    private boolean isRunning = false;
    private float realY = (float) Gdx.graphics.getWidth() / 2;
    private float realX = (float) Gdx.graphics.getHeight() / 2;
    private int xp =0;
    private int level =1 ;
    private int health ;
    private float invincibleTime =0;
    private int kill;
    private int survivalTime ;
    private int score;
    private boolean win;
    private String heroName;
    private int gameSpan = 120;
    private int initialHealth;
    private boolean muteSFX = false;
    private boolean autoReload = false;
    private boolean blackAndWhite = false;
    private Weapon weapon;
    private PlayerController playerController;


    public Player() {
        this.heroName = "Shana";
        playerTexture = new Texture(GameAssetManager.getInstance().getCharacter1_idle0());
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        collisionRect = new CollisionRect((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight()/2 , playerSprite.getWidth(), playerSprite.getHeight());
        kill = 0;
        speed = 8;
        health = 40;
        initialHealth = 40;
        weapon = new Weapon(1);

    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public float getTime() {
        return time;
    }

    public float getSpeed() {
        return speed;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public boolean isIdle() {
        return isIdle;
    }

    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(float playerY) {
        this.playerY = playerY;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setRealY(float realY) {
        this.realY = realY;
    }

    public void setRealX(float realX) {
        this.realX = realX;
    }

    public float getRealY() {
        return realY;
    }

    public float getRealX() {
        return realX;
    }

    public void addXp(int xp) {
        this.xp += xp;
        if(this.level==1 && this.xp > 20) {
            playerController.upgrading();
            this.level=2;
        }
        else if(this.level==2 && this.xp > 60) {
            playerController.upgrading();
            this.level=3;
        }
        else if(this.level==3 && this.xp > 120) {
            playerController.upgrading();
            this.level=4;
        }

    }

    public boolean isInvincible(float time) {
        if(time - invincibleTime < 1){
            return true;
        }
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int health) {
        this.health -= health;
    }

    public void setInvincibleTime(float invincibleTime) {
        this.invincibleTime = invincibleTime;
    }

    public void increaseKills(){
        this.kill++;
    }

    public int getKill() {
        return kill;
    }

    public void setSurvivalTime(float time) {
        survivalTime =(int) time;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public int getScore(){
        score = kill * survivalTime;
        return score;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isWin() {
        return win;
    }

    public String getHeroName() {
        return heroName;
    }

    public void changeHeroName(String heroName) {
        this.heroName = heroName;
        if (playerTexture != null) playerTexture.dispose();

        switch (heroName) {
            case"Shana":{
                playerTexture = new Texture(GameAssetManager.getInstance().getCharacter1_idle0());
                speed = 6;
                health = 40;
                initialHealth = 40;
                break;
            }
            case "Diamond":{
                playerTexture = new Texture(GameAssetManager.getInstance().getcharacter2_idle0());
                speed = 4;
                health = 70;
                initialHealth = 70;
                break;
            }
            case "Scarlet":{
                playerTexture = new Texture(GameAssetManager.getInstance().getcharacter3_idle0());
                speed = 12;
                health = 30;
                initialHealth = 30;
                break;
            }
            case "Lilith":{
                playerTexture = new Texture(GameAssetManager.getInstance().getcharacter4_idle0());
                speed = 8;
                health = 50;
                initialHealth = 50;
                break;
            }
            case "Dasher":{
                playerTexture = new Texture(GameAssetManager.getInstance().getcharacter5_idle0());
                speed = 18;
                health = 20;
                initialHealth = 20;
                break;
            }
        }
        playerSprite = new Sprite(playerTexture);
        playerSprite.setPosition((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
        playerSprite.setSize(playerTexture.getWidth() * 3, playerTexture.getHeight() * 3);
        time = 0; // reset animation timer
    }

    public int getGameSpan() {
        return gameSpan;
    }

    public void setGameSpan(int gameSpan) {
        this.gameSpan = gameSpan;
    }

    public int getInitialHealth() {
        return initialHealth;
    }

    public void setMuteSFX(boolean muteSFX) {
        this.muteSFX = muteSFX;
    }

    public boolean isMuteSFX() {
        return muteSFX;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public boolean isBlackAndWhite() {
        return blackAndWhite;
    }

    public void addHealth(int health) {
        this.health += health;
    }

    public int getLevel(){
        return level;
    }

    public int getXp() {
        return xp;
    }

    public int getRequieredXp(){
        switch (this.level){
            case 1:
                return 20;

            case 2:
                return 60;

            case 3:
                return 120;

            case 4:
                return 200;

        }
        return 0;
    }

    private int superSpeed;
    private int lastSpeedyTime = 0;
    public boolean checkSpeedy(int time){
        if(lastSpeedyTime!=0 && time < lastSpeedyTime + 10){
            return true;
        }
        return false;
    }

    public void applySpeedy(int time){
        lastSpeedyTime = time;
        superSpeed =(int) speed * 2;
    }

    public int getSuperSpeed(){
        return superSpeed;
    }

    public void changeWeapon(Weapon weapon,int damage,int ammo){
        this.weapon = weapon;
        this.weapon.setDamage(damage);
        this.weapon.setAmmo(ammo);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void setInitialHealth(int initialHealth) {
        this.initialHealth = initialHealth;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private ArrayList<AbilitiName> gainedAbilities = new ArrayList<>();
    public void addAbility(AbilitiName ability){
        gainedAbilities.add(ability);
    }

    public ArrayList<AbilitiName> getGainedAbilities() {
        return gainedAbilities;
    }
}
