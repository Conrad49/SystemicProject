package Game.Entities;

import Game.Animation;
import Game.Chunk;
import Game.Main;
import Game.Tiles.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.sql.SQLOutput;
import java.util.HashSet;

public class Player extends Entity {

    Tile[][] visibleTiles = new Tile[10][10];
    private static int speed = 5;
    private static boolean moving;

    private static HashSet<String> currentlyActiveKeys = new HashSet<String>(1);
    private boolean hasChangedFullscreen;

    static Image texture;
    public Player(Color backColor, javafx.scene.shape.Rectangle boundsBox) {
        super(backColor, boundsBox);
    }

    public Player(Image texture, javafx.scene.shape.Rectangle boundsBox) {
        super(texture, boundsBox);
    }

    public Animation walkDown = new Animation();
    private Animation walkUp;
    private Animation walkLeft;
    private Animation walkRight;

    private Animation currentAnimation;

    @Override
    public void tick() {
        super.tick();
        moving = false;

        this.currentAnimation = this.walkDown;

        if (currentlyActiveKeys.contains("A")) {
            this.currentAnimation = this.walkDown;

            this.xSpeed = speed * -1;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.getWidth();
            moving = true;
        } else {
            this.walkDown.resetCount();
        }

        if (currentlyActiveKeys.contains("D")) {
            this.currentAnimation = walkDown;

            this.xSpeed = speed;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.getWidth();
            moving = true;
        } else {
            this.walkDown.resetCount();
        }

        if (currentlyActiveKeys.contains("W")) {
            this.currentAnimation = walkDown;

            this.ySpeed = speed * -1;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            this.walkDown.resetCount();
        }

        if (currentlyActiveKeys.contains("S")) {
            this.currentAnimation = walkDown;

            this.ySpeed = speed;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            this.walkDown.resetCount();
        }


        // Binding for going in and out of fullscreen
        if(currentlyActiveKeys.contains("F11")) {
            if (!hasChangedFullscreen) {
                Main.switchFullscreen();
            }
            hasChangedFullscreen = true;
        }

        System.out.println(moving);

    }

    // Sets the variable that checks if the fullscreen has been changed
    public void setHasChangedFullscreen(boolean hasChangedFullscreen) {
        this.hasChangedFullscreen = hasChangedFullscreen;
    }

    public Chunk[] getSurroundingChunks(){

        Chunk[] surroundingChunks = new Chunk[9];

        return surroundingChunks;
    }

    public static HashSet<String> getCurrentlyActiveKeys() {
        return currentlyActiveKeys;
    }

    public void setTexture(Image image){
        texture = image;
        Image[] walkDown = new Image[2];
        walkDown[0] = image;
        walkDown[1] = new Image("/res/player.png");
        this.walkDown.setImages(walkDown);
    }

    public Image getTexture(){
        return currentAnimation.getImage();
        //return texture;
    }
}
