package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class World {

    private TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects entities;
    private float width, height;
    private final Player player;

    public World(Player player) {

        tiledMap = null;
        mapRenderer = new OrthogonalTiledMapRenderer(null);
        this.player = player;
    }

    public void loadTiledMap(String mapName) {

        tiledMap = new TmxMapLoader().load(mapName);
        mapRenderer.setMap(tiledMap);
        entities = tiledMap.getLayers().get("entities").getObjects();

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        width = layer.getWidth() * layer.getTileWidth();
        height = layer.getHeight() * layer.getTileHeight();
    }

    public RectangleMapObject getEntity(String name) {

        return (RectangleMapObject) entities.get(name);
    }

    public RectangleMapObject getEntity(int index) {

        return (RectangleMapObject) entities.get(index);
    }

    public RectangleMapObject getEntityById(int id) {

        MapObject e = new MapObject();

        for (MapObject entity : entities) {
            if (entity.getProperties().get("id").equals(id)) {
                e = entity;
            }
        }
        return (RectangleMapObject) e;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getOrigin() {

        return tiledMap.getProperties().get("origin").toString();
    }

    public void update(Vector3 camPos, Vector2 oldCamPos) {

        Vector2 playerSteps = new Vector2(
                Math.abs(player.x - player.oldPos.x),
                Math.abs(player.y - player.oldPos.y)
        );

        Rectangle tmpPlayer = new Rectangle(
                player.oldPos.x,
                player.oldPos.y,
                player.width,
                player.height
        );

        Vector2 camPosSteps = new Vector2(
                Math.abs(camPos.x - oldCamPos.x),
                Math.abs(camPos.y - oldCamPos.y)
        );

        Vector2 tmpCamPos = new Vector2(oldCamPos.x, oldCamPos.y);

        while (true) {

            if (playerSteps.y > 0) {

                //BAIXO
                if (tmpPlayer.y > player.y) {
                    tmpPlayer.y -= 1;
                    for (MapObject entity : entities) {
                        RectangleMapObject e = (RectangleMapObject) entity;
                        if (e.getProperties().get("type").toString().equals("collision") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.y += 1;
                        }
                    }
                }

                //CIMA
                if (tmpPlayer.y < player.y) {
                    tmpPlayer.y += 1;
                    for (MapObject entity : entities) {
                        RectangleMapObject e = (RectangleMapObject) entity;
                        if (e.getProperties().get("type").toString().equals("collision") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.y -= 1;
                        }
                    }
                }
                playerSteps.y -= 1;
            }


            if (playerSteps.x > 0) {

                //DIREITA
                if (tmpPlayer.x < player.x) {
                    tmpPlayer.x += 1;
                    for (MapObject entity : entities) {
                        RectangleMapObject e = (RectangleMapObject) entity;
                        if (e.getProperties().get("type").toString().equals("collision") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.x -= 1;
                        }
                    }
                }

                //ESQUERDA
                if (tmpPlayer.x > player.x) {
                    tmpPlayer.x -= 1;
                    for (MapObject entity : entities) {
                        RectangleMapObject e = (RectangleMapObject) entity;
                        if (e.getProperties().get("type").toString().equals("collision") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.x += 1;
                        }
                    }
                }
                playerSteps.x -= 1;
            }

            if (camPosSteps.y > 0) {
                //todo
            }

            if (camPosSteps.x > 0) {
                //todo
            }

            if (playerSteps.x == 0 && playerSteps.y == 0) {
                player.x = tmpPlayer.x;
                player.y = tmpPlayer.y;
                //camPos.x = tmpCamPos.x;
                //camPos.y = tmpCamPos.y;
                break;
            }
        }

        player.oldPos.x = player.x;
        player.oldPos.y = player.y;
        //oldCamPos.x = camPos.x;
        //oldCamPos.y = camPos.y;
    }

    public void render(OrthographicCamera cam) {

        mapRenderer.setView(cam);
        mapRenderer.render();
    }
}
