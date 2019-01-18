package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;


public class World {

    public TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects entities;

    public World(Player player) {

        tiledMap = null;
        mapRenderer = new OrthogonalTiledMapRenderer(null);
    }

    public void init(String mapName) {

        loadTiledMap(mapName);
    }

    public void loadTiledMap(String mapName) {

        tiledMap = new TmxMapLoader().load(mapName);
        mapRenderer.setMap(tiledMap);
        entities = tiledMap.getLayers().get("entities").getObjects();
    }

    public MapObject getEntity(String name) {

        return entities.get(name);
    }

    public void setWaypoint(String waypoint) {
        //tiledMap.getProperties().
    }

    public String getWaypoint() {

        return tiledMap.getProperties().get("waypoint").toString();
    }

    public void render(OrthographicCamera cam) {

        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    public Vector2 getOrigin() {

        RectangleMapObject origin = (RectangleMapObject) entities.get(tiledMap.getProperties().get("waypoint").toString());

        return origin.getRectangle().getCenter(new Vector2());
    }

    public float getWidth() {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        return layer.getWidth() * layer.getTileWidth();
    }

    public float getHeight() {

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);

        return layer.getHeight() * layer.getTileHeight();
    }
}
