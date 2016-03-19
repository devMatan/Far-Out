package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Elements.Wave;
import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Utils.WaveConfig;
import com.sagi.dayan.Games.Utils.WaveConfigs;

/**
 * Created by sagi on 3/19/16.
 */
public class FirstStage extends Level{

    public FirstStage(int width, int height, int numOfPlayers, GameEngine engine, String stageTitle, int[] waveDelay) {
        super(width, height, numOfPlayers, engine, stageTitle, waveDelay);
    }

    @Override
    protected void launchWave(long now) {
        lastWaveTime = now;
        System.out.println("New Wave!! Time: " + now);
        WaveConfig wc;
        int numOfEnemies = 5, numOfHits = 1;
        double launchDelay = 0.5, fireDelay = 5;
        switch (currentWave){
            case 0:
                numOfEnemies = 5;
                fireDelay = 0.2;
                launchDelay = 0.5;
                numOfHits = 1;
                wc = engine.getWaveConfigs().getWaveConfig(WaveConfigs.DEMO);
                break;
            case 1:
                numOfEnemies = 5;
                fireDelay = 5;
                launchDelay = 1;
                numOfHits = 1;
                wc = engine.getWaveConfigs().getWaveConfig(WaveConfigs.DEMO);
                break;
            default:
                wc = engine.getWaveConfigs().getWaveConfig(WaveConfigs.DEMO);
                break;
        }
        waves.add(new Wave(numOfEnemies, wc.getMoveVector(), fireDelay, wc.getStepDelay(), launchDelay, wc.getAcc(), "L1-ES1.png", wc.getStartX(), wc.getStartY(), this, numOfHits));
        currentWave++;
    }
}
