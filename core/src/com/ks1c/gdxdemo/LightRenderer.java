package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class LightRenderer {

    private static final float CELL_SIZE = 16f;
    public static final int MAX_INTERACTIONS = 20;
    private final List<Light> lights;
    private static final int MAX_INDEX_X = (int) Math.ceil(GdxDemo.GAME_WIDTH / CELL_SIZE);
    private static final int MAX_INDEX_Y = (int) Math.ceil(GdxDemo.GAME_HEIGHT / CELL_SIZE);
    private float opacity[][];

    public LightRenderer() {

        lights = new ArrayList<Light>();
    }

    public void addLights(Light light) {
        lights.add(light);
    }

    public void render(ShapeRenderer shapeRenderer, Vector3 camPos) {

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        for (int x = 0; x < MAX_INDEX_X; x++)
            for (int y = 0; y < MAX_INDEX_Y; y++) {

                shapeRenderer.setColor(new Color(0, 0, 0, 1f));

                Circle c = new Circle(
                        (camPos.x - GdxDemo.GAME_WIDTH / 2f) + x * CELL_SIZE + CELL_SIZE / 2f,
                        (camPos.y - GdxDemo.GAME_HEIGHT / 2f) + y * CELL_SIZE + CELL_SIZE / 2f,
                        CELL_SIZE / 2f
                );

                for (Light light : lights) {
                    for (int i = MAX_INTERACTIONS - 1; i >= 0; i--) {
                        if (light.getCircle(i).overlaps(c)) {
                            shapeRenderer.setColor(new Color(0, 0, 0, i / (float) MAX_INTERACTIONS));
                        }
                    }
                }

                shapeRenderer.rect(
                        (camPos.x - GdxDemo.GAME_WIDTH / 2f) + x * CELL_SIZE,
                        (camPos.y - GdxDemo.GAME_HEIGHT / 2f) + y * CELL_SIZE,
                        CELL_SIZE,
                        CELL_SIZE
                );
            }
        shapeRenderer.end();
        lights.clear();
    }
}
