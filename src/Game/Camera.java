package Game;

import Game.Entities.Player;
import Game.Tiles.Tile;
import Game.displayablesHidingPlace.Displayable;
import Game.plants.Plant;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * This class contains a hierarchy of groups that are all laid on top of each other to
 * form a screen view.
 */
public class Camera extends Pane {
    private int screenTilesTall;
    private int screenTilesWide;
    private double oldScreenTall = 0;
    private double oldScreenWide = 0;

    private double screenCenterX;
    private int screenCenterTileX;
    private double oldScreenCenterX;
    private int oldScreenCenterTileX;
    private int changeTileX;

    private double screenCenterY;
    private int screenCenterTileY;
    private double oldScreenCenterY;
    private int oldScreenCenterTileY;
    private int changeTileY;

    private int topLeftX;
    private int topLeftY;
    private static Player player = Main.getPlayer();

    private Group mainGroup = new Group();
    private ArrayList<Node> entities = new ArrayList<>();
    private Group visibleTiles = new Group();
    private Group standingGroup = new Group();

    public Camera() {
        mainGroup.getChildren().addAll(visibleTiles, standingGroup);
        getChildren().add(mainGroup);
    }

    /**
     * Determines which update method should be called (updateSome or updateAll) and calls it.
     * Uses findScreenCenter in the process so the screen center variables will be updated too.
     */
    public void update() {
        updateVariables();

        boolean sameCenterBlock = oldScreenCenterTileX == screenCenterTileX && oldScreenCenterTileY == screenCenterTileY;
        boolean adjacentCenterBlock = changeTileX <= 1 && changeTileX >= -1 && changeTileY <= 1 && changeTileY >= -1;
        boolean sameScreenSize = (oldScreenTall == this.getHeight()) && (oldScreenWide == this.getWidth());
        boolean sameScreenCenter = screenCenterX == oldScreenCenterX && screenCenterY == oldScreenCenterY;

        oldScreenWide = this.getWidth();
        oldScreenTall = this.getHeight();

        if(!sameScreenSize){
            updateAll();
        }else if(sameCenterBlock){
            if(sameScreenCenter){

            }else {
                updateCords();
            }
        }else if(adjacentCenterBlock){
            updateSome();
        }else{
            updateAll();
        }

        // Since entities move around we have to recheck their images source every frame.
        // We cannot find it's real position from the image so we update it all every time.
        updateEntities();

        sortGroup(visibleTiles);
        sortGroup(standingGroup);
    }

    /**
     * Adds a new item to be displayed. This creates a new image view calculates a
     * position and adds it to the proper array. This assumes that all tiles and only
     * tiles are drawn from the top left and all entities are at the bottom middle of
     * their feet. This method also displays any displayabls inside the given
     * displayable.
     */
    private void display(Displayable dis){
        double x = dis.getX();
        double y = dis.getY();
        double[] cords = shift(x, y);
        Image image = dis.getImage();
        ImageView i = new ImageView(image);

        if(dis.cordsAtCorner()){
            // this is a tile, part of the ground
            i.setX(cords[0]);
            i.setY(cords[1]);
            visibleTiles.getChildren().add(i);

            for(Plant p : ((Tile)dis).getPlants()){
                display(p);
            }
        }else{
            // this is something that stands up off the ground.
            i.setX(cords[0] - image.getWidth() / 2);
            i.setY(cords[1] - image.getHeight());
            standingGroup.getChildren().add(i);
        }
    }

    /**
     * Sorts a group's children list to the order they would display based on y level.
     */
    private void sortGroup(Group g){
        // Sort the standing group so that the highest up is drawn first and all lower things will go on top of that.
        double oldY;
        double oldX;
        double y = -1000;
        double x = -1000;
        boolean startNewCheck = true;
        for (int j = 0; j < g.getChildren().size(); j++) {
            Node n = g.getChildren().get(j);
            oldY = y;
            oldX = x;

            if (n instanceof ImageView) {
                ImageView i = (ImageView) n;
                y = i.getY();
                y += i.getImage().getHeight();
                x = i.getX();
            } else if (n instanceof Rectangle) {
                Rectangle r = (Rectangle) n;
                y = r.getY();
                y += r.getHeight();
                x = r.getX();
            }

            // First time through the oldx and oldy will be weird numbers.
            // The second they will be the numbers of the first time through.
            if (!startNewCheck) {
                // check if a higher up thing is being drawn after a lower
                if (y < oldY || (y == oldY && x < oldX)) {
                    // Remove this item and re-add it one space to the left
                    g.getChildren().remove(n);
                    g.getChildren().add(j - 1, n);
                    // Set the loop to continue from one before where this was added
                    // So we can check if it needs to go further back
                    if (j > 2) {// so we don't go out of bounds
                        j -= 3;
                        // This means the values are weirdy and need to be reset
                    }else{
                        j = 0;
                    }
                    startNewCheck = true;
                }
            }else{
                // only set to false if a check was skipped
                startNewCheck = false;
            }
        }
    }

