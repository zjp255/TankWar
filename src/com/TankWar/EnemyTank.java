package com.TankWar;

import java.util.Random;

public class EnemyTank extends Tank implements Runnable{
    private Bullet bullet = null;
    public EnemyTank(int x, int y, Direction direction) {
        super(x, y, 1, direction);
    }

    public Bullet getBullet() {
        return bullet;
    }
    public void launchBullet()
    {
        if(!isAlive())
        {
            return;
        }
        if(  bullet == null || !bullet.isAlive) {
            int x = 0, y = 0;
            switch (this.getDirection()) {
                case up -> {
                    x = this.getX() + this.getBox()[0] / 2;
                    y = this.getY();
                }
                case down -> {
                    x = this.getX() + this.getBox()[0] / 2;
                    y = this.getY() + this.getBox()[1];
                }
                case left -> {
                    x = this.getX();
                    y = this.getY() + this.getBox()[0] / 2;
                }
                case right -> {
                    x = this.getX() + this.getBox()[1];
                    y = this.getY() + this.getBox()[0] / 2;
                }
            }
            bullet = new Bullet(x, y, this.getDirection(),this.getType());
            new Thread(bullet).start();
        }
    }

    @Override
    public void run() {
        while (isAlive())
        {
            for (int i = 0; i < 30;i++)
            {
                if(collisionDetection())
                    move();
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            switch ((int) (Math.random() * 4))
            {
                case 0 -> setDirection(Direction.up);
                case 1 -> setDirection(Direction.down);
                case 2 -> setDirection(Direction.left);
                case 3 -> setDirection(Direction.right);
            }
        }
    }
}
