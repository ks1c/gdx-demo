package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends Rectangle {

    static final public float WIDTH = 32;
    static final public float HEIGHT = 32;

    private Vector2 displacement;


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
        displacement = new Vector2(5f, 5f);
    }

    public void init() {
    }

    public float moveRight(Vector3 camPos) {

        addX(displacement.x);

        return displacement.x;
    }

    public float moveLeft(Vector3 camPos) {

        addX(-displacement.x);

        return -displacement.x;
    }

    public float moveUp(Vector3 camPos) {

        addY(displacement.y);

        return displacement.y;
    }

    public float moveDown(Vector3 camPos) {

        addY(-displacement.y);

        return -displacement.y;
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
