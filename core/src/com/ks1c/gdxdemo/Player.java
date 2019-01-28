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
        displacement = new Vector2(30f, 30f);
        oldPos = new Vector2();
    }

    public void init() {
    }

    public void fall(float G) {

    }

    public float moveRight(Vector3 camPos, float worldWidth) {

        float camPosXMax = worldWidth - GdxDemo.GAME_WIDTH / 2f;

        if (camPos.x < camPosXMax) {
            if (getXOnScreen(camPos) < DEAD_ZONE.x) {
                addToX(displacement.x);
                if (getXOnScreen(camPos) > DEAD_ZONE.x) {
                    x = DEAD_ZONE.x;
                }
                return 0;
            } else {
                if (camPos.x + displacement.x < camPosXMax) {
                    addToX(displacement.x);
                    return displacement.x;
                } else {
                    addToX(camPosXMax - camPos.x);
                    return (camPosXMax - camPos.x);
                }
            }
        } else {
            addToX(displacement.x);
            if (x + width > worldWidth) {
                x = worldWidth - width;
            }
            return 0;
        }
    }

    public float moveLeft(Vector3 camPos, float worldWidth) {

        float camPosXMin = GdxDemo.GAME_WIDTH / 2f;

        if (camPos.x > camPosXMin) {
            if (getXOnScreen(camPos) > DEAD_ZONE.x) {
                addToX(-displacement.x);
                if (getXOnScreen(camPos) < DEAD_ZONE.x) {
                    x = worldWidth - (DEAD_ZONE.x + DEAD_ZONE.width);
                }
                return 0;
            } else {
                if (camPos.x - displacement.x > camPosXMin) {
                    addToX(-displacement.x);
                    return -displacement.x;
                } else {
                    addToX(-(camPos.x - camPosXMin));
                    return (-(camPos.x - camPosXMin));
                }
            }
        } else {
            addToX(-displacement.x);
            if (x < 0) {
                x = 0;
            }
            return 0;
        }
    }

    public float moveUp(Vector3 camPos, float worldHeight) {

        float camPosYMax = worldHeight - GdxDemo.GAME_HEIGHT / 2f;

        if (camPos.y < camPosYMax) {
            if (getYOnScreen(camPos) < DEAD_ZONE.y) {
                addToY(displacement.y);
                if (getYOnScreen(camPos) > DEAD_ZONE.y) {
                    y = DEAD_ZONE.y;
                }
                return 0;
            } else {
                if (camPos.y + displacement.y < camPosYMax) {
                    addToY(displacement.y);
                    return displacement.y;
                } else {
                    addToY(camPosYMax - camPos.y);
                    return (camPosYMax - camPos.y);
                }
            }
        } else {
            addToY(displacement.y);
            if (y + height > worldHeight) {
                y = worldHeight - height;
            }
            return 0;
        }
    }

    public float moveDown(Vector3 camPos, float worldHeight) {

        float camPosYMin = GdxDemo.GAME_HEIGHT / 2f;

        if (camPos.y > camPosYMin) {
            if (getYOnScreen(camPos) > DEAD_ZONE.y) {
                addToY(-displacement.y);
                if (getYOnScreen(camPos) < DEAD_ZONE.y) {
                    y = worldHeight - (DEAD_ZONE.y + DEAD_ZONE.height);
                }
                return 0;
            } else {
                if (camPos.y - displacement.y > camPosYMin) {
                    addToY(-displacement.y);
                    return -displacement.y;
                } else {
                    addToY(-(camPos.y - camPosYMin));
                    return (-(camPos.y - camPosYMin));
                }
            }
        } else {
            addToY(-displacement.y);
            if (y < 0) {
                y = 0;
            }
            return 0;
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
