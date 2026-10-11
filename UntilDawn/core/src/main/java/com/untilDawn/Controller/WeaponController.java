package com.untilDawn.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.untilDawn.Main;
import com.untilDawn.Module.Bullet;
import com.untilDawn.Module.GameAssetManager;
import com.untilDawn.Module.Player;
import com.untilDawn.Module.Weapon;

import java.util.ArrayList;

public class WeaponController {
    private Weapon weapon;
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private Player player;
//    private boolean isAutoAim = true;

    public WeaponController(Weapon weapon,Player player){
        this.weapon = weapon;
        this.player = player;
    }

    public void update() {
        weapon.getSprite().draw(Main.getBatch());
        updateBullets();
    }

    public void handleWeaponRotation(int x, int y) {
        Sprite weaponSprite = weapon.getSprite();

        float weaponCenterX = (float) Gdx.graphics.getWidth() / 2;
        float weaponCenterY = (float) Gdx.graphics.getHeight() / 2;

        float angle = (float) Math.atan2(y - weaponCenterY, x - weaponCenterX);

        weaponSprite.setRotation((float) (3.14 - angle * MathUtils.radiansToDegrees));
    }

    public void handleShooting(int x, int y) {
        if(weapon.getBullets() > 0){
            for(int i = 0; i < weapon.getProjecTile(); i++){
                bullets.add(new Bullet(x+(i-(30*(i+1))), y+(i-(30*(i+1))),(int)player.getPlayerSprite().getX(),(int)player.getPlayerSprite().getY()));
            }
            weapon.setBullets(weapon.getBullets() - 1);
        }
    }

    private void updateBullets() {
        for(Bullet b : bullets) {
            b.getSprite().draw(Main.getBatch());
            Vector2 direction = b.getDirection();
            b.getSprite().setX(b.getSprite().getX() - direction.x * 10);
            b.getSprite().setY(b.getSprite().getY() + direction.y * 10);
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

    Texture texture ;
    Sprite sprite ;
    Animation<Texture> animation = GameAssetManager.getInstance().getWeapon_frames();
    float animationTiming = 0;
    private boolean isReloading = false;

    public void reload(){
        if (!isReloading) {
            isReloading = true;
            animationTiming = 0f;
            Texture firstFrame = animation.getKeyFrame(0f);
            sprite = new Sprite(firstFrame);
            sprite.setSize(firstFrame.getWidth(), firstFrame.getHeight());
        }

    }

    public boolean isEmpty(){
        return bullets.isEmpty();
    }

    public void showingReload(){
        if (isReloading) {
            animationTiming += Gdx.graphics.getDeltaTime() / 2;

            Texture currentFrame = animation.getKeyFrame(animationTiming, false); // false = don't loop
            sprite.setRegion(currentFrame);
            sprite.setSize(50, 50);
            sprite.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
            sprite.draw(Main.getBatch());

            if (animation.isAnimationFinished(animationTiming)) {
                isReloading = false;
                weapon.setBullets(weapon.getAmmo());
            }
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }
}
