package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.untilDawn.Main;
import com.untilDawn.Module.*;
import com.untilDawn.View.EndingGame;
import com.untilDawn.View.GameView;
import com.untilDawn.View.MainMenu;

import java.util.ArrayList;
import java.util.Iterator;

import static com.badlogic.gdx.math.MathUtils.random;

public class GameController {
    private GameView gameView;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private ArrayList<Seed> seeds;
    private ArrayList<EnemyController> enemyController =new ArrayList<>();
    private ArrayList<ShootingMonsterController> shootingMonsterControllers = new ArrayList<>();
    private ArrayList <Tree> trees = new ArrayList <>();
    private float gameTime = 0;
    private float spawnTimer = 0f;
    private Player player;
    private ArrayList<WingedMonster> wingedMonsters = new ArrayList<>();
    private float heartAnimationTime = 0f;
    private Animation<Texture> heartAnimation = GameAssetManager.getInstance().getHeart_frames();
    private Sprite heartSprite;
    private Sprite emtyHeartSprite;
    private BitmapFont font = GameAssetManager.getInstance().getFont();
    private long timeForTree;
    private long gameSpan;
    private ArrayList<DeadEnemy> deadEnemies = new ArrayList<>();
    private boolean isPaused = false;
    private boolean isPauseMenuShown = false;
    private boolean mute;
    private boolean autoReload;
    private boolean blackAndWhite;
    private boolean givUp;
    private CheatCodes cheatCodes;


    public GameController(){
        player = App.getInstance().getCurrentUser().getPlayer();
        gameSpan = player.getGameSpan();
        givUp = false;
        cheatCodes = new CheatCodes(this);
        initializeSounds();

    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
        Enemy enemy = new Enemy(10,10);
        weaponController = new WeaponController(player.getWeapon(),player);
        enemyController.add( new EnemyController(enemy,player));
        enemyController.add( new EnemyController(new Enemy(1000,1000), player));
        shootingMonsterControllers.add(new ShootingMonsterController(player,new ShootingMonster(50,50)));
        seeds = new ArrayList<>();
        playerController = new PlayerController(player,weaponController,this);
        player.setPlayerController(playerController);
        worldController = new WorldController(playerController);
        trees.add(new Tree(100,100));
        timeForTree = (long)gameTime;
        mute = player.isMuteSFX();
        autoReload = player.isAutoReload();
        blackAndWhite = player.isBlackAndWhite();

    }

    public void update(float deltaTime) {
            if(!isPaused){
                worldController.update();
                playerController.update();
                weaponController.update();
                handleTree();
                checkSeeds();
                enemySpawnTime();
                gameTime += deltaTime;
//        monsterSpawnTime();
                for (ShootingMonsterController shootingMonsterController : shootingMonsterControllers) {
                    shootingMonsterController.update();
                }
                shootingEnemy();
                for (EnemyController controller : enemyController) {
                    controller.update();
                }
                shootingMonstersShoot();
                handleWingedMonsters();
                showingHealth();
                showTime();
                endGame();
                showKills();
                updateAndDrawDeadEnemies(Gdx.graphics.getDeltaTime());
                damageAnimation(Gdx.graphics.getDeltaTime());
                isPauseMenuShown = false;
                weaponController.showingReload();
                printCircle();
                showRemainBullets();
                showLevel();
                drawLevelProgressBar();
                monsterSpawnTime();
                showHitPlayer();
//                weaponController.getWeapon().checkProcrease((int)gameTime);

            }

            else if(!isPauseMenuShown){
                pause();
                isPauseMenuShown = true;
            }


    }

    public WeaponController getWeaponController() {
        return weaponController;
    }

    public void enemySpawnTime(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        float spawnInterval = Math.max(1f, 1.5f - gameTime / 10f);


        spawnTimer += deltaTime;

        if (spawnTimer >= spawnInterval) {
            int x = spawnEnemy()[0];
            int y = spawnEnemy()[1];
            Enemy newEnemy = new Enemy(x, y);
            enemyController.add(new EnemyController(newEnemy, player));
            spawnTimer = 0f;
        }
    }

