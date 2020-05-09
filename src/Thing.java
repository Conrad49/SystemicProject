import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class Thing {

    static ArrayList<Thing> allThings = new ArrayList<Thing>(1);

    public Thing(){
        allThings.add(this);
    }

    public void tick(){

    }

    public void render(GraphicsContext g){

    }

}
