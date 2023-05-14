package main;


import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public boolean isActive = false;
    public int commandNumber = 0;
    public int titleScreenState = 0;
    BufferedImage heart_full, heart_half, heart_empty;
    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Dubai", Font.PLAIN, 40);
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.imageHeart[0];
        heart_half = heart.imageHeart[1];
        heart_empty = heart.imageHeart[2];
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void StartDialogue(){
        isActive = true;
    }
    public void EndDialogue(){
        isActive = false;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        if(gp.gameState == gp.playState){
            drawPlayerLife();
        }
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }
    }

    private void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        while(i < gp.player.maxLife / 2){
            g2.drawImage(heart_empty,x,y,null);
            i++;
            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        while(i < gp.player.life){
            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }
    }
    private void drawTitleScreen() {
        if(titleScreenState == 0){
        g2.setColor(new Color(0,102,0));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC,96F));
        String text = "Harvestvale Hero";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
        g2.setColor(Color.BLACK);
        g2.drawString(text,x+5,y+5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        text = "New Game";
        x = getXforCenteredText(text);
        y = gp.tileSize * 6;
        g2.drawString(text,x,y);
        if(commandNumber == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNumber == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }


        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNumber == 2) {
            g2.drawString(">", x - gp.tileSize, y);
            }
        }
        else if (titleScreenState == 1){
            g2.setColor(new Color(0,102,0));
            g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(42F));
            g2.setColor(Color.white);
            String text = "Short Tutorial";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;
            g2.drawString(text,x,y);

            text = "W,A,S,D - walk";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);

            text = "MOUSE1 - attack";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);

            text = "MOUSE2 - defend";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);

            text = "E - interact";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);

            text = "I - open inventory";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);

            text = "Start Game";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text,x,y);
            if(commandNumber == 0){
                g2.drawString(">",x-gp.tileSize,y);
            }
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if(commandNumber == 1){
                g2.drawString(">",x-gp.tileSize,y);
            }
        }
    }
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "Paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }
    public void drawDialogueScreen(){
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x,y,width,height);
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 35F));
        x += gp.tileSize;
        y += gp.tileSize;
        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0,0,0,225);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,width-10,height-10,25,25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