    private int[] spawnEnemy(){
        int rand = random.nextInt(4);
        float spawnX =0;
        float spawnY =0;
        switch (rand) {
            case 0:
                spawnX = (float) Math.random() * Gdx.graphics.getWidth();
                spawnY = Gdx.graphics.getHeight();
                break;
            case 1:
                spawnX = (float) Math.random() * Gdx.graphics.getWidth();
                spawnY = 10;
                break;
            case 2:
                spawnX = Gdx.graphics.getWidth();
                spawnY = (float) Math.random() * Gdx.graphics.getHeight();
                break;
            case 3:
                spawnX = 10;
                spawnY = (float) Math.random() * Gdx.graphics.getHeight();
                break;
        }

        return new int[]{(int)spawnX,(int)spawnY};
    }
    private float lastSpawnTime = 0;

    public void monsterSpawnTime(){
        if(gameTime * 4 < gameSpan){
            return;
        }
        if(gameTime < lastSpawnTime + 10){
            return;
        }
        float count = ((4 * gameTime - gameSpan) / 30) * 30;
        int x = spawnEnemy()[0];
        int y = spawnEnemy()[1];
        ShootingMonster monster = new ShootingMonster(x,y);
        shootingMonsterControllers.add(new ShootingMonsterController(player,monster));
        lastSpawnTime = gameTime;
    }


    private Sound bulletHitSound;
    private Sound bloodSplashSound;

    public void initializeSounds() {
        bulletHitSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/single_shot.wav"));
        bloodSplashSound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Blood_Splash_Quick_01.wav"));
    }

    public void shootingEnemy() {
        Iterator<EnemyController> enemyIterator = enemyController.iterator();

        while (enemyIterator.hasNext()) {
            EnemyController controller = enemyIterator.next();
            Enemy enemy = controller.getEnemy();

            if (enemy.getRect().collides(player.getCollisionRect())) {
                if (!player.isInvincible((long) gameTime)) {
                    player.decreaseHealth(5);
                    player.setInvincibleTime((long) gameTime);
                }
                continue;
            }

            if (checkBulletsHit(enemy)) {
                enemyIterator.remove();
            }
        }

        Iterator<WingedMonster> wingedMonsterIterator = wingedMonsters.iterator();
        while (wingedMonsterIterator.hasNext()) {
            WingedMonster wingedMonster = wingedMonsterIterator.next();

            if(wingedMonster.getCollisionRect().collides(player.getCollisionRect())){
                if(!player.isInvincible((long) gameTime)){
                    player.decreaseHealth(5);
                    player.setInvincibleTime((long) gameTime);
                }
            }
            if(chceckBullethitWingedMonster(wingedMonster)){
                wingedMonsterIterator.remove();
            }
        }

        Iterator<ShootingMonsterController> shootingMonsterControllerIterator = shootingMonsterControllers.iterator();
        while (shootingMonsterControllerIterator.hasNext()) {
            ShootingMonster shootingMonster = shootingMonsterControllerIterator.next().getMonster();
            if(checkBulletsHitShootingMonster(shootingMonster)){
                shootingMonsterControllerIterator.remove();
            }
        }
    }

    private boolean checkBulletsHit(Enemy enemy) {
        Iterator<Bullet> bulletIterator = weaponController.getBullets().iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            if (!enemy.getRect().collides(bullet.getRect())) continue;

            bulletIterator.remove();
            if(weaponController.getWeapon().checkProcrease((int)gameTime)){
                applyDamageToEnemy(enemy, weaponController.getWeapon().getProDamage());
            }
            else{
                applyDamageToEnemy(enemy, weaponController.getWeapon().getDamage());
            }
            applyKnockback(enemy, bullet, 10f);

            if (!mute) bulletHitSound.play();

            if (enemy.getHealth() <= 0) {
                handleEnemyDeath(enemy);
                return true; // Enemy removed from list
            }

            break; // Bullet hit, stop checking more
        }

