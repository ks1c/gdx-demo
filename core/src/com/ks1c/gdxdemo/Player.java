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

    public Player() {
        super(0, 0, WIDTH, HEIGHT);
        sprite = new Sprite(new Texture("player.png"));
        displacement = new Vector2(10f, 10f);
        oldPos = new Vector2();
    }

    public void init() {
    }

    public void fall(float G, Vector3 camPos, float worldHeight) {

        setInTheAir(true);

        if (G - jumpStep > 0) {
            displacement.y = G - jumpStep;
            moveDown(camPos, worldHeight);
        } else {
            displacement.y = jumpStep - G;
            moveUp(camPos, worldHeight);
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

    public void moveRight(Vector3 camPos, float worldWidth) {

        float camPosXMax = worldWidth - GdxDemo.GAME_WIDTH / 2f;

        if (camPos.x < camPosXMax) {
            if (getXOnScreen(camPos) < DEAD_ZONE.x) {
                addToX(displacement.x);
                if (getXOnScreen(camPos) > DEAD_ZONE.x) {
                    x = DEAD_ZONE.x;
                }
            } else {
                if (camPos.x + displacement.x < camPosXMax) {
                    addToX(displacement.x);
                    camPos.add(displacement.x, 0, 0);
                } else {
                    addToX(camPosXMax - camPos.x);
                    camPos.add(camPosXMax - camPos.x, 0, 0);
                }
            }
        } else {
            addToX(displacement.x);
            if (x + width > worldWidth) {
                x = worldWidth - width;
            }
        }
    }

    public void moveLeft(Vector3 camPos, float worldWidth) {

        float camPosXMin = GdxDemo.GAME_WIDTH / 2f;

        if (camPos.x > camPosXMin) {
            if (getXOnScreen(camPos) > DEAD_ZONE.x) {
                addToX(-displacement.x);
                if (getXOnScreen(camPos) < DEAD_ZONE.x) {
                    x = worldWidth - (DEAD_ZONE.x + DEAD_ZONE.width);
                }
            } else {
                if (camPos.x - displacement.x > camPosXMin) {
                    addToX(-displacement.x);
                    camPos.add(-displacement.x, 0, 0);
                } else {
                    addToX(-(camPos.x - camPosXMin));
                    camPos.add(-(camPos.x - camPosXMin), 0, 0);
                }
            }
        } else {
            addToX(-displacement.x);
            if (x < 0) {
                x = 0;
            }
        }
    }

    public void moveUp(Vector3 camPos, float worldHeight) {

        float camPosYMax = worldHeight - GdxDemo.GAME_HEIGHT / 2f;

        if (camPos.y < camPosYMax) {
            if (getYOnScreen(camPos) < DEAD_ZONE.y) {
                addToY(displacement.y);
                if (getYOnScreen(camPos) > DEAD_ZONE.y) {
                    y = DEAD_ZONE.y;
                }
            } else {
                if (camPos.y + displacement.y < camPosYMax) {
                    addToY(displacement.y);
                    camPos.add(0, displacement.y, 0);
                } else {
                    addToY(camPosYMax - camPos.y);
                    camPos.add(0, camPosYMax - camPos.y, 0);
                }
            }
        } else {
            addToY(displacement.y);
            if (y + height > worldHeight) {
                y = worldHeight - height;
            }
        }
    }

    public void moveDown(Vector3 camPos, float worldHeight) {

        float camPosYMin = GdxDemo.GAME_HEIGHT / 2f;

        if (camPos.y > camPosYMin) {
            if (getYOnScreen(camPos) > DEAD_ZONE.y) {
                addToY(-displacement.y);
                if (getYOnScreen(camPos) < DEAD_ZONE.y) {
                    y = worldHeight - (DEAD_ZONE.y + DEAD_ZONE.height);
                }
            } else {
                if (camPos.y - displacement.y > camPosYMin) {
                    addToY(-displacement.y);
                    camPos.add(0, -displacement.y, 0);
                } else {
                    addToY(-(camPos.y - camPosYMin));
                    camPos.add(0, -(camPos.y - camPosYMin), 0);
                }
            }
        } else {
            addToY(-displacement.y);
            if (y < 0) {
                y = 0;
            }
        }
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
}
