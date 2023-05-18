package main;

public class EventHandler {
    GamePanel gp;
    EventRect[][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 30;
            eventRect[col][row].y = 30;
            eventRect[col][row].width = 5;
            eventRect[col][row].height = 5;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if (canTouchEvent) {
            if (hit(93, 6, "down")) {
                damage(gp.dialogueState);
            }
            if (hit(96, 6, "idle")) {
                healingPool(gp.dialogueState);
            }
        }
    }

    private void damage(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Ouch!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public boolean hit(int eventCol, int eventRow, String regDirection) {
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[eventCol][eventRow].x = eventCol * gp.tileSize + eventRect[eventCol][eventRow].x;
        eventRect[eventCol][eventRow].y = eventRow * gp.tileSize + eventRect[eventCol][eventRow].y;
        if (gp.player.solidArea.intersects(eventRect[eventCol][eventRow])) {
            if (gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")) {
                hit = true;
                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].eventRectDefaultX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].eventRectDefaultY;
        return hit;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.ePressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You pressed E (EA sports)";
            gp.player.life = gp.player.maxLife;
            gp.aSetter.setMonster();
        }
        gp.keyH.ePressed = false;
    }
}