        return false;
    }

    private boolean checkBulletsHitShootingMonster(ShootingMonster shootingMonster) {
        Iterator<Bullet> bulletIterator = weaponController.getBullets().iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            if (!shootingMonster.getCollisionRect().collides(bullet.getRect())) continue;

            bulletIterator.remove();
            if(weaponController.getWeapon().checkProcrease((int)gameTime)){
                shootingMonster.decreaseHealth(weaponController.getWeapon().getProDamage());
            }
            else{
                shootingMonster.decreaseHealth(weaponController.getWeapon().getDamage());
            }

            if (!mute) bulletHitSound.play();

            if (shootingMonster.getHealth() <= 0) {
                handleSeed((int) shootingMonster.getX(), (int) shootingMonster.getY());
                player.increaseKills();
                if (!mute) bloodSplashSound.play();
                return true; // Enemy removed from list
            }

            break; // Bullet hit, stop checking more
        }

        return false;
    }

    private boolean chceckBullethitWingedMonster(WingedMonster wingedMonster){
        Iterator<Bullet> bulletIterator = weaponController.getBullets().iterator();

        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            if (!wingedMonster.getCollisionRect().collides(bullet.getRect())) continue;

            bulletIterator.remove();
            if(weaponController.getWeapon().checkProcrease((int)gameTime)){
                applyDamageToWingedMonster(wingedMonster, weaponController.getWeapon().getProDamage());
            }
            else{
                applyDamageToWingedMonster(wingedMonster, weaponController.getWeapon().getDamage());
            }

            if (!mute) bulletHitSound.play();

            if (wingedMonster.getHealth() <= 0) {
                handleWingedMonsterDeath(wingedMonster);
                return true; // Enemy removed from list
            }

            break; // Bullet hit, stop checking more
        }

        return false;
    }

    private void applyDamageToEnemy(Enemy enemy, int damage) {
        enemy.decreaseHealth(damage);
    }

    private void applyDamageToWingedMonster(WingedMonster wingedMonster, int damage) {
        wingedMonster.decreaseHealth(damage);
    }

    private void applyKnockback(Enemy enemy, Bullet bullet, float knockbackStrength) {
        float dx = enemy.getEnemySprite().getX() - bullet.getSprite().getX();
        float dy = enemy.getEnemySprite().getY() - bullet.getSprite().getY();
        Vector2 knockback = new Vector2(dx, dy).nor().scl(knockbackStrength);

        float newX = enemy.getEnemyX() + knockback.x;
        float newY = enemy.getEnemyY() + knockback.y;

        enemy.setEnemyX(newX);
        enemy.setEnemyY(newY);
        enemy.getEnemySprite().setPosition(newX, newY);
        enemy.getRect().setPosition(newX, newY);
    }

    private void handleEnemyDeath(Enemy enemy) {
        handleSeed((int) enemy.getEnemyX(), (int) enemy.getEnemyY());
        player.increaseKills();
        deadEnemies.add(new DeadEnemy(enemy));

        if (!mute) bloodSplashSound.play();
    }

    private void handleWingedMonsterDeath(WingedMonster wingedMonster) {
        handleSeed(wingedMonster.getX(), wingedMonster.getY());
        player.increaseKills();
        if(!mute) bloodSplashSound.play();
    }


    public void shootingMonstersShoot(){
        Iterator<ShootingMonsterController> iterator = shootingMonsterControllers.iterator();
        while (iterator.hasNext()) {
            ShootingMonsterController controller = iterator.next();
            Iterator<Bullet> bulletIterator = controller.getBullets().iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if(bullet.getRect().collides(player.getCollisionRect())){
                    bulletIterator.remove();
                    if(!player.isInvincible(gameTime)){
                        player.decreaseHealth(3);
                        player.setInvincibleTime(gameTime);
                        showHitAnimation = true;
                        hitAnimationTime = 0f;
                    }
                }
                if(calcDistance(bullet.getSprite().getX(), bullet.getSprite().getY(),controller.getMonster().getX(), controller.getMonster().getY()) > calcDistance(player.getPlayerSprite().getX(),player.getPlayerSprite().getY(),controller.getMonster().getX(), controller.getMonster().getY())+20){
                    bulletIterator.remove();
                }
                if(controller.getMonster().getCollisionRect().collides(player.getCollisionRect())){
                    if(!player.isInvincible(gameTime)){
                        player.decreaseHealth(10);
                        damageElapsedTime = 0f;
                        damage = true;
                        break;
                    }

                }
            }

        }
    }

    private int calcDistance(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void handleSeed(int x, int y) {
        seeds.add(new Seed(x, y));
    }

    public void checkSeeds(){
        for (Seed seed : seeds) {
            if(seed.collides(player.getCollisionRect())){
                seeds.remove(seed);
                Sound sound = Gdx.audio.newSound(Gdx.files.internal("Sfx/Coins (10).wav"));
                if(!mute)
                    sound.play();
                player.addXp(3);
                break;
            }
        }
        for (Seed seed : seeds) {
            seed.getSprite().draw(Main.getBatch());
        }
    }

    public void handleTree() {
        // Limit max number of trees
        if (trees.size() < 100) {
            // Try every few seconds
            if (gameTime - (timeForTree) > 2) {
                timeForTree = (long) gameTime;

                float mapWidth = 3000;
                float mapHeight = 1000;

                float randomX = MathUtils.random(0, mapWidth);
                float randomY = MathUtils.random(0, mapHeight);

                // Keep trees away from player
                float playerX = player.getRealX(); // Use actual map position
                float playerY = player.getRealY();

                float dist = Vector2.dst(randomX, randomY, playerX, playerY);

                if (dist > 200) { // Only spawn if far from player
                    boolean overlaps = false;
                    for (Tree t : trees) {
                        if (Vector2.dst(t.getSprite().getX(), t.getSprite().getY(), randomX, randomY) < 64) { // Avoid overlaps
                            overlaps = true;
                            break;
                        }
                    }

                    if (!overlaps) {
                        trees.add(new Tree((int) randomX, (int) randomY));
                    }
                }
            }
        }

        // Handle collisions
        Iterator<Tree> iterator = trees.iterator();
        while (iterator.hasNext()) {
            Tree tree = iterator.next();
            if (tree.collides(player.getCollisionRect())) {
                if(!player.isInvincible(gameTime)){
                    player.decreaseHealth(5);
                    player.setInvincibleTime(gameTime);
                    break;
                }
            }
        }

        // Render trees
        for (Tree tree : trees) {
            tree.getSprite().draw(Main.getBatch());
        }
    }


    private final int MAX_TREES = 30;


    public void handleWingedMonsters() {
        if(gameTime * 2 >= gameSpan ){
            if(wingedMonsters.isEmpty()){
                wingedMonsters.add(new WingedMonster(100,100));
            }
            for (WingedMonster wingedMonster : wingedMonsters) {
                if(wingedMonster.canAttack(gameTime)){
                    wingedMonster.moveTowardPlayer();
                }
                else {
                    wingedMonster.moveTowardRandomGoal();
                }
            }
        }

    }

    public void showingHealth() {
        if (heartSprite == null && emtyHeartSprite == null) {
            Texture initialTexture = new Texture(GameAssetManager.getInstance().getHeart0());
            Texture emptyHeart = new Texture(GameAssetManager.getInstance().getHeart3());
            heartSprite = new Sprite(initialTexture);
            emtyHeartSprite = new Sprite(emptyHeart);
            heartAnimation.setPlayMode(Animation.PlayMode.LOOP);
        }

        heartAnimationTime += Gdx.graphics.getDeltaTime();
        Texture currentFrame = heartAnimation.getKeyFrame(heartAnimationTime, true);

        int heartsCount = player.getHealth() / 20 + 1;
        int maxHearts = player.getInitialHealth() / 20 + 1;
        int heartSize = 32;

        // Draw empty hearts first, spaced evenly
        for(int i = 0; i < maxHearts; i++){
            emtyHeartSprite.setSize(heartSize, heartSize);
            emtyHeartSprite.setPosition(20 + i * (heartSize + 5), Gdx.graphics.getHeight() - 40);
            emtyHeartSprite.draw(Main.getBatch());
        }

        // Draw full hearts over the empty ones
        for (int i = 0; i < heartsCount; i++) {
            heartSprite.setRegion(currentFrame);
            heartSprite.setSize(heartSize, heartSize);
            heartSprite.setPosition(20 + i * (heartSize + 5), Gdx.graphics.getHeight() - 40);
            heartSprite.draw(Main.getBatch());
        }
    }



    public void moveAll(int dx , int dy) {
        for (EnemyController enemyController : enemyController) {
            enemyController.escape(dx,dy);
        }
        for (ShootingMonsterController shootingMonsterController : shootingMonsterControllers) {
            shootingMonsterController.escape(dx,dy);
            shootingMonsterController.moveBullets(dx,dy);
        }
        weaponController.moveBullets(dx,dy);
        for (Tree tree : trees) {
            tree.move(dx, dy);
        }
        for (Seed seed : seeds) {
            seed.move(dx, dy);
        }
        for (WingedMonster wingedMonster : wingedMonsters) {
            wingedMonster.move(dx, dy);
        }
    }

    public Enemy closestEnemy() {
        Enemy closestEnemy = null;
        float closestDistance = Float.MAX_VALUE;

        float playerX = player.getPlayerSprite().getX();
        float playerY = player.getPlayerSprite().getY();

        for (EnemyController enemyController : enemyController) {
            Enemy enemy = enemyController.getEnemy();
            float dx = enemy.getEnemySprite().getX() - playerX;
            float dy = enemy.getEnemySprite().getY() - playerY;
            float distance = (float) Math.sqrt((dx * dx) + (dy * dy));

            if (distance < closestDistance) {
                closestDistance = distance;
                closestEnemy = enemy;
            }
        }

        return closestEnemy;
    }

    private void endGame(){
        if(player.getHealth() <= 0){
            player.setSurvivalTime(gameTime);
            player.setWin(false);
            handleUser();
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new EndingGame(GameAssetManager.getInstance().getSkin()));
        }

        if(gameTime > gameSpan){
            player.setSurvivalTime(gameTime);
            player.setWin(false);
            handleUser();
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new EndingGame(GameAssetManager.getInstance().getSkin()));
        }

    }

    private void handleUser(){
        User user = App.getInstance().getCurrentUser();
        user.setScore(player.getScore());
        user.setKills(user.getKills());
        user.setSurvivalTime(player.getSurvivalTime());
        user.setHasWonLastGame(player.isWin());
    }

    private void showTime() {
        float elapsedMillis = gameTime;
        elapsedMillis = gameSpan - elapsedMillis;

        int totalSeconds = (int) (elapsedMillis);

        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String timeString = String.format("%02d:%02d", minutes, seconds);

        float x = Gdx.graphics.getWidth() / 2f;
        float y = Gdx.graphics.getHeight() - 20;


        font.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        font.draw(Main.getBatch(), timeString, x + 1, y - 1);

        font.setColor(Color.GREEN);
        font.draw(Main.getBatch(), timeString, x, y);
    }

    private void showKills(){
        float x = Gdx.graphics.getWidth() / 2f + 400;
        float y = Gdx.graphics.getHeight() - 20;
        font.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        font.draw(Main.getBatch(), "Kills: "+player.getKill(), x, y);
    }

    private Texture enemyDeath;
    private Sprite enemyDeathsprite;
    private Animation<Texture> enemyDeathAnimation = GameAssetManager.getInstance().getenemyDeath_frames();
    public void updateAndDrawDeadEnemies(float deltaTime) {
        Iterator<DeadEnemy> iterator = deadEnemies.iterator();

        while (iterator.hasNext()) {
            DeadEnemy deadEnemy = iterator.next();

            deadEnemy.elapsedTime += deltaTime;

            // Play animation, not looping
            Texture currentFrame = enemyDeathAnimation.getKeyFrame(deadEnemy.elapsedTime, false);

            if (enemyDeathsprite == null) {
                enemyDeathsprite = new Sprite(currentFrame);
            } else {
                enemyDeathsprite.setRegion(currentFrame);
            }

            // Position the sprite at enemy location
            enemyDeathsprite.setPosition(deadEnemy.enemy.getEnemyX(), deadEnemy.enemy.getEnemyY());
            enemyDeathsprite.setSize(64, 64); // or any size you want
            enemyDeathsprite.draw(Main.getBatch());

            // Remove dead enemy after 1 second (or after animation ends)
            if (deadEnemy.elapsedTime >= 1f) {
                iterator.remove();
            }
        }
    }


    private float hitAnimationTime;
    private boolean showHitAnimation = false;
    private Animation<Texture> hitAnimation = GameAssetManager.getInstance().getenemyDeath_frames();

    public void showHitPlayer(){
        if (showHitAnimation) {
            hitAnimationTime += Gdx.graphics.getDeltaTime();
            Texture currentFrame = hitAnimation.getKeyFrame(hitAnimationTime, false);

            Main.getBatch().draw(currentFrame, player.getPlayerSprite().getX(), player.getPlayerSprite().getY());

            if (hitAnimation.isAnimationFinished(hitAnimationTime)) {
                showHitAnimation = false;
            }
        }
    }







    private Texture hitAnimationTexture;
    private Sprite hitAnimationSprite;

    private float damageElapsedTime = 0f;
    private boolean damage = false;

    public void damageAnimation(float deltaTime) {

        if(damage){
            damageElapsedTime += deltaTime;
            Texture currentFrame = enemyDeathAnimation.getKeyFrame(damageElapsedTime, false);

            if (enemyDeathsprite == null) {
                enemyDeathsprite = new Sprite(currentFrame);
            } else {
                enemyDeathsprite.setRegion(currentFrame);
            }
            // Position the sprite at enemy location
            enemyDeathsprite.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
            enemyDeathsprite.setSize(64, 64); // or any size you want
            enemyDeathsprite.draw(Main.getBatch());

            // Remove dead enemy after 1 second (or after animation ends)
            if (damageElapsedTime >= 1f) {
                damage = false;
            }
        }

    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Stage getStage() {
        return gameView.getStage();
    }

    public void pause() {
        Stage stage = gameView.getStage();
        Skin skin = GameAssetManager.getInstance().getSkin();
        PauseMenuDialog dialog = new PauseMenuDialog(skin,
            () -> isPaused = false,() -> {
            player.setSurvivalTime(gameTime);
            player.setWin(false);
            Main.getMain().getScreen().dispose();
            Main.getMain().setScreen(new EndingGame(GameAssetManager.getInstance().getSkin()));
        },
            () -> {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new MainMenu(new MainMenuController(), skin));
            },
            gameView.getStage()
            );
        stage.addActor(dialog);
        dialog.show(stage);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(gameView); // or whatever handles shooting
        Gdx.input.setInputProcessor(multiplexer);
    }
    Sprite circleSprite;
    Texture circleTexture = null;
    public void printCircle(){
        if(circleTexture == null){
            circleTexture = new Texture(Gdx.files.internal("Soft.png"));
            circleSprite = new Sprite(circleTexture);
            circleSprite.setColor(1,1,1,0.1f);
            float centerX = Gdx.graphics.getWidth() / 2f;
            float centerY = Gdx.graphics.getHeight() / 2f;
            float scale = 4f;
            float width = circleTexture.getWidth() * scale;
            float height = circleTexture.getHeight() * scale;
            float drawX = centerX - width / 2f;
            float drawY = centerY - height / 2f;
            circleSprite.setPosition(drawX, drawY);
            circleSprite.setSize(width, height);
        }
        circleSprite.draw(Main.getBatch());
    }

    public boolean isAutoReload(){
        return autoReload;
    }

    public boolean isBlackAndWhite(){
        return blackAndWhite;
    }

    public void addGameTime(int time){
        this.gameTime += time;
    }

    public CheatCodes getCheatCodes() {
        return cheatCodes;
    }


    public void showRemainBullets(){
        float x = Gdx.graphics.getWidth() / 2f + 800;
        float y = Gdx.graphics.getHeight() - 20;
        font.setColor(Color.GREEN);
        String string = "Bullets: "+weaponController.getWeapon().getBullets();
        font.draw(Main.getBatch(),string,x,y);
    }


    public void showLevel(){
        float x = Gdx.graphics.getWidth() / 2f - 200;
        float y = Gdx.graphics.getHeight() - 20;
        font.setColor(Color.GREEN);
        String string = "Level: "+ player.getLevel();
        font.draw(Main.getBatch(),string,x,y);
    }
    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    public void drawLevelProgressBar() {
        Main.getBatch().end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        float x = Gdx.graphics.getWidth() / 2f - 600;
        float y = Gdx.graphics.getHeight() - 30;
        float width = 200;
        float height = 20;

        // Background
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, width, height);

        // XP Fill
        float progressRatio = player.getXp() / (float) player.getRequieredXp();
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, width * progressRatio, height);

        shapeRenderer.end();
        Main.getBatch().begin();
    }

    public void addWingedMonster(){
        int x = random.nextInt(1000);
        int y = random.nextInt(1000);
        if(gameTime * 2 > gameSpan){
            wingedMonsters.add(new WingedMonster(x,y));
        }
    }

    public float getGameTime(){
        return gameTime;
    }

    public GameView getGameView(){
        return gameView;
    }



}
