package com.sagi.dayan.Games.Stage;

import com.sagi.dayan.Games.Elements.*;
import com.sagi.dayan.Games.Engine.GameEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Created by sagi on 2/10/16.
 */
public class SampleScene extends Scene {
    private final int STOP=0, UP=1, DOWN=-1, TURN_SPEED=10;
    private DemoSprite sprite;
    private AnimatedDemoSprite animated;
    private int r = 0, g = 0, b = 0;
    private boolean toWhite = true;

    public SampleScene(int width, int height, GameEngine engine){
        super(width, height, engine);
//        sprite = new DemoSprite(50,50,width, height, 50);
        animated = new AnimatedDemoSprite(width/2, height/2, width, height,5,"animatedSample.png", 0, 58,87);
    }

    @Override
    public void update() {
        sprite.update();
        animated.update();
    }

    @Override
    public void render(JPanel p) {
        sceneImage = new BufferedImage(this.stageWidth, this.stageHeight, Image.SCALE_FAST);
        Graphics g = sceneImage.getGraphics();
        g.setColor(getColor());
        g.fillRect(0,0,stageWidth, stageWidth);
        sprite.drawSprite(g, p);
        animated.drawSprite(g, p);
    }


    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("Pressed!");

        if (keyEvent.getKeyCode() == keyEvent.VK_LEFT) {
            sprite.turnShip(DOWN);
        }
        if (keyEvent.getKeyCode() == keyEvent.VK_RIGHT) {
            sprite.turnShip(UP);
        }
        if (keyEvent.getKeyCode() == keyEvent.VK_UP) {
            sprite.setDirection(UP);
        }
        if (keyEvent.getKeyCode() == keyEvent.VK_DOWN) {
            sprite.setDirection(DOWN);
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                sprite.setDirection(STOP);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_LEFT:
                sprite.turnShip(STOP);
                break;
//			case KeyEvent.VK_SPACE:
//				laserAudioClip.stop();
//				break;
            default:
                break;

        }
    }

    public void mousePressed(MouseEvent e){
        System.out.println("Mouse pressed");
    }

    private Color getColor() {
        if(toWhite){
            if(r < 225) {
                r++;
            } else if (g < 225) {
                g++;
            } else if ( b < 225) {
                b++;
            }else if (r == 225 && g ==225 && b == 225){
                toWhite = false;
            }
        } else {
            if(b > 0) {
                b--;
            } else if (g > 0) {
                g--;
            } else if ( r > 0) {
                r--;
            }else if (r == 0 && g ==0 && b == 0){
                toWhite = true;
            }
        }
        return new Color(r, g, b);

    }
}
