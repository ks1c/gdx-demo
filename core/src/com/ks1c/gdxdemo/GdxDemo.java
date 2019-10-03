package com.ks1c.gdxdemo;

import com.badlogic.gdx.Game;
import com.ks1c.gdxdemo.screens.GameScreen;

public class GdxDemo extends Game {

    public static final int GAME_HEIGHT = 540;
    public static final int GAME_WIDTH = 960;

    public final GameState saveGame = new GameState();

    @Override
    public void create() {

        setScreen(new GameScreen(this));
    }
}
