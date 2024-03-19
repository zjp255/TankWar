import com.TankWar.GameFrame;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        System.out.println("1.新游戏   2.继续游戏");
        String key = scanner.next();
        GameFrame gameFrame = new GameFrame(key);
    }
}