package com.untilDawn.Module;

public class CollisionRect {
    private float x;
    private float y;
    private float width;
    private float height;

    public CollisionRect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public boolean collides(CollisionRect other) {
        return x < other.x + other.width &&
            x + width > other.x &&
            y < other.y + other.height &&
            y + height > other.y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }


}
