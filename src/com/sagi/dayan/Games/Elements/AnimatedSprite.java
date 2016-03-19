package com.sagi.dayan.Games.Elements;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Created by sagi on 2/10/16.
 */
public abstract class AnimatedSprite extends Sprite {
    protected Vector<Animation> animations;
    protected int currentAnimation;


    public AnimatedSprite(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight, int numOfFirstFrames) {
        super(x, y, w, h, acc, imgName, angle, sWidth, sHeight);
        animations = new Vector<>();
        initFirstAnimation(imgName, numOfFirstFrames);
        currentAnimation = 0;

    }

    protected abstract void initFirstAnimation(String spriteSheet, int numOfFirstFrames);

    @Override
    public void drawSprite(Graphics g, JPanel p) {
        if(animations.size() == 0)
            return;
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angle), locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        g.drawImage(animations.get(currentAnimation).getCurrentFrame(), locX, locY, p);
        g2d.rotate(-1 * Math.toRadians(angle), locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        if(screenLoop) {
            drawScreenLoopFix(g, p);
            outOfScreeFix();
        }
    }

    @Override
    protected void drawScreenLoopFix(Graphics g, JPanel p) {
        bImage = animations.get(currentAnimation).getCurrentFrame();
        super.drawScreenLoopFix(g,p);
    }
    
    public int getFrameNum(){
    	return animations.get(currentAnimation).getFrameIndex();
    }

    public void setCurrentAnimation(int animation){
        if(animation < 0){
            throw new IllegalArgumentException("Animation index cant be negative");
        }else if(animation >= animations.size()){
            throw new IllegalArgumentException("Number of animations is: "+ animations.size() + 1+". " +animation +"is out the limit");
        }else{
            currentAnimation = animation;
        }
    }

    public BufferedImage getImageFrame() {
        return animations.get(currentAnimation).getCurrentFrame();
    }


    protected class Animation {

        private int totalLoopTime;
        private Vector<AnimationFrame> frames;
        private int currentFrame;
        private long startingTime;
        private BufferedImage spriteSheet;

        public Animation(String spriteSheetName, int numOfFrames, int totalAnimationTime) {
            this.currentFrame = 0;
            this.totalLoopTime = 0;
            this.startingTime = System.currentTimeMillis();
            this.frames = new Vector<>();

            //load image from source files
            try {
                spriteSheet = ImageIO.read(getClass().getResource("/com/sagi/dayan/Games/Images/" + spriteSheetName));
            } catch (IOException pin) {
                pin.printStackTrace();
                spriteSheet = null;
            }
            int frameHeight = spriteSheet.getHeight();
            int frameWidth = spriteSheet.getWidth() / numOfFrames;
            sWidth = frameWidth;
            sHeight = frameHeight;
            int currentX = 0;
            for(int i = 0 ; i < numOfFrames ; i++) {
                addFrame(spriteSheet.getSubimage(currentX, 0, frameWidth, frameHeight), (double)totalAnimationTime/numOfFrames);
                currentX += frameWidth;
            }


        }

        public int getFrameIndex() { return currentFrame;}

        private void addFrame(BufferedImage image, double frameLength) {
            frames.add(new AnimationFrame(image, totalLoopTime,frameLength));
            totalLoopTime += frameLength;
        }

        public BufferedImage getCurrentFrame() {
            long now = System.currentTimeMillis();
            long delta = now - startingTime;
            while( !( frames.get(currentFrame).getStartTime() <= delta && delta < frames.get(currentFrame).getEndTime() ) ){
                currentFrame++;
                if(currentFrame >= frames.size()) {
                    currentFrame = 0;
                    startingTime = now;
                    return  frames.get(currentFrame).getFrame();
                }
            }
            return frames.get(currentFrame).getFrame();
        }

        protected class AnimationFrame {
            private double startTime, endTime;
            private BufferedImage frame;

            public AnimationFrame(BufferedImage image, double startTime , double frameLength){
                this.startTime = startTime;
                this.endTime = this.startTime + frameLength;
                this.frame = image;
            }

            public double getStartTime() { return startTime;}
            public double getEndTime() { return endTime;}
            public BufferedImage getFrame() {return frame;}

        }

    }


}
