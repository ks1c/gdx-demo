package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class World {

    private TiledMap tiledMap;
    static final float G = 10;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects entities;
    private float width, height;
    private final Player player;
    private LightRenderer lightRenderer;
    private final Sprite bg;

    public World(Player player) {

        tiledMap = null;
        mapRenderer = new OrthogonalTiledMapRenderer(null);
        this.player = player;
        lightRenderer = new LightRenderer();
        bg = new Sprite(new Texture("bg.png"));
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

        player.fall(G, camPos, getHeight());

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
                            if (camPos.y < oldCamPos.y) {
                                camPos.y += 1;
                            }
                            player.setInTheAir(false);
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
                            if (camPos.y > oldCamPos.y) {
                                camPos.y -= 1;
                            }
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
                            if (camPos.x > oldCamPos.x) {
                                camPos.x -= 1;
                            }
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
                            if (camPos.x < oldCamPos.x) {
                                camPos.x += 1;
                            }
                        }
                    }
                }
                playerSteps.x -= 1;
            }

            if (playerSteps.x == 0 && playerSteps.y == 0) {
                player.x = tmpPlayer.x;
                player.y = tmpPlayer.y;
                break;
            }
        }

        player.oldPos.x = player.x;
        player.oldPos.y = player.y;
        oldCamPos.x = camPos.x;
        oldCamPos.y = camPos.y;

        for (MapObject entity : entities) {
            RectangleMapObject e = (RectangleMapObject) entity;
            if (e.getProperties().get("type").toString().equals("light") &&
                    player.getLightZone().overlaps(e.getRectangle())) {
                //lightRenderer.addLights(new Light(e.getRectangle().getCenter(new Vector2()), 500f));
            }
        }
    }

    public void render(OrthographicCamera cam, SpriteBatch batch) {
        batch.begin();
        batch.draw(bg, cam.position.x - bg.getWidth() / 2f, cam.position.y - bg.getHeight() / 2f);
        batch.end();
        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    public void renderLights(ShapeRenderer shapeRenderer, Vector3 camPos) {
        lightRenderer.addLights(new Light(player.getCenter(new Vector2()), 400f));
        lightRenderer.render(shapeRenderer, camPos);
    }
}
