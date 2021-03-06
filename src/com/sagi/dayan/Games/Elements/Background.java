package com.sagi.dayan.Games.Elements;

/**
 * Created by sagi on 2/20/16.
 */
public class Background extends Sprite {
    public Background(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
        locX = 0;
        locY = pHeight - sHeight;
    }

    @Override
    public void update() {
        locY  += acceleration;
        if(locY >= 0)
            locY = 0;
    }
}
