package com.sagi.dayan.Games.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sagi on 2/10/16.
 */
public class AnimatedDemoSprite extends AnimatedSprite {

    private int timerCouner = 0;
    protected Timer t;

    public AnimatedDemoSprite(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
        setScreenLoop(true);
        animations.add(new Animation("animatedSample2.png", 16, 2000));
        t = new Timer(1 * 1000, new TimerTick());
        t.start();
    }

    @Override
    public void update() {
        locX += acceleration;
    }

    @Override
    protected void initFirstAnimation(String spriteSheet) {
        animations.add(new Animation(spriteSheet, 8, 1 * 1000));
    }

    private class TimerTick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(timerCouner < 5){
                timerCouner++;
            }
            else if(5 <= timerCouner && timerCouner < 7){
                System.out.println("Explode");
                timerCouner++;
                currentAnimation = 1;
                acceleration = 0;
            }else{
                currentAnimation = 0;
                timerCouner = 0;
                acceleration = 5;
            }
        }
    }


}
