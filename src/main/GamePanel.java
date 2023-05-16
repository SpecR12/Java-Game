package main;

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
    public int index = 999;
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
    public SuperObject[] obj = new SuperObject[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
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
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
            for (Entity entity : monster) {
                if (entity != null) {
                    if (entity.alive && !entity.dying) {
                        entity.update();
                    }
                    if (!entity.alive) {
                        entityListMonsters.remove(entity);
                    }
                }
            }
        }
        if (gameState == pauseState) {
            //nothing
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
            for (SuperObject superObject : obj) {
                if (superObject != null) {
                    superObject.draw(g2, this);
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
            for (Entity value : npc) {
                if (value != null) {
                    entityListNPC.add(value);
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
            for (Entity entity : monster) {
                if (entity != null) {
                    entityListMonsters.add(entity);
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
