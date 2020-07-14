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

import java.sql.SQLOutput;
import java.util.HashSet;

public class Player extends Entity {

    Tile[][] visibleTiles = new Tile[10][10];
    private static int speed = 5;
    private static boolean moving;

    private static boolean w;
    private static boolean a;
    private static boolean s;
    private static boolean d;

    private Vector direction = new Vector(0, 0);
    private Vector velocity = new Vector(1, 1);
    private Vector up = new Vector(0, -1);
    private Vector down = new Vector(0, 1);
    private Vector left = new Vector(-1, 0);
    private Vector right = new Vector(1, 0);
    private Vec2d acceleration;


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

        System.out.println(direction.x + ", " + direction.y);

        velocity.x = direction.x * speed;
        velocity.y = direction.y * speed;

        this.addToPositionX(velocity.x);
        this.addToPositionY(velocity.y);

        handleKeyPresses();

        if(!moving){
            //velocity.x = 0;
            //velocity.y = 0;
            //direction.x = 0;
            //direction.y = 0;
        }
    }

    public void handleKeyPresses(){
        //this.posX += this.xSpeed;
        //this.posY += this.ySpeed;

        //this.currentAnimation = this.walkDown;

        if (currentlyActiveKeys.contains("A")) {
            a = true;
            this.direction = left;
            //this.currentAnimation = this.walkDown;

            //this.xSpeed = speed * -1;
            //this.velocity.x = speed * -1;
            //this.posX += this.xSpeed;
            this.tileX = (int)this.posX / Tile.getWidth();
            moving = true;
        } else {
            a = false;
            this.walkLeft.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("D")) {
            d = true;
            this.direction = right;
                    //this.setCurrentAnimation(walkRight);

            //this.xSpeed = speed;
            //this.velocity.x = speed;
            //this.posX += this.xSpeed;

            moving = true;
        } else {
            d = false;
            this.walkRight.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("W")) {
            w = true;
            this.direction = up;
                    //this.currentAnimation = walkDown;

            //this.ySpeed = speed * -1;
            //this.velocity.y = speed * -1;
            //this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            w = false;
            this.walkUp.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }

        if (currentlyActiveKeys.contains("S")) {
            s = true;
            this.direction = down;
                    //this.setCurrentAnimation(this.walkDown);

            //this.ySpeed = speed;
            //this.velocity.y = speed;
            //this.posY += this.ySpeed;
            this.tileY = (int)this.posY / Tile.getWidth();
            moving = true;
        } else {
            s = false;
            this.walkDown.resetCount();
            //this.setCurrentAnimation(this.idleAnimation);
        }


        setCurrentAnimation();

        // Binding for going in and out of fullscreen
        if(currentlyActiveKeys.contains("F11")) {
            if (!hasChangedFullscreen) {
                Main.switchFullscreen();
            }
            hasChangedFullscreen = true;
        }

        if(!a && !d){
            this.xSpeed = 0;
        }

        if(!w && !s){
            this.ySpeed = 0;
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

    public void setTexture(Image image){
        texture = image;
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
        // walkUp[0] = new Image("res/player1.png");
        // walkLeft[0] = new Image("res/player1.png");

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

            if(this.xSpeed < 0){
                this.setCurrentAnimation(this.walkLeft);
                System.out.println("l");
            }
            if(this.ySpeed < 0){
                this.setCurrentAnimation(this.walkUp);
                System.out.println("u");
            }
            if(this.ySpeed > 0){
                this.setCurrentAnimation(this.walkDown);
                System.out.println("d");
            }
            if(this.xSpeed > 0){
                this.setCurrentAnimation(this.walkRight);
                System.out.println("r");
            }
        }
    }
}
