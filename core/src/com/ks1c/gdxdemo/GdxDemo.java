package com.ks1c.gdxdemo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.ScreenAdapter;

public class GdxDemo extends Game {

    public static final int GAME_HEIGHT = 540;
    public static final int GAME_WIDTH = 960;

    @Override
    public void create() {

        setScreen(new ScreenAdapter());
    }
}
