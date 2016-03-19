package com.sagi.dayan.Games.Utils;

import java.util.Vector;

/**
 * Created by sagi on 3/18/16.
 */
public class WaveConfigs  {
    public static final int DEMO = 0;
    Vector<WaveConfig> configs;

    public WaveConfigs(){
        configs = new Vector<>();
//        int[] moveVector, double stepDelay, int acc, int staryX, int startY
        configs.add(new WaveConfig(new int[]{90,90,120, 120, 150, 150, 270, 270, 270} , 0.5, 8,500 , 0));

//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
//        configs.add(new WaveConfig( , , , , ));
    }

    public WaveConfig getWaveConfig(int config){
        if (config < 0 || configs.size() <= config)
            throw new IllegalArgumentException("no such config...");
        return configs.get(config);
    }
}
