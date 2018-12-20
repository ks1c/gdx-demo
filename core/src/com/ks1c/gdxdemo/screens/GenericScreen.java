package com.ks1c.gdxdemo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ks1c.gdxdemo.DMsg;
import com.ks1c.gdxdemo.GdxDemo;

public class GenericScreen extends ScreenAdapter {

    protected final SpriteBatch batch;
    protected final ShapeRenderer shapeRenderer;
    protected final OrthographicCamera cam;
    private final BitmapFont font;
    private boolean debugMode = false;

    public GenericScreen() {

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, GdxDemo.GAME_WIDTH, GdxDemo.GAME_HEIGHT);
        cam.update();

        font = new BitmapFont();
    }

    public void update() {
    }

    public void renderSprites() {
    }

    public void renderShapes() {
    }

    public void renderBackGroungTiles() {
    }

    public void renderForeGroungTiles() {
    }

    @Override
    public void render(float delta) {

        update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
        cam.update();

        renderBackGroungTiles();

        batch.begin();

        renderSprites();

        if (isDebugModeEnabled()) {
            font.draw(
                    batch,
                    Gdx.graphics.getFramesPerSecond() + "\n" + DMsg.show(),
                    cam.position.x - GdxDemo.GAME_WIDTH / 2,
                    cam.position.y + GdxDemo.GAME_HEIGHT / 2
            );
        }

        batch.end();

        renderForeGroungTiles();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        renderShapes();
        shapeRenderer.end();
    }

    void enableDebugMode() {
        debugMode = true;
    }

    void disableDebugMode() {
        debugMode = false;
    }

    boolean isDebugModeEnabled() {
        return debugMode;
    }
}
