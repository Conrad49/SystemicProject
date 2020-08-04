package Game.Tiles;

import Game.displayablesHidingPlace.Displayable;
import Game.plants.Plant;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.ArrayList;


public abstract class Tile extends Displayable implements Serializable {

    static int width = 16 * 4;

    //Color backColor;
    static boolean isTextured = false;
    boolean isInteractiveTile;
    private static int mapWidth;
    private static int mapHeight;
    private static Tile[][] allTiles;    // TODO: fill array with Tiles and calculate size of array
    private static int xCount = 0;
    private static int yCount = 0;
    String url;
    boolean isSolid;

    protected ArrayList<Plant> plants = new ArrayList<>(); // Plants growing on this tile.

    public Tile(int posX, int posY, Color backColor){
        super(posX, posY, width, width, true);
        //this.backColor = backColor;

        this.isTextured = false;


        allTiles[posX / width][posY / width] = this;
    }

    public Tile(int posX, int posY){
        super(posX, posY, width, width, true);

        this.isTextured = true;

        //this.boundsBox.setFill(backColor);

        allTiles[xCount][yCount] = this;
        xCount ++;
        if(xCount >= allTiles.length){
            yCount++;
            xCount = 0;
        }
    }

    public Rectangle getBoundsBox() {
        return new Rectangle(this.x, this.y, width, width);
    }

    public void boundingLines(){         /* TODO: either use rectangle object for ray intersection or four lines
                                            TODO: that represent the rectangle */

    }

    public static Tile[][] getAllTiles() {
        return allTiles;
    }

    public Image getTexture() {
        return new Image(this.url);
    }


    public static int getTileWidth() {
        return width;
    }

    //public Color getBackColor() {
    //    return backColor;
    //}

    public boolean isTextured() {
        return isTextured;
    }

    public boolean isInteractiveTile() {
        return isInteractiveTile;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public static void setMapDimensions(int width, int height){
        mapWidth = width;
        mapHeight = height;
        allTiles = new Tile[mapWidth][mapHeight];
    }

    public static int getMapWidth() {
        return mapWidth;
    }

    public static int getMapHeight() {
        return mapHeight;
    }

    public void setTexture(String url) {
        this.url = url;
        isTextured = true;
    }

    /**
     * adds a plant to the tiles list of plants growing on it if the plant can grow on it
     */
    public void addPlant(Plant plant){
        if(plant.canGrowOn(getTileCode())){
            plants.add(plant);
        }
    }

    /**
     * Every tile needs a string code to be printed onto a file. It should be stored
     * in a public static final string and this should return that string.
     */
    abstract String getTileCode();

    /**
     * simply tells all the plants on this tile to grow
     */
    protected void tickPlants(){
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).tick();
        }
    }

    /**
     * By default this method only tells plants to be ticked but can be overridden
     * in the subclass for something like a furnace or fire or who knows what that
     * needs to do more than just tick its plants.
     */
    public void tick(){
        tickPlants();
    }

    /**
     * Returns an arraylist of all the coordinates of each plant on this tile.
     */
    public ArrayList<Plant> getPlants(){
        return plants;
    }

    /**
     * Removes the specified plant
     */
    public void removePlant (Plant p){
        plants.remove(p);
    }
}
