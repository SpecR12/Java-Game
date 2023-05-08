package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends SuperObject{
    GamePanel gp;
    public OBJ_Chest(GamePanel gp){
        this.gp = gp;
        name = "Chest";
        try{
            imageChest = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            uTool.scaleImage(imageChest, gp.tileSizeChest, gp.tileSizeChest);

        } catch (IOException e){
            e.printStackTrace();
        }
        solidArea.x = 10;
        solidArea.y = 10;
        collision = true;
    }
}
