package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.swing.*;

public class TileManager{
    GamePanel gp;
    public Tile[] tile;
    public int[][][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[100];
        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/map01.txt", 0);
        loadMap("/maps/map02.txt", 1);
    }
    public void getTileImage() {
            setupBufferImage(0, "grass", false);
            setupBufferImage(1, "another_stone", false);
            setupGIF(2, "watergif", true);
            setupBufferImage(3, "earth", false);
            setupBufferImage(4, "sand1", false);
            setupBufferImage(5, "grass-with-tree", true);
            setupBufferImage(6, "o_casajoc", true);
            setupBufferImage(7, "doamne_ajuta (1)", true);
            setupBufferImage(8, "tree1", true);
            setupBufferImage(9, "tree2", true);
            setupBufferImage(10, "tree3", true);
            setupBufferImage(11, "tree4", true);
            setupBufferImage(12, "tree5", true);
            setupBufferImage(13, "tree6", true);
            setupBufferImage(14, "tree7", true);
            setupBufferImage(15, "bigtree", true);
            setupBufferImage(16, "wheat",false);
            setupBufferImage(17, "house2", true);
            setupBufferImage(18, "lamp", true);
            setupBufferImage(19, "bench", true);
            setupBufferImage(20, "bench_rotated",true);
            setupBufferImage(21, "bench_rotated2",true);
            setupBufferImage(22, "preview-2",true);
            setupBufferImage(23, "portal", false);
            setupBufferImage(24, "volcano_floor", false);
            setupBufferImage(25, "lava", false);
    }
    public void setupGIF(int index, String gifName, boolean collision){
        tile[index] = new Tile();
        tile[index].collision = collision;

        ImageIcon gifIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/tiles/" + gifName + ".gif")));
        tile[index].animationComponent = new JLabel(gifIcon);

        if(index == 23){
            int animationDelay = 100;
            Timer animationTimer = new Timer(animationDelay, e -> gifIcon.getImage().flush());
            animationTimer.start();
        }
        else {
            int animationDelay = 9999999;
            Timer animationTimer = new Timer(animationDelay, e -> gifIcon.getImage().flush());
            animationTimer.start();
        }
    }
    public void setupBufferImage(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName + ".png")));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception ignored) {}
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                if (tileNum == 2) {
                    if(tile[tileNum].animationComponent.isVisible()){
                        Icon animationIcon = tile[tileNum].animationComponent.getIcon();
                        if(animationIcon instanceof ImageIcon){
                            Image image = ((ImageIcon) animationIcon).getImage();
                            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                        }
                    }
                }
                if (tileNum == 17){
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize + 20, gp.tileSize + 20, null);
                } else {
                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }
            }
            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

