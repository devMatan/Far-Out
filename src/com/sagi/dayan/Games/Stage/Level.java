package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Elements.*;
import com.sagi.dayan.Games.Engine.CollisionUtil;
import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Utils.Utils;
import com.sagi.dayan.Games.Utils.WaveConfig;
import com.sagi.dayan.Games.Utils.WaveConfigs;

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
public abstract class Level extends Scene {
    protected Vector<Player> players;
    protected int p1Speed = 10;
    protected Vector<Missile> p1Missiles, p2Missiles, enemyMissiles;
    protected Background bg;
    protected int[] waveDelay;
    protected int currentWave;
    protected int[] yAxisStartingAnimation;
    protected int startingAnimationIndex;
    protected boolean isStarted;
    protected Vector<Wave> waves;
    protected int numOfPlayers;
    protected Map<Integer, Boolean> keys;
    protected String title;
    protected JLabel stageTitle;
    protected long lastWaveTime;



    public Level(int width, int height, int numOfPlayers, GameEngine engine, String stageTitle, int[] waveDelay){
        super(width, height, engine);
        players = new Vector<>();
        p1Missiles = new Vector<>();
        p2Missiles = new Vector<>();
        enemyMissiles = new Vector<>();
        this.waveDelay = waveDelay;
        this.lastWaveTime = System.currentTimeMillis();
        this.currentWave = 0;
        this.waves = new Vector<>();
        isStarted = false;
        keys = new HashMap<>();
        yAxisStartingAnimation = new int[]{height + (5*GameEngine.PLAYER_HEIGHT) , height - (4*GameEngine.PLAYER_HEIGHT) , height - (GameEngine.PLAYER_HEIGHT + 15)};
        startingAnimationIndex = 0;
        bg = new Background(0,0,width,height, 1, "L1-BG.jpg", 0,1000, 4760);
        this.numOfPlayers = numOfPlayers;
        this.title = stageTitle;
        this.stageTitle = new JLabel(this.title);

        if(numOfPlayers == 1) {
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2), yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P1",6));
        }else{
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2) + GameEngine.PLAYER_WIDTH, yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P1", 6));
            players.add(new Player((width / 2) + (GameEngine.PLAYER_WIDTH / 2) - GameEngine.PLAYER_WIDTH*3, yAxisStartingAnimation[startingAnimationIndex], width, height, p1Speed, "emptyImage.png", 0, GameEngine.PLAYER_WIDTH, GameEngine.PLAYER_HEIGHT, "P2", 6));

        }

        setupKeys();
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
        Vector <Wave> wavesToRemove = new Vector<Wave>();

        long now = System.currentTimeMillis();
        if(currentWave < waveDelay.length && now - lastWaveTime >= waveDelay[currentWave] * 1000){
            launchWave(now);
        }

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

            for(int i = 0 ; i < p1Missiles.size() ; i++){
                p1Missiles.get(i).update();
            }
            for(int i = 0 ; i < p2Missiles.size() ; i++){
                p2Missiles.get(i).update();
            }
            for(int i = 0 ; i < enemyMissiles.size() ; i++){
                enemyMissiles.get(i).update();
            }

            for(int i = 0 ; i < waves.size() ; i++){
                waves.get(i).update();
                if(waves.get(i).isWaveOver()) {
                    wavesToRemove.add(waves.get(i));
                }
            }
            waves.removeAll(wavesToRemove);
        }
        checkCollision();
    }

    protected abstract void launchWave(long time);

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
                p1Missiles.add(new Missile(players.get(0).getCenterX() - 15, (int)players.get(0).getLocY(), players.get(0).getAcceleration() + 3, "P1Laser.png", 4));
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
                    p2Missiles.add(new Missile(players.get(1).getCenterX() - 15, (int)players.get(1).getLocY(), players.get(1).getAcceleration() + 3, "P1Laser.png", 4));
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


        for(int i = 0 ; i < p1Missiles.size() ; i++){
            p1Missiles.get(i).drawSprite(g,p);
        }
        for(int i = 0 ; i < p2Missiles.size() ; i++){
            p2Missiles.get(i).drawSprite(g,p);
        }
        for(int i = 0 ; i < enemyMissiles.size() ; i++){
            enemyMissiles.get(i).drawSprite(g,p);
        }
        for(int i = 0 ; i < players.size() ; i++){
            players.get(i).drawSprite(g,p);
        }
        for(int i = 0 ; i < waves.size() ; i++){
            waves.get(i).render(g,p);
        }

    }

    public void checkCollision() {
        Vector<Missile> p1MTR, p2MTR, eMTR;
        eMTR = new Vector<>();
        p1MTR = new Vector<>();
        p2MTR = new Vector<>();
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < enemyMissiles.size(); j++) {
                if(CollisionUtil.collidesWith(players.get(i),enemyMissiles.get(j))){
                    //Remove players Life
                    eMTR.add(enemyMissiles.get(j));
                    System.out.println("Hit Missile");
                }
            }
            for (int j = 0; j < waves.size(); j++) {
                // Ship hits enemy
                for (int k = 0; k < waves.get(j).getEnemies().size(); k++) {
                    if (CollisionUtil.collidesWith(waves.get(j).getEnemies().get(k), players.get(i))) {
                        engine.setPlayerStrikes(i, -1);
                        waves.get(j).enemyHit(waves.get(j).getEnemies().get(k));
                        System.out.println("PIN");
                    }
                }
            }

            if(i == 0){
                for(int m = 0 ; m < p1Missiles.size() ; m++){
                    for (int j = 0; j < waves.size(); j++) {
                        // Ship hits enemy
                        for (int k = 0; k < waves.get(j).getEnemies().size(); k++) {
                            if (CollisionUtil.collidesWith(waves.get(j).getEnemies().get(k), p1Missiles.get(m))) {
                                waves.get(j).enemyHit(waves.get(j).getEnemies().get(k));
                                p1MTR.add(p1Missiles.get(m));
                            }
                        }
                    }
                }
            }else{
                for(int m = 0 ; m < p2Missiles.size() ; m++){
                    for (int j = 0; j < waves.size(); j++) {
                        // Ship hits enemy
                        for (int k = 0; k < waves.get(j).getEnemies().size(); k++) {
                            if (CollisionUtil.collidesWith(waves.get(j).getEnemies().get(k), p2Missiles.get(m))) {
                                waves.get(j).enemyHit(waves.get(j).getEnemies().get(k));
                                p2MTR.add(p2Missiles.get(m));
                            }
                        }
                    }
                }
            }
        }



        p1Missiles.removeAll(p1MTR);
        p2Missiles.removeAll(p2MTR);
        enemyMissiles.removeAll(eMTR);

    }

    public void enemyFire(int x, int y, int acc) {
        enemyMissiles.add(new Missile(x, y, acc,"E1-Fire.png", 15));
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


}
