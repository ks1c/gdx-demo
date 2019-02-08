package com.ks1c.gdxdemo;

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

    public void moveRight() {

        addToX(displacement.x);
    }

    public void moveLeft() {

        addToX(-displacement.x);
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
        bodyOfLights.setTransform(
                (x + width / 2f) / World.PIXELS_TO_METERS,
                (y + height / 2f) / World.PIXELS_TO_METERS,
                bodyOfLights.getAngle()
        );
    }

    public void setBodyOfLights(com.badlogic.gdx.physics.box2d.World worldOfLights) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                (x + width / 2f) / World.PIXELS_TO_METERS,
                (y + height / 2f) / World.PIXELS_TO_METERS
        );
        bodyOfLights = worldOfLights.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                (width / 2f) / World.PIXELS_TO_METERS,
                (height / 2f) / World.PIXELS_TO_METERS
        );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.isSensor = true;
        bodyOfLights.setFixedRotation(true);
        bodyOfLights.createFixture(fixtureDef);
        shape.dispose();
    }
}
