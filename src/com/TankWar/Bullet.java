package com.TankWar;

import java.awt.*;

public class Bullet implements Runnable{
    int x;
    int y;
    Tank.Direction direction;
    boolean isAlive;

    int speed = 2;

    int type;
    PlayerTank playerTank = null;

    public Bullet(int x, int y, Tank.Direction direction,int type) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isAlive = true;
        this.type = type;
    }

    public Bullet(int x, int y, Tank.Direction direction,int type,PlayerTank playerTank) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isAlive = true;
        this.type = type;
        this.playerTank = playerTank;
    }

    @Override
    public void run() {
        while(isAlive)
        {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Move();
            if(!(x <= 1000 && x >= 0 && y <= 750 && y >= 0)) {
                if(playerTank != null)
                {
                    playerTank.bullets.remove(this);
                }
                isAlive = false;
            }
        }
    }

    void Move()
    {
        switch (direction) {
            case up -> y -= speed;
            case down -> y += speed;
            case right -> x += speed;
            case left -> x -= speed;
        }
    }

    public void paintBullet(Graphics g)
    {
        if(isAlive)
        {
            switch (type) {
                case 0 -> g.setColor(Color.cyan);
                case 1 -> g.setColor(Color.orange);
            }
            g.fillOval(x,y,2,2);
        }
    }

    public boolean isHit(Tank tank,GamePanel gp) {
        if(isAlive && tank.isAlive())
        {
            switch (tank.getDirection()) {
                case up:
                case down:
                    if (x > tank.getX() && x < tank.getX() + tank.getBox()[0]
                            && y > tank.getY() && y < tank.getY() + tank.getBox()[1]) {
                        if(playerTank != null)
                        {
                            playerTank.bullets.remove(this);
                        }
                        isAlive = false;
                        tank.setAlive(false);
                        Bomb bomb = new Bomb(tank.getX(), tank.getY() + (tank.getWheel()[1] -tank.getBody()[1]) / 2);
                        Thread thread = new Thread(bomb);
                        gp.getBombs().add(bomb);
                        thread.start();
                        return true;
                    }
                    break;
                case right:
                case left:
                    if (x > tank.getX() && x < tank.getX() + tank.getBox()[1]
                            && y > tank.getY() && y < tank.getY() + tank.getBox()[0]) {
                        if(playerTank != null)
                        {
                            playerTank.bullets.remove(this);
                        }
                        isAlive = false;
                        tank.setAlive(false);
                        Bomb bomb = new Bomb(tank.getX() + (tank.getWheel()[1] -tank.getBody()[1]) / 2, tank.getY());
                        Thread thread = new Thread(bomb);
                        gp.getBombs().add(bomb);
                        thread.start();
                        return true;
                    }
                    break;
            }
        }
        return  false;
    }
}
