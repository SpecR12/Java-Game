package main;

import data_base.DataBase;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable {
    //screen settings
    final int originalTileSize = 18;
    public final int maxMap = 10;
    public int currentMap = 0;
    final int scale = 5;
    public final int tileSize = originalTileSize * scale;
    public final int tileSizeKey = 40;
    public final int tileSizeChest = 85;
    public final int tileSizeHeart = 50;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    double FPS = 60;
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this, keyH, mouseH);
    public SuperObject[][] obj = new SuperObject[maxMap][10];
    public Entity[][] npc = new Entity[maxMap][10];
    public Entity[][] monster = new Entity[maxMap][20];
    ArrayList<Entity> entityListPlayer = new ArrayList<>();
    ArrayList<Entity> entityListNPC = new ArrayList<>();
    ArrayList<Entity> entityListMonsters = new ArrayList<>();
    public AssetSetter aSetter = new AssetSetter(this);
    //Game State
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            player.update();
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive && !monster[currentMap][i].dying) {
                        monster[currentMap][i].update();
                    }
                    if (!monster[currentMap][i].alive) {
                        entityListMonsters.remove(monster[currentMap][i]);
                    }
                }
            }
        }
        if (gameState == pauseState) {
            DataBase loadGame = new DataBase("Harvestvale Hero", "Alex", player.life, player.level, player.strength, player.dexterity, player.attackPlayer, player.defence, player.exp, player.coin);
            loadGame.loadAndStoreData();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        long drawStart = 0;
        //int playerY = player.worldY;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    obj[currentMap][i].draw(g2, this);
                }
            }
            //add npc to list
            entityListPlayer.add(player);
            //sort player
            entityListPlayer.sort(Comparator.comparingInt(e -> e.worldY));
            for (Entity entity : entityListPlayer) {
                entity.draw(g2);
            }
            for(int i = 0; i < entityListPlayer.size(); i++){
                entityListPlayer.remove(i);
            }
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityListNPC.add(npc[currentMap][i]);
                }
            }
            //sort npc
            entityListNPC.sort(Comparator.comparingInt(e -> e.worldY));
            //draw npc
            for (Entity entity : entityListNPC) {
                entity.draw(g2);
            }
            for(int i = 0; i < entityListNPC.size(); i++){
                entityListNPC.remove(i);
            }
            //add monster to list
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityListMonsters.add(monster[currentMap][i]);
                }
            }
            //sort
            entityListMonsters.sort(Comparator.comparingInt(e -> e.worldY));
            //draw monsters
            for(Entity entity : entityListMonsters){
                entity.draw(g2);
            }
            for(int i = 0; i < entityListMonsters.size(); i++){
                entityListMonsters.remove(i);
            }
            ui.draw(g2);
        }
        if (keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }
        g2.dispose();
    }


    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
