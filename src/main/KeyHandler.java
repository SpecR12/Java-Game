package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    private final GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, ctrlPressed, ePressed;
    boolean checkDrawTime = false;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(gp.gameState == gp.titleState){
             if(gp.ui.titleScreenState == 0) {
                 if (code == KeyEvent.VK_W) {
                     gp.ui.commandNumber--;
                     if (gp.ui.commandNumber < 0) {
                         gp.ui.commandNumber = 2;
                     }
                 }
                 if (code == KeyEvent.VK_S) {
                     gp.ui.commandNumber++;
                     if (gp.ui.commandNumber > 2) {
                         gp.ui.commandNumber = 0;
                     }
                 }
                 if (code == KeyEvent.VK_ENTER) {
                     if (gp.ui.commandNumber == 0) {
                         gp.ui.titleScreenState = 1;
                         gp.playMusic(0);
                         gp.stopMusic();
                     }
                     if (gp.ui.commandNumber == 1) {
                         //load game
                     }
                     if (gp.ui.commandNumber == 2) {
                         System.exit(0);
                     }
                 }
             }
             else if(gp.ui.titleScreenState == 1) {
                 if (code == KeyEvent.VK_W) {
                     gp.ui.commandNumber--;
                     if (gp.ui.commandNumber < 0) {
                         gp.ui.commandNumber = 1;
                     }
                 }
                 if (code == KeyEvent.VK_S) {
                     gp.ui.commandNumber++;
                     if (gp.ui.commandNumber > 1) {
                         gp.ui.commandNumber = 0;
                     }
                 }
                 if (code == KeyEvent.VK_ENTER) {
                     if (gp.ui.commandNumber == 0) {
                         gp.gameState = gp.playState;
                         gp.playMusic(0);
                     }
                     if (gp.ui.commandNumber == 1) {
                         gp.ui.titleScreenState = 0;
                     }
                 }
             }
         }
         if(gp.gameState == gp.playState) {
             if (code == KeyEvent.VK_W) {
                 upPressed = true;
             }
             if (code == KeyEvent.VK_S) {
                 downPressed = true;
             }
             if (code == KeyEvent.VK_A) {
                 leftPressed = true;
             }
             if (code == KeyEvent.VK_D) {
                 rightPressed = true;
             }
             if (code == KeyEvent.VK_CONTROL) {
                 ctrlPressed = true;
             }
             if (code == KeyEvent.VK_T) {
                 checkDrawTime = !checkDrawTime;
             }
             if (code == KeyEvent.VK_P) {
                 gp.gameState = gp.pauseState;
             }
             if(code == KeyEvent.VK_C){
                 gp.gameState = gp.characterState;
             }
             if(code == KeyEvent.VK_E){
                 ePressed = true;
             }
         }
         else if(gp.gameState == gp.pauseState){
             if(code == KeyEvent.VK_P){
                 gp.gameState = gp.playState;
             }
         }
         else if(gp.gameState == gp.dialogueState){
             if(code == KeyEvent.VK_E){
                 gp.gameState = gp.playState;
             }
         }
         else if(gp.gameState == gp.characterState){
             if(code == KeyEvent.VK_C){
                 gp.gameState = gp.playState;
             }
         }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_CONTROL){
            ctrlPressed = false;
        }
    }
}
