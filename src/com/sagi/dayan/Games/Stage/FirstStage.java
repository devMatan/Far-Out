package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Elements.Background;
import com.sagi.dayan.Games.Elements.EnemyShip;
import com.sagi.dayan.Games.Elements.Missile;
import com.sagi.dayan.Games.Elements.Player;
import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Utils.Utils;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * Created by sagi on 2/20/16.
 */
public class FirstStage extends Scene {
    protected Vector<Player> players;
    protected int p1Speed = 10;
    protected Vector<Missile> missiles;
    protected Background bg;
    protected Timer enemyWaveT, enemyT;
    protected Vector<EnemyShip> enemies;
    protected int[] yAxisStartingAnimation;
    protected int startingAnimationIndex;
    protected boolean isStarted;
    protected int numOfPlayers;
    protected Map<Integer, Boolean> keys;
    protected String title;
    protected JLabel stageTitle;




    public FirstStage(int width, int height, int numOfPlayers, GameEngine engine, String stageTitle){
        super(width, height, engine);
        players = new Vector<>();
        missiles = new Vector<>();
        isStarted = false;
        keys = new HashMap<>();
        yAxisStartingAnimation = new int[]{height + (5*GameEngine.PLAYER_HEIGHT) , height - (4*GameEngine.PLAYER_HEIGHT) , height - (GameEngine.PLAYER_HEIGHT + 15)};
        startingAnimationIndex = 0;
        bg = new Background(0,0,width,height, 1, "L1-BG.jpg", 0,1000, 4760);
        this.numOfPlayers = numOfPlayers;
        this.title = stageTitle;
        this.stageTitle = new JLabel(this.title);

        if(numOfPlayers == 1) {
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2), yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P1"));
        }else{
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2) + GameEngine.PLAYER_WIDTH, yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P1"));
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2) - GameEngine.PLAYER_WIDTH*3, yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P2"));

        }

        setupKeys();
        enemies = new Vector<>();
        enemyWaveT = new Timer(10000, new enemyWaveLaunch());
        enemyT = new Timer(10000, new enemyLaunch());
        enemyWaveT.start();

        Utils.playSound("jetSound.wav");
    }

    private void setupKeys() {
        int[] p1 = engine.getP1Controlles();
        for(int i = 0 ; i < p1.length ; i++){
            keys.put(p1[i], false);
        }
        if(numOfPlayers > 1){
            int[] p2 = engine.getP2Controlles();
            for(int i = 0 ; i < p1.length ; i++){
                keys.put(p2[i], false);
            }
        }
    }

    @Override
    public void update() {

        bg.update();
        movePlayers();

        if(startingAnimationIndex < 3 && !isStarted){
            if(startingAnimationIndex == 0){
                startingAnimationIndex++;
            }
            if(players.get(0).getLocY() > yAxisStartingAnimation[startingAnimationIndex] && startingAnimationIndex == 1){
                for(int i = 0 ; i < players.size() ; i++){
                    players.get(i).setLocY((int)players.get(i).getLocY() - (p1Speed));
//                    players.get(i).update();
                }
                if(players.get(0).getLocY() <= yAxisStartingAnimation[startingAnimationIndex]){
                    startingAnimationIndex++;
                }
            }else{

                for(int i = 0 ; i < players.size() ; i++){
                    players.get(i).setLocY((int)players.get(i).getLocY() + (p1Speed - 5));
//                    players.get(i).update();
                }
                if(players.get(0).getLocY() >= yAxisStartingAnimation[startingAnimationIndex]){
                    startingAnimationIndex++;
                }
            }
        }else{

            isStarted = true;
            for(int i = 0 ; i < players.size() ; i++){
                players.get(i).update();
            }

            for(int i = 0 ; i < missiles.size() ; i++){
                missiles.get(i).update();
            }

            for(int i = 0 ; i < enemies.size() ; i++){
                enemies.get(i).update();
            }
        }


    }

    private void movePlayers() {
        /**
         * Player 1 Movement:
         */
        if(keys.get(engine.getP1Controlles()[GameEngine.UP]) ){ //UP
            players.get(0).sethDirection(1);
        }
        if(keys.get(engine.getP1Controlles()[GameEngine.DOWN])){ // DOWN
            players.get(0).sethDirection(-1);
        }
        if(!keys.get(engine.getP1Controlles()[GameEngine.UP]) && !keys.get(engine.getP1Controlles()[GameEngine.DOWN])){ // Not up Or Down
            players.get(0).sethDirection(0);
        }
        if(keys.get(engine.getP1Controlles()[GameEngine.LEFT])) { // Left
            players.get(0).setvDirection(-1);
        }
        if(keys.get(engine.getP1Controlles()[GameEngine.RIGHT])) { // Right
            players.get(0).setvDirection(1);
        }
        if(!keys.get(engine.getP1Controlles()[GameEngine.LEFT]) && !keys.get(engine.getP1Controlles()[GameEngine.RIGHT])){ // Not right or left
            players.get(0).setvDirection(0);
        }
        if(keys.get(engine.getP1Controlles()[GameEngine.FIRE]) ){
            if(players.get(0).isAbleToFire()){
                missiles.add(new Missile(players.get(0).getCenterX() - 15, (int)players.get(0).getLocY(), players.get(0).getAcceleration() + 3, "P1Laser.png"));
                players.get(0).updateFireTime();
            }
        }

        /**
         * Player 2 Movement
         */
        if(numOfPlayers > 1){
            if(keys.get(engine.getP2Controlles()[GameEngine.UP]) ){ //UP
                players.get(1).sethDirection(1);
            }
            if(keys.get(engine.getP2Controlles()[GameEngine.DOWN])){ // DOWN
                players.get(1).sethDirection(-1);
            }
            if(!keys.get(engine.getP2Controlles()[GameEngine.UP]) && !keys.get(engine.getP2Controlles()[GameEngine.DOWN])){ // Not up Or Down
                players.get(1).sethDirection(0);
            }
            if(keys.get(engine.getP2Controlles()[GameEngine.LEFT])) { // Left
                players.get(1).setvDirection(-1);
            }
            if(keys.get(engine.getP2Controlles()[GameEngine.RIGHT])) { // Right
                players.get(1).setvDirection(1);
            }
            if(!keys.get(engine.getP2Controlles()[GameEngine.LEFT]) && !keys.get(engine.getP2Controlles()[GameEngine.RIGHT])){ // Not right or left
                players.get(1).setvDirection(0);
            }
            if(keys.get(engine.getP2Controlles()[GameEngine.FIRE]) ){
                if(players.get(1).isAbleToFire()){
                    missiles.add(new Missile(players.get(1).getCenterX() - 15, (int)players.get(1).getLocY(), players.get(1).getAcceleration() + 3, "P1Laser.png"));
                    players.get(1).updateFireTime();
                }
            }
        }
    }

    @Override
    public void render(JPanel p) {
        sceneImage = new BufferedImage(this.stageWidth, this.stageHeight, Image.SCALE_FAST);
        Graphics g = sceneImage.getGraphics();

        bg.drawSprite(g, p);
        Color c = g.getColor();
        if(!isStarted){
            Font f = engine.getGameFont();
            if(f == null) {
                f = g.getFont();
            }
             f = f.deriveFont(60F);
             g.setColor(Color.DARK_GRAY);
             g.setFont(f);


            // Get the FontMetrics
            FontMetrics metrics = g.getFontMetrics(f);
            // Determine the X coordinate for the text
            int x = (stageWidth - metrics.stringWidth(this.title)) / 2;
            // Determine the Y coordinate for the text
            int y = ((stageHeight - metrics.getHeight()) / 2) - metrics.getAscent();
            g.drawString(this.title, x, y);
            g.setColor(c);
        }


        for(int i = 0 ; i < missiles.size() ; i++){
            missiles.get(i).drawSprite(g,p);
        }
        for(int i = 0 ; i < players.size() ; i++){
            players.get(i).drawSprite(g,p);
        }

        for(int i = 0 ; i < enemies.size() ; i++){
            enemies.get(i).drawSprite(g,p);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(isStarted)
            keys.put(keyEvent.getKeyCode(), true);

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if(isStarted)
            keys.put(keyEvent.getKeyCode(), false);
    }

    private class fireTimer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            missiles.add(new Missile(((EnemyShip)actionEvent.getSource()).getCenterX(), (int)((EnemyShip)actionEvent.getSource()).getLocY(), ((EnemyShip)actionEvent.getSource()).getAcceleration() + 3, "P1Laser.png"));
        }
    }

    private class enemyWaveLaunch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
        }


    }

    private class enemyLaunch implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            enemies.add(new EnemyShip(0,0,0,0,3,"L1-ES1.png",0,15,15,new fireTimer()));
        }

    }
}
