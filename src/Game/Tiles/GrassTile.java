package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A grass tile has no interesting things about it.
 */
public class GrassTile extends Tile {
    public static final String tileCode = "g";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public GrassTile(int posX, int posY) {
        super(posX, posY, Color.GREEN);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getImage() {
        return texture;
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
