
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class Tile {

    static int width = 16 * 4;

    Color backColor;
    int posX;
    int posY;
    boolean isTextured = false;
    boolean isInteractiveTile;
    private static Tile[][] allTiles = new Tile[999][999];    // TODO: fill array with Tiles and calculate size of array
    private static int xCount = 0;
    private static int yCount = 0;
    Rectangle boundsBox;
    private Image texture;
    boolean isSolid;

    public Tile(int posX, int posY, Color backColor){
        this.posX = posX;
        this.posY = posY;

        this.backColor = backColor;

        this.isTextured = false;

        this.boundsBox = new Rectangle(this.posX, this.posY, width, width);
        //this.boundsBox.setFill(backColor);

        allTiles[xCount][yCount] = this;
        xCount ++;
        if(xCount >= allTiles.length){
            yCount++;
            xCount = 0;
        }
    }

    public Tile(int posX, int posY){
        this.posX = posX;
        this.posY = posY;

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

    public Image getTexture() {
        return this.texture;
    }

    public void setTexture(Image image){
        this.texture = image;
        isTextured = true;
    }
}
