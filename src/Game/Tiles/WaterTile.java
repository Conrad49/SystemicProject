package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A water tile has no interesting things about it.
 */
public class WaterTile extends Tile {
    public static final String tileCode = "w";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public WaterTile(int posX, int posY) {
        super(posX, posY, Color.BLUE);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