    /**
     * Updates a variety of static class level variables that are used in the
     * calculations about updating the screen.
     */
    private void updateVariables(){
        oldScreenCenterTileX = screenCenterTileX;
        oldScreenCenterTileY = screenCenterTileY;
        oldScreenCenterY = screenCenterY;
        oldScreenCenterX = screenCenterX;
        findScreenCenter();
        // get_____() + Tile.getTileWidth() - 1 adds 1 pixel less than a whole tile so any
        // partial tile will get counted but if the screen is at an exact tile value it
        // will not add anything
        screenTilesWide = (int) ((getWidth() + Tile.getTileWidth() - 1) / Tile.getTileWidth() - 2);
        screenTilesTall = (int) ((getHeight() + Tile.getTileWidth() - 1) / Tile.getTileWidth() - 2);
        // + 0.5 will only increase the value if there is already a 0.5 from division
        // + 1 only really makes sense if you draw out the tiles and realize which would be at the center without it
        topLeftX = screenCenterTileX - (int)(screenTilesWide / 2.0 + 0.5) + 1;
        topLeftY = screenCenterTileY - (int)(screenTilesTall / 2.0 + 0.5) + 1;
        changeTileX = screenCenterTileX - oldScreenCenterTileX;
        changeTileY = screenCenterTileY - oldScreenCenterTileY;
    }

    /**
     * Clears and re-updates everything in all the groups. This should only be used
     * after teleport, startup, going into another dimension, down stairs, moving
     * multiple blocks at once, or any such action that would need more than one new
     * row of tiles shown.
     */
    private void updateAll() {
        visibleTiles.getChildren().clear();
        standingGroup.getChildren().clear();
        Tile[][] allTiles = Tile.getAllTiles();

        for (int i = topLeftY; i < topLeftY + screenTilesTall; i++) {
            for (int j = topLeftX; j < topLeftX + screenTilesWide; j++) {
                display(allTiles[i][j]);
            }
        }
    }

    /**
     * Adds all the entities to the standing group and an array list so they can be
     * sorted out easily.
     */
    private void addAllEntities(){
        double[] playerCoords = shift(player.getPosX(), player.getPosY());;
        //player.setTexture(new Image("/res/player(1).png"));
        ImageView playerImage = (new ImageView(player.getTexture()));
        playerImage.setX(playerCoords[0] - player.getWidth() / 2);
        playerImage.setY(playerCoords[1] - player.getHeight());
        standingGroup.getChildren().add(playerImage);
        entities.add(playerImage);
    }

    /**
     * Safely removes all the entities from the standing group and adds back the new versions.
     * Standing group will need to be sorted afterwords.
     */
    private void updateEntities(){
        for (int i = 0; i < entities.size(); i++) {
            standingGroup.getChildren().remove(entities.get(i));
        }
        entities.clear();
        addAllEntities();
    }

    /**
     * This method is an even more optimized method that only updates the newly visible
     * row after moving off the tile you were on before. The previously stood on tile
     * will be compared to the current to find out how to change the groups properly.
     */
    private void updateSome(){
        updateCords();

        if(changeTileX > 0){
            // right move means remove a layer on the left and add one to the right
            removeTileLayerSide(false);
            addTileLayerSide(true);

        }else if(changeTileX < 0){
            // left move means remove a layer on the right and add one to the left
            removeTileLayerSide(true);
            addTileLayerSide(false);
        }

        if(changeTileY > 0){
            // down move means remove a layer on the top and add one to the bottom
            removeTileLayerTopBot(true);
            addTileLayerTopBot(false);

        }else if(changeTileY < 0){
            // up move means remove a layer on the bottom and add one to the top
            removeTileLayerTopBot(false);
            addTileLayerTopBot(true);
        }

        updateEntities();
    }

    /**
     * This method removes a vertical layer on the far right or left out of visible tiles
     * and all their plants and entities from standing group. This method assumes that the groups have
     * already been sorted.
     *
     * This method assumes that only image views are in the children lists.
     */
    private void removeTileLayerSide(boolean rightSide){
        int startIndex = (screenTilesWide) * (screenTilesTall - 1);
        if(rightSide){
            startIndex += screenTilesWide - 1;
        }

        for (int i = startIndex; i >= 0; i -= screenTilesWide) {
            ImageView tile = (ImageView)visibleTiles.getChildren().get(i);
            double x = tile.getX();
            double y = tile.getY();
            double w = Tile.getTileWidth();

            for (int j = standingGroup.getChildren().size() - 1; j >= 0; j--) {
                ImageView stander = (ImageView) standingGroup.getChildren().get(j);
                double xx = stander.getX() + stander.getImage().getWidth() / 2;

                // y axis does not matter since
                if(xx >= x && xx < x + w){
                    standingGroup.getChildren().remove(j);
                }
            }


            visibleTiles.getChildren().remove(i);
        }
    }

