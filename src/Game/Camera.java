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
    private int tileGap = 2;

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
    private Group visibleTiles = new Group();

    private Group standingGroup = new Group();
    private static Group GUIGroup = new Group();
    private ArrayList<Node> entities = new ArrayList<>();
    private final int numGroups = 30;

    private boolean updateAll = false;

    public Camera() {
        for (int i = 0; i < numGroups; i++) {
            Group g = new Group();
            standingGroup.getChildren().add(g);
        }
        mainGroup.getChildren().addAll(visibleTiles, standingGroup, GUIGroup);
        getChildren().add(mainGroup);
    }

    /**
     * Signals the next frame to update everything
     */
    public void doUpdateAll(){
        updateAll = true;
    }

    /**
     * Determines which update method should be called (updateSome or updateAll) and calls it.
     * Uses findScreenCenter in the process so the screen center variables will be updated too.
     */
    public void update() {

        updateVariables();

        boolean sameCenterBlock = oldScreenCenterTileX == screenCenterTileX && oldScreenCenterTileY == screenCenterTileY;
        boolean adjacentCenterBlock = (changeTileX <= 1 && changeTileX >= -1) || changeTileY <= 1 && changeTileY >= -1;
        boolean sameScreenSize = (oldScreenTall == this.getHeight()) && (oldScreenWide == this.getWidth());
        boolean sameScreenCenter = screenCenterX == oldScreenCenterX && screenCenterY == oldScreenCenterY;

        oldScreenWide = this.getWidth();
        oldScreenTall = this.getHeight();


        if (!sameScreenSize || updateAll) {
            updateAll = false;
            updateAll();
        } else{
            if (sameCenterBlock) {
                if (sameScreenCenter) {

                } else {
                    updateCords();
                    updateStandingGroupPositions();
                }
            } else if (adjacentCenterBlock) {
                updateSome();
            } else {
                updateAll();
            }
        }


        // Since entities move around we have to recheck their images source every frame.
        // We cannot find it's real position from the image so we update it all every time.
        removeEntities();
        addAllEntities();

        sortGroup(visibleTiles);
        for (int i = 0; i < standingGroup.getChildren().size(); i++) {
            sortGroup((Group)standingGroup.getChildren().get(i));
        }
    }

    /**
     * Adds a new item to be displayed. This creates a new image view calculates a
     * position and adds it to the proper array. This assumes that all tiles and only
     * tiles are drawn from the top left and all entities are at the bottom middle of
     * their feet. This method also displays any displayables inside the given
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

            double percentDown = (y - topLeftY * Tile.getTileWidth()) / (screenTilesTall * Tile.getTileWidth());
            double percentInterval = 1 / ((double)standingGroup.getChildren().size());

            for (int j = 1; j <= standingGroup.getChildren().size(); j++) {
                // the or case is in case of accuracy lost making it < 1 and something being above it and not enter a group
                if(percentDown < j * percentInterval || j == standingGroup.getChildren().size()){
                    addAtSortedPos((Group)standingGroup.getChildren().get(j - 1), cords[1], i);
                    break;
                }
            }
        }
    }

    /**
     * Add a node at it's proper position in a group.
     *
     * @param y must be the shifted position of the displayable not actual coordinates nor display coordinates.
     */
    private void addAtSortedPos(Group g, double y, Node n){
        for (int i = 0; i < g.getChildren().size(); i++) {
            ImageView iv = (ImageView)g.getChildren().get(i);
            double checkY = iv.getY() + iv.getImage().getHeight();

            if(checkY >= y){
                g.getChildren().add(i, n);
                // mission accomplished
                return;
            }
        }

        g.getChildren().add(n);
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
        int count = 0;
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

                    count++;

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

        // get_____() + Tile.getTileWidth() - 1 adds 1 pixel less than a whole tile so any
        // partial tile will get counted but if the screen is at an exact tile value it
        // will not add anything
        screenTilesWide = (int) ((getWidth() + Tile.getTileWidth() - 1) / Tile.getTileWidth() + tileGap);
        screenTilesTall = (int) ((getHeight() + Tile.getTileWidth() - 1) / Tile.getTileWidth() + tileGap);

        findScreenCenter();

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
        for (int i = 0; i < standingGroup.getChildren().size(); i++) {
            ((Group)standingGroup.getChildren().get(i)).getChildren().clear();
        }
        entities.clear();

        //System.out.println(topLeftX + " " + topLeftY);
        for (int i = topLeftY; i < topLeftY + screenTilesTall; i++) {
            for (int j = topLeftX; j < topLeftX + screenTilesWide; j++) {
                display(World.getTile(j, i));
            }
        }
    }

    /**
     * Adds all the entities to the standing group and an array list so they can be
     * sorted out easily.
     */
    private void addAllEntities(){
        double[] playerCoords = shift(player.getPosX(), player.getPosY());
        ImageView playerImage = (new ImageView(player.getTexture()));
        playerImage.setX(playerCoords[0] - player.getWidth() / 2);
        playerImage.setY(playerCoords[1] - player.getHeight());

        int index = standingGroup.getChildren().size() / 2;
        ((Group)standingGroup.getChildren().get(index)).getChildren().add(playerImage);
        entities.add(playerImage);
    }

    /**
     * Safely removes all the entities from the standing group and adds back the new versions.
     * Standing group will need to be sorted afterwords.
     */
    private void updateEntities(){
        removeEntities();
        addAllEntities();
    }

    private void removeEntities(){
        for (int i = standingGroup.getChildren().size() - 1; i >= 0; i--) {
            Group g = (Group)standingGroup.getChildren().get(i);
            for (int j = entities.size() - 1; j >= 0; j--) {
                if(g.getChildren().remove(entities.get(j))){
                    entities.remove(j);
                }
            }
        }
        entities.clear();
    }

    /**
     * This method is an even more optimized method that only updates the newly visible
     * row after moving off the tile you were on before. The previously stood on tile
     * will be compared to the current to find out how to change the groups properly.
     */
    private void updateSome(){
        updateCords();
        updateStandingGroupPositions();

        int oldX = topLeftX - changeTileX;
        int oldY = topLeftY - changeTileY;

        for (int i = visibleTiles.getChildren().size() - 1; i >= 0; i--) {
            // get the x and y of the current node
            int x = oldX + i % screenTilesWide; // extra tiles
            int y = oldY + i / screenTilesWide; // whole layers

            // determine if the node is outside the new screen
            boolean outOfBoundsY = y < topLeftY || y >= topLeftY + screenTilesTall;
            boolean outOfBoundsX = x < topLeftX || x >= topLeftX + screenTilesWide;
            if(outOfBoundsY || outOfBoundsX){
                // no longer visible
                visibleTiles.getChildren().remove(i);


                /* must be replaced
                    The new tile will appear opposite this one.
                    Say a downward movement is made that changes center tiles.
                    This is the first tile removed so it is the top right tile.
                    We want to add the bottom right tile.

                    To get the opposite tile we need to account for the shift in center
                    first so everything lines up nicely with the new screen
                 */

                x += changeTileX;
                y += changeTileY;

                /* Every tile can be thought of at its old position just shifted onto
                the new screen now
                The next step is to find the difference between the left side and x with leftx - x
                Now we can add the screen width and the left side again so
                we are at the right side - the how much bigger x was the -1 is for 0 based indexing
                */

                x = (screenTilesWide - 1) - x + 2 * topLeftX;
                y = (screenTilesTall - 1) - y + 2 * topLeftY;


                display(World.getTile(x, y));
            }
        }

        // All plants outside of the screen must now be removed
        double[] topLeft = shift(topLeftX * Tile.getTileWidth(), topLeftY * Tile.getTileWidth());
        double leftX = topLeft[0], rightX = leftX + screenTilesWide * Tile.getTileWidth();
        double topY = topLeft[1], bottomY = topY + screenTilesTall * Tile.getTileWidth();

        for (int j = 0; j < standingGroup.getChildren().size(); j++) {
            Group g = (Group)standingGroup.getChildren().get(j);
            for (int i = g.getChildren().size() - 1; i >= 0; i--) {
                ImageView stander = (ImageView) g.getChildren().get(i);
                double x = stander.getX() + stander.getImage().getWidth() / 2;
                double y = stander.getY() + stander.getImage().getHeight();

                boolean outOfBoundsY = y < topY || y >= bottomY;
                boolean outOfBoundsX = x < leftX || x >= rightX;
                if (outOfBoundsY || outOfBoundsX) {
                    g.getChildren().remove(i);
                }
            }
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

        for (int j = 0; j < standingGroup.getChildren().size(); j++) {
            Group g = (Group) standingGroup.getChildren().get(j);
            for (Node n : g.getChildren()) {
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
    }

    /**
     * Checks to see if every stander is still in the right group and if not moves it.
     */
    private void updateStandingGroupPositions(){
        for (int j = 0; j < standingGroup.getChildren().size(); j++) {
            Group g = (Group) standingGroup.getChildren().get(j);
            for (int l = 0; l < g.getChildren().size(); l++) {
                Node n = g.getChildren().get(l);
                double y = 0;
                if (n instanceof ImageView) {
                    ImageView i = (ImageView) n;
                    y = i.getY();
                    y += i.getImage().getHeight();
                } else if (n instanceof Rectangle) {
                    Rectangle r = (Rectangle) n;
                    y = r.getY();
                    y += r.getHeight();
                }

                double percentDown = ((y + screenCenterY - getHeight() / 2.0)
                        - topLeftY * Tile.getTileWidth()) / (screenTilesTall * Tile.getTileWidth());
                double percentInterval = 1 / ((double)standingGroup.getChildren().size());

                for (int k = 1; k <= standingGroup.getChildren().size(); k++) {
                    // find the group the node should be in
                    if(percentDown < k * percentInterval || k == standingGroup.getChildren().size()){
                        // check if it is the same as the group it is already in
                        if(k != j){

                            // Check for index out of bounds
                            if(k >= standingGroup.getChildren().size()){
                                k = standingGroup.getChildren().size() - 1;
                            }else if(k < 0){
                                k = 0;
                            }
                            // check if an index out of bounds put it back to the same group
                            if(k == j){
                                // no need to move it out of it's positonto the back of the list just to be sorted back here
                                break;
                            }

                            g.getChildren().remove(n);
                            l--; // so we don't skip the next node which has just been moved to this slot
                            ((Group)standingGroup.getChildren().get(k)).getChildren().add(n);
                        }
                        //((Group)standingGroup.getChildren().get(j - 1)).getChildren().add(i);
                        break;
                    }
                }
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
        double px = Main.getPlayer().getPosX(), py = Main.getPlayer().getPosY();
        double halfWidth = screenTilesWide * Tile.getTileWidth() / 2.0,
                halfHeight = screenTilesTall * Tile.getTileWidth() / 2.0;

        if(px - halfWidth < 0){
            screenCenterX = halfWidth;

            topLeftX = 0;
        }else if(px + halfWidth > World.getWorldChunkWidth() * Chunk.getTileSize() * Tile.getTileWidth()){
            screenCenterX = World.getWorldChunkWidth() * Chunk.getTileSize() * Tile.getTileWidth() - halfWidth;

            topLeftX = World.getWorldChunkWidth() * Chunk.getTileSize()  - screenTilesWide;
        }else{
            screenCenterX = px;

            // needs to be done earlier
            screenCenterTileX = ((int)screenCenterX) / Tile.getTileWidth();

            // + 0.5 will only increase the value if there is already a 0.5 from division
            // + 1 only really makes sense if you draw out the tiles and realize which would be at the center without it
            topLeftX = screenCenterTileX - (int)((double)screenTilesWide / 2.0 + 0.5) + 1;
        }

        if(py - halfHeight < 0){
            screenCenterY = halfHeight;

            topLeftY = 0;
        }else if(py + halfHeight > World.getWorldChunkHeight() * Chunk.getTileSize() * Tile.getTileWidth()){
            screenCenterY = World.getWorldChunkHeight() * Chunk.getTileSize() * Tile.getTileWidth() - halfHeight;

            topLeftY = World.getWorldChunkHeight() * Chunk.getTileSize() - screenTilesTall;
        }else{
            screenCenterY = py;

            // needs to be done earlier
            screenCenterTileY = ((int)screenCenterY) / Tile.getTileWidth();

            // + 0.5 will only increase the value if there is already a 0.5 from division
            // + 1 only really makes sense if you draw out the tiles and realize which would be at the center without it
            topLeftY = screenCenterTileY - (int)((double)screenTilesTall / 2.0 + 0.5) + 1;
        }



        // truncating is ok since even if you are 99.999999% of the way through a block you are still on it
        screenCenterTileX = ((int)screenCenterX) / Tile.getTileWidth();
        screenCenterTileY = ((int)screenCenterY) / Tile.getTileWidth();
    }

    public void drawGUI(){

    }

    public static void setGUIGroup(Group Group) {
        GUIGroup.getChildren().clear();

        for (int i = Group.getChildren().size() - 1; i >= 0; i--) {
            GUIGroup.getChildren().add(Group.getChildren().get(i));
        }
    }

    public double getScreenCenterY() {
        return screenCenterY;
    }

    public double getScreenCenterX() {
        return screenCenterX;
    }
}