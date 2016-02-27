package com.sagi.dayan.Games.Elements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by sagi on 2/20/16.
 */
public class EnemyShip extends AnimatedSprite {
    Timer t;
    public EnemyShip(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight, ActionListener timerListener) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);

        t = new Timer(1000,timerListener);

    }

    @Override
    protected void initFirstAnimation(String spriteSheet) {
        animations.add(new Animation("P1Laser.png", 4, 500));
    }

    @Override
    public void update() {
        locX = 200;
        locY += acceleration;
    }





}
