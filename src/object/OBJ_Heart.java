package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject{
    GamePanel gp;
    public OBJ_Heart(GamePanel gp){
        this.gp = gp;
        name = "Heart";
        try{
            imageHeart[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/0.png")));
            imageHeart[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/1.png")));
            imageHeart[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/2.png")));
            for(int i = 0; i < imageHeart.length; i++){
                imageHeart[i] = uTool.scaleImage(imageHeart[i], gp.tileSizeHeart, gp.tileSizeHeart);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        solidArea.x = 5;
        solidArea.y = 5;
    }
}
