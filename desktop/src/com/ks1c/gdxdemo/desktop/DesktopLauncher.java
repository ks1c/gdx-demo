package com.ks1c.gdxdemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ks1c.gdxdemo.GdxDemo;

public class DesktopLauncher {
    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "gdx-demo";
        config.width = GdxDemo.GAME_WIDTH;
        config.height = GdxDemo.GAME_HEIGHT;

        new LwjglApplication(new GdxDemo(), config);
    }
}
