package com.sagi.dayan.Games.Elements;

/**
 * Created by sagi on 2/20/16.
 */
public class Player extends AnimatedSprite {
    private final int NORMAL_ANIMATION = 0, RIGHT_ANIMATION = 1, LEFT_ANIMATION = 2, PADDING_BOTTOM = 35;
    private int hDirection = 0, vDirection = 0;
    private String imagePrefix;
    private boolean ableToFire;
    private int fireDelay;
    private long lastFired;


    public Player(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight, String imagePrefix) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
        this.imagePrefix = imagePrefix;
        initFirstAnimation("");
        this.ableToFire = true;
        fireDelay = 100;
        lastFired = System.currentTimeMillis();
    }

    @Override
    protected void initFirstAnimation(String spriteSheet) {
        if(imagePrefix == null)
            return;
        System.out.println(imagePrefix);
        animations.add(new Animation(imagePrefix+"StraighSheet.png", 7, 200));
        animations.add(new Animation(imagePrefix+"RightSheet.png", 7, 200));
        animations.add(new Animation(imagePrefix+"LeftSheet.png", 7, 200));
    }

    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if(now - lastFired >= fireDelay){
            ableToFire = true;
        }else{
            ableToFire = false;
        }
        locX += vDirection * acceleration;
        locY -= hDirection * acceleration;
        if(locX < 0)
            locX = 0;
        else if(locX > pWidth - animations.get(currentAnimation).getCurrentFrame().getWidth()){
            locX = pWidth - animations.get(currentAnimation).getCurrentFrame().getWidth();
        }

        if(locY < 0)
            locY = 0;
        else if(locY > pHeight - animations.get(currentAnimation).getCurrentFrame().getHeight() - PADDING_BOTTOM){
            locY = pHeight - animations.get(currentAnimation).getCurrentFrame().getHeight() - PADDING_BOTTOM;
        }


    }

    public void sethDirection(int direction) {
        this.hDirection = direction;
    }

    public void setvDirection(int direction) {
        this.vDirection = direction;
        if(direction != NORMAL_ANIMATION){
            currentAnimation = (direction == 1) ? RIGHT_ANIMATION : LEFT_ANIMATION;
        }else{
            currentAnimation = 0;
        }
    }

    public boolean isAbleToFire() {
        return ableToFire;
    }

    public void setAbleToFire(boolean ableToFire) {
        this.ableToFire = ableToFire;
    }

    public int getFireDelay() {
        return fireDelay;
    }

    public void setFireDelay(int fireDelay) {
        this.fireDelay = fireDelay;
    }

    public void updateFireTime(){
        lastFired = System.currentTimeMillis();
    }
}
