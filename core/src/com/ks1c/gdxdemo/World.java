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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class World {

    private TiledMap tiledMap;
    static final float G = 10;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private MapObjects entities;
    private float width, height;
    private final Player player;

    //BOX2D AND BOX2DLIGHTS
    public static final float PIXELS_TO_METERS = 100f;
    private com.badlogic.gdx.physics.box2d.World worldOfLights;
    private RayHandler rayHandler;

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
                        if (e.getProperties().get("type").toString().equals("staticBody") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.y += 1;
                            player.setInTheAir(false);
                        }
                    }
                }

                //CIMA
                if (tmpPlayer.y < player.y) {
                    tmpPlayer.y += 1;
                    for (MapObject entity : entities) {
                        RectangleMapObject e = (RectangleMapObject) entity;
                        if (e.getProperties().get("type").toString().equals("staticBody") &&
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
                        if (e.getProperties().get("type").toString().equals("staticBody") &&
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
                        if (e.getProperties().get("type").toString().equals("staticBody") &&
                                tmpPlayer.overlaps(e.getRectangle())) {
                            tmpPlayer.x += 1;
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
    }

    public void render(OrthographicCamera cam) {
        mapRenderer.setView(cam);
        mapRenderer.render();
    }

    public void initLighting() {
        worldOfLights = new com.badlogic.gdx.physics.box2d.World(
                new Vector2(0, 0),
                true
        );
        player.setBodyOfLights(worldOfLights);
        rayHandler = new RayHandler(worldOfLights);
        rayHandler.setAmbientLight(0, 0, 0, 0);
        rayHandler.setBlurNum(3);
        rayHandler.setShadows(true);

        for (MapObject entity : entities) {
            if (entity.getProperties().get("type").toString().equals("staticBody")) {
                generateStaticBody((RectangleMapObject) entity);
            }
            if (entity.getProperties().get("type").toString().equals("staticLight")) {
                generateStaticLight((RectangleMapObject) entity);
            }
        }
    }

    private void generateStaticBody(RectangleMapObject rectangleMapObject) {
        Rectangle rect = rectangleMapObject.getRectangle();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(
                (rect.x + rect.width / 2f) / PIXELS_TO_METERS,
                (rect.y + rect.height / 2f) / PIXELS_TO_METERS
        );
        Body body = worldOfLights.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
                (rect.width / 2f - 0.5f) / PIXELS_TO_METERS,
                (rect.height / 2f - 0.5f) / PIXELS_TO_METERS
        );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void generateStaticLight(RectangleMapObject rectangleMapObject) {

        PointLight pointLight = new PointLight(rayHandler,
                100,
                rectangleMapObject.getProperties().get("Color", Color.class),
                500f / PIXELS_TO_METERS,
                rectangleMapObject.getRectangle().x / PIXELS_TO_METERS,
                rectangleMapObject.getRectangle().y / PIXELS_TO_METERS
        );
        pointLight.setSoft(true);
        pointLight.setSoftnessLength(1f);
    }

    public void stepLighting() {
        worldOfLights.step(1 / 60f, 6, 2);
    }

    public void renderLighting(OrthographicCamera cam) {
        Matrix4 debugMatrix = cam.combined;
        debugMatrix.scl(PIXELS_TO_METERS);
        rayHandler.setCombinedMatrix(
                debugMatrix,
                cam.position.x / PIXELS_TO_METERS,
                cam.position.y / PIXELS_TO_METERS,
                cam.viewportWidth * cam.zoom,
                cam.viewportHeight * cam.zoom
        );
        rayHandler.updateAndRender();
    }
}
