package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * Stone is a solid tile that you cannot move past.
 */
public class StoneTile extends Tile {
    public static final String tileCode = "s";

    double movementSpeed;
    //static Image texture;

    public StoneTile(int posX, int posY) {
        super(posX, posY, Color.GRAY);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
        super.isSolid = true;
    }


    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public PixelReader getPixelReader() {
        return res.Resources.getStoneTileReader();
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
