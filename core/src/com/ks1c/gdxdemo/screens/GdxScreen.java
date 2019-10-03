package com.ks1c.gdxdemo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ks1c.gdxdemo.GdxDemo;

public class GdxScreen implements Screen {

    protected final SpriteBatch batch;
    protected final OrthographicCamera cam;
    protected final GdxDemo game;

    public GdxScreen(GdxDemo game) {

        this.game = game;
        batch = new SpriteBatch();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, GdxDemo.GAME_WIDTH, GdxDemo.GAME_HEIGHT);
        cam.update();
    }

    public void update() {
    }

    public void renderSprites() {
    }

    public void renderBackGroundTiles() {
    }

    public void renderForeGroundTiles() {
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(cam.combined);
        cam.update();
        renderBackGroundTiles();
        batch.begin();
        renderSprites();
        batch.end();
        renderForeGroundTiles();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public float updateX(float x) {

        return cam.position.x - GdxDemo.GAME_WIDTH / 2f + x;
    }

    public float updateY(float y) {

        return cam.position.y - GdxDemo.GAME_HEIGHT / 2f + y;
    }
}