    private void removeTileLayerTopBot(boolean topSide){
        int startIndex = screenTilesWide * (screenTilesTall - 1);
        if(topSide){
            // top left corner
            startIndex = 0;
        }

        // - 1 for 0 based indexing
        for (int i = startIndex + screenTilesWide - 1; i >= startIndex; i --) {
            ImageView tile = (ImageView)visibleTiles.getChildren().get(i);
            double x = tile.getX();
            double y = tile.getY();
            double w = Tile.getTileWidth();

            for (int j = standingGroup.getChildren().size() - 1; j >= 0; j--) {
                ImageView stander = (ImageView) standingGroup.getChildren().get(j);
                double yy = stander.getY() + stander.getImage().getHeight();

                // x axis does not matter since
                if(yy >= y && yy < y + w){
                    standingGroup.getChildren().remove(j);
                }
            }

            visibleTiles.getChildren().remove(i);
        }
    }

    /**
     * This method adds a vertical layer of tiles and their plants to the given side of
     * the screen.
     *
     * WARNING: This method breaks sorting.
     */
    private void addTileLayerSide(boolean rightSide){
        int j = 0;
        if(rightSide){
            j = screenTilesWide - 1;
        }

        for (int i = 0; i < screenTilesTall; i++) {
            display(Tile.getAllTiles()[topLeftY + i][topLeftX + j]);
            // it does not matter where it is placed in the children list since it will be sorted afterwords
        }
    }

    private void addTileLayerTopBot(boolean topSide){
        int j = screenTilesTall - 1;
        if(topSide){
            j = 0;
        }

        for (int i = 0; i < screenTilesWide; i++) {
            display(Tile.getAllTiles()[topLeftY + j][topLeftX + i]);
            // it does not matter where it is placed in the children list since it will be sorted afterwords
        }
    }

    private void updateCords() {

        double changeX = oldScreenCenterX - screenCenterX;
        double changeY = oldScreenCenterY - screenCenterY;

        for (Node n : visibleTiles.getChildren()){
            // visibleTiles will only ever contain a rectangle or and image view
            if (n instanceof ImageView) {
                ImageView i = (ImageView) n;
                i.setX(i.getX() + changeX);
                i.setY(i.getY() + changeY);
            } else {
                Rectangle r = (Rectangle) n;
                r.setX(r.getX() + changeX);
                r.setY(r.getY() + changeY);
            }
        }
        for (Node n : standingGroup.getChildren()){
            // visibleTiles will only ever contain a rectangle or and image view
            if (n instanceof ImageView) {
                ImageView i = (ImageView) n;
                i.setX(i.getX() + changeX);
                i.setY(i.getY() + changeY);
            } else {
                Rectangle r = (Rectangle) n;
                r.setX(r.getX() + changeX);
                r.setY(r.getY() + changeY);
            }
        }
    }

    /**
     * Shifts a set of coordinates from where they are in the game to where they would
     * appear on the pane if drawn.
     * <p>
     * Usage assumptions:
     * Screen center variable is up to date
     * (use: findScreenCenter();)
     * <p>
     * Explanation of the problem:
     * since the top left corner of the pane is 0, 0 you cannot draw everything where
     * it is in the game. For example if you were standing at 12,498, 3256 and your
     * window was 2000 by 2000 pixels 0, 0 on the pane would be 11,498, 2256 (half the
     * screen width up and to the left of your position). If things appeared at their
     * in game coordinates you would not see everything around you and you would always
     * see what is at 0, 0 to 2000, 2000.
     * <p>
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
    private double[] shift(double x, double y) {
        double halfWidth = getWidth() / 2.0;
        double halfHeight = getHeight() / 2.0;

        x = x - screenCenterX + halfWidth;
        y = y - screenCenterY + halfHeight;

        return new double[]{x, y};
    }

    /**
     * Updates the screenCenter variable which many calculations are based on. Most of
     * the time the screen's center is where the player is standing but there are times
     * when the player is by a world border that you do not want the center of the
     * screen and the players position to be the same.
     */
    private void findScreenCenter() {
        screenCenterX = Main.getPlayer().getPosX();
        screenCenterY = Main.getPlayer().getPosY();

        // truncating is ok since even if you are 99.999999% of the way through a block you are still on it
        screenCenterTileX = ((int)screenCenterX) / Tile.getTileWidth();
        screenCenterTileY = ((int)screenCenterY) / Tile.getTileWidth();
    }
}