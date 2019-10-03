package com.ks1c.gdxdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;
import java.io.StringWriter;

public class GameState {

    private XmlReader.Element root, world;

    public GameState() {

        StringWriter writer = new StringWriter();
        XmlWriter xmlWriter = new XmlWriter(writer);

        try {

            xmlWriter.element("root");

            xmlWriter.element("world");

            xmlWriter.element("tiledMap", "start.tmx");

            xmlWriter.element("waypoint", "default");

            xmlWriter.pop();

            xmlWriter.pop();

            xmlWriter.close();

            XmlReader xmlReader = new XmlReader();
            root = xmlReader.parse(writer.toString());
            world = root.getChildByName("world");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {

        FileHandle saveFile;
        saveFile = Gdx.files.local("save.xml");
        XmlReader xmlReader = new XmlReader();
        root = xmlReader.parse(saveFile);
        world = root.getChildByName("world");
    }

    public void saveFile() {
        FileHandle saveFile = Gdx.files.local("save.xml");
        saveFile.writeString(root.toString(), false);
    }

    public boolean exists() {

        return Gdx.files.local("save.xml").exists();
    }

    public String getMapName() {

        return world.getChildByName("tiledMap").getText();
    }

    public void setWaypoint(String waypoint) {
        world.getChildByName("waypoint").setText(waypoint);
    }

    public String getWaypoint() {

        return world.getChildByName("waypoint").getText();
    }
}
