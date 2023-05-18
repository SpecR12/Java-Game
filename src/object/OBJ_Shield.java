package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Shield extends SuperObject {
    GamePanel gp;
    public OBJ_Shield(GamePanel gp){
        this.gp = gp;
        name = "Shield";
        try{
            imageShield = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/shield4.png")));
            uTool.scaleImage(imageShield, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        defenceValue = 1;
    }
}
