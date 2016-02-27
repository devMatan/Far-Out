package com.sagi.dayan.Games.Elements;

/**
 * Created by sagi on 2/20/16.
 */
public class Missile extends AnimatedSprite {


    public Missile(int x, int y, int acc, String imgName) {
        super(x, y, 0, 0, acc, imgName, 0, 15, 15);
    }

    @Override
    protected void initFirstAnimation(String spriteSheet) {
        animations.add(new Animation("P1Laser.png", 4, 500));
    }

    @Override
    public void update() {
        locY -= acceleration;
    }
}
