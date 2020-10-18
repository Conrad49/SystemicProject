package Game;

import Game.Tiles.Tile;

/**
 * @author Benjamin Vick
 */
public class World {
    public static final int worldChunkWidth = 3, worldChunkHeight = 3;// in chunks
    private static Chunk[][] loadedChunks = new Chunk[worldChunkWidth][worldChunkHeight];

    public static Tile getTile(int x, int y){
        return loadedChunks[x / Chunk.getSize()][y / Chunk.getSize()].
                getTiles()[x % Chunk.getSize()][y % Chunk.getSize()];
    }

    public static void tick(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

            }
        }

        for (int j = 0; j < loadedChunks[0].length; j++) {
            for (int i = 0; i < loadedChunks.length; i++) {
                loadedChunks[i][j].tick();
            }
        }
    }

    public static void setChunk(int x, int y, Chunk c){
        loadedChunks[x][y] = c;
    }

    public static int getWorldChunkWidth() {
        return worldChunkWidth;
    }

    public static int getWorldChunkHeight() {
        return worldChunkHeight;
    }
}
