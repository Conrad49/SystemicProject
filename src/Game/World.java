package Game;

import Game.Tiles.Tile;

/**
 * @author Benjamin Vick
 */
public class World {
    public static final int worldWidth = 10, worldHeight = 10;// in chunks
    private static Chunk[][] loadedChunks = new Chunk[worldWidth][worldHeight];

    public static Tile getTile(double x, double y){
        return loadedChunks[(int)x / Chunk.getSize()][(int)y / Chunk.getSize()].
                getTiles()[(int)x % Chunk.getSize()][(int)y % Chunk.getSize()];
    }

    public static Chunk setChunk(int x, int y, Chunk c){
        return loadedChunks[x][y] = c;
    }

    public static int getWorldWidth() {
        return worldWidth;
    }

    public static int getWorldHeight() {
        return worldHeight;
    }
}
