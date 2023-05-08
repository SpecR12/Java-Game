package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_1 extends Entity {
    public NPC_1(GamePanel gp) {
        super(gp);
        direction = "idle";
        speed = 1;
        talkable = true;
        getNPCImage();
        setDialogue();
    }
    public void getNPCImage() {
        upImages = setup("boschete_up", 2,false);
        downImages = setup("boschete_down", 2,false);
        leftImages = setup("boschete_left", 2,false);
        rightImages = setup("boschete_right", 2,false);
        idleImages = setup("boschete_idle", 1,false);
    }
    public void setDialogue(){
        dialogues[0] = "Hello, adventurer!";
        dialogues[1] = "Welcome to Harvestvale, the village with the most \nresources ever!";
        dialogues[2] = "Well...what it used to be...";
        dialogues[3] = "Now it's a ghost village, no one ever dares to come here.\n";
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
