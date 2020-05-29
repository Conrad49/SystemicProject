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
    private Animation walkUp = new Animation();
    private Animation walkLeft = new Animation();
    private Animation walkRight = new Animation();
    private Animation idleAnimation = new Animation();

    @Override
    public void tick() {
        super.tick();
        moving = false;

        //this.currentAnimation = this.walkDown;

        if (currentlyActiveKeys.contains("A")) {
            //this.currentAnimation = this.walkDown;

            this.xSpeed = speed * -1;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.getWidth();
            moving = true;
        } else {
            this.walkLeft.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("D")) {
            this.setCurrentAnimation(walkRight);

            this.xSpeed = speed;
            this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.getWidth();
            moving = true;
        } else {
            this.walkRight.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("W")) {
            //this.currentAnimation = walkDown;

            this.ySpeed = speed * -1;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            this.walkUp.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("S")) {
            this.setCurrentAnimation(this.walkDown);

            this.ySpeed = speed;
            this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            this.walkDown.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }


        // Binding for going in and out of fullscreen
        if(currentlyActiveKeys.contains("F11")) {
            if (!hasChangedFullscreen) {
                Main.switchFullscreen();
            }
            hasChangedFullscreen = true;
        }

        System.out.println(moving);
        if(!moving){
            this.setCurrentAnimation(this.idleAnimation);
        }

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

    public void getNumOfChars(HashSet<String> currentlyActiveKeys){

    }

    public void setTexture(Image image){
        texture = image;
        Image[] walkDown = new Image[6];
        Image[] walkRight = new Image[8];
        Image[] idle = new Image[1];
       /* walkDown[0] = image;
        walkDown[1] = new Image("/res/player.png");
        walkDown[2] = new Image("/res/player(1).png");
        walkDown[3] = new Image("/res/player(1).png");
        walkDown[4] = new Image("/res/player(1).png");*/

        for(int i = 0; i < walkDown.length; i ++){
            String url = "/res/player" + (i + 1) + ".png";
            walkDown[i] = new Image(url);
        }

        for (int j = 0; j < walkRight.length; j++) {
            String url = "/res/playerR" + (j+1) + ".png";
            walkRight[j] = new Image(url);
        }

        idle[0] = new Image("res/player1.png");

        this.idleAnimation.setImages(idle);

        this.walkDown.setImages(walkDown);
        this.walkRight.setImages(walkRight);
    }

    public Image getTexture(){
        Animation currentAnimation = this.getCurrentAnimation();
        return currentAnimation.getImage();
        //return texture;
    }
}
