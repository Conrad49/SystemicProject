import java.util.ArrayList;

public class Chunk {

    ArrayList<Entity> entities;
    ArrayList<Tile> tiles;
    static int sideLength;
    int tileX;
    int tileY;

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
