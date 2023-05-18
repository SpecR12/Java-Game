package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Sword extends SuperObject{
    GamePanel gp;
    public OBJ_Sword(GamePanel gp){
        this.gp = gp;
        name = "Sword";
        try{
            imageSword = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/sword_normal.png")));
            uTool.scaleImage(imageSword, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        attackValue = 1;
    }
}
