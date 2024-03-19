package com.TankWar;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GameFrame extends JFrame {
    GamePanel gamePanel;
    InfoPanel infoPanel;

    public GameFrame(String key){

        if(key.equals("1")) {
            gamePanel = new GamePanel();
        } else if (key.equals("2")) {
            gamePanel = new GamePanel(key);
        }


        this.add(gamePanel);


        this.addKeyListener(gamePanel);
        this.setSize(1214,787);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                InfoPanel.getInstance().saveInfo();
                System.exit(0);
            }
        });


    }
}
