package com.sagi.dayan.Games.Elements;

/**
 * Created by sagi on 2/24/16.
 */
public class MenuBoxSprite extends AnimatedSprite {
    public MenuBoxSprite(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
    }

    @Override
    protected void initFirstAnimation(String spriteSheet) {
        animations.add(new Animation("menuBox.png", 15, 150));
    }

    @Override
    public void update() {

    }
}
