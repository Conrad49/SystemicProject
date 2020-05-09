import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GrassTile extends Tile {

    double movementSpeed;
    static Image texture;

    public GrassTile(int posX, int posY, Color backColor) {
        super(posX, posY, backColor);
        this.movementSpeed = 5;
        this.isInteractiveTile = true;
    }


    public void setTexture(Image image){
        texture = image;
        this.isTextured = true;
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
