package com.sagi.dayan.Games.Elements;

/**
 * Created by sagi on 2/20/16.
 */
public class Missile extends AnimatedSprite {


    public Missile(int x, int y, int w, int h, int acc, String imgName, int numOfFrames) {
        super(x, y, w, h, acc, imgName, 0, 15, 15, numOfFrames);

    }

    @Override
    protected void initFirstAnimation(String spriteSheet, int numOfFirstFrames) {
        animations.add(new Animation(imageName, numOfFirstFrames, 500));
    }

    @Override
    public void update() {
        locY -= acceleration;
    }
}
