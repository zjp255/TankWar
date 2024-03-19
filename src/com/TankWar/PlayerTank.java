package com.TankWar;

import org.w3c.dom.events.Event;

import java.awt.event.KeyEvent;
import java.util.Vector;

public class PlayerTank extends Tank{
    Vector<Bullet> bullets = null;
    int maxSize = 5;
    public PlayerTank(int x, int y) {
        super(x, y);
        bullets = new Vector<>(5);
    }

    public PlayerTank(int x, int y, Direction direction) {
        super(x, y, 0, direction);
    }

    public void launchBullet(KeyEvent e)
    {
        if(!isAlive())
            return;
        if( e.getKeyCode() == KeyEvent.VK_SPACE && (bullets.size() < maxSize)) {
            int x = 0, y = 0;
            switch (this.getDirection())
            {
                case up:
                    x = this.getX() + this.getBox()[0] / 2;
                    y = this.getY();
                    break;
                case down:
                    x = this.getX() + this.getBox()[0] / 2;
                    y = this.getY() + this.getBox()[1];
                    break;
                case left:
                    x = this.getX();
                    y = this.getY() + this.getBox()[0] / 2;
                    break;
                case right:
                    x = this.getX() + this.getBox()[1];
                    y = this.getY() + this.getBox()[0] / 2;
                    break;
            }
            Bullet bullet = new Bullet(x, y, this.getDirection(),this.getType(),this);
            bullets.add(bullet);
            System.out.println(bullets.size());
            new Thread(bullet).start();
        }
    }

}
