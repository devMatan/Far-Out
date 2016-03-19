package com.sagi.dayan.Games.Utils;

import java.util.Vector;

/**
 * Created by sagi on 3/18/16.
 */
public class WaveConfigs  {
    public static final int DEMO = 0;//, UPPER_MIDDLE_LEFT=0, UPPER_MIDDLE_RIGHT=1;
    //public static final int UPPER_LEFT = 2, UPPER_RIGHT=3;
    Vector<WaveConfig> configs;

    public WaveConfigs(){
        configs = new Vector<>();
//        int[] moveVector, double stepDelay, int acc, int staryX, int startY
		
		//middle top to left top
        configs.add(new WaveConfig(new int[]{90,90,120, 120, 150, 150, 270} , 0.5, 8,500 , -30));
		
		//middle top to right top
        configs.add(new WaveConfig(new int[]{90,90, 60, 60, 30, 30, 270} , 0.5, 8,500 , -30));
		
		//top left to middle top
		configs.add(new WaveConfig(new int[]{90,90, 60, 60, 30, 30, 270} , 0.5, 8,100 , -30));
		
		//top right to middle top
		configs.add(new WaveConfig(new int[]{90,90,120, 120, 150, 150, 270} , 0.5, 8,900 , -30));
		
		//right buttom to middle buttom
        configs.add(new WaveConfig(new int[]{270,270,300, 300, 330, 330, 90} , 0.5, 8,100 , 0));
		
		//left buttom to middle buttom
        configs.add(new WaveConfig(new int[]{270,270,240, 240, 210, 210, 90} , 0.5, 8,900 , 0));
		
		//middle right to middle right
		configs.add(new WaveConfig(new int[]{180,180,180,90, 90, 0} , 0.5, 8,1010 , 400));
		
		//middle left to middle left
		configs.add(new WaveConfig(new int[]{0,0,0,90, 90, 180} , 0.5, 8,-30 , 400));

    }

    public WaveConfig getWaveConfig(int config){
        if (config < 0 || configs.size() <= config)
            throw new IllegalArgumentException("no such config...");
        return configs.get(config);
    }
}