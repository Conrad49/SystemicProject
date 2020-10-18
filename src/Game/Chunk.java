package Game;

import Game.Entities.Entity;
import Game.Tiles.Tile;
import Game.plants.Plant;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Chunk implements Serializable{
    private static final int size = 100;// size of chunk in tiles
    ArrayList<Entity> entities;
    Tile[][] tiles;
    ArrayList<Plant> plants;
    private int x;// coordinates on the chunk level of the world
    private int y;



    /**
     * Loads the specified chunk from memory.
     */
    public static void loadChunk(int x, int y) {
        File chunk = new File("chunk " + x + ", " + y);
        try {
            FileInputStream fis = new FileInputStream(chunk);
            ObjectInputStream ois = new ObjectInputStream(fis);

            World.setChunk(x, y, (Chunk)ois.readObject());
        }catch(Exception e){
            e.printStackTrace();
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

        World.setChunk(x, y, this);
        save();
    }

    public void tick (){
        // tick all things that need to be ticked
        // include ticking tiles eventually


        for(Entity entity : entities){
            entity.tick();
        }

        if(Main.numOfFrames % 60 == 0) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            for (Plant plant : plants) {
                if (rand.nextInt(6000) == 0) {
                    Main.getRoot().doUpdateAll();
                    plant.tick();
                }
            }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }
}
