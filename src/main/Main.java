package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Harvestvale Hero");

       //GameManager gameManager = new GameManager();
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
       //gameManager.run(gamePanel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
        //gameManager.close();
    }
}