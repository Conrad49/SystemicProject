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

        screenTilesWide = (int)(getWidth() / Tile.width + 4);
        screenTilesTall = (int)(getHeight() / Tile.width + 4);

        Player p = Main.getPlayer();

        int topLeftX = p.tileX - screenTilesWide / 2;
        int topLeftY = p.tileY - screenTilesTall / 2;
        Tile[][] allTiles = Tile.getAllTiles();

        for(int i = topLeftY; i < topLeftY + screenTilesTall; i ++){
            for(int j = topLeftX; j < topLeftX + screenTilesWide; j ++){
                if(i < 0){
                    i = 0;
                }
                if(j < 0){
                    j = 0;
                }

                if(allTiles[i][j].isTextured) {
                    getChildren().add(new ImageView(allTiles[i][j].texture));
                }else{
                    Color tileCol = (Color) allTiles[i][j].getBoundsBox().getFill();
                    int[] cords = shift(allTiles[i][j].posX, allTiles[i][j].posY);
                    Rectangle rect = new Rectangle(cords[0], cords[1], Tile.width, Tile.width);

                    rect.setFill(tileCol);
                    rect.setStrokeWidth(5);
                    rect.setStroke(Color.BLACK);
                    getChildren().add(rect);
                }
            }
        }
    }

    public int[] shift(int x, int y){
        x = (int)(x - Math.round(Main.getPlayer().getPosX()) + getWidth() / 2);
        y = (int)(y - Math.round(Main.getPlayer().getPosY()) + getHeight() / 2);

        return new int[] {x, y};
    }

    public void renderVisibleTiles(GraphicsContext g){

        //TODO: replace 10 with calculated value

        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j++){
                // visibleTiles[i][j].render(g);
            }
        }
    }
}
