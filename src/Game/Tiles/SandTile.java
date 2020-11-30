package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * A sand tile has no interesting things about it.
 */
public class SandTile extends Tile {
    public static final String tileCode = "a";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public SandTile(int posX, int posY) {
        super(posX, posY, Color.YELLOW);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public PixelReader getPixelReader() {
        return res.Resources.getSandTileReader();
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
