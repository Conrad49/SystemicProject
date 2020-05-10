import javafx.scene.Group;
import javafx.scene.Node;
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
    private int screenCenterTileX;
    private int oldScreenCenterX;
    private int oldScreenCenterTileX;
    private int changeTileX;

    private int screenCenterY;
    private int screenCenterTileY;
    private int oldScreenCenterY;
    private int oldScreenCenterTileY;
    private int changeTileY;

    private int topLeftX;
    private int topLeftY;
    private static Player player = Main.getPlayer();

    private Group mainGroup = new Group();
    private ArrayList<ArrayList<Node>> visibleTilesArray = new ArrayList<>();
    private Group visibleTiles = new Group();
    private Group entityGroup = new Group();

    public Camera(){
        mainGroup.getChildren().addAll(visibleTiles, entityGroup);
        getChildren().add(mainGroup);
    }

    /**
     * Determines which update method should be called (updateSome or updateAll) and calls it.
     * Uses findScreenCenter in the process so the screen center variables will be updated too.
     */
    public void update(){
        findScreenCenter();

        oldScreenCenterY = screenCenterY;
        oldScreenCenterX = screenCenterX;
        int oldScreenWide = screenTilesWide;
        int oldScreenTall = screenTilesTall;
        screenTilesWide = (int)(getWidth() / Tile.width + 4);
        screenTilesTall = (int)(getHeight() / Tile.width + 4);
        topLeftX = screenCenterTileX - screenTilesWide / 2;
        topLeftY = screenCenterTileY - screenTilesTall / 2;
        changeTileX = oldScreenCenterTileX - screenCenterTileX;
        changeTileY = oldScreenCenterTileY - screenCenterTileY;

        boolean sameCenterBlock = oldScreenCenterTileX == screenCenterX && oldScreenCenterTileY == screenCenterY;
        boolean adjacentCenterBlock = changeTileX < 1 && changeTileX > -1 && changeTileY < 1 && changeTileY > -1;
        boolean sameScreenSize = oldScreenTall == screenTilesTall && oldScreenWide == screenTilesWide;

        if(sameCenterBlock){
            updateCords();
        }

        if(adjacentCenterBlock && sameScreenSize){
            updateSome();
        }else{
            updateAll();
        }



        //Temporary position

        //getChildren().add(new Rectangle(getWidth()/2 - Main.getPlayer().boundsBox.getWidth()/2, getHeight()/2 - Main.getPlayer().boundsBox.getHeight()/2, Main.getPlayer().boundsBox.getWidth(), Main.getPlayer().boundsBox.getHeight()));

        entityGroup.getChildren().clear();

        int[] playerCoords = shift((int)player.posX, (int)player.posY);

        Rectangle playerRect = new Rectangle(playerCoords[0] - player.boundsBox.getWidth()/2, playerCoords[1] - player.boundsBox.getHeight() / 2, player.boundsBox.getWidth(), player.boundsBox.getHeight());
        playerRect.setFill(Color.BLACK);
        entityGroup.getChildren().add(playerRect);
    }

    /**
     * Clears and re-updates everything in all the groups. This should only be used
     * after teleport, startup, going into another dimension, down stairs, moving
     * multiple blocks at once, or any such action that would need more than one new
     * row of tiles shown.
     */
    private void updateAll(){
        visibleTiles.getChildren().clear();
        visibleTilesArray.clear();

        Tile[][] allTiles = Tile.getAllTiles();

        for(int i = topLeftY; i <= topLeftY + screenTilesTall; i ++){
            ArrayList<Node> visibleRow = new ArrayList<>();
            for(int j = topLeftX; j <= topLeftX + screenTilesWide; j ++){
                int[] cords = shift(allTiles[i][j].posX, allTiles[i][j].posY);

                    //getChildren().add(new ImageView(allTiles[i][j].texture));
                    if (allTiles[i][j].getTexture() != null) {
                        ImageView imageToAdd = new ImageView(allTiles[i][j].getTexture());
                        imageToAdd.setX(cords[0]);
                        imageToAdd.setY(cords[1]);

                        visibleRow.add(imageToAdd);
                    }else{
                    Rectangle rect = new Rectangle(cords[0], cords[1], Tile.width, Tile.width);

                    rect.setFill(allTiles[i][j].backColor);
                    rect.setStrokeWidth(3);
                    rect.setStroke(Color.BLACK);
                    visibleRow.add(rect);
                }
            }
            visibleTilesArray.add(visibleRow);
        }

        visibleTiles.getChildren().clear();
        for(ArrayList<Node> a : visibleTilesArray){
            for(Node n : a){
                visibleTiles.getChildren().add(n);
            }
        }
    }

    /**
     * This method is an even more optimized method that only updates the newly visible
     * row after moving off the tile you were on before. The previously stood on tile
     * will be compared to the current to find out how to change the groups properly.
     */
    private void updateSome(){
        Tile[][] allTiles = Tile.getAllTiles();

        int sideX = -1;
        if(changeTileX < 0){
            // left move
            sideX = topLeftX;
        }else if(changeTileX > 0){
            // right move
            sideX = topLeftX + screenTilesWide;
        }

        if(sideX != -1) {//will only be false if the change in x is 0

            for (int i = 0; i <= screenTilesTall; i++) {
                int[] cords = shift(allTiles[i][sideX].posX, allTiles[i][sideX].posY);
                if (allTiles[i][sideX].isTextured) {
                    //getChildren().add(new ImageView(allTiles[i][j].texture));
                } else {
                    Rectangle rect = new Rectangle(cords[0], cords[1], Tile.width, Tile.width);

                    rect.setFill(allTiles[i][sideX].backColor);
                    rect.setStrokeWidth(3);
                    rect.setStroke(Color.BLACK);

                    if(changeTileX < 0) {
                        // left move
                        visibleTilesArray.get(i).add(0, rect);
                        visibleTilesArray.get(i).remove(visibleTilesArray.get(i).size() - 1);
                    }else{
                        // right move
                        visibleTilesArray.get(i).add(rect);
                        visibleTilesArray.get(i).remove(0);
                    }
                }
            }
        }

        int sideY = -1;
        if(changeTileY < 0){
            // up move
            sideY = topLeftY;
        }else if(changeTileY > 0){
            // down move
            sideY = topLeftY + screenTilesTall;
        }

        if(sideY != -1) {//will only be false if the change in x is 0

            ArrayList<Node> newRow = new ArrayList<>();
            for (int i = 0; i <= screenTilesWide; i++) {
                int[] cords = shift(allTiles[sideY][i].posY, allTiles[sideY][i].posX);
                if (allTiles[sideY][i].isTextured) {
                    //getChildren().add(new ImageView(allTiles[i][j].texture));
                } else {
                    Rectangle rect = new Rectangle(cords[0], cords[1], Tile.width, Tile.width);

                    rect.setFill(allTiles[sideY][i].backColor);
                    rect.setStrokeWidth(3);
                    rect.setStroke(Color.BLACK);

                    newRow.add(rect);
                }
            }
            if(changeTileY < 0){
                // up
                visibleTilesArray.add(0, newRow);
                visibleTilesArray.remove(visibleTilesArray.size());
            }else{
                visibleTilesArray.remove(0);
                visibleTilesArray.add(newRow);
            }
        }

        updateCords();
    }

    private void updateCords(){
        int changeX = oldScreenCenterX - screenCenterX;
        int changeY = oldScreenCenterY - screenCenterY;

        for(ArrayList<Node> a : visibleTilesArray){
            for(Node n : a){
                // visibleTilesArray will only ever contain a rectangle or and image view
                if(n instanceof ImageView){

                }else{
                    Rectangle r = (Rectangle) n;
                    r.setX(r.getX() + changeX);
                    r.setY(r.getY() + changeY);
                }
            }
        }
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

        screenCenterTileX = screenCenterX / Tile.width;
        screenCenterTileY = screenCenterY / Tile.width;
    }
}