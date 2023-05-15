package monster;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class MON_Snake extends Entity {
    public MON_Snake(GamePanel gp) {
        super(gp);
        name = "Green Snake";
        speed = 2;
        direction = "up";
        talkable = false;
        maxLife = 4;
        life = maxLife;
        getSnakeImage();

        solidArea = new Rectangle(0, 0, 60, 60);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
    public void getSnakeImage(){
        upImages = setup("snake_down", 4, true);
        downImages = setup("snake_up", 4, true);
        leftImages = setup("snake_right", 4, true);
        rightImages = setup("snake_left", 4, true);
    }
    public void setAction(){
        actionLockCounter++;
        if(actionLockCounter == 120){
            Random random = new Random();
            int i = random.nextInt(125) + 1;
            if(i <= 25){
                direction = "up";
            }
            else if(i <= 50){
                direction = "down";
            }
            else if(i <= 75)
            {
                direction = "left";
            }
            else if(i <= 100){
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
    public void damageReaction(){
        actionLockCounter = 0;
        direction = gp.player.direction;
    }
}
