package com.ks1c.gdxdemo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.ks1c.gdxdemo.Player;
import com.ks1c.gdxdemo.GdxDemo;
import com.ks1c.gdxdemo.World;

public class GameScreen extends GenericScreen {

    private final World world;
    private final Player player;
    private final Vector2 oldCamPos;


    public GameScreen(GdxDemo game) {
        super(game);

        player = new Player();
        world = new World(player);
        oldCamPos = new Vector2();
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
        setCameraAndPlayerPosition();
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            oldCamPos.y = cam.position.y;
            cam.translate(0, player.moveUp(cam.position, world.getHeight()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            oldCamPos.y = cam.position.y;
            cam.translate(0, player.moveDown(cam.position, world.getHeight()));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            oldCamPos.x = cam.position.x;
            cam.translate(player.moveLeft(cam.position, world.getWidth()), 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            oldCamPos.x = cam.position.x;
            cam.translate(player.moveRight(cam.position, world.getWidth()), 0);
        }
        world.update();
        cam.update();
    }

    @Override
    public void renderSprites() {
        player.render(batch);
    }

    @Override
    public void renderBackGroungTiles() {
        world.render(cam);
    }

    private void setCameraAndPlayerPosition() {

        Vector2 camPos = new Vector2();

        Vector2 origin = world.getEntity(game.saveGame.getWaypoint()).getRectangle().getCenter(new Vector2());

        //ZERA CAMERA
        camPos.add(-GdxDemo.GAME_WIDTH / 2f, -GdxDemo.GAME_HEIGHT / 2f);

        if (origin.x < GdxDemo.GAME_WIDTH / 2f) {
            camPos.add(GdxDemo.GAME_WIDTH / 2f, 0);

        } else if (origin.x > world.getWidth() - GdxDemo.GAME_WIDTH / 2f) {
            camPos.add(world.getWidth() - GdxDemo.GAME_WIDTH / 2f, 0);

        } else {
            camPos.add(origin.x, 0);
        }

        if (origin.y > world.getHeight() - GdxDemo.GAME_HEIGHT / 2f) {
            camPos.add(0, world.getHeight() - GdxDemo.GAME_HEIGHT / 2f);

        } else if (origin.y < GdxDemo.GAME_HEIGHT / 2f) {
            camPos.add(0, GdxDemo.GAME_HEIGHT / 2f);

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
        shapeRenderer.rect(updateX(0), updateY(Player.DEAD_ZONE.y), GdxDemo.GAME_WIDTH, Player.DEAD_ZONE.height);
        shapeRenderer.rect(updateX(Player.DEAD_ZONE.x), updateY(0), Player.DEAD_ZONE.width, GdxDemo.GAME_HEIGHT);

        //BOUNDING BOX
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(player.x, player.y, Player.WIDTH, Player.HEIGHT);
    }
}
