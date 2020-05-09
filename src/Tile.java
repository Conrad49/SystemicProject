
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class Tile {

    static int width = 20;

    Image texture;
    Color backColor;
    int posX;
    int posY;
    boolean isTextured;
    boolean isInteractiveTile;
    private static Tile[][] allTiles = new Tile[999][999];    // TODO: fill array with Tiles and calculate size of array
    private static int xCount = 0;
    private static int yCount = 0;
    Rectangle boundsBox;

    public Tile(int posX, int posY, Color backColor){
        this.posX = posX;
        this.posY = posY;

        this.backColor = backColor;

        this.isTextured = false;

        this.boundsBox = new Rectangle(this.posX, this.posY, 10, 10);   // TODO: DECIDE WIDTH VARIABLE!

        allTiles[xCount][yCount] = this;
        xCount ++;
    }

    public Tile(int posX, int posY, Image texture){
        this.posX = posX;
        this.posY = posY;

        this.texture = texture;

        this.isTextured = true;
    }

    public Rectangle getBoundsBox() {
        return boundsBox;
    }

    public void boundingLines(){         /* TODO: either use rectangle object for ray intersection or four lines
                                            TODO: that represent the rectangle */

    }

    public static Tile[][] getAllTiles() {
        return allTiles;
    }
}
