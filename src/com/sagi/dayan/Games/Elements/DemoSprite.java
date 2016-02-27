package com.sagi.dayan.Games.Elements;

import java.util.Random;

/**
 * Created by sagi on 2/10/16.
 */
public class DemoSprite extends Sprite {
    private final int STOP=0, UP=1, DOWN=-1, TURN_SPEED=10;
    private final double MAX_SPEED = 6, SLOWING_FACTOR = 0.1;
    Random r;
    private double selfAccel;
    private int direction, turnDirection;
    int counter = 0;
    public DemoSprite(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
        selfRotationSpeed = 5;
        direction = STOP;
        turnDirection = STOP;
        selfAccel = acceleration;
        setScreenLoop(true);

    }

    @Override
    public void update() {
        setSpeed();
        this.angle+=TURN_SPEED*turnDirection;
        locX += selfAccel * Math.cos(Math.toRadians(angle));
        locY -= selfAccel * (-1 * Math.sin(Math.toRadians(angle)));
        System.out.println("locX: " + locX + "\tlocY: "+locY);
    }

    private void setSpeed(){
        if (direction == UP && !(selfAccel > MAX_SPEED)){
            selfAccel+=SLOWING_FACTOR*2;
        }
        else if (direction == DOWN && (selfAccel > MAX_SPEED*(-1))){
            selfAccel-=SLOWING_FACTOR*2;
        }
        else { //slowing down
            if (selfAccel > 0) {
                selfAccel -= SLOWING_FACTOR;
                if (selfAccel < 0) {
                    selfAccel = 0;
                }
            }
            if (selfAccel < 0) {
                selfAccel += SLOWING_FACTOR;
                if (selfAccel > 0) {
                    selfAccel = 0;
                }
            }
        }
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void turnShip(int direction){
        turnDirection=direction;
    }
}
