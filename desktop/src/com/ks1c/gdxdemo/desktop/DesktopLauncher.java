package com.ks1c.gdxdemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ks1c.gdxdemo.GdxDemo;

public class DesktopLauncher {
    public static void main(String[] arg) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Prototype";
        config.width = 960;
        config.height = 540;

        new LwjglApplication(new GdxDemo(), config);
    }
}
