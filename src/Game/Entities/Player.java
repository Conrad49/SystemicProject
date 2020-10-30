package Game.Entities;

import Game.Animation;
import Game.Chunk;
import handlers.player.PlayerCollisionHandler;
import handlers.player.PlayerMovementHandler;
import Game.testing.Vector;
import handlers.player.PlayerAnimationHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Player extends Entity {

    public static int speed = 0;
    public static int maxSpeed = 5;
    public static int accelVal = 1;
    public static boolean moving;

    private Vector acceleration;

    public boolean hasChangedFullscreen;

    public Player(Color backColor, javafx.scene.shape.Rectangle boundsBox) {
        super(backColor, boundsBox);
        setTexture();
        this.setMovementHandler(new PlayerMovementHandler(this));
        this.setAnimationHandler(new PlayerAnimationHandler(this));
        this.setCollisionHandler(new PlayerCollisionHandler(this));
    }

    public Player(Image texture, javafx.scene.shape.Rectangle boundsBox) {
        super(texture, boundsBox);
        setTexture();
    }

    public Animation walkDown = new Animation();
    public Animation walkUp = new Animation();
    public Animation walkLeft = new Animation();
    public Animation walkRight = new Animation();
    public Animation idleAnimation = new Animation();

    @Override
    public void tick() {
        super.tick();

        if(!moving){
            speed = 0;
            this.getVelocity().setX(0);
            this.getVelocity().setY(0);
        }
    }

    // Sets the variable that checks if the fullscreen has been changed
    public void setHasChangedFullscreen(boolean hasChangedFullscreen) {
        this.hasChangedFullscreen = hasChangedFullscreen;
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

    public void setMoving(boolean moving) {
        Player.moving = moving;
    }
}
