package Game;

import Game.Entities.Entity;
import Game.Tiles.Tile;
import Game.plants.Plant;

import java.io.*;
import java.util.ArrayList;

public class Chunk implements Serializable{
    private static final int size = 100;
    ArrayList<Entity> entities;
    Tile[][] tiles;
    ArrayList<Plant> plants;
    public static int sideLength;
    private int x;
    private int y;

    private static Chunk[][] allChunks = new Chunk[Tile.getMapWidth() / size]
                                          [Tile.getMapHeight() / size];

    /**
     * Loads the specified chunk from memory.
     */
    public static void loadChunk(int x, int y) {
        File chunk = new File("chunk " + x + ", " + y);
        try {
            FileInputStream fis = new FileInputStream(chunk);
            ObjectInputStream ois = new ObjectInputStream(fis);

            allChunks[x][y] = (Chunk)ois.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Tile.getAllTiles()[x * size + j][y * size + i] = allChunks[x][y].tiles[j][i];
            }
        }
    }

    /**
     * Makes a new chunk
     */
    public Chunk(ArrayList<Entity> entities, Tile[][] tiles, ArrayList<Plant> plants, int x, int y) {
        this.entities = entities;
        this.tiles = tiles;
        this.plants = plants;
        this.x = x;
        this.y = y;

        allChunks[x][y] = this;
        save();
    }

    public void tick (){
        // tick all things that need to be ticked
        // include ticking tiles eventually


        for(Entity entity : entities){
            entity.tick();
        }
    }

    /**
     * Writes this chunk to a file
     */
    public void save(){
        File chunk = new File("chunk " + x + ", " + y);
        try {
            FileOutputStream fos = new FileOutputStream(chunk);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static int getSize() {
        return size;
    }

    public static Chunk[][] getAllChunks() {
        return allChunks;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
