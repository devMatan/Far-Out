package com.sagi.dayan.Games.Engine;

/**
 * Created by sagi on 2/8/16.
 */

import com.sagi.dayan.Games.Stage.*;
import com.sagi.dayan.Games.Utils.Utils;
import com.sagi.dayan.Games.Utils.WaveConfigs;

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

    private int p1Lives, p2Lives, p1Health, p2Health, credits, p1Score, p2Score;



    private Font gameFont;

    private WaveConfigs waveConfigs;

    public GameEngine(int width, int height, Stage stage){
        this.currentScene = 0;
        this.isFirstGame = true;
        this.gameOver = true;
        this.pWidth = width;
        this.pHeight = height;
        this.scenes = new Vector<>();
        this.stage = stage;
//        scenes.add(new Level(width, height, 2)); // Need to be a menu Scene
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
        this.waveConfigs = new WaveConfigs();
        startNewGame();
        resetPlayerHealth(0);
        resetPlayerHealth(1);
        credits = 3;
    }


    private void resetPlayerHealth(int i){
        if (i==0){
            p1Health = 100;
        }
        else{
            p2Health = 100;
        }
    }
    private void resetPlayer(int i){
        resetPlayerHealth(i);

        if (i==0){
            p1Lives = 3;
        }
        else{
            p2Lives = 3;
        }
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

    public WaveConfigs getWaveConfigs() {
        return waveConfigs;
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
        scenes.add(new FirstStage(pWidth, pHeight, numOfPlayers, this, "-= STAGE 1.0 =-", new int[]{5, 20}));
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

    public int getP1Lives() {
        return p1Lives;
    }
    public int getP2Lives() {
        return p2Lives;
    }

    public int getP1Health() {
        return p1Health;
    }

    public int getP2Health() {
        return p2Health;
    }

    public int getP1Score() {
        return p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public int getCredits() {return credits;}


    public void useCredit(){
        credits--;
    }

    public void setScore(int i, int score)
    {
        if (i == 0) {
            p1Score += score;
        } else {
            p2Score += score;
        }
    }
    public void setPlayerHealth(int i, int strike) {
        if (i == 0) {
            p1Health += strike;
        } else {
            p2Health += strike;
        }
    }

}