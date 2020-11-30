package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * An ice tile has no interesting things about it.
 */
public class IceTile extends Tile {
    public static final String tileCode = "i";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public IceTile(int posX, int posY) {
        super(posX, posY, Color.LIGHTBLUE);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public PixelReader getPixelReader() {
        return res.Resources.getIceTileReader();
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
