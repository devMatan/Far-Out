package com.sagi.dayan.Games.Elements;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public abstract class Sprite {
    protected BufferedImage bImage;
    protected int imageWidth, imageHeight; // image dimensions
    protected URL imagePath;
    protected String imageName;
    protected int locX, locY;
    protected int acceleration;
    protected int pWidth, pHeight;  // panel's dimensions
    protected int sWidth, sHeight;
    protected int selfRotationSpeed;
    protected double angle;
    protected boolean screenLoop = false;




    public Sprite(int x, int y, int w, int h, int acc, String imgName, double angle, int sWidth, int sHeight) {
        this.imagePath = getClass().getResource("/com/sagi/dayan/Games/Images/" + imgName);
        this.imageName = imgName;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        locX = x;
        locY = y;
        acceleration = acc;
        pWidth = w;
        pHeight = h;
        this.angle = angle;

        //load image from source files
        try {
            bImage = ImageIO.read(imagePath);
        } catch (IOException pin) {
            pin.printStackTrace();
            bImage = null;
        }

//        setImageDimensions();
    }


    /*
     * resizes image to a set size
     */
    protected void setImageDimensions() {
        Image tmp = bImage.getScaledInstance(sWidth, sHeight, Image.SCALE_SMOOTH);
        BufferedImage bi = new BufferedImage(sWidth, sHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        bImage = bi;
    }

    /**
     * Abstract method to update sprite.
     */
    public abstract void update();

    /**
     * returns sprite x position
     *
     * @return double
     */
    public double getLocX() {
        return locX;
    }

    public void setLocX(int locX) {
        this.locX = locX;
    }

    public void setLocY(int locY) {
        this.locY = locY;
    }

    /**
     * returns sprite y position
     *
     * @return double
     */
    public double getLocY() {
        return locY;
    }

    /**
     * returns sprite image width
     *
     * @return int
     */
    public int getSWidth() {
        return sWidth;
    }

    /**
     * returns sprite image height
     *
     * @return int
     */
    public int getsHeight() {
        return sHeight;
    }

    /**
     * returns sprite acceleration
     *
     * @return int
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     * returns sprite size
     *
     * @return int
     */
    public BufferedImage getbImage() {
        return bImage;
    }

    /**
     * returns image width
     *
     * @return int
     */
    public int getImageWidth() {
        return imageWidth;
    }

    /**
     * returns image height
     *
     * @return int
     */
    public int getImageHeight() {
        return imageHeight;
    }

    /**
     * returns sprite angle
     *
     * @return double
     */
    public double getAngle() {
        return angle;
    }

    public int getCenterX() {return locX + (sWidth / 2);}
    public int getCenterY() {return locY + (sHeight / 2);}


    /**
     * returns shape location and dimensions as a Rectangle.
     *
     * @return Rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle((int) locX, (int) locY, bImage.getWidth(), bImage.getHeight());
    }

    public boolean isScreenLoop() {
        return screenLoop;
    }

    public void setScreenLoop(boolean screenLoop) {
        this.screenLoop = screenLoop;
    }


    /**
     * its not a bug it's  a feature. actually it just moves a shape that goes beyond the screen to the other side.
     */
	protected void outOfScreeFix(){
        /**
         * X: Left Edge
         */
        if(locX < 0 - sWidth)
			locX = pWidth + locX;
        /**
         * X: Right Edge
         */
		else if (locX  > pWidth + sWidth)
            locX = Math.abs(pWidth - (locX + sWidth)) - sWidth;

        /**
         * Y: Top Edge
         */
		if(locY < 0 - sHeight)
			locY = pHeight + locY;
        /**
         * Y: Bottom Edge
         */
		else if(locY > pHeight+sHeight)
            locY = Math.abs(pHeight - (locY + sHeight)) - sHeight;
	}

    protected void drawScreenLoopFix(Graphics g, JPanel p) {
        Graphics2D g2d = (Graphics2D)g;

        /**
         * Left Edge
         */
         if (locX < 0) {
            g2d.rotate(Math.toRadians(angle), pWidth + locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
            g.drawImage(bImage, pWidth + locX, locY, p);
            g2d.rotate(-1 * Math.toRadians(angle), pWidth + locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        }

        /**
         * Right Edge
         */
         if (locX + sWidth > pWidth ) {
            g2d.rotate(Math.toRadians(angle), Math.abs(pWidth - (locX + sWidth)) - sWidth + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
            g.drawImage(bImage, Math.abs(pWidth - (locX + sWidth)) - sWidth, locY, p);
            g2d.rotate(-1 * Math.toRadians(angle), Math.abs(pWidth - (locX + sWidth)) - sWidth + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        }
        /**
         * Top Edge
         */
         if (locY < 0) {
            g2d.rotate(Math.toRadians(angle), locX + (bImage.getWidth() / 2), pHeight + locY + (bImage.getHeight() / 2));
            g.drawImage(bImage, locX , pHeight + locY, p);
            g2d.rotate(-1 * Math.toRadians(angle), locX + (bImage.getWidth() / 2),  pHeight + locY + (bImage.getHeight() / 2));
        }
        /**
         * Bottom Edge
         */
         if ( pHeight < locY + sHeight) {
            g2d.rotate(Math.toRadians(angle), locX + (bImage.getWidth() / 2), Math.abs(pHeight - (locY + sHeight)) - sHeight+ (bImage.getHeight() / 2));
            g.drawImage(bImage, locX , Math.abs(pHeight - (locY + sHeight)) - sHeight, p);
            g2d.rotate(-1 * Math.toRadians(angle), locX + (bImage.getWidth() / 2), Math.abs(pHeight - (locY + sHeight)) - sHeight + (bImage.getHeight() / 2));
        }
    }

    /**
     *
     *
     * @param g
     */
    public void drawSprite(Graphics g, JPanel p) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(angle), locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        g.drawImage(bImage, locX, locY, p);
        g2d.rotate(-1 * Math.toRadians(angle), locX + (bImage.getWidth() / 2), locY + (bImage.getHeight() / 2));
        if(screenLoop) {
            outOfScreeFix();
            drawScreenLoopFix(g, p);
        }
    }

    public boolean isOutOfScreen() {
        if (this.getLocX() + this.sWidth < 0 || this.getLocX() - this.sWidth > pHeight || this.getLocY() + this.sHeight < 0 || this.getLocY() - sHeight > pHeight) {
            return true;
        } else {
            return false;
        }
    }

}