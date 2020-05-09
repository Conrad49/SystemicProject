import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player extends Entity {

    Tile[][] visibleTiles = new Tile[10][10];


    public Player(Color backColor, javafx.scene.shape.Rectangle boundsBox) {
        super(backColor, boundsBox);
    }

    public Player(Image texture, javafx.scene.shape.Rectangle boundsBox) {
        super(texture, boundsBox);
    }

    @Override
    public void render(GraphicsContext g) {
        super.render(g);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
