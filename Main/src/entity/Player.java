package entity;

import main.GamePanel;
import main.KeyHandler;
import monster.MON_Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    private final Map<String, BufferedImage[]> imageCache = new HashMap<>();
    public final int screenX;
    public final int screenY;
    private BufferedImage[] upImages;
    private BufferedImage[] downImages;
    private BufferedImage[] rightImages;
    private BufferedImage[] leftImages;
    private BufferedImage[] idleImages;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(20, 14, 11, 13);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 82;
        worldY = gp.tileSize * 13;
        speed = 4;
        direction = "idle";

        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        upImages = setup("adventurer_run", 6);
        downImages = setup("adventurer_run", 6);
        leftImages = setup("adventurer_run_left", 6);
        rightImages = setup("adventurer_run", 6);
        idleImages = setup("adventurer_idle", 4);
        ctrlImages = setup("adventurer_crouch", 4);
    }

    public BufferedImage[] setup(String imageName, int numImages) {
        BufferedImage[] scaledImage = null;
        if (imageCache.containsKey(imageName)) {
            scaledImage = imageCache.get(imageName);
        } else {
            try {
                scaledImage = new BufferedImage[numImages];
                for (int i = 0; i < numImages; i++) {
                    String imageFileName = String.format("/player/%s_%d.png", imageName, i);
                    scaledImage[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageFileName)));
                }
                imageCache.put(imageName, scaledImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scaledImage;
    }

    public void update() {
        if (keyH.upPressed) {
            direction = "up";
        } else if (keyH.downPressed) {
            direction = "down";
        } else if (keyH.leftPressed) {
            direction = "left";
        } else if (keyH.rightPressed) {
            direction = "right";
        } else if (keyH.ctrlPressed) {
            direction = "ctrl";
        } else {
            direction = "idle";
        }

        //colisiuni
        collisionOn = false;
        gp.cChecker.checkTile(this);

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        interactNPC(gp.cChecker.getClosest(this, gp.npc));

        gp.eHandler.checkEvent();

        gp.keyH.ePressed = false;

        contactMonster(gp.cChecker.checkEntity(this, gp.monster));


        if (!collisionOn) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteCounter = 0;
            spriteNum++;
            if (spriteNum >= 6) {
                spriteNum = 0;
            }
        }
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(Entity npc) {
        if (npc.talkable && gp.keyH.ePressed) {
            int dx = npc.worldX - gp.player.worldX;
            int dy = npc.worldY - gp.player.worldY;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < 100) {
                gp.gameState = gp.dialogueState;
                npc.speak();
            }

        }
    }

    public void contactMonster(Entity monster) {
        if (!this.invincible && monster != null) {
            if (monster instanceof MON_Snake) {
                life -= 1;
                System.out.println("Ajung aci");
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
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
            case "ctrl" -> {
                if (ctrlImages.length > 0) {
                    image = ctrlImages[spriteNum % 4];
                }
            }
            case "idle", default -> {
                if (idleImages.length > 0) {
                    image = idleImages[spriteNum % 4];
                }
            }
        }
        if (image != null) {
            g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize); //TODO show player collision

            g2.setColor(new Color(0, 0, 0, 0));
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.white);
        g2.drawString("Invincible: " + invincibleCounter, 10, 400);
    }
}
