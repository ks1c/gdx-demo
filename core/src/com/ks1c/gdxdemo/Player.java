package com.ks1c.gdxdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player extends Rectangle {

    static final public float WIDTH = 32;
    static final public float HEIGHT = 32;

    private Vector2 displacement;
    private Vector2 maxVel, minVel, vel;
    private float velCount, velCountMax = 2;

    public Vector2 oldPos;

    public float jumpStep = 0;
    private boolean jumped = false;
    private boolean inTheAir = true;
    private static final float MAX_JUMP_STEP = 13 + World.G;

    public static final Rectangle DEAD_ZONE = new Rectangle(
            GdxDemo.GAME_WIDTH / 2f - WIDTH / 2,
            GdxDemo.GAME_HEIGHT / 2f - HEIGHT / 2,
            WIDTH,
            HEIGHT
    );

    private final Sprite sprite;

    //BOX2D AND BOX2DLIGHTS
    private Body bodyOfLights;


    public Player() {
        super(0, 0, WIDTH, HEIGHT);
        sprite = new Sprite(new Texture("player.png"));
        displacement = new Vector2(2f, 10f);
        maxVel = new Vector2(1, 0);
        minVel = new Vector2(-2, 0);
        vel = new Vector2();
        oldPos = new Vector2();
    }

    public void init() {
    }

    public void fall(float G) {

        setInTheAir(true);

        if (G - jumpStep > 0) {
            displacement.y = G - jumpStep;
            moveDown();
        } else {
            displacement.y = jumpStep - G;
            moveUp();
        }

        if (jumpStep > 0) {
            jumpStep -= 1;
        }
    }

    public void jump() {

        if (!isInTheAir() && !jumped) {
            jumped = true;
            setInTheAir(true);
            jumpStep = MAX_JUMP_STEP;
        }
    }

    public void endJump() {
        jumped = false;
    }

    public boolean isInTheAir() {
        return inTheAir;
    }

    public void setInTheAir(boolean inTheAir) {
        this.inTheAir = inTheAir;
    }

    public float getJumpHeight() {

        float jumpStepTmp = MAX_JUMP_STEP;

        float jumpHeight = 0;

        while (jumpStepTmp > 0) {

            if (World.G - jumpStepTmp <= 0) {
                jumpHeight += (jumpStepTmp - World.G);
            }
            jumpStepTmp -= 1;
        }

        return jumpHeight;
    }

    public void moveRight() {

        if (vel.x < 0) {
            vel.x = 0;
        }
        if (velCount >= velCountMax) {
//            if (vel.x < maxVel.x) {
//                vel.x += 1;
//            }
            velCount = 0;
            addToX(1);
        }
        velCount++;
    }

    public void moveLeft() {

        velCount = 0;
        if (vel.x > 0) {
            vel.x = 0;
        }
        if (vel.x > minVel.x) {
            vel.x--;
        }
        addToX(vel.x);
    }

    public void moveUp() {

        addToY(displacement.y);
    }

    public void moveDown() {

        addToY(-displacement.y);
    }

    public void render(SpriteBatch batch) {
        sprite.setAlpha(0);
        batch.draw(sprite, x, y);
    }

    private void addToX(float x) {
        setPosition(getX() + x, getY());
    }

    private void addToY(float y) {
        setPosition(getX(), getY() + y);
    }

    private float getXOnScreen(Vector3 camPos) {

        if (x < camPos.x) {
            return (GdxDemo.GAME_WIDTH / 2f - (camPos.x - x));
        } else if (x > camPos.x) {
            return ((GdxDemo.GAME_WIDTH / 2f) + (x - camPos.x));
        } else return GdxDemo.GAME_WIDTH / 2f;
    }

    private float getYOnScreen(Vector3 camPos) {

        if (y < camPos.y) {
            return (GdxDemo.GAME_HEIGHT / 2f - (camPos.y - y));
        } else if (y > camPos.y) {
            return ((GdxDemo.GAME_HEIGHT / 2f) + (y - camPos.y));
        } else return GdxDemo.GAME_HEIGHT / 2f;
    }

    public void update() {
    }
}
