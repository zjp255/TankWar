package com.TankWar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener ,Runnable{
    private  PlayerTank playerTank;
    private Vector<EnemyTank> enemyTanks;
    private Vector<Tank> allTanks;

    private InfoPanel infoPanel;

    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    public Vector<Tank> getAllTanks() {
        return allTanks;
    }

    public Vector<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

    private Vector<Bomb> bombs;

    public Vector<Bomb> getBombs() {
        return bombs;
    }

    private  int enemyTankSize = 3;

    private static GamePanel instance = null;

    public GamePanel(String key)
    {
        infoPanel = new InfoPanel();
        infoPanel.reedInfo();
        this.playerTank = new PlayerTank(20,100);
        enemyTanks = new Vector<>();
        allTanks = new Vector<>();
        allTanks.add(playerTank);
        Vector<InfoNode> infoNodes = infoPanel.getInfoNodes();
        for (int i = 0; i < infoNodes.size(); i++)
        {
            InfoNode infoNode = infoNodes.get(i);
            EnemyTank enemyTank = new EnemyTank(infoNode.x, infoNode.y, infoNode.direction);
            Thread thread = new Thread(enemyTank);
            thread.start();
            this.enemyTanks.add(enemyTank);
            allTanks.add(enemyTank);

        }
        Thread repaintT = new Thread(this);
        repaintT.start();
        bombs  = new Vector<>();
        if(instance == null)
            instance = this;
    }
    public GamePanel() {
        infoPanel = new InfoPanel();
        infoPanel.reedInfo();
        this.playerTank = new PlayerTank(20,100);
        enemyTanks = new Vector<>();
        allTanks = new Vector<>();
        allTanks.add(playerTank);
        for (int i = 0; i < enemyTankSize; i++)
        {
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0, Tank.Direction.down);
            Thread thread = new Thread(enemyTank);
            thread.start();
            this.enemyTanks.add(enemyTank);
            allTanks.add(enemyTank);
        }
        Thread repaintT = new Thread(this);
        repaintT.start();
        bombs  = new Vector<>();
        if(instance == null)
            instance = this;
    }

    public static GamePanel getInstance() {
        return instance;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,1000,750);
        infoPanel.paint(g);
        paintTank(playerTank,g);
        for (int i = 0; i < enemyTanks.size(); i++)
        {
            paintTank(enemyTanks.get(i),g);
            enemyTanks.get(i).getBullet().paintBullet(g);
        }
        for(int i = 0; i < playerTank.bullets.size(); i++)
        {
            playerTank.bullets.get(i).paintBullet(g);
        }
//        if(playerTank.bullet != null)
//        {
//            playerTank.bullet.paintBullet(g);
//        }
        for (int i = 0; i < bombs.size(); i++)
        {
            if(!bombs.get(i).paint(g))
            {
                bombs.remove(i);
            }
        }
    }

    void paintTank(Tank tank,Graphics g)
    {
        if(!tank.isAlive())
            return;
        int x = tank.getX();
        int y = tank.getY();
        Tank.Direction direction = tank.getDirection();
        int scale = tank.getScale();

        switch(tank.getType())
        {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.orange);
        }

        switch (direction)
        {
            case up:
                g.fill3DRect(x,y,tank.getWheel()[0],tank.getWheel()[1],false);
                g.fill3DRect(x+30,y,tank.getWheel()[0],tank.getWheel()[1],false);
                g.fill3DRect(x+10,y+10,tank.getBody()[0],tank.getBody()[1],false);
                g.fillOval(x + 10,y + 20,tank.getCannon()[0],tank.getCannon()[1]);
                g.fill3DRect(x + 20,y + 0,tank.getBarrel()[0],tank.getBarrel()[1],false);
                break;
            case down:
                g.fill3DRect(x,y,tank.getWheel()[0],tank.getWheel()[1],false);
                g.fill3DRect(x+30,y,tank.getWheel()[0],tank.getWheel()[1],false);
                g.fill3DRect(x+10,y+10,tank.getBody()[0],tank.getBody()[1],false);
                g.fillOval(x + 10,y + 20,tank.getCannon()[0],tank.getCannon()[1]);
                g.fill3DRect(x + 20,y + 30,tank.getBarrel()[0],tank.getBarrel()[1],false);
                break;
            case left:
                g.fill3DRect(x,y,tank.getWheel()[1],tank.getWheel()[0],false);
                g.fill3DRect(x,y + 30,tank.getWheel()[1],tank.getWheel()[0],false);
                g.fill3DRect(x+10,y+10,tank.getBody()[1],tank.getBody()[0],false);
                g.fillOval(x + 20,y + 10,tank.getCannon()[1],tank.getCannon()[0]);
                g.fill3DRect(x + 0,y + 20,tank.getBarrel()[1],tank.getBarrel()[0],false);
                break;
            case right:
                g.fill3DRect(x,y,tank.getWheel()[1],tank.getWheel()[0],false);
                g.fill3DRect(x,y + 30,tank.getWheel()[1],tank.getWheel()[0],false);
                g.fill3DRect(x+10,y+10,tank.getBody()[1],tank.getBody()[0],false);
                g.fillOval(x + 20,y + 10,tank.getCannon()[1],tank.getCannon()[0]);
                g.fill3DRect(x + 30,y + 20,tank.getBarrel()[1],tank.getBarrel()[0],false);
                break;
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void tankMove(Tank tank,KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            if(tank.getDirection() == Tank.Direction.left || tank.getDirection() == Tank.Direction.right)
                tank.changeXYForDirection(false);
            tank.setDirection(Tank.Direction.up);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            if(tank.getDirection() == Tank.Direction.left || tank.getDirection() == Tank.Direction.right)
                tank.changeXYForDirection(false);
            tank.setDirection(Tank.Direction.down);
        }else if (e.getKeyCode() == KeyEvent.VK_A) {
            if(tank.getDirection() == Tank.Direction.up || tank.getDirection() == Tank.Direction.down)
                tank.changeXYForDirection(true);
            tank.setDirection(Tank.Direction.left);
        }else if (e.getKeyCode() == KeyEvent.VK_D) {
            if (tank.getDirection() == Tank.Direction.up || tank.getDirection() == Tank.Direction.down)
                tank.changeXYForDirection(true);
            tank.setDirection(Tank.Direction.right);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        tankMove(playerTank,e);
        playerTank.launchBullet(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < enemyTanks.size(); i++)
            {
                enemyTanks.get(i).launchBullet();
                if(enemyTanks.get(i).getBullet() != null)
                {
                    enemyTanks.get(i).getBullet().isHit(playerTank,this);
                }

                for(int j = 0; j < playerTank.bullets.size(); j++) {
                    if (playerTank.bullets.get(j).isHit(enemyTanks.get(i), this)) {
                        allTanks.remove(enemyTanks.get(i));
                        enemyTanks.remove(i);
                        InfoPanel.getInstance().addRuinCount();
                    }
                }

            }
            this.repaint();
        }
    }
}
