import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

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
        update();
    }

    /**
     * makes sure all the groups are up to date
     */
    private void update(){
        screenTilesWide = (int)(getWidth() / tileWidth) + 3;
        screenTilesTall = (int)(getHeight() / tileWidth) + 3;

        int topLeftX = Main.getPlayer().tileX - screenTilesWide / 2;
        int topLeftY = Main.getPlayer().tileY - screenTilesTall / 2;
        Tile[][] allTiles = Tile.getAllTiles();

        if (topLeftX >= 0 && topLeftY >= 0) {
            for(int i = topLeftY; i < topLeftY + screenTilesTall; i ++){
                for(int j = topLeftX; j < topLeftX + screenTilesWide; j ++){
                    //getChildren().add(new ImageView(allTiles[j][i].texture));
                }
            }
        }
    }

    public Group getScreen(){
        update();
        return mainGroup;
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
