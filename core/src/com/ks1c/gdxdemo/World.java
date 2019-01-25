package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
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

    public RectangleMapObject getEntity(int id) {

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

    public void update() {

        Vector2 playerStep = new Vector2(
                Math.abs(0),
                Math.abs(0)
        );
        Vector2 camPosStep = new Vector2();
        Vector2 tmpPlayer = new Vector2();
        Vector2 tmpCamPos = new Vector2();

        while (true) {
            break;
        }
    }

    public void render(OrthographicCamera cam) {

        mapRenderer.setView(cam);
        mapRenderer.render();
    }
}
