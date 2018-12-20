package com.ks1c.gdxdemo;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;


public class World {

    private SaveGame saveGame;
    public TiledMap tiledMap;
    private final OrthogonalTiledMapRenderer levelRenderer;
    private MapObjects entities;

    public World(Player player, SaveGame saveGame) {

        this.saveGame = saveGame;
        tiledMap = null;
        levelRenderer = new OrthogonalTiledMapRenderer(null);
    }

    public void init() {

        loadTiledMap(saveGame.world.get("tiledMap"));
    }

    private void loadTiledMap(String mapName) {

        tiledMap = new TmxMapLoader().load(mapName);
        levelRenderer.setMap(tiledMap);
        entities = tiledMap.getLayers().get("entities").getObjects();
    }

    public void render(OrthographicCamera cam) {

        levelRenderer.setView(cam);
        levelRenderer.render();
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
