package res;

import Game.Tiles.GrassTile;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 * A place for all the static final things that cannot be serialized on their class.
 */
public class Resources {
    // anything with only one image
    private static final PixelReader
            dirtTileReader = new Image("/res/DirtTile.png").getPixelReader(),
            grassTileReader = new Image("/res/GrassTile.png").getPixelReader(),
            iceTileReader = new Image("/res/IceTile.png").getPixelReader(),
            lavaTileReader = new Image("/res/LavaTile.png").getPixelReader(),
            sandTileReader = new Image("/res/SandTile.png").getPixelReader(),
            snowTileReader = new Image("/res/SnowTile.png").getPixelReader(),
            stoneTileReader = new Image("/res/StoneTile.png").getPixelReader(),
            waterTileReader = new Image("/res/WaterTile.png").getPixelReader();

    // anything with multiple possible images
    private static final PixelReader[]
            SingleTallGrass = new PixelReader[6],
            TallGrass = new PixelReader[6];

    public static void initialize(){
        for (int i = 1; i <= 5; i++) {
            SingleTallGrass[i] = new Image("/res/plants/TallGrass/SingleTallGrass" + i + ".png").getPixelReader();
        }

        for (int i = 1; i <= 5; i++) {
            TallGrass[i] = new Image("/res/plants/TallGrass/TallGrass" + i + ".png").getPixelReader();
        }
    }

    public static PixelReader getTallGrass(int i) {
        return TallGrass[i];
    }

    public static PixelReader getSingleTallGrass(int i) {
        return SingleTallGrass[i];
    }

    public static PixelReader getDirtTileReader() {
        return dirtTileReader;
    }

    public static PixelReader getGrassTileReader() {
        return grassTileReader;
    }

    public static PixelReader getIceTileReader() {
        return iceTileReader;
    }

    public static PixelReader getLavaTileReader() {
        return lavaTileReader;
    }

    public static PixelReader getSandTileReader() {
        return sandTileReader;
    }

    public static PixelReader getSnowTileReader() {
        return snowTileReader;
    }

    public static PixelReader getStoneTileReader() {
        return stoneTileReader;
    }

    public static PixelReader getWaterTileReader() {
        return waterTileReader;
    }
}
