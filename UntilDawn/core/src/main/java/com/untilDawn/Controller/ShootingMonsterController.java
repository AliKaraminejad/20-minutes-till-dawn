package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.untilDawn.Main;
import com.untilDawn.Module.Bullet;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.Player;
import com.untilDawn.Module.ShootingMonster;

import java.util.ArrayList;

public class ShootingMonsterController {
    private Player player;
    private ShootingMonster monster;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public ShootingMonsterController(Player player, ShootingMonster monster) {
        this.player = player;
        this.monster = monster;
    }

    private float timer = 0f;
    private final float interval = 3f;

    public void update() {
        move();

        timer += Gdx.graphics.getDeltaTime();
        if (timer >= interval) {
            timer = 0f;
            shooting();
        }
        updateBullets();
    }

    private void move(){
        float delta = Gdx.graphics.getDeltaTime();

        float monsterX = monster.getX();
        float monsterY = monster.getY();
        float targetX = player.getPlayerSprite().getX();
        float targetY = player.getPlayerSprite().getY();

        float dx = targetX - monsterX;
        float dy = targetY - monsterY;

        int distance = (int) Math.sqrt(dx*dx + dy*dy);
        if(distance != 0){
            dx /= distance;
            dy /= distance;
        }

        float speed = 50f;

        float velx = dx * delta * speed;
        float vely = dy * delta * speed;

        monster.setX(monster.getX()+velx);
        monster.setY(monster.getY()+vely);
        monster.getSprite().setPosition(monster.getX(), monster.getY());
        monster.getCollisionRect().setPosition(monster.getX(), monster.getY());

        monster.setTime(monster.getTime() + delta);

        com.badlogic.gdx.graphics.g2d.Animation<Texture> animation = GameAssetManager.getInstance().getEyeBat_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Texture currentFrame = animation.getKeyFrame(monster.getTime(), true);
        monster.getSprite().setRegion(currentFrame);
        if(monster.getSprite().getX() > player.getPlayerSprite().getX()){
            monster.getSprite().flip(true, false);
        }

        monster.getSprite().draw(Main.getBatch());

    }

    public void escape(int dx,int dy){
        monster.move(dx,dy);
    }

    private void shooting(){
        bullets.add(new Bullet((int)player.getPlayerSprite().getX(),(int)player.getPlayerSprite().getY(),(int)monster.getX()+ (int)monster.getSprite().getWidth(),(int)monster.getY()+(int)monster.getSprite().getHeight()));
//        TODO :decrease monster's bullets
    }

    private void updateBullets(){
        for(Bullet b : bullets) {
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = b.getDirection();
            b.getSprite().setX(b.getSprite().getX() - direction.x * 5);
            b.getSprite().setY(b.getSprite().getY() - direction.y * 5);
            b.getRect().setPosition(b.getSprite().getX(), b.getSprite().getY());
        }
    }

    public void moveBullets(int x , int y){
        for (Bullet bullet : bullets) {
            bullet.getSprite().setPosition(bullet.getSprite().getX()-x, bullet.getSprite().getY()-y);
        }
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ShootingMonster getMonster() {
        return monster;
    }
}
