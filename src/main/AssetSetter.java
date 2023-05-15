package main;

import entity.NPCFactory;
import monster.MON_Snake;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

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
        gp.monster[0] = new MON_Snake(gp);
        gp.monster[0].worldX = gp.tileSize * 26; //26
        gp.monster[0].worldY = gp.tileSize * 14; //14

        gp.monster[1] = new MON_Snake(gp);
        gp.monster[1].worldX = gp.tileSize * 26;
        gp.monster[1].worldY = gp.tileSize * 12;

        gp.monster[2] = new MON_Snake(gp);
        gp.monster[2].worldX = gp.tileSize * 25;
        gp.monster[2].worldY = gp.tileSize * 11;

        gp.monster[3] = new MON_Snake(gp);
        gp.monster[3].worldX = gp.tileSize * 82;
        gp.monster[3].worldY = gp.tileSize * 16;
    }
}
