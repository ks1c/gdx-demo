package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Rectangle {

    static final public float WIDTH = 32;
    static final public float HEIGHT = 32;


    static final public Rectangle DEAD_ZONE = new Rectangle(
            GdxDemo.GAME_WIDTH / 2f - WIDTH / 2,
            GdxDemo.GAME_HEIGHT / 2f - HEIGHT / 2,
            WIDTH,
            HEIGHT
    );

    private final Sprite sprite;

    public Player() {
        super(0, 0, WIDTH, HEIGHT);
        sprite = new Sprite(new Texture("player.png"));
    }

    public void init() {
    }

    public float moveRight() {

        return -5f;
    }

    public float moveLeft() {

        return 5f;
    }

    public float moveUp() {

        return -5f;
    }

    public float moveDown() {

        return 5f;
    }

    public void render(SpriteBatch batch) {
        sprite.setAlpha(0);
        batch.draw(sprite, x, y);
    }

    public void addX(float x) {
        setPosition(getX() + x, getY());
    }

    public void addY(float y) {
        setPosition(getX(), getY() + y);
    }
}
