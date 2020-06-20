package Game.Tiles;

import javafx.scene.image.Image;

public class SnowTile extends Tile {
    public static final String tileCode = "o";

    static Image texture;


    public SnowTile(int posX, int posY) {
        super(posX, posY);

    }

    @Override
    public String getTileCode() {
        return tileCode;
    }
}
