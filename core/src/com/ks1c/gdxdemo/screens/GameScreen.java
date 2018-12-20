package com.ks1c.gdxdemo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.ks1c.gdxdemo.Player;
import com.ks1c.gdxdemo.GdxDemo;
import com.ks1c.gdxdemo.SaveGame;
import com.ks1c.gdxdemo.World;

public class GameScreen extends GenericScreen {

    private final World world;
    private final Player player;


    public GameScreen() {

        player = new Player();
        world = new World(player, new SaveGame());
    }

    @Override
    public void show() {

        enableDebugMode();
        world.init();
        setCameraAndPlayerPosition();
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, 5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -5);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(5, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(-5, 0);
        }
        cam.update();
    }

    @Override
    public void renderSprites() {
        player.render(batch);
    }

    @Override
    public void renderBackGroungTiles() {
        if (world.tiledMap != null) world.render(cam);
    }

    private void setCameraAndPlayerPosition() {

        Vector2 origin = world.getOrigin();

        Vector2 camPos = new Vector2();

        //ZERA CAMERA
        camPos.add(-GdxDemo.GAME_WIDTH / 2, -GdxDemo.GAME_HEIGHT / 2);

        if (origin.x < GdxDemo.GAME_WIDTH / 2) {
            camPos.add(GdxDemo.GAME_WIDTH / 2, 0);

        } else if (origin.x > world.getWidth() - GdxDemo.GAME_WIDTH / 2) {
            camPos.add(world.getWidth() - GdxDemo.GAME_WIDTH / 2, 0);

        } else {
            camPos.add(origin.x, 0);
        }

        if (origin.y > world.getHeight() - GdxDemo.GAME_HEIGHT / 2) {
            camPos.add(0, world.getHeight() - GdxDemo.GAME_HEIGHT / 2);

        } else if (origin.y < GdxDemo.GAME_HEIGHT / 2) {
            camPos.add(0, GdxDemo.GAME_HEIGHT / 2);

        } else {
            camPos.add(0, origin.y);
        }

        cam.translate(camPos);
        player.setCenter(origin);
    }

    @Override
    public void renderShapes() {

        //DEAD_ZONE
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(Player.DEAD_ZONE.x, Player.DEAD_ZONE.y, Player.DEAD_ZONE.width, Player.DEAD_ZONE.height);
        shapeRenderer.rect(0, Player.DEAD_ZONE.y, GdxDemo.GAME_WIDTH, Player.DEAD_ZONE.height);
        shapeRenderer.rect(Player.DEAD_ZONE.x, 0, Player.DEAD_ZONE.width, GdxDemo.GAME_HEIGHT);

        //BOUNDING BOX
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.x, player.y, Player.WIDTH, Player.HEIGHT);
    }
}
