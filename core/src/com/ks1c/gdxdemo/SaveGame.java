package com.ks1c.gdxdemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;

import java.io.IOException;
import java.io.StringWriter;

public class SaveGame {

    XmlReader.Element root, world, player;

    public SaveGame() {

        if (Gdx.files.local("save.xml").exists()) {
            loadFile();
        } else {
            saveFile();
        }
    }

    private void loadFile() {

        FileHandle saveFile;
        saveFile = Gdx.files.local("save.xml");
        XmlReader xmlReader = new XmlReader();
        root = xmlReader.parse(saveFile);
        world = root.getChildByName("world");
        player = root.getChildByName("player");
    }

    private void saveFile() {

        FileHandle saveFile = Gdx.files.local("save.xml");
        StringWriter writer = new StringWriter();
        XmlWriter xmlWriter = new XmlWriter(writer);

        try {

            //BEGIN SAVE
            xmlWriter.element("root");

            //BEGIN WORLD
            xmlWriter.element("world");

            xmlWriter.element("tiledMap", "start.tmx");

            //END WORLD
            xmlWriter.pop();

            //BEGIN PLAYER
            xmlWriter.element("player");

            xmlWriter.element("waypoint", "start");

            //END PLAYER
            xmlWriter.pop();

            //END SAVE
            xmlWriter.pop();

            xmlWriter.close();

            saveFile.writeString(writer.toString(), false);
            xmlWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        loadFile();
    }
}
