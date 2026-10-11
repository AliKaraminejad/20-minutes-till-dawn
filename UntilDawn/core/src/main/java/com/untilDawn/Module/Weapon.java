package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.untilDawn.Controller.WeaponController;

public class Weapon {
    private Texture texture = new Texture("Sfx/T_Shotgun_SS_0.png");
    private Sprite sprite = new Sprite(texture);
    private int Bullets ;
    private int projecTile;
    private int damage;
    private int ammo;
    private int lastProcreaseTime;
    private int proDamage;
    private WeaponController controller;

    public Weapon(int projecTile) {
        this.controller = controller;
        sprite.setX((float) Gdx.graphics.getWidth() / 2 );
        sprite.setY((float) Gdx.graphics.getHeight() / 2);
        sprite.setSize(50,50);
        this.projecTile = projecTile;
        damage = 25;
        lastProcreaseTime = 0;
        Bullets = 6;
        ammo = Bullets;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setBullets(int bullets) {
        Bullets = bullets;
    }

    public int getBullets() {
        return Bullets;
    }

    public int getProjecTile() {
        return projecTile;
    }

    public void addProjecTile(){
        projecTile++;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void increaseDamage(int time){
        proDamage = 5*damage /4;
        lastProcreaseTime = time;
    }

    public boolean checkProcrease(int time){
        if(lastProcreaseTime!=0 && time < lastProcreaseTime + 10){
            return true;
        }
        return false;
    }

    public void increaseAmmo(){
        ammo+=5;
    }

    public int getAmmo(){
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getProDamage() {
        return proDamage;
    }

    public void resetWeapon(){
        Bullets = ammo;
    }

    public void setController(WeaponController weaponController){
        controller = weaponController;
    }

    public boolean isEmpty(){
        if(Bullets == 0){
            return true;
        }
        return false;
    }
}
