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
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize * 25;
        gp.obj[0].worldY = gp.tileSize * 12;

        gp.obj[1] = new OBJ_Chest(gp);
        gp.obj[1].worldX = gp.tileSize * 18;
        gp.obj[1].worldY = gp.tileSize;

    }

    public void setNPC() {
        NPCFactory npc_f = new NPCFactory();
        gp.npc[0] = npc_f.getNPC(gp, "boschete");
        gp.npc[0].worldX = gp.tileSize * 85;
        gp.npc[0].worldY = gp.tileSize * 12;

        gp.npc[1] = npc_f.getNPC(gp, "floria");
        gp.npc[1].worldX = gp.tileSize * 80;
        gp.npc[1].worldY = gp.tileSize * 15;
    }

    public void setMonster() {
        int i = 0;
        gp.monster[i] = new MON_Snake(gp);
        gp.monster[i].worldX = gp.tileSize * 26; //26
        gp.monster[i].worldY = gp.tileSize * 14; //14
        i++;

        gp.monster[i] = new MON_Snake(gp);
        gp.monster[i].worldX = gp.tileSize * 26;
        gp.monster[i].worldY = gp.tileSize * 12;
        i++;

        gp.monster[i] = new MON_Snake(gp);
        gp.monster[i].worldX = gp.tileSize * 25;
        gp.monster[i].worldY = gp.tileSize * 11;
        i++;

        gp.monster[i] = new MON_Snake(gp);
        gp.monster[i].worldX = gp.tileSize * 28;
        gp.monster[i].worldY = gp.tileSize * 14;
        i++;
    }
}
