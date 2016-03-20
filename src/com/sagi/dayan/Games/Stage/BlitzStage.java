package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Elements.Wave;
import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Utils.WaveConfig;
import com.sagi.dayan.Games.Utils.WaveConfigs;

/**
 * Created by sagi on 3/19/16.
 */
public class BlitzStage extends Level{
	
	protected final int NUM_OF_WAVES = 4;

    public BlitzStage(int width, int height, int numOfPlayers, GameEngine engine, String stageTitle, int[] waveDelay) {
        super(width, height, numOfPlayers, engine, stageTitle, waveDelay);
        numOfWaves = NUM_OF_WAVES;
    }

    @Override
    protected void launchWave(long now) {
        lastWaveTime = now;
        System.out.println("New Wave!! " + currentWave + ", Time: " + now);
        WaveConfig wc;
        int numOfEnemies = 5, numOfHits = 1;
        double launchDelay = 0.5, fireDelay = 5;
        switch (currentWave){
            case 0:
                numOfEnemies = 15;
                fireDelay = 0.5;
                launchDelay = 0.5;
                numOfHits = 1;
                wc = engine.getWaveConfigs().getWaveConfig(0);
                break;
            case 1:
                numOfEnemies = 5;
                fireDelay = 0.3;
                launchDelay = 0.5;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(1);
                break;
            case 2:
                numOfEnemies = 5;
                fireDelay = 0.3;
                launchDelay = 1;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(2);
                break;
            case 3:
                numOfEnemies = 15;
                fireDelay = 0.2;
                launchDelay = 1;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(3);
                break;
            case 4:
                numOfEnemies = 20;
                fireDelay = 0.1;
                launchDelay = 1.5;
                numOfHits = 5;
                wc = engine.getWaveConfigs().getWaveConfig(4);
                break;
            case 5:
                numOfEnemies = 20;
                fireDelay = 0.2;
                launchDelay = 1;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(5);
                break;
            case 6:
                numOfEnemies = 20;
                fireDelay = 0.2;
                launchDelay = 0.5;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(6);
                break;
            case 7:
                numOfEnemies = 40;
                fireDelay = 0.2;
                launchDelay = 2;
                numOfHits = 10;
                wc = engine.getWaveConfigs().getWaveConfig(7);
                break;
            default:
                wc = engine.getWaveConfigs().getWaveConfig(WaveConfigs.DEMO);
                break;
        }
        waves.add(new Wave(numOfEnemies, wc.getMoveVector(), fireDelay, wc.getStepDelay(), launchDelay, wc.getAcc(), "L1-ES1.png", wc.getStartX(), wc.getStartY(), this, numOfHits));
        currentWave++;
    }
}
