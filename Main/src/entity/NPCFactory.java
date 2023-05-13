package entity;

import main.GamePanel;

public class NPCFactory {
    public NPC getNPC(GamePanel gp, String npc_name) {
        NPC npc = new NPC(gp);
        npc.getNPCImage(npc_name);

        if (npc_name.equals("floria")) {
            setFloriaDialogue(npc);
        }
        else if (npc_name.equals("boschete")) {
            setBoscheteDialogue(npc);
        }

        return npc;
    }



    void setBoscheteDialogue(NPC npc) {
        npc.dialogues[0] = "Hello, adventurer!";
        npc.dialogues[1] = "Welcome to Harvestvale, the village with the most \nresources ever!";
        npc.dialogues[2] = "Well...what it used to be...";
        npc.dialogues[3] = "Now it's a ghost village, no one ever dares to come here.\n";
    }

    void setFloriaDialogue(NPC npc) {
        npc.dialogues[0] = "Hello, adventurer!";
        npc.dialogues[1] = "I guess you need my healing abilities...";
        npc.dialogues[2] = "Well...I guess i could look at your injuries...";
    }
}
