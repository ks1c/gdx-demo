package com.ks1c.gdxdemo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Bresenham2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.ks1c.gdxdemo.DMsg;
import com.ks1c.gdxdemo.Player;
import com.ks1c.gdxdemo.GdxDemo;
import com.ks1c.gdxdemo.World;

public class GameScreen extends GenericScreen {

    private final World world;
    private final Player player;
    private final Vector2 camPosMin, camPosMax;

    public GameScreen(GdxDemo game) {
        super(game);
        player = new Player();
        world = new World(player);
        camPosMin = new Vector2();
        camPosMax = new Vector2();
    }

    @Override
    public void show() {
        enableDebugMode();
        if (game.saveGame.exists()) {
            game.saveGame.loadFile();
            world.loadTiledMap(game.saveGame.getMapName());
        } else {
            world.loadTiledMap(game.saveGame.getMapName());
            game.saveGame.setWaypoint(world.getOrigin());
            game.saveGame.saveFile();
        }
        setPlayerPosition();
        world.initLighting();
    }

    @Override
    public void update() {

        //VERTICAL
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.jump();
        } else player.endJump();


        //HORIZONTAL
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }

        //RESET
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            reset();
        }

        world.update();
        player.update();
        world.stepLighting();
        updateCamPosition();
    }

    @Override
    public void renderBackGroundTiles() {
        world.render(cam);
    }

    @Override
    public void renderSprites() {
        player.render(spriteBatch);
    }

    @Override
    public void renderForeGroundTiles() {
        //world.render(cam);
        world.renderLighting(cam);
    }

    private void reset() {
        setPlayerPosition();
    }

    private void updateCamPosition() {

        camPosMin.set(
                GdxDemo.GAME_WIDTH / 2f,
                GdxDemo.GAME_HEIGHT / 2f
        );
        camPosMax.set(
                world.getWidth() - GdxDemo.GAME_WIDTH / 2f,
                world.getHeight() - GdxDemo.GAME_HEIGHT / 2f
        );

        cam.position.x = player.getX() + player.getWidth() / 2f;
        cam.position.y = player.getY() + player.getHeight() / 2f;

        if (cam.position.x > camPosMax.x) {
            cam.position.x = camPosMax.x;
        }

        if (cam.position.x < camPosMin.x) {
            cam.position.x = camPosMin.x;
        }

        if (cam.position.y > camPosMax.y) {
            cam.position.y = camPosMax.y;
        }

        if (cam.position.x < camPosMin.x) {
            cam.position.x = camPosMin.x;
        }

        cam.update();
    }

    private void setPlayerPosition() {

        Vector2 origin = world.getEntity(game.saveGame.getWaypoint()).getRectangle().getCenter(new Vector2());

        player.setCenter(origin);
        player.oldPos.x = player.x;
        player.oldPos.y = player.y;
        cam.position.x = player.x + player.width / 2f;
        cam.position.y = player.y + player.height / 2f;
    }
}
