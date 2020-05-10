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
    private int screenCenterX;
    private int screenCenterY;
    private Group mainGroup = new Group();
    private static Player player = Main.getPlayer();

    private ArrayList<Group> tiles = new ArrayList<>();

    public Camera(){
    }

    /**
     * Updates the children list to have everything that needs to be displayed at any given moment.
     */
    public void update(){
        getChildren().clear();

        findScreenCenter();

        screenTilesWide = (int)(getWidth() / Tile.width + 4);
        screenTilesTall = (int)(getHeight() / Tile.width + 4);

        int screenCenterTileX = screenCenterX / Tile.width;
        int screenCenterTileY = screenCenterY / Tile.width;
        int topLeftX = screenCenterTileX - screenTilesWide / 2;
        int topLeftY = screenCenterTileY - screenTilesTall / 2;
        Tile[][] allTiles = Tile.getAllTiles();

        for(int i = topLeftY; i < topLeftY + screenTilesTall; i ++){
            for(int j = topLeftX; j < topLeftX + screenTilesWide; j ++){
                int[] cords = shift(allTiles[i][j].posX, allTiles[i][j].posY);

                if(allTiles[i][j].isTextured) {
                    //getChildren().add(new ImageView(allTiles[i][j].texture));
                }else{
                    Rectangle rect = new Rectangle(cords[0], cords[1], Tile.width, Tile.width);

                    //Color tileCol = (Color) allTiles[i][j].getBoundsBox().getFill();
                    rect.setFill(allTiles[i][j].backColor);
                    rect.setStrokeWidth(3);
                    rect.setStroke(Color.BLACK);
                    getChildren().add(rect);
                }
            }
        }
        //getChildren().add(new Rectangle(getWidth()/2 - Main.getPlayer().boundsBox.getWidth()/2, getHeight()/2 - Main.getPlayer().boundsBox.getHeight()/2, Main.getPlayer().boundsBox.getWidth(), Main.getPlayer().boundsBox.getHeight()));

        int[] playerCoords = shift((int)player.posX, (int)player.posY);

        Rectangle playerRect = new Rectangle(playerCoords[0] - player.boundsBox.getWidth()/2, playerCoords[1] - player.boundsBox.getHeight() / 2, player.boundsBox.getWidth(), player.boundsBox.getHeight());
        playerRect.setFill(Color.BLACK);
        getChildren().add(playerRect);
    }

    /**
     * Shifts a set of coordinates from where they are in the game to where they would
     * appear on the pane if drawn.
     *
     * Usage assumptions:
     * Screen center variable is up to date
     * (use: findScreenCenter();)
     *
     * Explanation of the problem:
     * since the top left corner of the pane is 0, 0 you cannot draw everything where
     * it is in the game. For example if you were standing at 12,498, 3256 and your
     * window was 2000 by 2000 pixels 0, 0 on the pane would be 11,498, 2256 (half the
     * screen width up and to the left of your position). If things appeared at their
     * in game coordinates you would not see everything around you and you would always
     * see what is at 0, 0 to 2000, 2000.
     *
     * Solution:
     * If you shift everything's game coordinates by taking away whatever the center of
     * the screen's coordinates should be (which is almost always where the player is)
     * you will find that everything that should be on the bottom right corner is now in
     * the middle of the screen, the whole screen would be shifted half its length up
     * and to the left. (Example player is at 1000, 1000 tile is at 500, 500 screen is
     * 1000 wide this tile should be displayed at 0, 0 but will be at -500, -500 which is
     * off the by 500 or half the screen length). So the simple solution is to add half
     * the screens width and height after subtracting the screens center.
     */
    private int[] shift(int x, int y){
        int px = (int)Math.round(Main.getPlayer().getPosX());
        int py = (int)Math.round(Main.getPlayer().getPosY());
        int halfWidth = (int)getWidth() / 2;
        int halfHeight = (int)getHeight() / 2;

        x = x - screenCenterX + halfWidth;
        y = y - screenCenterY + halfHeight;

        return new int[] {x, y};
    }

    /**
     * Updates the screenCenter variable which many calculations are based on. Most of
     * the time the screen's center is where the player is standing but there are times
     * when the player is by a world border that you do not want the center of the
     * screen and the players position to be the same.
     */
    private void findScreenCenter(){
        screenCenterX = (int)Main.getPlayer().posX;
        screenCenterY = (int)Main.getPlayer().posY;
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