package com.untilDawn.Module;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.untilDawn.Main;

import static com.badlogic.gdx.math.MathUtils.random;

public class WingedMonster {
    private int x, y;
    private CollisionRect collisionRect;
    private Texture texture;
    private Sprite sprite;
    private float time1 =0;
    private float time2 =0;
    private int health = 400;
    private float lastDashTime = 0;

    public WingedMonster(int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(GameAssetManager.getInstance().getWingedMonster0());
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(this.x, this.y);
        this.sprite.setSize(texture.getWidth(), texture.getHeight());
        this.collisionRect = new CollisionRect(x, y, sprite.getWidth(), sprite.getHeight());

    }

    public void move(int dx, int dy) {
        this.x -= dx;
        this.y -= dy;
        this.sprite.setPosition(x, y);
        this.collisionRect.setPosition(x, y);
    }

    public boolean collides(CollisionRect collisionRect) {
        if(this.collisionRect.collides(collisionRect)) {
            return true;
        }
        return false;
    }

    public void moveTowardPlayer() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        float enemyX = sprite.getX();
        float enemyY = sprite.getY();
        float targetX = (float)Gdx.graphics.getWidth()/2;
        float targetY = (float)Gdx.graphics.getHeight()/2;

        float dx = targetX - enemyX;
        float dy = targetY - enemyY;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance != 0) {
            dx /= distance;
            dy /= distance;
        }

        float speed = 400f;
        float velocityX = dx * speed * deltaTime;
        float velocityY = dy * speed * deltaTime;


        enemyX += velocityX;
        enemyY += velocityY;


        x = (int)(enemyX);
        y = (int) (enemyY);

        sprite.setPosition(x, y);
        collisionRect.setPosition(x, y);

        time1 += ( deltaTime);

        Animation<Texture> animation = GameAssetManager.getInstance().getwingedMonster_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Texture currentFrame = animation.getKeyFrame(time1, true);
        sprite.setRegion(currentFrame);
        sprite.draw(Main.getBatch());

    }

    public void moveTowardRandomGoal(){
        float deltaTime = Gdx.graphics.getDeltaTime();

        float enemyX = sprite.getX();
        float enemyY = sprite.getY();
        float targetX = random.nextInt(Gdx.graphics.getWidth());
        float targetY = random.nextInt(Gdx.graphics.getHeight());

        float dx = targetX - enemyX;
        float dy = targetY - enemyY;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance != 0) {
            dx /= distance;
            dy /= distance;
        }

        float speed = 100f;
        float velocityX = dx * speed * deltaTime;
        float velocityY = dy * speed * deltaTime;


        enemyX += velocityX;
        enemyY += velocityY;


        x = (int)(enemyX);
        y = (int) (enemyY);

        sprite.setPosition(x, y);
        collisionRect.setPosition(x, y);

        time2 += ( deltaTime);

        Animation<Texture> animation = GameAssetManager.getInstance().getwingedMonster_frames();
        animation.setPlayMode(Animation.PlayMode.LOOP);
        Texture currentFrame = animation.getKeyFrame(time2, true);
        sprite.setRegion(currentFrame);
        sprite.draw(Main.getBatch());
    }

    public boolean canAttack(float time) {
        if((time - lastDashTime) >= 10){
            lastDashTime = time;
        }
        if ((time - lastDashTime) >= 5) {
            return true;
        }

        return false;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public CollisionRect getCollisionRect() {
        return collisionRect;
    }

    public void setTime(float time) {
        this.time1 = time;
    }

    public float getTime() {
        return time1;
    }

    public int getHealth() {
        return health;
    }
    public void decreaseHealth(int health) {
        this.health -= health;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
