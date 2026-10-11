package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.untilDawn.Main;
import com.untilDawn.Module.Enemy;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.Player;

public class EnemyController {
    private Enemy enemy;
    private Player player;

    public EnemyController(Enemy enemy,Player player){
        this.enemy = enemy;
        this.player = player;
    }

    public void update() {
        moveEnemy();

    }

    public void moveEnemy(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        float enemyX = enemy.getEnemySprite().getX();
        float enemyY = enemy.getEnemySprite().getY();
        float targetX = player.getPlayerSprite().getX();
        float targetY = player.getPlayerSprite().getY();

        float dx = targetX - enemyX;
        float dy = targetY - enemyY;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance != 0) {
            dx /= distance;
            dy /= distance;
        }

        float speed = 50f;
        float velocityX = dx * speed * deltaTime;
        float velocityY = dy * speed * deltaTime;

        enemyX += velocityX;
        enemyY += velocityY;


        enemy.setEnemyX(enemyX);
        enemy.setEnemyY(enemyY);
        enemy.getEnemySprite().setPosition(enemyX, enemyY);
        enemy.getRect().setPosition(enemyX, enemyY);

        enemy.setTime(enemy.getTime() + deltaTime);

        Animation<Texture> animation = GameAssetManager.getInstance().getMonster_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Texture currentFrame = animation.getKeyFrame(enemy.getTime(), true);
        enemy.getEnemySprite().setRegion(currentFrame);
        if(enemy.getEnemySprite().getX() > player.getPlayerSprite().getX()){
            enemy.getEnemySprite().flip(true, false);
        }

        enemy.getEnemySprite().draw(Main.getBatch());

//        if (enemy.getRect().collides(player.getCollisionRect())) {
//            Gdx.app.exit();
//        }
    }

    public void escape(int dx, int dy) {
        enemy.move(dx,dy);
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
