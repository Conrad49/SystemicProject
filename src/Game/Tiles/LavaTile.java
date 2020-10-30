package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A lava tile has no interesting things about it.
 */
public class LavaTile extends Tile {
    public static final String tileCode = "l";

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public LavaTile(int posX, int posY) {
        super(posX, posY, Color.ORANGE);
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
