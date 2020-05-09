import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * This class contains a hierarchy of groups that are all laid on top of each other to
 * form a screen view.
 */
public class Camera extends Pane{
    private static int tileWidth = 16 * 4;
    private int screenTilesTall;
    private int screenTilesWide;
    private Group mainGroup = new Group();

    private ArrayList<Group> tiles = new ArrayList<>();

    public Camera(){
    }

    /**
     * makes sure all the groups are up to date
     */
    public void update(){
        getChildren().clear();

        screenTilesWide = (int)(getWidth() / tileWidth) + 3;
        screenTilesTall = (int)(getHeight() / tileWidth) + 3;

        Player p = Main.getPlayer();

        int topLeftX = p.tileX - screenTilesWide / 2;
        int topLeftY = p.tileY - screenTilesTall / 2;
        Tile[][] allTiles = Tile.getAllTiles();

        // The offset is how far the player is away from the top left corner of the tile.
        // This is used for shifting all tiles that far so that the player will not always on the top left corner of the tile.
        int xOffset = (int) Math.round(p.getPosX() % tileWidth);
        int yOffset = (int) Math.round(p.getPosY() % tileWidth);

        if (topLeftX >= 0 && topLeftY >= 0) {
            for(int i = topLeftY; i < topLeftY + screenTilesTall; i ++){
                for(int j = topLeftX; j < topLeftX + screenTilesWide; j ++){
                    if(allTiles[j][i].isTextured) {
                        getChildren().add(new ImageView(allTiles[j][i].texture));
                    }else{
                        Color tileCol = (Color) allTiles[j][i].getBoundsBox().getFill();
                        int x = xOffset + j * tileWidth;
                        int y = yOffset + i * tileWidth;
                        Rectangle rect = new Rectangle(x, y, tileWidth, tileWidth);
                        rect.setFill(tileCol);
                        getChildren().add(rect);
                    }
                }
            }
        }
    }

    public void renderVisibleTiles(GraphicsContext g){

        //TODO: replace 10 with calculated value

        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                // visibleTiles[j][i].render(g);
            }
        }
    }
}
