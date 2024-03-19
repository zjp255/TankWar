package com.TankWar;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class InfoPanel{
    private static InfoPanel instance = null;
    private final String filePath = "src\\info.txt";
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private Vector<InfoNode> infoNodes;
    int ruinEnemyTankNum = 0;

    public Vector<InfoNode> getInfoNodes() {
        return infoNodes;
    }

    public static InfoPanel getInstance() {
        return instance;
    }

    public InfoPanel() {
        if(instance == null)
        {
            instance = this;
        }
        infoNodes = new Vector<>();
    }
    public void reedInfo()
    {
        try {
            br = new BufferedReader(new FileReader(filePath));
            ruinEnemyTankNum = Integer.parseInt(br.readLine());
            String line = "";
            while((line = br.readLine()) != null)
            {
                String[] s = line.split(" ");
                Tank.Direction direction = Tank.Direction.valueOf(s[2]);
                InfoNode infoNode = new InfoNode(Integer.parseInt(s[0]),Integer.parseInt(s[1]),direction);
                infoNodes.add(infoNode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveInfo()
    {
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write("" + ruinEnemyTankNum);
            bw.newLine();
            Vector<EnemyTank> tanks = GamePanel.getInstance().getEnemyTanks();
            for(int i = 0; i < tanks.size(); i++)
            {
                Tank tank = tanks.get(i);
                bw.write(tank.getX() + " " + tank.getY() + " " + tank.getDirection());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(bw != null)
            {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void paint(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体",Font.PLAIN,25);
        g.setFont(font);
        g.drawString("击毁的坦克总数",1015,25);
        g.drawString("" +ruinEnemyTankNum,1100,75);
        GamePanel.getInstance().paintTank(new Tank(1015,40, 1,Tank.Direction.up),g);
    }

    public void addRuinCount()
    {
        ruinEnemyTankNum++;
    }
}
