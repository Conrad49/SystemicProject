package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * A water tile has no interesting things about it.
 */
public class WaterTile extends Tile {

    double movementSpeed;
    //static Image texture;
    static boolean isTextured = true;

    public WaterTile(int posX, int posY) {
        super(posX, posY, Color.BLUE);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }

    public Image getTexture() {
        return texture;
    }

}
