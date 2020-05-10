import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashSet;

public class Player extends Entity {

    Tile[][] visibleTiles = new Tile[10][10];
    private static int speed = 5;

    private static HashSet<String> currentlyActiveKeys = new HashSet<String>(1);


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

        if (currentlyActiveKeys.contains("A")) {
            this.xSpeed = speed * -1;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.width;
        }

        if (currentlyActiveKeys.contains("D")) {
            this.xSpeed = speed;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.width;
        }

        if (currentlyActiveKeys.contains("W")) {
            this.ySpeed = speed * -1;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.width;
        }

        if (currentlyActiveKeys.contains("S")) {
            this.ySpeed = speed;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.width;
        }
    }

    public Chunk[] getSurroundingChunks(){

        Chunk[] surroundingChunks = new Chunk[9];

        return surroundingChunks;
    }

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }
}
