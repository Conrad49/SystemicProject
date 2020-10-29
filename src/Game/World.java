package Game;

import Game.Tiles.Tile;
import Game.testing.NoiseBiomeGen;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * @author Benjamin Vick
 */
public class World {
    public static final int worldChunkWidth = 3, worldChunkHeight = 3;
    private static Chunk[][] loadedChunks = new Chunk[worldChunkWidth][worldChunkWidth];
    public static Point[][] loadedChunkCoords = new Point[worldChunkWidth][worldChunkWidth];
    public static Point[][] currentChunkCoords = new Point[worldChunkWidth][worldChunkWidth];
    static boolean test = true;

    public static Tile getTile(int x, int y){
        return loadedChunks[x / Chunk.getTileSize()][y / Chunk.getTileSize()].
                getTiles()[x % Chunk.getTileSize()][y % Chunk.getTileSize()];
    }

    public static void tick(){

        // Test for how fast it would be to load only one row of chunks

        if((Main.getPlayer().getTileX() % Chunk.getTileSize() == 0 || Main.getPlayer().getTileY() % Chunk.getTileSize() == 0)){
            System.out.println("Chunk change");
            /*for (int i = 0; i < 1; i++) {
                for (int j = 0; j < World.getWorldChunkWidth(); j++) {
                    loadedChunks[j][i] = Chunk.getChunk((Main.getPlayer().getChunkCoords().x - 1) + j, (Main.getPlayer().getChunkCoords().y - 1) + i);
                }

            }
            test = false;*/
        }

        //System.out.println(Main.getPlayer().getChunkCoords().x + ", " + Main.getPlayer().getChunkCoords().y);
        for (int i = 0; i < World.getWorldChunkWidth(); i++) {
            for (int j = 0; j < World.getWorldChunkWidth(); j++) {

                loadedChunkCoords[j][i] = new Point((Main.getPlayer().getChunkCoords().x - 1) + j, (Main.getPlayer().getChunkCoords().y - 1) + i);
                currentChunkCoords[j][i] = new Point(loadedChunks[j][i].getX(), loadedChunks[j][i].getY());

                //System.out.println("X: " + ((Main.getPlayer().getChunkCoords().x - 1) + j) + ", Y: " + ((Main.getPlayer().getChunkCoords().y - 1) + i));
            }
        }

        for (int i = 0; i < World.getWorldChunkWidth(); i++) {
            for (int j = 0; j < World.getWorldChunkWidth(); j++) {
                if(arrayContainsPoint(loadedChunkCoords[j][i]) != null){
                    int[] index = arrayContainsPoint(loadedChunkCoords[j][i]);
                    loadedChunks[j][i] = loadedChunks[index[0]][index[1]];
                }
            }
        }

        //System.out.println(Main.getPlayer().getChunkCoords().x + ", " + Main.getPlayer().getChunkCoords().y);

        for (int j = 0; j < loadedChunks[0].length; j++) {
            for (Chunk[] loadedChunk : loadedChunks) {
                loadedChunk[j].tick();
            }
        }

        setChunkFileNames();
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

    public static void setChunkFileNames(){
        Point playerChunkCoords = Main.getPlayer().getChunkCoords();

        boolean up = chunkFileExists(playerChunkCoords.x, playerChunkCoords.y - 2);
        boolean down = chunkFileExists(playerChunkCoords.x, playerChunkCoords.y + 2);
        boolean left = chunkFileExists(playerChunkCoords.x - 2, playerChunkCoords.y);
        boolean right = chunkFileExists(playerChunkCoords.x + 2, playerChunkCoords.y);

        int topLeftX = loadedChunks[0][0].getX() - 1;
        int topLeftY = loadedChunks[0][0].getY() - 1;

        // loop around perimeter of loaded chunks instead?
        //for(int i = 0; i < loadedChunks.length + 2; i ++){
        //    NoiseBiomeGen.generateChunk(topLeftX + loadedChunks.length + 1, topLeftY + i);
        //}

        if(!up){
            for(int i = 0; i < loadedChunks.length + 2; i ++){
                if (!World.chunkFileExists(topLeftX + i, topLeftY)) {
                    NoiseBiomeGen.generateChunk(topLeftX + i, topLeftY);
                }
            }
        }

        if(!down){
            for(int i = 0; i < loadedChunks.length + 2; i ++){
                if (!World.chunkFileExists(topLeftX + i, topLeftY + loadedChunks.length + 1)) {
                    NoiseBiomeGen.generateChunk(topLeftX + i, topLeftY + loadedChunks.length + 1);
                }
            }
        }

        if(!left){
            for(int i = 0; i < loadedChunks.length + 2; i ++){
                if (!World.chunkFileExists(topLeftX, topLeftY + i)) {
                    NoiseBiomeGen.generateChunk(topLeftX, topLeftY + i);
                }
            }
        }

        if(!right){
            for(int i = 0; i < loadedChunks.length + 2; i ++){
                if (!World.chunkFileExists(topLeftX + loadedChunks.length + 1, topLeftY + i)) {
                    NoiseBiomeGen.generateChunk(topLeftX + loadedChunks.length + 1, topLeftY + i);
                }
            }
        }

    }

    public static boolean chunkFileExists(int x, int y){

        File file = new File("D:\\AppData\\Intellij Projects\\SystemicProject\\chunk " + x + ", " + y);
        return file.exists();

    }

    public static int[] arrayContainsPoint(Point point){
        for (int i = 0; i < World.getWorldChunkWidth(); i++) {
            for (int j = 0; j < World.getWorldChunkWidth(); j++) {
                if(currentChunkCoords[j][i].x == point.x && currentChunkCoords[j][i].y == point.y){
                    int[] coords = new int[2];
                    coords[0] = j;
                    coords[1] = i;
                    return coords;
                }
            }
        }
        return null;
    }
}
