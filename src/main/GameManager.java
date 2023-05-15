package main;

import data_base.DataBase;
import entity.Player;

public class GameManager {
    private final DataBase dataBase;

    public GameManager(){
        dataBase = new DataBase();
    }
    public void savePlayerData(Player player){
        dataBase.setPlayerHP(player.life);
        dataBase.setPlayerAttack(1);
    }
    public void loadPlayerData(GamePanel gp){
        int hp = dataBase.getPlayerHP();
        dataBase.getPlayerAttack();
        gp.player.getHP(hp);
    }
    public void run(GamePanel gp){
        loadPlayerData(gp);
        while (gp.player.life > 0){
            savePlayerData(gp.player);
        }
    }
    public void close() {
        dataBase.closeConnection();
    }
}
