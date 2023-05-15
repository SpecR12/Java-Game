package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import monster.MON_Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Player extends Entity {
    KeyHandler keyH;
    MouseHandler mouseH;
    private final Map<String, BufferedImage[]> imageCache = new HashMap<>();
    public int screenX;
    public int screenY;
    private BufferedImage[] upImages;
    private BufferedImage[] downImages;
    private BufferedImage[] rightImages;
    private BufferedImage[] leftImages;
    private BufferedImage[] idleImages;

    public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
        super(gp);
        this.keyH = keyH;
        this.mouseH = mouseH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle(gp.tileSize / 2 - 25, gp.tileSize - 80, 50, 80);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
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

    public void getPlayerAttackImage() {
        attack = setup("adventurer_attack2", 6);
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
        if (mouseH.mouseClicked) {
            setWalking(false);
            setAttacking(true);
            spriteNum = 1;
            spriteCounter = 0;
            mouseH.mouseClicked = false;
        }
        if (attacking) {
            if (spriteCounter == 26) {
                spriteNum = 1;
                setAttacking(false);
                setWalking(true);
            } else {
                attack();
            }
        } else {
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
        }
        if (keyH.ePressed) {
            interactNPC(gp.cChecker.getClosest(this, gp.npc));
        }
        //colisiuni
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkEntity(this, gp.npc);

        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        gp.eHandler.checkEvent();

        contactMonster(gp.cChecker.checkEntity(this, gp.monster));

        if (walking) {
            if (!collisionOn && !keyH.ePressed) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            gp.keyH.ePressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum >= 6) {
                    spriteNum = 0;
                }
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
    public void preparationAttack(){
        int currentWorldX = worldX;
        int currentWorldY = worldY;
        int solidAreaWidth = solidArea.width;
        int solidAreaHeight = solidArea.height;
        switch (direction){
            case "up" -> worldY -=attackArea.height;
            case "down" -> worldY += attackArea.height;
            case "left" -> worldX -= attackArea.width;
            case "right" -> worldX += attackArea.width;
        }
        solidArea.width = attackArea.width;
        solidArea.height = attackArea.height;

        Entity monster = gp.cChecker.checkEntity(this, gp.monster);
        damageMonster(monster);

        worldX = currentWorldX;
        worldY = currentWorldY;
        solidArea.width = solidAreaWidth;
        solidArea.height = solidAreaHeight;
    }

    private void damageMonster(Entity monster) {
        if(monster instanceof MON_Snake){
            if(!monster.invincible){
                monster.life -= 1;
                monster.invincible = true;
                monster.damageReaction();
                if(monster.life <= 0){
                    monster.dying = true;
                }
            }
        }

    }

    private void attack() {
        spriteCounter++;

        if (spriteCounter <= 6) {
            spriteNum = 0;
        }
        if (spriteCounter > 6 && spriteCounter <= 10) {
            spriteNum = 1;
            preparationAttack();
        }
        if (spriteCounter > 10 && spriteCounter <= 14) {
            spriteNum = 2;
            preparationAttack();
        }
        if (spriteCounter > 14 && spriteCounter <= 18) {
            spriteNum = 3;
            preparationAttack();
        }
        if (spriteCounter > 18 && spriteCounter <= 22) {
            spriteNum = 4;
            preparationAttack();
        }
        if (spriteCounter > 22 && spriteCounter <= 26) {
            spriteNum = 5;
            preparationAttack();
        }
        if (spriteCounter > 26) {
            spriteNum = 0;
            spriteCounter = 0;
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
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (upImages.length > 0) {
                        image = upImages[spriteNum];
                    }
                }
                if (attacking) {
                    if (attack.length > 0) {
                        image = attack[spriteNum];
                    }
                }
            }
            case "down" -> {
                if (!attacking) {
                    if (downImages.length > 0) {
                        image = downImages[spriteNum];
                    }
                }
                if (attacking) {
                    if (attack.length > 0) {
                        image = attack[spriteNum];
                    }
                }
            }
            case "left" -> {
                if (!attacking) {
                    if (leftImages.length > 0) {
                        image = leftImages[spriteNum];
                    }
                }
                if (attacking) {
                    if (downImages.length > 0) {
                        image = downImages[spriteNum];
                    }
                }
            }
            case "right" -> {
                if (!attacking) {
                    if (rightImages.length > 0) {
                        image = rightImages[spriteNum];
                    }
                }
                if (attacking) {
                    if (attack.length > 0) {
                        image = attack[spriteNum];
                    }
                }
            }
            case "ctrl" -> {
                if (!attacking) {
                    if (ctrlImages.length > 0) {
                        image = ctrlImages[spriteNum % 4];
                    }
                }
                if (attacking) {
                    if (attack.length > 0) {
                        image = attack[spriteNum];
                    }
                }
            }
            case "idle", default -> {
                if (!attacking) {
                    if (idleImages.length > 0) {
                        image = idleImages[spriteNum % 4];
                    }
                }
                if (attacking) {
                    if (attack.length > 0) {
                        image = attack[spriteNum];
                    }
                }
            }
        }
        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        if (image != null) {
            g2.drawRect(screenX + this.solidArea.x, screenY + this.solidArea.y, this.solidArea.width, this.solidArea.height); //TODO show player collision
            g2.setColor(new Color(0, 0, 0, 0));
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }
}
