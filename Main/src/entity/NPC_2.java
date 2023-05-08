package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_2 extends Entity{
    public NPC_2(GamePanel gp) {
        super(gp);
        direction = "idle";
        speed = 1;
        talkable = true;
        getNPCImage();
        setDialogue();
    }
    public void getNPCImage() {
        upImages = setup("floria_up", 2,false);
        downImages = setup("floria_down", 2,false);
        leftImages = setup("floria_left", 2,false);
        rightImages = setup("floria_right", 2,false);
        idleImages = setup("floria_idle", 1,false);
    }
    public void setDialogue(){
        dialogues[0] = "Hello, adventurer!";
        dialogues[1] = "I guess you need my healing abilities...";
        dialogues[2] = "Well...I guess i could look at your injuries...";
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
            else {
                direction = "idle";
            }
            actionLockCounter = 0;
        }
    }
    public void speak(){
        super.speak();
    }
}
