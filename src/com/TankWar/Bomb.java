package com.TankWar;

import java.awt.*;

public class Bomb implements Runnable{

    int x,y;
    int state = 20;
    boolean isAlive;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.isAlive = true;
    }

    public boolean paint(Graphics g)
    {
        g.setColor(Color.red);
        g.fillOval(x,y,state * 2,state * 2);
        return isAlive;
    }

    @Override
    public void run() {
        while (isAlive)
        {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            state -= 1;
            x += 1;
            y += 1;
            if (state <= 0)
                isAlive = false;
        }
    }
}
