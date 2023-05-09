package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Entity {
    GamePanel gp;
    private final Map<String, BufferedImage[]> imageCache = new HashMap<>();
    public int worldX, worldY;
    public int speed;
    public BufferedImage[] upImages;
    public BufferedImage[] downImages;
    public BufferedImage[] rightImages;
    public BufferedImage[] leftImages;
    public BufferedImage[] idleImages;
    public BufferedImage[] ctrlImages;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int spriteNumIdle = 0;
    public int spriteNumForSnake = 0;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int actionLockCounter = 0;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    String[] dialogues = new String[20];
    int dialogueIndex = 0;
    public int maxLife;
    public int life;
    public boolean talkable;
    public String name;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {
    }

    public void speak() {
        if (talkable) {
            if (dialogues[dialogueIndex] == null) {
                dialogueIndex = 0;
            }
            gp.ui.currentDialogue = dialogues[dialogueIndex];
            dialogueIndex++;
            switch (gp.player.direction) {
                case "up" -> direction = "down";
                case "down" -> direction = "up";
                case "left" -> direction = "right";
                case "right" -> direction = "left";
            }
        }
    }

    public void update() {
        setAction();
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);
        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        //character principal
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteCounter = 0;
            spriteNum++;
            //npc
            if (spriteNum >= 2) {
                spriteNum = 0;
            }
            //snake
            spriteNumForSnake++;
            if (spriteNumForSnake >= 4) {
                spriteNumForSnake = 0;
            }
        }
    }

    public BufferedImage[] setup(String imageName, int numImages, boolean isMonster) {
        BufferedImage[] scaledImage = null;
        if (imageCache.containsKey(imageName)) {
            scaledImage = imageCache.get(imageName);
        } else {
            try {
                scaledImage = new BufferedImage[numImages];
                for (int i = 0; i < numImages; i++) {
                    String imageFolder = isMonster ? "/monster/" : "/npc/";
                    String imageFileName = String.format("%s%s_%d.png", imageFolder, imageName, i);
                    InputStream inputStream = getClass().getResourceAsStream(imageFileName);
                    if (inputStream == null) {
                        throw new IOException("Could not find image file: " + imageFileName);
                    }
                    scaledImage[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageFileName)));
                }
                imageCache.put(imageName, scaledImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scaledImage;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            BufferedImage image = null;
            switch (direction) {
                case "up" -> {
                    if (upImages.length > 0) {
                        image = upImages[spriteNum];
                    }
                }
                case "down" -> {
                    if (downImages.length > 0) {
                        image = downImages[spriteNum];
                    }
                }
                case "left" -> {
                    if (leftImages.length > 0) {
                        image = leftImages[spriteNum];
                    }
                }
                case "right" -> {
                    if (rightImages.length > 0) {
                        image = rightImages[spriteNum];
                    }
                }
                case "idle", default -> {
                    if (idleImages.length > 0) {
                        image = idleImages[spriteNumIdle % 4];
                    }
                }
            }
            g2.drawRect(screenX, screenY, gp.tileSize - 30, gp.tileSize - 30); //TODO show entity collision
            g2.drawImage(image, screenX, screenY, gp.tileSize - 30, gp.tileSize - 30, null);
        }
    }
}
