package com.sagi.dayan.Games;

import com.sagi.dayan.Games.Engine.GameEngine;
import com.sagi.dayan.Games.Stage.Stage;
import com.sagi.dayan.Games.Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by sagi on 2/8/16.
 */


public class Main   {

    public static final int WIDTH = 1000, HEIGHT = 1000;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Stage(WIDTH, HEIGHT));
        frame.setVisible(true);
        frame.setBackground(Color.BLACK);
        System.out.println(WIDTH + "\t" + (HEIGHT));
    }



}
