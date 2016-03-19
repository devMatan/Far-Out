package com.sagi.dayan.Games.Stage;

/**
 * Created by sagi on 2/8/16.
 */

import com.sagi.dayan.Games.Elements.*;
import com.sagi.dayan.Games.Engine.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

public abstract class Scene extends MouseAdapter implements KeyListener{

    public int getStageWidth() {
        return stageWidth;
    }

    public int getStageHeight() {
        return stageHeight;
    }

    protected int stageWidth, stageHeight;
    protected BufferedImage sceneImage;
    protected GameEngine engine;

    public Scene (int stageWidth, int stageHeight, GameEngine engine) {
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
        this.engine = engine;
    }

    public abstract void update ();

    public abstract void render(JPanel p);

    public BufferedImage getSceneImage () {
        return sceneImage;
    }



}
