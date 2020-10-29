package Game;

import Game.Entities.Entity;
import Game.Tiles.Tile;
import java.util.ArrayList;

public class Chunk {
    ArrayList<Entity> entities;
    ArrayList<Tile> tiles;
    public static int sideLength;
    int tileX;
    int tileY;
    boolean firstEntering;

    public Chunk() {

    }

    public void tick (){
        // tick all things that need to be ticked
        // include ticking tiles eventually


        for(Entity entity : entities){
            entity.tick();
        }

    }

}
