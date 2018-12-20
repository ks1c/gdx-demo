package com.ks1c.gdxdemo;

import com.badlogic.gdx.Game;
import com.ks1c.gdxdemo.screens.GenericScreen;

public class GdxDemo extends Game {

    public static final int GAME_HEIGHT = 540;
    public static final int GAME_WIDTH = 960;

    @Override
    public void create() {

        setScreen(new GenericScreen());
    }
}
