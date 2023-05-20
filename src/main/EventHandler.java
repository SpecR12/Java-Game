package main;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map  = 0;
        int col = 0;
        int row = 0;
        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 30;
            eventRect[map][col][row].y = 30;
            eventRect[map][col][row].width = 5;
            eventRect[map][col][row].height = 5;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if(row == gp.maxWorldRow){
                    row = 0;
                    map++;
                }
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
            if (hit(0,93, 6, "down")) {
                damage(gp.dialogueState);
            }
            else if (hit(0,96, 6, "idle")) {
                healingPool(gp.dialogueState);
            }
            else if(hit(0, 88, 21, "any")){
                teleport(1, 3, 2);
            }
            else if(hit(1, 3, 2, "any")){
                teleport(0, 88, 21);
            }
            else if(hit(1, 4, 2, "any")){
                healingPool(gp.dialogueState);
            }
            else if(hit(1, 9, 2, "any")){
                teleport(2,3,4);
            }
            else if(hit(2, 3, 4, "any")){
                teleport(1,9,2);
            }
        }
    }

    private void teleport(int map, int col, int row) {
        gp.currentMap = map;
        gp.player.worldX = gp.tileSize * col;
        gp.player.worldY = gp.tileSize * row;
        previousEventX = gp.player.worldX;
        previousEventY = gp.player.worldY;
        canTouchEvent = false;
    }

    private void damage(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Ouch!";
        gp.player.life -= 1;
        canTouchEvent = false;
    }

    public boolean hit(int map, int eventCol, int eventRow, String regDirection) {
        boolean hit = false;
        if(map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][eventCol][eventRow].x = eventCol * gp.tileSize + eventRect[map][eventCol][eventRow].x;
            eventRect[map][eventCol][eventRow].y = eventRow * gp.tileSize + eventRect[map][eventCol][eventRow].y;
            if (gp.player.solidArea.intersects(eventRect[map][eventCol][eventRow])) {
                if (gp.player.direction.contentEquals(regDirection) || regDirection.contentEquals("any")) {
                    hit = true;
                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }
            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].eventRectDefaultX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].eventRectDefaultY;
        }
        return hit;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.ePressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You are fresh again.";
            gp.player.life = gp.player.maxLife;
            gp.aSetter.setMonster();
        }
        gp.keyH.ePressed = false;
    }
}
