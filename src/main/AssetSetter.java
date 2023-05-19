package main;

import entity.NPCFactory;
import monster.MON_Snake;
import object.OBJ_Chest;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum  = 0;
        gp.obj[mapNum][0] = new OBJ_Key(gp);
        gp.obj[mapNum][0].worldX = gp.tileSize * 25;
        gp.obj[mapNum][0].worldY = gp.tileSize * 12;

        gp.obj[mapNum][1] = new OBJ_Chest(gp);
        gp.obj[mapNum][1].worldX = gp.tileSize * 18;
        gp.obj[mapNum][1].worldY = gp.tileSize;

    }

    public void setNPC() {
        int mapNum  = 0;
        NPCFactory npc_f = new NPCFactory();
        gp.npc[mapNum][0] = npc_f.getNPC(gp, "boschete");
        gp.npc[mapNum][0].worldX = gp.tileSize * 85;
        gp.npc[mapNum][0].worldY = gp.tileSize * 12;

        gp.npc[mapNum][1] = npc_f.getNPC(gp, "floria");
        gp.npc[mapNum][1].worldX = gp.tileSize * 80;
        gp.npc[mapNum][1].worldY = gp.tileSize * 15;
    }

    public void setMonster() {
        int i = 0;
        int mapNum  = 0;
        gp.monster[mapNum][i] = new MON_Snake(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26; //26
        gp.monster[mapNum][i].worldY = gp.tileSize * 14; //14
        i++;

        gp.monster[mapNum][i] = new MON_Snake(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 26;
        gp.monster[mapNum][i].worldY = gp.tileSize * 12;
        i++;

        gp.monster[mapNum][i] = new MON_Snake(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 25;
        gp.monster[mapNum][i].worldY = gp.tileSize * 11;
        i++;

        gp.monster[mapNum][i] = new MON_Snake(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize * 28;
        gp.monster[mapNum][i].worldY = gp.tileSize * 14;
        i++;
    }
}
