package com.TankWar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;


public class Tank {
    private int x;
    private int y;

    private int speed = 4;
    private int type = 0;
    private Direction direction = Direction.right;

    private  int scale = 1;

    private  boolean isAlive;

    private int[] wheel = {10,60};
    private int[] body = {20,40};
    private int[] cannon ={20,20};
    private int[] barrel = {1,30};

    private int[] box = {40,60};

    public int[] getBox() {
        return box;
    }
    public int[] getBoxByDirection(){
        int[] temp = {0,0};
        switch (direction)
        {
            case up :
            case down:
                temp[0] = box[0];
                temp[1] = box[1];
                break;
            case right:
            case left:
                temp[0] = box[1];
                temp[1] = box[0];
                break;
        }
        return temp;
    }
    public Tank(int x, int y, int type, Direction direction) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.direction = direction;
        isAlive = true;
    }

    public Tank(int x, int y, Direction direction, int scale) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.scale = scale;
        isAlive = true;
    }

    public Tank(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        isAlive = true;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public int[] getWheel() {
        return wheel;
    }

    public int[] getBody() {
        return body;
    }

    public int[] getCannon() {
        return cannon;
    }

    public int[] getBarrel() {
        return barrel;
    }

    public enum Direction{
        up,
        down,
        left,
        right
    }

    public Direction getDirection() {
        return direction;
    }

    public void changeXYForDirection(boolean bool)
    {
        if(bool == true)
        {
            y += 10;
            x -= 10;
        }else {
            y -= 10;
            x += 10;
        }
    }
    public void move()
    {
            switch (direction) {
                case up:
                    y -= speed;
                    if(y < 0)
                    {
                        y = 0;
                    }
                    break;
                case down:
                    y += speed;
                    int temp = 750 - box[1];
                    if(y > temp)
                    {
                        y = temp;
                    }
                    break;
                case right:
                    x += speed;
                    int temp2 = 1000 - box[1];
                    if(x > temp2)
                    {
                        x = temp2;
                    }
                    break;
                case left:
                    x -= speed;
                    if(x < 0)
                    {
                        x = 0;
                    }
                    break;
            }

    }
    public void setDirection(Direction direction) {
        this.direction = direction;
        if(collisionDetection())
            move();
    }

    public boolean collisionDetection()
    {
        GamePanel instance = GamePanel.getInstance();
        Vector<Tank> tanks = instance.getAllTanks();
        int[] f1 = {0,0}, f2 = {0, 0};
        switch (direction)
        {
            case up -> {f1[0] = x; f1[1] = y;f2[0] = x + getBoxByDirection()[0];f2[1] = y;}
            case down -> {f1[0] = x; f1[1] = y + getBoxByDirection()[1];f2[0] = x + getBoxByDirection()[0];f2[1] = y + getBoxByDirection()[1];}
            case right -> {f1[0] = x + getBoxByDirection()[0]; f1[1] = y;f2[0] = x + getBoxByDirection()[0];f2[1] = y + getBoxByDirection()[1];}
            case left -> {f1[0] = x; f1[1] = y;f2[0] = x;f2[1] = y + getBoxByDirection()[1];}
        }
        for (int i = 0; i < tanks.size(); i++)
        {
            Tank tank = tanks.get(i);
            if(tank == this)
            {
                continue;
            }
            int[] tempBox = tank.getBoxByDirection();
            if((f1[0] >= tank.getX() && f1[0] <= tank.getX() + tempBox[0]
                    && f1[1] >= tank.getY() && f1[1] <= tank.getY() + tempBox[1]) ||
                    (f2[0] >= tank.getX() && f2[0] <= tank.getX() + tempBox[0]
                            && f2[1] >= tank.getY() && f2[1]<= tank.getY() + tempBox[1]) )
                return false;
        }
        return true;







//        Vector<EnemyTank> tanks = instance.getEnemyTanks();
//        for(int i = 0; i < tanks.size();i++)
//        {
//            EnemyTank  tank =  tanks.get(i);
//            if(tank == this)
//                continue;
//            int[] tempBox = tank.getBoxByDirection();
//            int tempX = 0,tempY = 0;
//            switch (direction)
//            {
//                case up -> {tempX = x +  }
//                case down -> {}
//                case left -> {}
//                case right -> {}
//            }
//            if((tempX >= tank.getX() && tempX <= tank.getX() + tempBox[0]
//            && tempY >= tank.getY() && tempY <= tank.getY() + tempBox[1]) ||
//                    (x >= tank.getX() && x <= tank.getX() + tempBox[0]
//                    && y >= tank.getY() && y<= tank.getY() + tempBox[1]) )
//                return false;
//        }
//        PlayerTank tank = instance.getPlayerTank();
//        if(tank != this) {
//            int tempX = x,tempY = y;
//            if(direction == Direction.down)
//            {
//                tempY = y + getBoxByDirection()[1];
//            }
//            if(direction == Direction.right)
//            {
//                tempX = x + getBoxByDirection()[0];
//            }
//            int[] tempBox = tank.getBoxByDirection();
//            if((tempX >= tank.getX() && tempX <= tank.getX() + tempBox[0]
//                    && tempY >= tank.getY() && tempY <= tank.getY() + tempBox[1]) ||
//                    (x >= tank.getX() && x <= tank.getX() + tempBox[0]
//                            && y >= tank.getY() && y<= tank.getY() + tempBox[1]) )
//                return false;
//        }
//        return true;
    }
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
