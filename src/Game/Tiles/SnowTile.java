package Game.Tiles;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class SnowTile extends Tile {
    public static final String tileCode = "o";




    public SnowTile(int posX, int posY) {
        super(posX, posY, Color.WHITE);

    }

    public Image getImage() {
        return new Image(this.url);
    }

    @Override
    public PixelReader getPixelReader() {
        return res.Resources.getSnowTileReader();
    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
