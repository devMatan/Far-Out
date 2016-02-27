package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Utils.Utils;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by sagi on 2/27/16.
 */
public class SettingsMenuScene extends Scene {
    private BufferedImage background;


    public SettingsMenuScene(int stageWidth, int stageHeight, GameEngine engine) {
        super(stageWidth, stageHeight, engine);
        try {
            background = ImageIO.read(Utils.getImageResourceAsURL("Settigns_menu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void render(JPanel p) {
        sceneImage = new BufferedImage(this.stageWidth, this.stageHeight, Image.SCALE_FAST);
        Graphics g = sceneImage.getGraphics();
        g.drawImage(background, 0,0,p);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_ESCAPE:
                Utils.playSound("menuItem.wav");
                engine.goToMainMenu();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
