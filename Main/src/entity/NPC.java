package entity;

import main.GamePanel;

import java.util.Random;

public class NPC extends Entity {
    public NPC(GamePanel gp) {
        super(gp);
        direction = "idle";
        speed = 1;
        talkable = true;

    }

    public void getNPCImage(String file_prefix) {
        upImages = setup(file_prefix + "_up", 2, false);
        downImages = setup(file_prefix + "_down", 2, false);
        leftImages = setup(file_prefix + "_left", 2, false);
        rightImages = setup(file_prefix + "_right", 2, false);
        idleImages = setup(file_prefix + "_idle", 1, false);
    }


    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(125) + 1;
            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else if (i <= 100) {
                direction = "right";
            } else {
                direction = "idle";
            }
            actionLockCounter = 0;
        }
    }

    public void speak() {
        super.speak();
    }
}
