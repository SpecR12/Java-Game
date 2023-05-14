package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Key extends SuperObject {
    GamePanel gp;
    public OBJ_Key(GamePanel gp){
        this.gp = gp;
        name = "key";
        //de facut mai multe tile-uri pentru keys
        try{
            imageKey = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/key.png")));
            uTool.scaleImage(imageKey, gp.tileSizeKey, gp.tileSizeKey);
        }catch (IOException e){
            e.printStackTrace();
        }
        solidArea.x = 5;
        solidArea.y = 5;
    }
}
