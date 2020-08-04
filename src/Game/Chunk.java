package Game;

import Game.Entities.Entity;
import Game.Tiles.Tile;
import Game.plants.Plant;

import java.io.*;
import java.util.ArrayList;

public class Chunk implements Serializable{
    private static final int size = 10;
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
    public static Chunk loadChunk(int x, int y) {
        File chunk = new File("chunk " + x + ", " + y);
        try {
            FileInputStream fos = new FileInputStream(chunk);
            ObjectInputStream oos = new ObjectInputStream(fos);

            return (Chunk)oos.readObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
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
