package com.sagi.dayan.Games.Engine;

/**
 * Created by sagi on 2/8/16.
 */

import com.sagi.dayan.Games.Elements.*;
import com.sagi.dayan.Games.Stage.*;
import com.sagi.dayan.Games.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/**
 * Created by sagi on 12/18/15.
 */



public class GameEngine {
    public boolean gameOn , gameOver, isFirstGame;
    private JFrame frame;
    private int pWidth, pHeight, numOfPlayers;	//panel dimensions
    private Random r;
    private Stage stage;
    private Vector<Scene> scenes;
    private int currentScene;
    public static final int PLAYER_WIDTH = 120, PLAYER_HEIGHT = 120;
    public static final int UP=0,RIGHT=1,DOWN=2, LEFT=3, FIRE=4, SPECIAL=5;

    private int[] p1Controlles = {KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_K};
    private int[] p2Controlles = {KeyEvent.VK_W, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_SHIFT};

    private Font gameFont;

    public GameEngine(int width, int height, Stage stage){
        this.currentScene = 0;
        this.isFirstGame = true;
        this.gameOver = true;
        this.pWidth = width;
        this.pHeight = height;
        this.scenes = new Vector<>();
        this.stage = stage;
//        scenes.add(new FirstStage(width, height, 2)); // Need to be a menu Scene
        scenes.add(new MainMenuScene(width, height, this));
        stage.addKeyListener(scenes.get(currentScene));
        stage.addMouseListener(scenes.get(currentScene));
        r = new Random();
        try{
            gameFont = Font.createFont(Font.TRUETYPE_FONT,Utils.getFontStream("transformers.ttf"));
        } catch (FontFormatException e) {
            e.printStackTrace();
            gameFont = null;
        } catch (IOException e) {
            e.printStackTrace();
            gameFont = null;
        }
        startNewGame();
    }



    /**
     * initialize and reset vars and timers to "new game" configuration.
     */
    private void startNewGame(){
        this.gameOn = true;
        initGame();
    }

    /**
     * Setup all actors in the game to a new game - reset timer
     */
    private void initGame(){



    }





    /**
     * returns gameOver flag
     * @return
     * boolean
     */
    public boolean isGameOver(){
        return this.gameOver;
    }





    /**
     * Update all sprites, including collision handling.
     */
    public void update(){
        scenes.get(currentScene).update();
    }

    public void render(JPanel p) {
        scenes.get(currentScene).render(p);
    }

    public BufferedImage getScene() {
        return scenes.get(currentScene).getSceneImage();
    }

    private void changeScene(int index) {
        if (index >= scenes.size()){
            throw new IllegalArgumentException("Invalid Index. scenes size: "+scenes.size());
        }
        stage.removeKeyListener(scenes.get(currentScene));
        stage.removeMouseListener(scenes.get(currentScene));
        currentScene = index;
        stage.addKeyListener(scenes.get(currentScene));
        stage.addMouseListener(scenes.get(currentScene));
    }

    public void startGame(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        scenes.add(new FirstStage(pWidth, pHeight, numOfPlayers, this, "-= STAGE 1.0 =-"));
        changeScene(currentScene+1);
    }


    public void goToSettings() {
        scenes.add(new SettingsMenuScene(pWidth, pHeight, this));
        changeScene(currentScene+1);
    }

    public void goToMainMenu() {

        changeScene(0);
        for(int i = scenes.size() -1  ; i > 0 ; i--){
            scenes.remove(i);
        }
    }

    public int getScenesSize(){
        return scenes.size();
    }

    public int[] getP1Controlles(){
        return p1Controlles;
    }

    public int[] getP2Controlles(){
        return p2Controlles;
    }

    public Font getGameFont() {
        return gameFont;
    }
}