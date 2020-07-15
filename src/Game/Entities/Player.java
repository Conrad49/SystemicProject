package Game.Entities;

import Game.Animation;
import Game.Chunk;
import Game.Main;
import Game.Tiles.Tile;
import Game.testing.Vector;
import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec2f;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashSet;

public class Player extends Entity {

    Tile[][] visibleTiles = new Tile[10][10];
    private static int speed = 5;
    private static int maxSpeed = 5;
    private static boolean moving;


    private Vector direction = new Vector(0, 0);
    private Vector velocity = new Vector(1, 1);
    private Vector up = new Vector(0, -1);
    private Vector down = new Vector(0, 1);
    private Vector left = new Vector(-1, 0);
    private Vector right = new Vector(1, 0);
    private Vec2d acceleration;


    private static HashSet<String> currentlyActiveKeys = new HashSet<String>(1);
    private boolean hasChangedFullscreen;

    public Player(Color backColor, javafx.scene.shape.Rectangle boundsBox) {
        super(backColor, boundsBox);
        setTexture();
    }

    public Player(Image texture, javafx.scene.shape.Rectangle boundsBox) {
        super(texture, boundsBox);
        setTexture();
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

        //direction.normalize();

        double magnitude = Math.sqrt(Math.pow(direction.x, 2) + Math.pow(direction.y, 2));
        System.out.println(magnitude);

        if (magnitude != 0) {
            direction.x = (direction.x / magnitude);
            direction.y = (direction.y / magnitude);
        }


        velocity.x = direction.x * speed;
        velocity.y = direction.y * speed;

        this.addToPositionX(velocity.x);
        this.addToPositionY(velocity.y);

        handleKeyPresses();

        if(!moving){
            speed = 0;
        }
    }

    public void handleKeyPresses(){
        direction = new Vector(0, 0);
        boolean isMoving = currentlyActiveKeys.contains("A") || currentlyActiveKeys.contains("D") || currentlyActiveKeys.contains("W") || currentlyActiveKeys.contains("S");

        if (isMoving) {
            moving = true;
            speed = maxSpeed;

            setDirection();
        }


        if (!currentlyActiveKeys.contains("A")) {
            this.walkLeft.resetCount();
        }

        if (!currentlyActiveKeys.contains("D")) {
            this.walkRight.resetCount();
        }

        if (!currentlyActiveKeys.contains("W")) {
            this.walkUp.resetCount();
        }

        if (!currentlyActiveKeys.contains("S")) {
            this.walkDown.resetCount();
        }


        setCurrentAnimation();

        // Binding for going in and out of fullscreen
        if(currentlyActiveKeys.contains("F11")) {
            if (!hasChangedFullscreen) {
                Main.switchFullscreen();
            }
            hasChangedFullscreen = true;
        }


        if(!moving){
            this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("Z")) {
            direction.x ++;
        }

        if (currentlyActiveKeys.contains("X")) {
            direction.y ++;
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

    public void setTexture(){
        Image[] walkDown = new Image[6];
        Image[] walkRight = new Image[8];
        Image[] idle = new Image[1];
        Image[] walkUp = new Image[6];
        Image[] walkLeft = new Image[8];

        for(int i = 0; i < walkDown.length; i ++){
            String url = "/res/player" + (i + 1) + ".png";
            walkDown[i] = new Image(url);
        }

        for(int i = 0; i < walkUp.length; i ++){
            String url = "/res/playerU" + (i + 1) + ".png";
            walkUp[i] = new Image(url);
        }

        for(int i = 0; i < walkLeft.length; i ++){
            String url = "/res/playerL" + (i + 1) + ".png";
            walkLeft[i] = new Image(url);
        }

        for (int j = 0; j < walkRight.length; j++) {
            String url = "/res/playerR" + (j+1) + ".png";
            walkRight[j] = new Image(url);
        }

        idle[0] = new Image("res/player1.png");

        this.idleAnimation.setImages(idle);

        this.walkUp.setImages(walkUp);
        this.walkLeft.setImages(walkLeft);
        this.walkDown.setImages(walkDown);
        this.walkRight.setImages(walkRight);
    }

    public Image getTexture(){
        Animation currentAnimation = this.getCurrentAnimation();
        return currentAnimation.getImage();
    }

    public void setCurrentAnimation(){
        if(moving){

            if(this.direction.x < 0){
                this.setCurrentAnimation(this.walkLeft);
            }
            if(this.direction.y < 0){
                this.setCurrentAnimation(this.walkUp);
            }
            if(this.direction.y > 0){
                this.setCurrentAnimation(this.walkDown);
            }
            if(this.direction.x > 0){
                this.setCurrentAnimation(this.walkRight);
            }
        }
    }

    public void setDirection(){

        if (currentlyActiveKeys.contains("A")) {
            this.direction.add(left);
        } else if (currentlyActiveKeys.contains("D")) {
            this.direction.add(right);
        }

        if (currentlyActiveKeys.contains("W")) {
            this.direction.add(up);
        } else if (currentlyActiveKeys.contains("S")) {
            this.direction.add(down);
        }
    }
}
